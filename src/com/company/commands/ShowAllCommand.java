package com.company.commands;

import com.company.Command;
import com.company.UI;
import com.company.security.PasswordStorage;
import com.company.structures.Database;

/**
 * Created by jetbrains on 3/18/14.
 */
public class ShowAllCommand extends Command {

    private static final String description = "-showall      show all records.    passman.jar -showall";

    public ShowAllCommand() {
        super(CommandType.SHOWALL, description);
    }

    @Override
    public void execute(Database dpc, String[] args, PasswordStorage ps, UI ui) {
        System.out.println(dpc);
    }
}
