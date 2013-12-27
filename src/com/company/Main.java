package com.company;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import org.omg.CORBA.FREE_MEM;

import java.io.*;

public class Main {


    public static void main(String[] args) throws IOException {


        PassClass newPass;

        if(args.length == 3){

            //In case of 3 args, trying to define values
            //Check rules for the link
            newPass = new PassClass(args[0],args[1],args[2]);

            //Checking validity
            System.out.println(newPass);

            //Serializing record newPass to XML xstream
            XStream xstream = new XStream(new StaxDriver());
            String xml = xstream.toXML(newPass);

            System.out.println(xml);

            //Saving XML to a new file
            String outPath = "out/in.txt";
            FileWriter fw = new FileWriter(outPath);
            try{
                fw.write(xml);
                fw.close();
            } catch (IOException e) {
                System.out.println("Main.main: IOException by saving file to " + outPath);
                throw e;
            } finally {
                fw.close();
            }

            //Trying to read XML to new record
            String inPath = "in/in.txt";
            BufferedReader br = new BufferedReader(new FileReader(inPath));
            String everything;

            try {
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();

                while (line != null) {
                    sb.append(line);
                    sb.append('\n');
                    line = br.readLine();
                }
                 everything = sb.toString();

            } finally {
                br.close();
            }
            newPass = (PassClass) xstream.fromXML(everything);
            System.out.println(newPass);

        } else {

            //In case of user requests manual
            if ((args.length == 1) && (args[0].equals("?")))
                System.out.println("Manual:\n  password.jar link login password");
                return;
        }






    }
}
