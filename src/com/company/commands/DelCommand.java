package com.company.commands;

import com.company.Command;
import com.company.security.Password;
import com.company.structures.DataPassClass;

/**
 * Created by jetbrains on 3/18/14.
 */
public class DelCommand extends Command {

    private static final String description= " -del          delete some record.  passman.jar -del <link>";

    public DelCommand() {
        super(CommandType.DEL, description);
    }

    @Override
    public void execute(DataPassClass dpc, String[] args, Password password) {

        if (args.length < 2) {
            System.out.println("Oh, poor! You should write more arguments!");
            return;
        }

        dpc.delPC(args[1]);
    }
}
