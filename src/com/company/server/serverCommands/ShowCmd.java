package com.company.server.serverCommands;

import com.company.commands.CommandException;
import com.company.security.Password;
import com.company.server.CmCnsts;
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

        if (input.has(CmCnsts.masterpass))
            item = databaseControl.getItem(input.getString(CmCnsts.data), new Password(input.getString(CmCnsts.masterpass).getBytes()));
        else
            item = databaseControl.getItem(input.getString(CmCnsts.data), null);

        return new JSONObject().put(CmCnsts.answer, CmCnsts.ok).put(CmCnsts.item, Converter.itemToJSON(item));

    }


}
