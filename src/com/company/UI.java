package com.company;

/**
 * Created by jetbrains on 5/13/14.
 */
public interface UI {

    public byte[] readPassword(String prefix);

    public void init();

    public void print(String in);
}
