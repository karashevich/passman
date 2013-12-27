package com.company;

/**
 * Created with IntelliJ IDEA.
 * User: jetbrains
 * Date: 12/25/13
 * Time: 2:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class PassClass {

    private String link;

    private String login;

    private String pass;

    public PassClass(String link, String login, String pass) {
        this.link = link;
        this.login = login;
        this.pass = pass;
    }

    public String getLink() {
        return link;
    }

    public String getLogin() {
        return login;
    }

    public String getPass() {
        return pass;
    }

    @Override
    public String toString() {
        return "PassClass{" +
                "link='" + link + '\'' +
                ", login='" + login + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }
}
