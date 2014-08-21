package com.company.structures;

import com.company.UI;
import com.company.security.Password;
import com.company.security.PasswordStorage;
import com.company.structures.Exceptions.InvalidPasswordException;
import com.company.structures.Exceptions.NoSuchItemException;
import org.jetbrains.annotations.Nullable;

/**
 * Created by jetbrains on 8/18/14.
 */
public interface Database extends Iterable<Item>{

    boolean isEncrypted();

    void addItem(Item item);

    Item getItem(String s) throws NoSuchItemException;

    void delItem(String s) throws NoSuchItemException;

    int size();

    void setPassHash(byte[] passHash);

    byte[] getPassHash();

}
