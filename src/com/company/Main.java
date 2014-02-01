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

        // Try to find and load data file
        try{
            dpc.loadFromFile(dataPath);
        } catch (IOException e) {
            System.out.println("Attention! File 'data.xml' didn't find, passman will start without passwords history!");
        }

        if(args.length == 4) {

            if (args[0].equals("-add")){

                newPass = readPassClass(args[0], args[1], args[2]);
                dpc.addPC(newPass);

            }
        } if(args.length == 2) {

            if (args[0].equals("-del")) {

                dpc.delPC(args[1]);

            } if (args[0].equals("-show")) {

                System.out.println(dpc.getPC(args[1]));

            }
        } if(args.length == 1) {

            if (args[0].equals("-showall")) {

                System.out.println(dpc);

            } if ((args[0].equals("-help")) || (args[0].equals("-?"))) {
                System.out.println("Passman help: \n" +
                        "     passman.jar <command> <args> \n" +
                        "     -add          add a new record.    passman.jar -add <link> <login> <password> \n" +
                        "     -del          delete some record.  passman.jar -del <link> \n" +
                        "     -show         show some record.    passman.jar -show <link> \n" +
                        "     -showall      show all records.    passman.jar -showall \n" +
                        "     -? -help      print this help message \n");
            }

        } else {
//                System.out.println("Unknown command, please read the help: passman.jar -? or passman.jar -help");
        }



        try {
            dpc.saveToFile(dataPath);

        } catch (IOException e) {

            e.printStackTrace();
            System.out.println("Attention! File data.xml' didn't find. Trying to create a new one.");

            File f = new File(dataPath);
            f.createNewFile();

            dpc.saveToFile(dataPath);
        }
    }
}
