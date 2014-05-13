package com.company.security;

import org.jetbrains.annotations.NotNull;

import java.io.Console;

/**
 * Created by jetbrains on 5/8/14.
 */
public abstract class Password {

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

            timestamp = curTime;

        }

        return this.password;
    }

    public abstract String enterPassword() throws Exception;

}
