package com.company.commands;

import com.company.Command;
import com.company.UI;
import com.company.security.PasswordHolder;
import com.company.structures.Database;
import com.company.structures.DatabaseControl;
import com.company.structures.Exceptions.InvalidPasswordException;

/**
 * Created by jetbrains on 3/18/14.
 */
public class SetPassCommand extends Command {

    private static final String description = "setpass     add a new record.    passman.jar -setpass";


    public SetPassCommand() {
        super(CommandType.SETPASS, description);
    }

    @Override
    public void execute(DatabaseControl databaseControl, String[] args, PasswordHolder passwordHolder) throws InvalidPasswordException {

        try {
            databaseControl.setPassword(passwordHolder.getPassword(), passwordHolder.setNewPassword());
        } catch (InvalidPasswordException e) {
            System.out.println(e.getMessage());
        }

    }
}