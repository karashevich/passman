package com.company.tests;

import com.company.Main;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by jetbrains on 3/19/14.
 */
public class MainTestShowall {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
        System.setErr(null);
    }


    @Test
    public void testMain() throws Exception {

        String argumentsAdd1[] = {"-add", "link1", "user1", "pass1"};
        String argumentsAdd2[] = {"-add", "link2", "user2", "pass2"};
        String argumentsShowall[] = {"-showall"};

        Main.main(argumentsAdd1);
        Main.main(argumentsAdd2);

        Main.main(argumentsShowall);

        String expected = "DataPassClass{dataDPC={link1=Item{link='link1', login='user1', pass='pass1'}, link2=Item{link='link2', login='user2', pass='pass2'}}}\n";
        assertEquals(expected, outContent.toString());

    }


}

