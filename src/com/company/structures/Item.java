package com.company.structures;


import org.jetbrains.annotations.NotNull;


/**
 * Created with IntelliJ IDEA.
 * User: jetbrains
 * Date: 12/25/13
 * Time: 2:01 AM
 * To change this template use File | Settings | File Templates.
 *
 */

//Immutable class
public final class Item {

    @NotNull final String link;

    @NotNull final String login;

    @NotNull private final String pass;

    /**
     *
     * @param link - link to source, like gmail.com
     * @param login - login for this link
     * @param pass - pass for this login for this link
     */
    public Item(@NotNull String link, @NotNull String login, @NotNull String pass) {
        this.link = link;
        this.login = login;
        this.pass = pass;
    }

    @NotNull
    public String getLink() {
        return link;
    }

    @NotNull
    public String getLogin() {
        return login;
    }

    @NotNull
    public String getPass() {
        return pass;
    }


    @Override
    public String toString() {
        return "Item{" +
                "link='" + link + '\'' +
                ", login='" + login + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        return link.equals(item.link) && login.equals(item.login) && pass.equals(item.pass);

    }

    @Override
    public int hashCode() {
        int result = link.hashCode();
        result = 31 * result + login.hashCode();
        result = 31 * result + pass.hashCode();
        return result;
    }
}
