package com.company.structures;

import com.company.security.DesEncrypter;
import com.company.security.Hasher;
import com.company.security.Password;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.TreeMap;

/**
 * Created by jetbrains on 1/31/14.
 * @author Sergey.Karashevich
 */
public class DataPassClass {

    private TreeMap<String, PassClass> dataDPC; // dataDPC - main data holder based on tree map. key = link, value = PassClass
    private String passHash = "";
    private boolean isEncrypted = false;

    public DataPassClass() {
        this.dataDPC = new TreeMap<String, PassClass>();
    }

    /**
     *
     * @param pc - element to add to main data holder
     */
    public void addPC(PassClass pc){
        dataDPC.put(pc.getLink(), pc);
    }

    /**
     *
     * @param s - searched link
     * @return PassClass with respective link or null if there no PassClasses with such link
     */

    public PassClass getPC(String s){
        return dataDPC.get(s);
    }


    public void delPC(String s){
        dataDPC.remove(s);
    }

    /**
     *
     * @param pathFile - relative or absolute path for saving data
     * @throws IOException
     */
    public void saveToFile(String pathFile) throws IOException{

        XStream xstream = new XStream(new StaxDriver());
        String xml = xstream.toXML(this);

        //Saving XML to a new file
        FileWriter fw = new FileWriter(pathFile);
        try{
            fw.write(xml);

//            System.out.println("DataPassClass.saveToFile: Class is saved.");

        } catch (IOException e) {

            System.out.println("DataPassClass.saveToFile: IOException by saving file to " + pathFile);
            throw e;

        } finally {
            fw.close();
        }
    }

    /**
     *
     *
     * @param pathFile
     * @throws IOException
     */
    public static DataPassClass loadFromFile(String pathFile) throws IOException{

        XStream xstream = new XStream(new StaxDriver());

        BufferedReader br = new BufferedReader(new FileReader(pathFile));
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
        return "DataPassClass{" +
                "dataDPC=" + dataDPC +
                '}';
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

    public void setPassword(Password password, Password old_password){

        PassClass newPassClass;

        if (isEncrypted) {

            passHash = Hasher.encryptPassword(password);

            for (PassClass passClass : dataDPC.values()) {

                try {

                    DesEncrypter de = new DesEncrypter(password);
                    DesEncrypter deold = new DesEncrypter(old_password);

                    newPassClass = new PassClass(passClass.getLink(), passClass.getLogin(), de.encrypt(deold.decrypt(passClass.getPass())));

                    delPC(passClass.getLink());
                    addPC(newPassClass);


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        } else {

            passHash = Hasher.encryptPassword(password);
            isEncrypted = true;

            for (PassClass passClass : dataDPC.values()) {

                try {

                    DesEncrypter de = new DesEncrypter(password);
                    newPassClass = new PassClass(passClass.getLink(), passClass.getLogin(), de.encrypt(passClass.getPass()));

                    delPC(passClass.getLink());
                    addPC(newPassClass);


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

}
