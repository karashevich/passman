package com.company.structures;

import com.company.UI;
import com.company.preferences.Preferences;
import com.company.security.*;
import com.sun.tools.doclets.internal.toolkit.util.SourceToHTMLConverter;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.mapper.CannotResolveClassException;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeMap;

/**
 * Created by jetbrains on 1/31/14.
 * @author Sergey.Karashevich
 */
public class DataPassClass implements DataPassInterface {


    private HashMap<String, PassClass> dataDPC; // dataDPC - main data holder based on tree map. key = link, value = PassClass
    private byte[] passHash = null;
    private boolean isEncrypted = false;
    private String pathfile = Preferences.getDataPath();

    @Override
    public boolean isEncrypted() {
        return isEncrypted;
    }

//    public DataPassClass() {
//
//        this.dataDPC = new HashMap<String, PassClass>();
//    }

    public DataPassClass() {

        try {

            this.dataDPC = loadFromFile().dataDPC;
            this.isEncrypted = loadFromFile().isEncrypted();
            this.passHash = loadFromFile().passHash;


        } catch (IOException e) {

            this.dataDPC = new HashMap<String, PassClass>();

        } catch (CannotResolveClassException crce) {

            this.dataDPC = new HashMap<String, PassClass>();
        }
    }



    /**
     *
     * @param pc - element to add to main data holder
     */
    @Override
    public void addPC(PassClass pc, PasswordStorage ps, UI ui){

        if (!isEncrypted()) {
            dataDPC.put(pc.getLink(), pc);
        } else {


            DesEncrypter des = null;

            try {

                des = new DesEncrypter(PasswordGetter.getPassword(ui, ps, passHash));
                PassClass encryptedpc = new PassClass(pc.getLink(), pc.getLogin(),  des.encrypt(pc.getPass()));
                dataDPC.put(encryptedpc.getLink(), encryptedpc);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    /**
     *
     * @param s - searched link
     * @return PassClass with respective link or null if there no PassClasses with such link
     */

    @Override
    public PassClass getPC(String s){
        return dataDPC.get(s);
    }


    @Override
    public @Nullable PassClass getPC(String s, @Nullable PasswordStorage ps, UI ui){

        if (!dataDPC.containsKey(s)) {
            return null;
        }

        if (isEncrypted) {
            try {

                Password pass = PasswordGetter.getPassword(ui, ps, passHash);
                if (pass == null) System.err.println("ACHTUNG!!!");

                DesEncrypter des = new DesEncrypter(pass);
                PassClass pc = new PassClass(dataDPC.get(s).getLink(), dataDPC.get(s).getLogin(),  des.decrypt(dataDPC.get(s).getPass()));

                return pc;

            } catch (Exception e) {
                e.printStackTrace();
                return dataDPC.get(s);
            }

        } else {

            return dataDPC.get(s);

        }


    }


    @Override
    public void delPC(String s){
        dataDPC.remove(s);
    }

    /**
     *
     * @throws IOException
     */
    public void saveToFile() throws IOException{

        XStream xstream = new XStream(new StaxDriver());
        String xml = xstream.toXML(this);

        //Saving XML to a new file
        FileWriter fw = new FileWriter(pathfile);
        try{
            fw.write(xml);

//            System.out.println("DataPassClass.saveToFile: Class is saved.");

        } catch (IOException e) {

            System.out.println("DataPassClass.saveToFile: IOException by saving file to " + pathfile);
            throw e;

        } finally {
            fw.close();
        }
    }

    /**
     *
     *
     * @throws IOException
     */
    private DataPassClass loadFromFile() throws IOException{

        XStream xstream = new XStream(new StaxDriver());

        BufferedReader br = new BufferedReader(new FileReader(pathfile));
        String everything;

        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append('\n');
                line = br.readLine();
            }
            everything = sb.toString();

//        System.out.println("DataPassClass.loadFromFile: Class is loaded.");

        } catch (IOException e) {

            System.out.println("DataPassClass.loadFromFile: Exception caused by reading file" + e.getMessage());
            throw e;

        } finally {
            br.close();
        }

        return ((DataPassClass) xstream.fromXML(everything));

    }

    @Override
    public int size(){
        return dataDPC.size();
    }

//    @Override
//    public String toString() {
//        StringBuilder sbd = new StringBuilder();
//
//        for(PassClass pc: dataDPC.values()){
//            sbd.append(pc.toString());
//            sbd.append(";");
//        }
//
//        return sbd.toString();
//    }


    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        for (PassClass passClass : dataDPC.values()) {
            sb.append("  Link: \"").append(passClass.getLink()).append("\" Login: \"").append(passClass.getLogin());
            if (isEncrypted) {
                sb.append("\" Password: ******");
            } else {
                sb.append("\" Password: \"").append(passClass.getPass()).append("\"");
            }
            sb.append("\n");
        }

        return "DataPassClass:\n" +
                "Entries:" + dataDPC.size() + "\n" +
                new String(sb);
    }

    @Override
    public int hashCode() {
        return dataDPC.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

         DataPassClass dataPassClass = (DataPassClass) o;

        if (dataDPC!= null ? !dataDPC.equals(dataPassClass.dataDPC) : dataPassClass.dataDPC != null) return false;

        return true;
    }

    @Override
    public void setPassword(PasswordStorage ps, UI ui){


        if (isEncrypted) {

            ui.print("Please, enter current password:");
            Password curPass = new Password(ui.readPassword());


            while(!ps.checkPassword(curPass)) {
                System.out.println("Entered password is incorrect, please try again!");
                curPass = new Password(ui.readPassword());
            }

            ui.print("Please, enter new password:");
            byte[] newPass = ui.readPassword();

            passHash = Hasher.encryptPassword(newPass);
            ps.setPassword(newPass);

            for (PassClass passClass : dataDPC.values()) {

                try {

                    DesEncrypter de = new DesEncrypter(newPass);
                    DesEncrypter deold = new DesEncrypter(curPass);

                    passClass.updatePass(de.encrypt(deold.decrypt(passClass.getPass())));


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        } else {


            ui.print("Data is not encrypted, please enter new password:");
            byte[] newPass = ui.readPassword();

            ps.setPassword(newPass);

            passHash = ps.getPasshash();
            isEncrypted = true;

            for (PassClass passClass : dataDPC.values()) {

                try {

                    DesEncrypter de = new DesEncrypter(newPass);
                    passClass.updatePass(de.encrypt(passClass.getPass()));

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


        }
    }

    @Override
    public byte[] getPassHash() {
        return passHash;
    }
}
