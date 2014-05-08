package com.company.security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

/**
 * Created by jetbrains on 5/7/14.
 */
public class Hasher {

    public static String encryptPassword(Password password) {

        String sha1 = "";
        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(password.getPassword().getBytes("UTF-8"));
            sha1 = byteToHex(crypt.digest());
        }
        catch(NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch(UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return sha1;
    }

    protected static String byteToHex(final byte[] hash)
    {
        Formatter formatter = new Formatter();

        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }

        String result = formatter.toString();
        formatter.close();
        return result;
    }
}
