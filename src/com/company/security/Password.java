package com.company.security;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * Created by jetbrains on 5/8/14.
 */
public class Password {

    @NotNull
    private byte[] password;

    public Password(@NotNull byte[] password) {
        this.password = password;
    }

    public byte[] getPassword() {

        return this.password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Password password1 = (Password) o;

        if (!Arrays.equals(password, password1.password)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(password);
    }
}
