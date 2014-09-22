package com.company.server.serverCommands;

import com.company.security.Password;
import com.company.structures.DatabaseControl;
import com.company.structures.Exceptions.InvalidPasswordException;
import com.company.structures.Exceptions.NoSuchItemException;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jetbrains on 9/22/14.
 */
public class DelCmd extends ServerCommand{

   public DelCmd() {
        super(ServerCommandType.DEL);
    }

    @Override
    public JSONObject execute(DatabaseControl databaseControl, JSONObject args) throws JSONException, NoSuchItemException, InvalidPasswordException, ServerCommandException {

        if (!args.has("data")) throw new ServerCommandException(ServerCommandType.DEL, "not enough arguments");

        if (args.has("masterpassword"))
            databaseControl.delItem(args.getString("data"), (Password) args.get("masterpassword"));
        else databaseControl.delItem(args.getString("data"), null);


        return new JSONObject().put("answer", "ok");

    }


}
