package com.company.structures.Exceptions;

import com.company.structures.Item;

/**
 * Created by jetbrains on 8/19/14.
 */
public class NoSuchItemException extends Exception {

    String ItemKey;

    public NoSuchItemException(Item pc){
        this.ItemKey = pc.getLink();
    }

    public NoSuchItemException(String s){
        this.ItemKey = s;
    }

    public String getItemKey() {
        return ItemKey;
    }
}
