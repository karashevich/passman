package com.company.structures;

import com.company.UI;
import com.company.security.*;
import com.company.structures.Exceptions.NoSuchItemException;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.mapper.CannotResolveClassException;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by jetbrains on 1/31/14.
 * @author Sergey.Karashevich
 */
public class DatabaseImpl implements Database {


    private HashMap<String, Item> data; // data - main data holder based on tree map. key = link, value = Item
    private byte[] passHash = null;
    private boolean isEncrypted = false;

    @Override
    public boolean isEncrypted() {
        return isEncrypted;
    }

//    public DatabaseImpl() {
//
//        this.data = new HashMap<String, Item>();
//    }

    public DatabaseImpl() {

        this.data = new HashMap<String, Item>();

    }



    @Override
    public void addItem(Item item){

        data.put(item.getLink(), item);

    }

    @Override
    public Item getItem(String s){
        return data.get(s);
    }

    @Override
    public void delItem(String s) throws NoSuchItemException {
        if (!data.containsKey(s)) throw new NoSuchItemException(s);
        data.remove(s);
    }





    @Override
    public int size(){
        return data.size();
    }

//    @Override
//    public String toString() {
//        StringBuilder sbd = new StringBuilder();
//
//        for(Item pc: data.values()){
//            sbd.append(pc.toString());
//            sbd.append(";");
//        }
//
//        return sbd.toString();
//    }


    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        for (Item item : data.values()) {
            sb.append("  Link: \"").append(item.getLink()).append("\" Login: \"").append(item.getLogin());
            if (isEncrypted) {
                sb.append("\" Password: ******");
            } else {
                sb.append("\" Password: \"").append(item.getPass()).append("\"");
            }
            sb.append("\n");
        }

        return "DatabaseImpl:\n" +
                "Entries:" + data.size() + "\n" +
                new String(sb);
    }

    @Override
    public int hashCode() {
        return data.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

         DatabaseImpl databaseImpl = (DatabaseImpl) o;

        if (data != null ? !data.equals(databaseImpl.data) : databaseImpl.data != null) return false;

        return true;
    }


    @Override
    public byte[] getPassHash() {
        return passHash;
    }

    @Override
    public void setPassHash(byte[] newPassHash) {

        isEncrypted = true;
        passHash = newPassHash;

    }

    @Override
    public Iterator<Item> iterator() {
        return data.values().iterator();
    }
}
