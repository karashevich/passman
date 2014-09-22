package com.company.server.serverCommands;

/**
 * Created by jetbrains on 9/22/14.
 */
public class ClientCommandException extends Exception{

    private ServerCommandType commandType;


    public ClientCommandException(String message) {
        super(message);
    }
}
