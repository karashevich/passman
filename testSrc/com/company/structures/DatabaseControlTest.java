package com.company.structures;

import com.company.security.Password;
import org.junit.Before;
import org.junit.Test;
import org.omg.DynamicAny._DynArrayStub;

import static org.junit.Assert.assertEquals;

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

        Item item1 = new Item("link", "user", "pass");
        databaseControl.addItem(item1, newPassword);

    }

    @Test
    public void testDelItem() throws Exception {

    }

    @Test
    public void testGetItems() throws Exception {

    }

    @Test
    public void testIsEncrypted() throws Exception {

    }
}
