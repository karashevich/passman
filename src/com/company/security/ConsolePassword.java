package com.company.security;

import com.company.security.Password;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStreamReader;

/**
 * Created by jetbrains on 5/9/14.
 */
public class ConsolePassword extends Password {

    public ConsolePassword(@NotNull String password) {
        super(password);
    }

    public ConsolePassword() throws Exception {
        super();
    }

//    @Override
//    public String enterPassword() throws Exception{
//
//        Console console = System.console();
//
//        if (console == null) {
//            System.out.println("Couldn't get Console instance");
//            //System.exit(0);
//
//            throw new Exception("enterPassword: Couldn't get Console instance for reading password.");
//        }
//
//        char passwordArray[] = console.readPassword("Enter your secret password: ");
//
//        while (passwordArray.length < 8) {
//
//            console.printf("Password should have at least 8 characters. Please try again.");
//            passwordArray = console.readPassword("Enter your secret password: ");
//
//        }
//
//        return (new String(passwordArray));
//
//    }

    public String enterPassword() throws Exception{

        System.out.print("DANGER, IT IS TESTING MODE!!! Please type password:");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String pass;

        pass = br.readLine();
        return pass;

    }
}
