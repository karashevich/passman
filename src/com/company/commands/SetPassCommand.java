package com.company.commands;

import com.company.Command;
import com.company.UI;
import com.company.security.PasswordStorage;
import com.company.structures.Database;

/**
 * Created by jetbrains on 3/18/14.
 */
public class SetPassCommand extends Command {

    private static final String description = "-setpass     add a new record.    passman.jar -setpass";


    public SetPassCommand() {
        super(CommandType.SETPASS, description);
    }

    @Override
    public void execute(Database dpc, String[] args, PasswordStorage ps, UI ui) {

        try {
            dpc.setPassword(ps, ui);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}