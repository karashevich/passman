package com.company.security;

import com.company.UI;

import java.io.BufferedWriter;
import java.util.Arrays;

/**
 * Created by jetbrains on 5/13/14.
 */
public class PasswordGetter {

    public static Password getPassword(UI ui, PasswordStorage ps, byte[] hashPass){

        Password password = ps.getPassword();
        while (password == null) {

            Password pass = new Password(ui.readPassword());
            ps.setPassword(pass);

            if (!Arrays.equals(ps.getPasshash(), hashPass)){
                ui.print("Incorrect password, try again! ");
                pass = null;
            }
            password = pass;

        }

        return password;

    }

}
