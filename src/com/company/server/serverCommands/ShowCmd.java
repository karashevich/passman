package com.company.server.serverCommands;

import com.company.commands.CommandException;
import com.company.security.Password;
import com.company.security.PasswordHolder;
import com.company.server.Converter;
import com.company.structures.DatabaseControl;
import com.company.structures.Exceptions.InvalidPasswordException;
import com.company.structures.Exceptions.ItemWIthSuchKeyExists;
import com.company.structures.Exceptions.NoSuchItemException;
import com.company.structures.Item;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jetbrains on 9/22/14.
 */
public class ShowCmd extends ServerCommand{


    public ShowCmd() {
        super(ServerCommandType.SHOW);
    }

    @Override
    public JSONObject execute(DatabaseControl databaseControl, JSONObject input) throws CommandException, InvalidPasswordException, NoSuchItemException, ItemWIthSuchKeyExists, JSONException {

        Item item;

        if (input.has("masterpassword"))
            item = databaseControl.getItem(input.getString("data"), (Password) input.get("masterpassword"));
        else
            item = databaseControl.getItem(input.getString("data"), null);

        return new JSONObject().put("answer", "ok").put("item", Converter.itemToJSON(item));

    }


}
