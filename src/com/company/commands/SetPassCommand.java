package com.company.commands;

import com.company.Command;
import com.company.security.Password;
import com.company.structures.DataPassClass;
import com.company.structures.PassClass;

/**
 * Created by jetbrains on 3/18/14.
 */
public class SetPassCommand extends Command {

    private static final String description = "-setpass     add a new record.    passman.jar -setpass password";


    public SetPassCommand() {
        super(CommandType.SETPASS);
    }

    @Override
    protected void execute(DataPassClass dpc, String[] args) {
        PassClass newPass;


        try {
            Password password = new Password(args[1]);

            dpc.setPassword(password, password);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}