package com.company.security;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Formatter;

/**
 * Created by jetbrains on 5/7/14.
 */
public class Hasher {

    public static byte[] encryptPassword(byte[] password) {

        byte[] sha1 = null;
        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(password);
            sha1 = crypt.digest();
        }
        catch(NoSuchAlgorithmException e)
        {
            throw new RuntimeException(e);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return sha1;
    }

    public static byte[] encryptPassword(Password password) {

        byte[] sha1 = null;
        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(password.getPassword());
            sha1 = crypt.digest();
        }
        catch(NoSuchAlgorithmException e)
        {
            throw new RuntimeException(e);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return sha1;
    }

//    protected static String byteToHex(final byte[] hash)
//    {
//        Formatter formatter = new Formatter();
//
//        for (byte b : hash)
//        {
//            formatter.format("%02x", b);
//        }
//
//        String result = formatter.toString();
//        formatter.close();
//        return result;
//    }

    public static void main(String[] args) {
        String s1 = "12345678";
        String s2 = "12345679";

        byte[] b1 = s1.getBytes();
        byte[] b2 = s2.getBytes();

        System.out.println("s1 hash:" + Arrays.toString(Hasher.encryptPassword(b1)));
        System.out.println("s2 hash:" + Arrays.toString(Hasher.encryptPassword(b2)));
    }

}
