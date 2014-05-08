package com.company.tests;

import com.company.CommandFactory;
import com.company.commands.CommandType;
import org.junit.Test;

import static org.junit.Assert.fail;

/**
 * Created by jetbrains on 3/18/14.
 */
public class CommandFactoryTestShow {
    @Test
    public void testBuildCommand() throws Exception {
        CommandFactory cf1 = new CommandFactory();

        String argstest[] = {"-show", "link"};
        if ((cf1.buildCommand(argstest).getType() != CommandType.SHOW)) {
            fail("AddTest: CommandType is different");
        }
    }


}
