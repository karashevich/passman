package com.company;

public class Main {


    public static void main(String[] args) {

        if(args.length == 3){

            //In case of 3 args, trying to define values
            //Check rules for the link
            PassClass newPass = new PassClass(args[0],args[1],args[2]);

            //Checking validity
            System.out.println(newPass);

        } else {

            //In case of user requests manual
            if ((args.length == 1) && (args[0].equals("?")))
                System.out.println("Manual:\n  password.jar link login password");
        }

    }
}
