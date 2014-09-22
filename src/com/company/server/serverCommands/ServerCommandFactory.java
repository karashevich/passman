package com.company.server.serverCommands;

import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by jetbrains on 3/18/14.
 */


public class ServerCommandFactory {
    public ServerCommand buildCommand(JSONObject jsonObject) throws JSONException {


        String stringCommnad = jsonObject.getString("command");

        //detecting type of command. In bad case it is "null"
        @Nullable ServerCommandType cmdtype = null;
        for (ServerCommandType ct: ServerCommandType.values()) {
            if (stringCommnad.equalsIgnoreCase(ct.name())){
                cmdtype = ct;
                break;
            }
        }

        if( cmdtype == null) {

            return new NoCmd();

        } else {

            switch(cmdtype){
                case ADD:
                    return new AddCmd();

                case SHOW:
                    return new ShowCmd();

                case SHOWALL:
                    return new ShowallCmd();

                case DEL:
                    return new DelCmd();
            }
        }

        return new NoCmd();
    }

}
