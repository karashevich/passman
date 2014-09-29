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
 * Created by jetbrains on 9/23/14.
 */
public class ChckpassCmd extends ServerCommand {

    public ChckpassCmd() {
        super(ServerCommandType.CHECKPASS);
    }

    @Override
    public JSONObject execute(DatabaseControl databaseControl, JSONObject input) throws CommandException, InvalidPasswordException, NoSuchItemException, ItemWIthSuchKeyExists, JSONException, ServerCommandException {

        if (databaseControl.isEncrypted()) {

            if (input.has(CmCnsts.masterpass)) {

                Password password = new Password(input.getString(CmCnsts.masterpass).getBytes());

                if (Arrays.equals(Hasher.encryptPassword(password), databaseControl.getHash())) {

                    return new JSONObject().put(CmCnsts.answer, CmCnsts.ok);

                } else {

                    byte[] passHash = Hasher.encryptPassword(password);
                    throw new InvalidPasswordException("invalid master password.");

                }

            } else {

                throw new InvalidPasswordException("Database control is encrypted but master password is absent");

            }

        } else {

            throw new InvalidPasswordException("unencrypted!");

        }
    }
}
