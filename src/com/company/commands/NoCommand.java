package com.company.commands;

import com.company.Command;
import com.company.UI;
import com.company.security.PasswordStorage;
import com.company.structures.DataPassInterface;

/**
 * Created by jetbrains on 3/18/14.
 */
public class NoCommand extends Command {

    private static final String description= "There is no command with such name";


    public NoCommand() {
        super(CommandType.NOCOMMAND, description);
    }

    @Override
    public void execute(DataPassInterface dpc, String[] args, PasswordStorage ps, UI ui) {
        System.out.println(getDescription());
    }
}
