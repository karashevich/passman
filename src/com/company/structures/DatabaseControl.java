package com.company.structures;

import com.company.security.Encrypter;
import com.company.security.Hasher;
import com.company.security.Password;
import com.company.structures.Exceptions.DatabaseLoadException;
import com.company.structures.Exceptions.InvalidPasswordException;
import com.company.structures.Exceptions.NoSuchItemException;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import org.jetbrains.annotations.Nullable;

import javax.xml.bind.NotIdentifiableEvent;
import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by jetbrains on 8/21/14.
 */
public class DatabaseControl {

    private Database database;

    public DatabaseControl() {

        this.database = new DatabaseImpl();

    }

    public DatabaseControl(Database database){
        this.database = database;
    }

    /**
     * TODO: ?? Is it ok, to change database on fly?
     * @param pathfile to database on HDD
     * @throws DatabaseLoadException
     */
    public void loadDatabase(String pathfile) throws DatabaseLoadException {

        XStream xstream = new XStream(new StaxDriver());

        try {
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

                try {
                    this.database = ((DatabaseImpl) xstream.fromXML(everything));
                } catch (ConversionException e) {
                    throw new ConversionException(e);
                }

                br.close();


            } catch (IOException e) {

                throw new DatabaseLoadException("Cannot read database file!");

            }

        } catch(FileNotFoundException fnfe) {

            throw new DatabaseLoadException("Cannot find database file!");

        }

    }

    public void saveToFile(String pathfile) throws IOException{

        XStream xstream = new XStream(new StaxDriver());
        String xml = xstream.toXML(database);

        //Saving XML to a new file
        FileWriter fw = new FileWriter(pathfile);
        try{
            fw.write(xml);

        } catch (IOException e) {

            System.out.println("DatabaseImpl.saveToFile: IOException by saving file to " + pathfile);
            throw e;

        } finally {
            fw.close();
        }
    }


    /**
     *
     * @param item - put this item to database
     * @param password - master password
     */
    public void addItem(Item item, Password password) throws InvalidPasswordException{

       if (isEncrypted()) if (passwordIsRight(password)) {
           database.addItem(Encrypter.encryptItem(item, password));
       } else {
           throw new InvalidPasswordException("Invalid password!");
       }

       else database.addItem(item);


    }

    /**
     *
     * @param link for corresponding Item
     * @param password - master password
     * @return Item with given link
     */
    public Item getItem(String link, Password password) throws InvalidPasswordException, NoSuchItemException{

        if (isEncrypted()){
            if (passwordIsRight(password)) {

                return Encrypter.decryptItem(database.getItem(link), password);

            } else {

                throw new InvalidPasswordException("Invalid password!");

            }

        } else {

            return database.getItem(link);

        }


    }

    /**
     * @param oldPassword - master password
     * @param newPassword - new master password
     */
    public void setPassword(@Nullable Password oldPassword, Password newPassword) throws InvalidPasswordException{

        if (isEncrypted()) {
            if (passwordIsRight(oldPassword)) {
                for (Item item : database)
                    item.updatePass(Encrypter.encrypt(Encrypter.decrypt(item.getPass(), oldPassword), newPassword));
                database.setPassHash(Hasher.encryptPassword(newPassword));
            } else throw new InvalidPasswordException("Old password is invalid!");
        }
        else {

            for (Item item : database) item.updatePass(Encrypter.encrypt(item.getPass(), newPassword));
            database.setPassHash(Hasher.encryptPassword(newPassword));

        }

    }

    @Nullable
    public byte[] getHash(){
        return database.getPassHash();
    }


    /**
     *
     * @param link - corresponding to searching Item
     * @param password - master password
     */
    public void delItem(String link, Password password) throws NoSuchItemException, InvalidPasswordException{

        if (database.isEncrypted()) {

            if (passwordIsRight(password)) database.delItem(link);
            else throw new InvalidPasswordException("Invalid password!");

        } else {
            database.delItem(link);
        }

    }


    /**
     *
     * @return  full set of items in database
     */
    public HashSet<Item> getItems() throws InvalidPasswordException{

        HashSet<Item> hashSet = new HashSet<Item>();

        for(Item iterItem: database)
            hashSet.add(new Item(iterItem.getLink(), iterItem.getLogin(), isEncrypted() ? "*******" : iterItem.getPass()));

        return hashSet;

    }

    /**
     *
     * @return encryption status of database: true if database is encrypted;
     */
    public boolean isEncrypted(){
        return database.isEncrypted();
    }


    /**
     *
     * Comparing hashes of given password and database hash.
     * @param password given master password
     * @return true if hashes are same.
     */
    private boolean passwordIsRight(@Nullable Password password) {
        return !isEncrypted() || Arrays.equals(Hasher.encryptPassword(password), database.getPassHash());
    }


}
