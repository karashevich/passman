package com.company;

import com.company.commands.CommandType;
import com.company.structures.DataPassClass;
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


    public abstract void execute(DataPassClass dpc, String args[]);

    public CommandType getType(){
        return cmdtype;
    }

    public String getDescription(){
        return indescription;
    }

}
