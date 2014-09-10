package com.company.security;

import com.company.UI;
import com.company.preferences.Preferences;
import com.company.structures.Exceptions.InvalidPasswordException;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

/**
 * Created by jetbrains on 5/13/14.
 */
public class PasswordHolder {

    @Nullable
    private Password password;
    @Nullable
    private byte[] hashPassword;

    UI ui;

    private long timestamp;

    public PasswordHolder(Password password, UI ui) {
        this.password = password;
        this.ui = ui;
        this.timestamp = System.currentTimeMillis();

        if (password != null){
            hashPassword = Hasher.encryptPassword(password);
        } else {
            hashPassword = null;
        }
    }

    @Nullable
    public Password getPassword(){

        long currentTime = System.currentTimeMillis();

        if ((currentTime - timestamp) > Preferences.deltaTime){

            return touchPassword();

        } else {
            return password;
        }
    }


    public void setPassword(Password newPassword){
        this.password = newPassword;
        this.timestamp = System.currentTimeMillis();
    }

    public Password setNewPassword() throws InvalidPasswordException{

        //touchPassword();

        Password newPassword = new Password(ui.readPassword("Please enter new password"));

        if (newPassword.getPassword().length < 8)
            throw new InvalidPasswordException("Please enter at least 8 characters.");

        this.password = newPassword;
        this.hashPassword = Hasher.encryptPassword(newPassword);
        this.timestamp = System.currentTimeMillis();

        return newPassword;
    }

    @Nullable
    private Password touchPassword(){
        this.timestamp = System.currentTimeMillis();
        byte[] enteredHash = null;
        Password enteredPassword = null;

        if (hashPassword != null) ui.print("Password keeping time is expired!\n");

        while(!Arrays.equals(enteredHash, hashPassword)) {
            enteredPassword = new Password(ui.readPassword(""));
            enteredHash = Hasher.encryptPassword(enteredPassword);
            if (!Arrays.equals(enteredHash, hashPassword)) ui.print("Invalid password! ");
        }

        return enteredPassword;
    }

    private void readExpectedPassword(byte[] passwordHash){
        if (!Arrays.equals(Hasher.encryptPassword(ui.readPassword("")), passwordHash)) {

        }
    }

    public void setHashPassword(@Nullable byte[] hashPassword) {
        this.hashPassword = hashPassword;
    }
}
