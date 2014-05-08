package com.company.tests;

import com.company.CommandFactory;
import com.company.commands.CommandType;
import org.junit.Test;

import static org.junit.Assert.fail;

/**
 * Created by jetbrains on 3/18/14.
 */
public class CommandFactoryTestAdd {
    @Test
    public void testAddCommand() throws Exception {
        CommandFactory cf1 = new CommandFactory();

        String argstest[] = {"-add","link","user","pass"};
        if ((cf1.buildCommand(argstest).getType() != CommandType.ADD)) {
            fail("AddTest: CommandType is different");
        }
    }




}
