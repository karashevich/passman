package com.company.server.serverCommands;


/**
 * Created by jetbrains on 9/22/14.
 */

public class ServerCommandException extends Exception{

    private ServerCommandType commandType;
    private String message;

    public ServerCommandException(ServerCommandType commandType, String message) {
        this.commandType = commandType;
        this.message = message;
    }

    public ServerCommandType getCommandType() {
        return commandType;
    }

    public String getMessage() {
        return message;
    }
}
