package com.company.structures;

import com.company.UI;
import com.company.security.PasswordStorage;
import org.jetbrains.annotations.Nullable;

/**
 * Created by jetbrains on 8/18/14.
 */
public interface DataPassInterface {
    boolean isEncrypted();

    void addPC(PassClass pc, @Nullable PasswordStorage ps, UI ui);

    PassClass getPC(String s);

    @Nullable
    PassClass getPC(String s, @Nullable PasswordStorage ps, UI ui);

    void delPC(String s);

    int size();

    void setPassword(PasswordStorage ps, UI ui);

    byte[] getPassHash();
}
