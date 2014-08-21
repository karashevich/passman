package com.company.security;

import com.company.structures.Exceptions.InvalidPasswordException;
import com.company.structures.Item;
import sun.security.pkcs.EncodingException;

import java.util.Arrays;

/**
 * Created by jetbrains on 8/21/14.
 */
public class Encrypter{

    public static String encrypt(String s, Password password) throws InvalidPasswordException{

        try{

            DesEncrypter de = new DesEncrypter(password);
            return de.encrypt(s);

        } catch (Exception e) {

            throw new InvalidPasswordException("Encryptor error with \"" + Arrays.toString(password.getPassword()) + "\" password!");
        }

    }

    public static String decrypt(String s, Password password) throws InvalidPasswordException{

        try{

            DesEncrypter de = new DesEncrypter(password);
            return de.decrypt(s);

        } catch (Exception e) {

            throw new InvalidPasswordException("Encryptor error with \"" + Arrays.toString(password.getPassword()) + "\" password!");
        }

    }

    public static Item encryptItem(Item item, Password password) throws InvalidPasswordException{

        return new Item(item.getLink(), item.getLogin(), encrypt(item.getPass(), password));

    }

    public static Item decryptItem(Item item, Password password) throws InvalidPasswordException{

        return new Item(item.getLink(), item.getLogin(), decrypt(item.getPass(), password));

    }

    private DesEncrypter de;

    public Encrypter(Password password) throws InvalidPasswordException{

        try{

            this.de = new DesEncrypter(password);

        } catch (Exception e) {

            throw new InvalidPasswordException("Encryptor error with \"" + Arrays.toString(password.getPassword()) + "\" password!");
        }

    }


    public String encrypt(String s) throws InvalidPasswordException{

        try{

            return this.de.encrypt(s);

        } catch (Exception e) {

            throw new InvalidPasswordException("Encryptor error!");
        }

    }

    public String decrypt(String s) throws InvalidPasswordException{

        try{

            return this.de.decrypt(s);

        } catch (Exception e) {

            throw new InvalidPasswordException("Encryptor error!");
        }

    }

}
