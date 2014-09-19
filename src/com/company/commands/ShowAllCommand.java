package com.company.commands;

import com.company.Command;
import com.company.UI;
import com.company.security.PasswordHolder;
import com.company.structures.Database;
import com.company.structures.DatabaseControl;
import com.company.structures.Exceptions.InvalidPasswordException;
import com.company.structures.Item;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by jetbrains on 3/18/14.
 */
public class ShowAllCommand extends Command {

    private static final String description = "-showall      show all records.    passman.jar -showall";

    public ShowAllCommand() {
        super(CommandType.SHOWALL, description);
    }

    @Override
    public void execute(DatabaseControl databaseControl, String[] args, PasswordHolder passwordHolder) throws InvalidPasswordException{

        Set<Item> itemHashSet;

        itemHashSet = databaseControl.getItems();

        if (!itemHashSet.isEmpty()) {
            for (Item item : itemHashSet) {
                System.out.println(item);
            }
        } else {
            System.out.println("Database is empty.");
        }

    }
}
