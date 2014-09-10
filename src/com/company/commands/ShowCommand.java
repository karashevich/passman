package com.company.commands;

import com.company.Command;
import com.company.UI;
import com.company.security.PasswordHolder;
import com.company.structures.Database;
import com.company.structures.DatabaseControl;
import com.company.structures.Exceptions.InvalidPasswordException;
import com.company.structures.Exceptions.NoSuchItemException;
import com.company.structures.Item;

/**
 * Created by jetbrains on 3/18/14.
 */
public class ShowCommand extends Command {

    private static final String description = "show         show some record.    passman.jar -show <link>";

    public ShowCommand() {
        super(CommandType.SHOW, description);
    }

    @Override
    public void execute(DatabaseControl databaseControl, String[] args, PasswordHolder passwordHolder) throws CommandException, NoSuchItemException, InvalidPasswordException{

        if (args.length < 2) {
            throw new CommandException(CommandType.DEL, "Not enough arguments.");
        }

        StringBuilder sb = new StringBuilder();
        String link = args[1];

        Item item = null;

        item = databaseControl.getItem(link, passwordHolder.getPassword());

        sb.append("Link :\"").append(item.getLink()).append("\" Login:\"").append(item.getLogin()).append("\" Password:\"").append(item.getPass()).append("\"");
        System.out.println(new String(sb));
    }
}
