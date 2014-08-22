package com.company.structures;

import com.company.structures.Exceptions.NoSuchItemException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by jetbrains on 8/21/14.
 */
public class DatabaseImplTest {

    private final DatabaseImpl database = new DatabaseImpl();

    @Before
    public void setUp() throws Exception {


    }

    @Test
    public void testIsEncrypted() throws Exception {

        assertEquals(database.isEncrypted(), false);

        byte[] passHash = "ch34c5gc32c".getBytes();
        database.setPassHash(passHash);

        assertEquals(database.isEncrypted(), true);

    }

    @Test
    public void testAddItem() throws Exception {

        Item testItem = new Item("link", "user", "pass");

        database.addItem(testItem);
        assertEquals(database.getItem("link"), testItem);

    }

    @Test
    public void testGetItem() throws Exception {

        Item testItem = new Item("link", "user", "pass");

        database.addItem(testItem);
        assertEquals(database.getItem("link"), testItem);

        try {
            database.getItem("noSuchLink");
            fail("There should be no item with such link, but it is.");
        } catch (NoSuchItemException nsie) {
            System.out.println("Ok!");
        }


    }

    @Test
    public void testDelItem() throws Exception {
        Item testItem = new Item("link", "user", "pass");

        database.addItem(testItem);
        database.delItem("link");

        try {
            database.delItem("link");
            fail("There should be no item with such link, but it is.");
        } catch (NoSuchItemException nsie) {
            System.out.println("Ok!");
        }
    }

}
