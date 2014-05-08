package com.company.structures;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created with IntelliJ IDEA.
 * User: jetbrains
 * Date: 12/25/13
 * Time: 2:01 AM
 * To change this template use File | Settings | File Templates.
 *
 */
public final class PassClass {

    private final String link;

    private final String login;

    @NotNull private String pass;

    /**
     *
     * @param link - link to source, like gmail.com
     * @param login - login for this link
     * @param pass - pass for this login for this link
     */
    public PassClass(@NotNull String link, String login, @NotNull String pass) {
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

    @Nullable
    public String getPass() {
        return pass;
    }

    protected void updatePass(String newPass){
        this.pass = newPass;
    }

    @Override
    public String toString() {
        return "PassClass{" +
                "link='" + link + '\'' +
                ", login='" + login + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PassClass passClass = (PassClass) o;

        if (link != null ? !link.equals(passClass.link) : passClass.link != null) return false;
        if (!login.equals(passClass.login)) return false;
        if (!pass.equals(passClass.pass)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = link != null ? link.hashCode() : 0;
        result = 31 * result + login.hashCode();
        result = 31 * result + pass.hashCode();
        return result;
    }
}
