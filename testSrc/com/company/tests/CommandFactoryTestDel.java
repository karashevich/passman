package com.company.tests;

import com.company.CommandFactory;
import com.company.commands.CommandType;
import org.junit.Test;

import static org.junit.Assert.fail;

/**
 * Created by jetbrains on 3/18/14.
 */
public class CommandFactoryTestDel {
    @Test
    public void testBuildCommand() throws Exception {
        CommandFactory cf1 = new CommandFactory();

        String argstest[] = {"del","link"};
        if ((cf1.buildCommand(argstest).getType() != CommandType.DEL)) {
            fail("AddTest: CommandType is different");
        }
    }


}
