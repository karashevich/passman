package com.company.commands;

import com.company.Command;
import com.company.UI;
import com.company.security.PasswordHolder;
import com.company.structures.DatabaseControl;
import com.company.structures.Exceptions.InvalidPasswordException;
import com.company.structures.Exceptions.ItemWIthSuchKeyExists;
import com.company.structures.Item;

/**
 * Created by jetbrains on 3/18/14.
 */
public class AddCommand extends Command {

    private static final String description = "add        add a new record.    passman.jar -add <link> <login> <password>";

    private static Item readItem(String s0, String s1, String s2) {

        Item newPass;
        newPass = new Item(s0, s1, s2);

        return newPass;
    }

    public AddCommand() {
        super(CommandType.ADD, description);
    }

    @Override
    public void execute(DatabaseControl databaseControl, String[] args, PasswordHolder passwordHolder) throws CommandException, InvalidPasswordException, ItemWIthSuchKeyExists {

        if (args.length < 4) {
            throw new CommandException(CommandType.ADD, "Not enough arguments.");
        }

        databaseControl.addItem(readItem(args[1], args[2], args[3]), passwordHolder.getPassword());

    }
}