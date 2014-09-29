package com.company.server.serverCommands;

import com.company.commands.CommandException;
import com.company.security.Password;
import com.company.server.CmCnsts;
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
public class AddCmd extends ServerCommand{


    public AddCmd() {
        super(ServerCommandType.ADD);
    }

    @Override
    public JSONObject execute(DatabaseControl databaseControl, JSONObject args) throws CommandException, InvalidPasswordException, NoSuchItemException, ItemWIthSuchKeyExists, JSONException {


        JSONObject data = args.getJSONObject(CmCnsts.data);

        Item item = new Item(data.getString(CmCnsts.link), data.getString(CmCnsts.login), data.getString(CmCnsts.pass));

        if (args.has(CmCnsts.masterpass))
            databaseControl.addItem(item, new Password(args.getString(CmCnsts.masterpass).getBytes()));
        else databaseControl.addItem(item, null);

        return new JSONObject().put(CmCnsts.answer, CmCnsts.ok);

    }



}
