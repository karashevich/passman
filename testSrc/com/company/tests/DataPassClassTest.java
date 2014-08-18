package com.company.tests;

import com.company.console.CUI;
import com.company.security.PasswordStorage;
import com.company.structures.DataPassClass;
import com.company.structures.PassClass;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

/**
 * Created by jetbrains on 3/18/14.
 */
public class DataPassClassTest {
    @Test
    public void testAddPC() throws Exception {
        PassClass pc = new PassClass("link1", "login1", "pass1");
        DataPassClass dpcTest = new DataPassClass();
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
        PassClass pc = new PassClass("link1", "login1", "pass1");
        DataPassClass dpcTest = new DataPassClass();
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

        PassClass pc = new PassClass("link1", "login1", "pass1");
        DataPassClass dpcTest = new DataPassClass();
        PasswordStorage ps = null;
        CUI cui = null;

        dpcTest.addPC(pc, ps, cui);
        try {
            PassClass result = dpcTest.getPC("link1");
            assertSame(result, pc);

        } catch (Exception e) {
            fail(" Method getPC called exception.");
            e.printStackTrace();
        }
    }

    @Test
    public void testSaveToFile() throws Exception {

        PassClass pc = new PassClass("link1", "login1", "pass1");
        DataPassClass dpcTest = new DataPassClass();
        PasswordStorage ps = null;
        CUI cui = null;


        try {
            dpcTest.addPC(pc, ps, cui);
            dpcTest.saveToFile("data/testData.xml");
        } catch (IOException e) {
            fail("Method saveToFile has raised exception.");
            e.printStackTrace();
        }

    }

    @Test
    public void testLoadFromFile() throws Exception {

        PassClass pc = new PassClass("link1", "login1", "pass1");
        DataPassClass dpcTest = new DataPassClass();
        DataPassClass expected = dpcTest;

        String path = "data/testData2.xml";

        dpcTest.saveToFile(path);

        try {
            expected = DataPassClass.loadFromFile(path);
            assertSame(dpcTest, expected);
        } catch (IOException e) {
            fail("Method loadFromFile has raised exception.");
            e.printStackTrace();
        }

    }
}
