package com.company.commands;

import com.company.Command;
import com.company.structures.DataPassClass;

/**
 * Created by jetbrains on 3/18/14.
 */
public class ShowAllCommand extends Command {

    private static final String description = "-showall      show all records.    passman.jar -showall";

    public ShowAllCommand() {
        super(CommandType.SHOWALL);
    }

    @Override
    protected void execute(DataPassClass dpc, String[] args) {
        System.out.println(dpc);
    }
}
