package com.company.tests;

import com.company.console.CUI;
import com.company.security.PasswordStorage;
import com.company.structures.DataPassClass;
import com.company.structures.DataPassInterface;
import com.company.structures.Item;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

/**
 * Created by jetbrains on 3/18/14.
 */
public class DataItemTest {
    @Test
    public void testAddPC() throws Exception {
        Item pc = new Item("link1", "login1", "pass1");
        DataPassInterface dpcTest = new DataPassClass();
        PasswordStorage ps = null;
        CUI cui = null;

        try {
            dpcTest.addPC(pc, ps, cui);
            if (dpcTest.size() == 0) {
                fail("New entry haven't been added.");
            }
        } catch (Exception e) {
            fail(" Method addPC called exception.");
            e.printStackTrace();
        }

    }

    @Test
    public void testDelPc() throws Exception {
        Item pc = new Item("link1", "login1", "pass1");
        DataPassInterface dpcTest = new DataPassClass();
        PasswordStorage ps = null;
        CUI cui = null;

        dpcTest.addPC(pc, ps, cui);

        try {
            dpcTest.delPC("link1");
            if (dpcTest.size() != 0){
                fail("An Entry haven't been deleted.");
            }
        } catch (Exception e) {
            fail(" Method addPC called exception.");
            e.printStackTrace();
        }
    }

    @Test
    public void testGetPc() throws Exception {

        Item pc = new Item("link1", "login1", "pass1");
        DataPassInterface dpcTest = new DataPassClass();
        PasswordStorage ps = null;
        CUI cui = null;

        dpcTest.addPC(pc, ps, cui);
        try {
            Item result = dpcTest.getPC("link1");
            assertSame(result, pc);

        } catch (Exception e) {
            fail(" Method getPC called exception.");
            e.printStackTrace();
        }
    }

    @Test
    public void testSaveToFile() throws Exception {

        Item pc = new Item("link1", "login1", "pass1");
        DataPassClass dpcTest = new DataPassClass();
        PasswordStorage ps = null;
        CUI cui = null;


        try {
            dpcTest.addPC(pc, ps, cui);
            dpcTest.saveToFile();
        } catch (IOException e) {
            fail("Method saveToFile has raised exception.");
            e.printStackTrace();
        }

    }

}
