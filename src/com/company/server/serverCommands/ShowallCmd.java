package com.company.server.serverCommands;

import com.company.commands.CommandException;
import com.company.security.PasswordHolder;
import com.company.server.Converter;
import com.company.structures.DatabaseControl;
import com.company.structures.Exceptions.InvalidPasswordException;
import com.company.structures.Exceptions.ItemWIthSuchKeyExists;
import com.company.structures.Exceptions.NoSuchItemException;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jetbrains on 9/22/14.
 */
public class ShowallCmd extends ServerCommand{


    public ShowallCmd() {
        super(ServerCommandType.SHOWALL);
    }

    @Override
    public JSONObject execute(DatabaseControl databaseControl, JSONObject input) throws CommandException, InvalidPasswordException, NoSuchItemException, ItemWIthSuchKeyExists, JSONException {

        return new JSONObject().put("answer", "ok").put("data", Converter.itemsToJSON(databaseControl.getItems()));

    }



}
