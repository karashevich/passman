package com.company;

import com.company.commands.*;
import org.jetbrains.annotations.Nullable;

/**
 * Created by jetbrains on 3/18/14.
 */


public class CommandFactory {
    public Command buildCommand(String args[]){

        //String s = args[0].substring(1);
        String s = args[0];

        //detecting type of command. In bad case it is "null"
        @Nullable CommandType cmdtype = null;
        for (CommandType ct: CommandType.values()) {
            if (s.equalsIgnoreCase(ct.name())){
                cmdtype = ct;
                break;
            }
        }

        if( cmdtype == null) {

            return new NoCommand();

        } else {

            switch(cmdtype){
                case ADD:
                    return new AddCommand();

                case DEL:
                    return new DelCommand();

                case SHOW:
                    return new ShowCommand();

                case SHOWALL:
                    return new ShowAllCommand();

                case SETPASS:
                    return new SetPassCommand();

                default:
                    return new NoCommand();
            }
        }
    }
}
