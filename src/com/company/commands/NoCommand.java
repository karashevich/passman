package com.company.commands;

import com.company.Command;
import com.company.structures.DataPassClass;

/**
 * Created by jetbrains on 3/18/14.
 */
public class NoCommand extends Command {

    private static final String description= "There is no command with such name";


    public NoCommand() {
        super(CommandType.NOCOMMAND, description);
    }

    @Override
    public void execute(DataPassClass dpc, String[] args) {
        System.out.println(getDescription());
    }
}
