package com.company.commands;

/**
 * Created by jetbrains on 8/23/14.
 */
public class CommandException extends Exception{

    private CommandType commandType;
    private String message;

    public CommandException(CommandType commandType, String message) {
        this.commandType = commandType;
        this.message = message;
    }
}
