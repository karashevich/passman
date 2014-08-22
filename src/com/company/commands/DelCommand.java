package com.company.commands;

import com.company.Command;
import com.company.UI;
import com.company.security.PasswordStorage;
import com.company.structures.Database;
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
    public void execute(Database dpc, String[] args, PasswordStorage ps, UI ui) {

        if (args.length < 2) {
            System.out.println("Oh, poor! You should write more arguments!");
            return;
        }

        try {
            dpc.delItem(args[1]);
        } catch (NoSuchItemException e) {
            ui.print("No such entry with \"" + e.getItemKey() + "\" link!\n");
        }
    }
}
