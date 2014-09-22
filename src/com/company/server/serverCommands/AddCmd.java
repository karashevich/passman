package com.company.server.serverCommands;

import com.company.commands.CommandException;
import com.company.commands.CommandType;
import com.company.security.Password;
import com.company.security.PasswordHolder;
import com.company.structures.DatabaseControl;
import com.company.structures.Exceptions.InvalidPasswordException;
import com.company.structures.Exceptions.ItemWIthSuchKeyExists;
import com.company.structures.Exceptions.NoSuchItemException;
import com.company.structures.Item;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jetbrains on 9/22/14.
 */
public class AddCmd extends ServerCommand{


    public AddCmd() {
        super(ServerCommandType.ADD);
    }

    @Override
    public JSONObject execute(DatabaseControl databaseControl, JSONObject args) throws CommandException, InvalidPasswordException, NoSuchItemException, ItemWIthSuchKeyExists, JSONException {


        JSONObject data = args.getJSONObject("data");

        Item item = new Item(data.getString("link"), data.getString("login"), data.getString("pass"));

        if (args.has("masterpassword"))
            databaseControl.addItem(item, (Password) args.get("masterpassword"));
        else databaseControl.addItem(item, null);

        return new JSONObject().put("answer", "ok");

    }



}
