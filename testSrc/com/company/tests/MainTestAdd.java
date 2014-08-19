package com.company.tests;

import com.company.Main;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

/**
 * Created by jetbrains on 3/19/14.
 */
public class MainTestAdd {
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

        String argumentsAdd[] = {"add", "link1", "user1", "pass1"};
        String argumentsShow[] = {"show", "link1"};

        Main.main(argumentsAdd);

        Main.main(argumentsShow);
        assertEquals("PassClass{link='link1', login='user1', pass='pass1'}\n", outContent.toString());

    }


}

