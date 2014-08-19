package com.company.commands;

import com.company.Command;
import com.company.UI;
import com.company.security.PasswordStorage;
import com.company.structures.DataPassInterface;
import com.company.structures.PassClass;

/**
 * Created by jetbrains on 3/18/14.
 */
public class AddCommand extends Command {

    private static final String description = "-add        add a new record.    passman.jar -add <link> <login> <password>";

    private static PassClass readPassClass(String s0, String s1, String s2) throws Exception{

        PassClass newPass;
        newPass = new PassClass(s0, s1, s2);

        return newPass;
    }

    public AddCommand() {
        super(CommandType.ADD, description);
    }

    @Override
    public void execute(DataPassInterface dpc, String[] args, PasswordStorage ps, UI ui) {

        PassClass newPass;

        try {

            if (args.length < 4) {
                System.out.println("Oh, poor! You should write more arguments!");
                return;
            }

            newPass = readPassClass(args[1], args[2], args[3]);
            dpc.addPC(newPass, ps, ui);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}