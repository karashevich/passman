package com.company.commands;

import com.company.Command;
import com.company.UI;
import com.company.security.PasswordHolder;
import com.company.structures.Database;
import com.company.structures.DatabaseControl;

/**
 * Created by jetbrains on 3/18/14.
 */
public class NoCommand extends Command {

    private static final String description= "There is no command with such name.";


    public NoCommand() {
        super(CommandType.NOCOMMAND, description);
    }

    @Override
    public void execute(DatabaseControl databaseControl, String[] args, PasswordHolder passwordHolder) {
        System.out.println(getDescription());
    }
}
