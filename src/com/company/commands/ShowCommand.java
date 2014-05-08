package com.company.commands;

import com.company.Command;
import com.company.structures.DataPassClass;

/**
 * Created by jetbrains on 3/18/14.
 */
public class ShowCommand extends Command {

    private static final String description = "-show         show some record.    passman.jar -show <link>";

    public ShowCommand() {
        super(CommandType.SHOW);
    }

    @Override
    protected void execute(DataPassClass dpc, String[] args) {
        System.out.println(dpc.getPC(args[1]));
    }
}
