package com.company.structures;

import com.company.structures.Exceptions.NoSuchItemException;

import java.util.HashSet;

/**
 * Created by jetbrains on 8/18/14.
 */
public interface Database extends Iterable<Item>{

    boolean isEncrypted();

    void addItem(Item item);

    Item getItem(String s) throws NoSuchItemException;

    HashSet<Item> getItems();

    void delItem(String s) throws NoSuchItemException;

    int size();

    void setPassHash(byte[] passHash);

    byte[] getPassHash();

}
