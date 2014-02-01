package com.company;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import java.io.*;

public class Main {

    final static String dataPath = "./data.xml";

    private static PassClass readPassClass(String s0, String s1, String s2) throws Exception{

        PassClass newPass;
        newPass = new PassClass(s0, s1, s2);

        return newPass;
    }


    public static void main(String[] args) throws Exception {

        PassClass newPass;

        DataPassClass dpc = new DataPassClass();


        if(args.length == 3){

            //In case of 3 args, trying to define values
            newPass = readPassClass(args[0], args[1], args[2]);

            // Try to find data file
            try{
                dpc.loadFromFile(dataPath);
            } catch (IOException e) {
                System.out.println("Attention! File 'data.xml' didn't find, passman will start without passwords history!");
            }

            dpc.addPC(newPass);
            System.out.println("DataPassClass:" + dpc);

            dpc.addPC(readPassClass("vk.com", "bla@mail.ru", "blablabla"));
            System.out.println("DataPassClass last:" + dpc);


            try {
                dpc.saveToFile(dataPath);

            } catch (IOException e) {

                e.printStackTrace();
                System.out.println("Attention! File data.xml' didn't find. Trying to create a new one.");

                File f = new File(dataPath);
                f.createNewFile();
            }

        } else {

            //In case of user requests manual
            if ((args.length == 1) && (args[0].equals("?")))
                System.out.println("Manual:\n  password.jar link login password");
                return;
        }

    }
}
