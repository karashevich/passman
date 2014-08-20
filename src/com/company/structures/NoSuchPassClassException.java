package com.company.structures;

/**
 * Created by jetbrains on 8/19/14.
 */
public class NoSuchPassClassException extends Exception {

    String PassClassKey;

    NoSuchPassClassException(Item pc){
        this.PassClassKey = pc.getLink();
    }

    NoSuchPassClassException(String s){
        this.PassClassKey = s;
    }

    public String getPassClassKey() {
        return PassClassKey;
    }
}
