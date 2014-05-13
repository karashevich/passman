package com.company.commands;

import com.company.Command;
import com.company.security.Password;
import com.company.structures.DataPassClass;

/**
 * Created by jetbrains on 3/18/14.
 */
public class ShowAllCommand extends Command {

    private static final String description = "-showall      show all records.    passman.jar -showall";

    public ShowAllCommand() {
        super(CommandType.SHOWALL, description);
    }

    @Override
    public void execute(DataPassClass dpc, String[] args, Password password) {
        System.out.println(dpc);
    }
}
