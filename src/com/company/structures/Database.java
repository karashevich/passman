package com.company.structures;

import com.company.structures.Exceptions.ItemWIthSuchKeyExists;
import com.company.structures.Exceptions.NoSuchItemException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

/**
 * Created by jetbrains on 8/18/14.
 */
public interface Database {

    boolean isEncrypted();

    void addItem(Item item) throws ItemWIthSuchKeyExists;

    void addItemForce(Item item);

    @NotNull Item getItem(String s) throws NoSuchItemException;

    @NotNull
    Set<Item> getItems();

    void delItem(String s) throws NoSuchItemException;

    void setPassHash(byte[] passHash);

    @Nullable byte[] getPassHash();

}
