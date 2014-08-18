package com.company;

import com.company.console.CUI;


public class Main {

    final static String dataPath = "./data.xml";



//    public static void main(String[] args) {
//
//
//        passwordExample();
//        long startTime = System.currentTimeMillis();
//        System.out.println("Time is  =" + startTime);
//
//
//
//        long estimatedTime = System.currentTimeMillis() - startTime;
//        System.out.println("The diff is:" + (estimatedTime));
//    }

    public static void main(String[] args) throws Exception {


//        Password password = new Password("password");
//
//        DataPassClass dpc;
//
//        // Try to find and load data file
//        try{
//
//            dpc = DataPassClass.loadFromFile(dataPath);
//
//        // Cannot read from file or not such file
//        } catch (IOException e) {
//
//            System.out.println("Attention! File 'data.xml' didn't find, passman will start without passwords history!");
//            dpc = new DataPassClass();
//
//        //Cannot resolve class from file
//        } catch (CannotResolveClassException crce) {
//
//            System.out.println("Attention! File 'data.xml' has wrong format, passman will start without passwords history!");
//            dpc = new DataPassClass();
//
//        }
//
//        CommandFactory commandFactory = new CommandFactory();
//
//        if (args.length >= 1) {
//            String s = args[0].substring(1);
//            commandFactory.buildCommand(args).execute(dpc, args);
//
//        } else {
//                System.out.println("Unknown command, please read the help: passman.jar -? or passman.jar -help");
//        }
//
//
//
//        try {
//            dpc.saveToFile(dataPath);
//
//        } catch (IOException e) {
//
//            e.printStackTrace();
//            System.out.println("Attention! File data.xml' didn't find. Trying to create a new one.");
//
//            File f = new File(dataPath);
//            f.createNewFile();
//
//            dpc.saveToFile(dataPath);
//        }

        CUI cui = new CUI();

        cui.init();
    }
}
