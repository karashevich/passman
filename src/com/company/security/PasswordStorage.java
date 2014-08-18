package com.company.security;

import com.company.preferences.Preferences;

import java.util.Arrays;

/**
 * Created by jetbrains on 5/13/14.
 */
public class PasswordStorage {

    private Password password;
    private byte[] passhash;

    private long timestamp;

    public PasswordStorage(Password password) {
        this.password = password;

        if (this.password == null) {
            this.passhash = null;
        } else {
            this.passhash = Hasher.encryptPassword(password.getPassword());
        }

        this.timestamp = System.currentTimeMillis();
    }

    //if refresh time of password is expired getPassword() returns null.
    public Password getPassword(){

        long currentTime = System.currentTimeMillis();

        if ((currentTime - timestamp) > Preferences.deltaTime){
            return null;
        } else {
            return password;
        }
    }

    public void setPassword(byte[] newPassword){
        this.password = new Password(newPassword);
        if (this.password == null) {
            this.passhash = null;
        } else {
            this.passhash = Hasher.encryptPassword(password.getPassword());
        }
        this.timestamp = System.currentTimeMillis();
    }

    public void setPassword(Password newPassword){
        this.password = newPassword;
        if (this.password == null) {
            this.passhash = null;
        } else {
            this.passhash = Hasher.encryptPassword(password.getPassword());
        }
        this.timestamp = System.currentTimeMillis();
    }

    public byte[] getPasshash() {
        return this.passhash;
    }

    public boolean checkPassword(Password password){

        if (password == null) System.err.println("Entered password is NULL!");
        return password.equals(this.password);
    }

}
