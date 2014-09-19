package com.company.structures;

import com.company.structures.Exceptions.ItemWIthSuchKeyExists;
import com.company.structures.Exceptions.NoSuchItemException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

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
    public void addItem(Item item) throws ItemWIthSuchKeyExists {

        if (data.containsKey(item.getLink())) {

            throw new ItemWIthSuchKeyExists(item.getLink());

        }

        data.put(item.getLink(), copyItem(item));
    }

    @Override
    public void addItemForce(Item item){

        if (data.containsKey(item.getLink())) {
            data.remove(item.getLink());
        }

        data.put(item.getLink(), item);
    }

    @NotNull
    @Override
    public Item getItem(String s) throws NoSuchItemException{
        if (!data.containsKey(s)) throw new NoSuchItemException(s);
        return copyItem(data.get(s));
    }

    @NotNull
    @Override
    public Set<Item> getItems() {

        return Collections.unmodifiableSet(new HashSet<Item>(data.values()));

    }

    @Override
    public void delItem(String s) throws NoSuchItemException {
        if (!data.containsKey(s)) throw new NoSuchItemException(s);
        data.remove(s);
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

        return !(data != null ? !data.equals(databaseImpl.data) : databaseImpl.data != null);

    }


    @Override
    @Nullable public byte[] getPassHash() {
        return passHash;
    }

    @Override
    public void setPassHash(byte[] newPassHash) {

        isEncrypted = true;
        passHash = newPassHash;

    }

    private Item copyItem(Item item){
        return new Item(item.getLink(), item.getLogin(), item.getPass());
    }

}
