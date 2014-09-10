package com.company.commands;

import com.company.Command;
import com.company.UI;
import com.company.security.PasswordHolder;
import com.company.structures.Database;
import com.company.structures.DatabaseControl;
import com.company.structures.Exceptions.InvalidPasswordException;
import com.company.structures.Exceptions.NoSuchItemException;

/**
 * Created by jetbrains on 3/18/14.
 */
public class DelCommand extends Command {

    private static final String description= " del          delete some record.  passman.jar del <link>";

    public DelCommand() {
        super(CommandType.DEL, description);
    }

    @Override
    public void execute(DatabaseControl databaseControl, String[] args, PasswordHolder passwordHolder) throws CommandException, InvalidPasswordException, NoSuchItemException{

        if (args.length < 2) {
            throw new CommandException(CommandType.DEL, "Not enough arguments.");
        }


        databaseControl.delItem(args[1], passwordHolder.getPassword());

    }
}
