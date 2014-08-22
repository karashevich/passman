package com.company.structures;

import com.company.security.Password;
import com.company.structures.Exceptions.InvalidPasswordException;
import com.company.structures.Exceptions.NoSuchItemException;
import com.sun.tools.doclets.internal.toolkit.util.SourceToHTMLConverter;
import org.junit.Before;
import org.junit.Test;
import org.omg.DynamicAny._DynArrayStub;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 *
 * TODO: Add load test;
 * Created by jetbrains on 8/22/14.
 */
public class DatabaseControlTest {

    private final DatabaseControl databaseControl = new DatabaseControl();
    private final Password password = new Password("thisismypassword".getBytes());



    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testAddItem() throws Exception {

        Item item = new Item("link", "username", "password");
        databaseControl.addItem(item, password);

    }

    @Test
    public void testGetItem() throws Exception {

        String link = "link";

        Item item = new Item(link, "username", "password");
        databaseControl.addItem(item, password);
        assertEquals(databaseControl.getItem(link, password), item);

    }

    @Test
    public void testSetPassword() throws Exception {

        Password newPassword = new Password("NewPassword".getBytes());

        assertEquals(databaseControl.isEncrypted(), false);
        databaseControl.setPassword(null, password);
        assertEquals(databaseControl.isEncrypted(), true);

        Item item = new Item("link", "user", "pass");
        databaseControl.addItem(item, password);

        databaseControl.setPassword(password, newPassword);

        Item item1 = new Item("link1", "user1", "pass1");
        databaseControl.addItem(item1, newPassword);

        try {
            Item item2 = new Item("link2", "user2", "pass2");
            databaseControl.addItem(item2, password);
            fail("Didn't trigger InvalidPasswordException!");
        } catch (InvalidPasswordException ipe) {}

    }

    @Test
    public void testDelItem() throws Exception {

        String link = "link";
        Item item = new Item(link, "login", "pass");

        databaseControl.addItem(item, password);
        databaseControl.delItem(link, password);

        try{

            databaseControl.delItem(link, password);
            fail("Didn't trigger NoSuchItemException.");

        } catch (NoSuchItemException nsie) {}

        Password newPassword = new Password("NewPassword".getBytes());

        databaseControl.addItem(item, password);
        databaseControl.setPassword(password, newPassword);

        try {
            databaseControl.delItem(link, password);
            fail("Didn't trigger InvalidPasswordException!");
        } catch (InvalidPasswordException e) { }

    }

    @Test
    public void testGetItem2() throws Exception {

        String link = "link";
        final Item item = new Item(link, "login", "pass");

        //Compare items without password
        databaseControl.addItem(item, password);
        assertEquals(databaseControl.getItem(link, password), item);

        try{

            databaseControl.getItem("wronglink", password);
            fail("Didn't trigger NoSuchItemException.");

        } catch (NoSuchItemException nsie) {}

        //Crypted tests
        Password newPassword = new Password("NewPassword".getBytes());

        databaseControl.addItem(item, password);
        databaseControl.setPassword(password, newPassword);

        try {
            databaseControl.getItem(link, password);
            fail("Didn't trigger InvalidPasswordException!");
        } catch (InvalidPasswordException e) { }

        assertEquals(databaseControl.getItem(link, newPassword), item);
    }

    @Test
    public void testGetItems() throws Exception {

        Item item1 = new Item("link1", "user1", "pass1");
        Item item2 = new Item("link2", "user2", "pass2");

        databaseControl.addItem(item1, password);
        databaseControl.addItem(item2, password);

//        for (Item item: databaseControl.getItems()){
//            databaseControl.delItem(item.getLink(), password);
//        }

        databaseControl.getItems().remove(item1);
        databaseControl.getItems().remove(item2);

        assertEquals(2, databaseControl.getItems().size());

        for (Item item : databaseControl.getItems()) databaseControl.delItem(item.getLink(), password);

        assertEquals(0, databaseControl.getItems().size());
    }


    @Test
    public void testIsEncrypted() throws Exception {

        assertEquals(databaseControl.isEncrypted(), false);

        Password newPassword = new Password("NewPassword".getBytes());
        databaseControl.setPassword(password, newPassword);

        assertEquals(databaseControl.isEncrypted(), true);
    }
}
