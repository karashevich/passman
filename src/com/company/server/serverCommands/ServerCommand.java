package com.company.server.serverCommands;

import com.company.commands.CommandException;
import com.company.structures.DatabaseControl;
import com.company.structures.Exceptions.InvalidPasswordException;
import com.company.structures.Exceptions.ItemWIthSuchKeyExists;
import com.company.structures.Exceptions.NoSuchItemException;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jetbrains on 9/22/14.
 */
public abstract class ServerCommand {


    private ServerCommandType cmdtype = null;

    public ServerCommand(@NotNull ServerCommandType cmdtype){
        this.cmdtype = cmdtype;
    }

    public abstract JSONObject execute(DatabaseControl databaseControl, JSONObject input) throws CommandException, InvalidPasswordException, NoSuchItemException, ItemWIthSuchKeyExists, JSONException, ServerCommandException;



}