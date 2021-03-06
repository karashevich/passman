package com.company.server.serverCommands;

import com.company.server.CmCnsts;
import com.company.structures.DatabaseControl;
import com.company.structures.Exceptions.InvalidPasswordException;
import com.company.structures.Exceptions.ItemWIthSuchKeyExists;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jetbrains on 9/22/14.
 */
public class NoCmd extends ServerCommand{

    public NoCmd() {
        super(ServerCommandType.NOCOMMAND);
    }

    @Override
    public JSONObject execute(DatabaseControl databaseControl, JSONObject args) throws JSONException, ItemWIthSuchKeyExists, InvalidPasswordException {


        return new JSONObject().put(CmCnsts.answer, "nocommand");

    }



}
