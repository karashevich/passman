package com.company;

import com.company.commands.CommandException;
import com.company.commands.CommandType;
import com.company.security.PasswordHolder;
import com.company.structures.Database;
import com.company.structures.DatabaseControl;
import com.company.structures.Exceptions.InvalidPasswordException;
import com.company.structures.Exceptions.ItemWIthSuchKeyExists;
import com.company.structures.Exceptions.NoSuchItemException;
import com.sun.tools.internal.ws.wscompile.BadCommandLineException;
import org.jetbrains.annotations.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: jetbrains
 * Date: 2/28/14
 * Time: 5:50 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Command {

    private CommandType cmdtype = null;

    private String indescription = null;

    public Command(@NotNull CommandType cmdtype, @NotNull String description){
        this.cmdtype = cmdtype;
        this.indescription = description;
    }


    public abstract void execute(DatabaseControl databaseControl, String args[], PasswordHolder passwordHolder) throws CommandException, InvalidPasswordException, NoSuchItemException, ItemWIthSuchKeyExists;

    public CommandType getType(){
        return cmdtype;
    }

    public String getDescription(){
        return indescription;
    }

}
