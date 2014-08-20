package com.company.structures;

import com.company.UI;
import com.company.security.PasswordStorage;
import org.jetbrains.annotations.Nullable;

/**
 * Created by jetbrains on 8/18/14.
 */
public interface DataPassInterface {
    boolean isEncrypted();

    void addPC(Item pc, @Nullable PasswordStorage ps, UI ui);

    Item getPC(String s);

    @Nullable
    Item getPC(String s, @Nullable PasswordStorage ps, UI ui);

    void delPC(String s) throws NoSuchPassClassException;

    int size();

    void setPassword(PasswordStorage ps, UI ui);

    byte[] getPassHash();
}
