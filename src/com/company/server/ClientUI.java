package com.company.server;

import com.company.UI;
import com.company.preferences.Mode;
import com.company.preferences.Preferences;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * Created by jetbrains on 9/29/14.
 */
public class ClientUI implements UI {

    private byte[] toBytes(char[] chars) {
        CharBuffer charBuffer = CharBuffer.wrap(chars);
        ByteBuffer byteBuffer = Charset.forName("UTF-8").encode(charBuffer);
        byte[] bytes = Arrays.copyOfRange(byteBuffer.array(),
                byteBuffer.position(), byteBuffer.limit());
        Arrays.fill(charBuffer.array(), '\u0000'); // clear sensitive data
        Arrays.fill(byteBuffer.array(), (byte) 0); // clear sensitive data
        return bytes;
    }

    @Override
    public byte[] readPassword(String prefix) {

        if (Preferences.runmode == Mode.TEST) {

            System.out.print(prefix);
            System.out.print("TESTING MODE!!! Enter your secret password:");

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            try {
                return br.readLine().getBytes();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {

            Console console = System.console();
            console.printf(prefix);

            if (console == null) {
                System.out.println("Couldn't get Console instance");
                System.exit(0);

            }

            char passwordArray[];
            passwordArray = console.readPassword("Enter your secret password: ");

            while (passwordArray.length < 8) {

                console.printf("Password should have at least 8 characters. Please try again.");
                passwordArray = console.readPassword("Enter your secret password: ");

            }

            return toBytes(passwordArray);
        }

        return null;


    }

    @Override
    public void init() {

    }

    @Override
    public void print(String in) {
        System.out.println(in);
    }

}
