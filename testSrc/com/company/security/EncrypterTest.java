package com.company.security;

import com.company.structures.Item;
import org.junit.Before;
import org.junit.Test;
import org.omg.CosNaming._NamingContextExtStub;

import static org.junit.Assert.assertEquals;

/**
 * Created by jetbrains on 8/22/14.
 */
public class EncrypterTest {

    private final String passwordString = "12345678";
    private final Password password = new Password(passwordString.getBytes());
    private Encrypter encrypter;

    private final String testString = "Very important message!";

    @Before
    public void setUp() throws Exception {

        encrypter = new Encrypter(password);

    }

    @Test
    public void testEncrypt() throws Exception {
        encrypter.encrypt(testString);
    }

    @Test
    public void testDecrypt() throws Exception {

        encrypter.decrypt(encrypter.encrypt(testString));
    }

    @Test
    public void testEncryptDecrypt() throws Exception{
        String result = encrypter.encrypt(testString);
        assertEquals(encrypter.decrypt(result), testString);
    }

    @Test
    public void testEncryptItem() throws Exception {


        Item item = new Item("link", "login", "pass");
        Encrypter.encryptItem(item, password);

    }

    @Test
    public void testDecryptItem() throws Exception {

        Item item = new Item("link", "login", "pass");
        Encrypter.decryptItem(Encrypter.encryptItem(item, password), password);

    }

    @Test
    public void testEncryptDecryptItem() throws Exception{

        Item item = new Item("link", "login", "pass");

        Item encryptedItem = Encrypter.encryptItem(item, password);
        assertEquals(Encrypter.decryptItem(encryptedItem, password), item);

    }

    @Test
    public void testEncryptDecryptShort() throws Exception {

        String result = Encrypter.encrypt(testString, password);
        assertEquals(Encrypter.decrypt(result, password), testString);

    }

    @Test
    public void testDecryptShort() throws Exception {

        Encrypter.decrypt(Encrypter.encrypt(testString, password), password);

    }

    @Test
    public void testEncryptShort() throws Exception {

        Encrypter.encrypt(testString, password);

    }
}
