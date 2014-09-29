package com.company.server.serverCommands;

import com.company.commands.CommandException;
import com.company.security.Hasher;
import com.company.security.Password;
import com.company.server.CmCnsts;
import com.company.structures.DatabaseControl;
import com.company.structures.Exceptions.InvalidPasswordException;
import com.company.structures.Exceptions.ItemWIthSuchKeyExists;
import com.company.structures.Exceptions.NoSuchItemException;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by jetbrains on 9/22/14.
 */
public class SetpassCmd extends ServerCommand{

    public SetpassCmd() {
        super(ServerCommandType.SETPASS);
    }

    @Override
    public JSONObject execute(DatabaseControl databaseControl, JSONObject input) throws CommandException, InvalidPasswordException, NoSuchItemException, ItemWIthSuchKeyExists, JSONException, ServerCommandException {


        if (databaseControl.isEncrypted()) {

            if (!input.has(CmCnsts.masterpass)) throw new InvalidPasswordException("No master password!");

            Password curpassword = readpass(input, CmCnsts.masterpass);

            if (!Arrays.equals(Hasher.encryptPassword(curpassword), (databaseControl.getHash())))
                throw new InvalidPasswordException("Invalid password.");

            else {

               if (!input.has(CmCnsts.newpass))
                   throw new ServerCommandException(ServerCommandType.SETPASS, "No new password");

               else {

                   databaseControl.setPassword(readpass(input, CmCnsts.masterpass), readpass(input, CmCnsts.newpass));
                   return new JSONObject().put(CmCnsts.answer, CmCnsts.ok).put(CmCnsts.passchanged, new String(readpass(input, CmCnsts.newpass).getPassword()));
               }

            }


        } else {

            if (!input.has(CmCnsts.newpass))
                throw new ServerCommandException(ServerCommandType.SETPASS, "No new password");

            else {

                databaseControl.setPassword(null, readpass(input, CmCnsts.newpass));
                return new JSONObject().put(CmCnsts.answer, CmCnsts.ok).put(CmCnsts.passchanged, new String(readpass(input, CmCnsts.newpass).getPassword()));
            }


        }

    }

    private Password readpass(JSONObject jsonObject, String passwordTypeString) throws JSONException {
        return new Password(jsonObject.getString(passwordTypeString).getBytes());
    }

}
