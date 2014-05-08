package com.company.security;

import org.jetbrains.annotations.NotNull;

import java.io.Console;

/**
 * Created by jetbrains on 5/8/14.
 */
public class Password {

    @NotNull
    private String password;

    private long timestamp;
    private final long deltaTime = 10000;

    public Password(@NotNull String password) {
        this.password = password;
        timestamp = System.currentTimeMillis();
    }

    public Password() throws Exception{

        this.password = enterPassword();
        timestamp = System.currentTimeMillis();

    }

    public String getPassword() throws Exception{

        long curTime = System.currentTimeMillis();

        if ((curTime - timestamp) > deltaTime ) {

            System.out.println("The time for storing password is expired, please enter it once again.");
            this.password = enterPassword();

        }

        return this.password;
    }

    private String enterPassword() throws Exception{

        Console console = System.console();

        if (console == null) {
            System.out.println("Couldn't get Console instance");
            //System.exit(0);

            throw new Exception("enterPassword: Couldn't get Console instance for reading password.");
        }

        char passwordArray[] = console.readPassword("Enter your secret password: ");

        while (passwordArray.length < 8) {

            console.printf("Password should have at least 8 characters. Please try again.");
            passwordArray = console.readPassword("Enter your secret password: ");

        }

        return (new String(passwordArray));

    }

}
