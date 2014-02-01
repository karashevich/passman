package com.company;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.TreeMap;

/**
 * Created by jetbrains on 1/31/14.
 *
 */
public class DataPassClass {
    private TreeMap<String, PassClass> dataDPC;

    public DataPassClass(TreeMap<String, PassClass> dataDPC) {
        this.dataDPC = dataDPC;
    }

    public DataPassClass() {
        this.dataDPC = new TreeMap<String, PassClass>();
    }

    public void addPC(PassClass pc){
        dataDPC.put(pc.getLink(), pc);
    }

    public PassClass getPC(String s){
        if (dataDPC.containsKey(s)){
            return dataDPC.get(s);
        } else {
            return null;
        }
    }


    public void delPC(String s){
        dataDPC.remove(s);
    }

    public void saveToFile(String pathFile) throws IOException{

        XStream xstream = new XStream(new StaxDriver());
        String xml = xstream.toXML(this);

        //Saving XML to a new file
        FileWriter fw = new FileWriter(pathFile);
        try{
            fw.write(xml);
            fw.close();

            System.out.println("DataPassClass.saveToFile: Class is saved.");

        } catch (IOException e) {

            System.out.println("DataPassClass.saveToFile: IOException by saving file to " + pathFile);
            throw e;

        } finally {
            fw.close();
        }
    }

    public void loadFromFile(String pathFile) throws IOException{

        XStream xstream = new XStream(new StaxDriver());

        BufferedReader br = new BufferedReader(new FileReader(pathFile));
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

        System.out.println("DataPassClass.loadFromFile: Class is loaded.");

        } catch (IOException e) {

            System.out.println("DataPassClass.loadFromFile: Exception caused by reading file" + e.getMessage());
            throw e;

        } finally {
            br.close();
        }

        this.dataDPC = ((DataPassClass) xstream.fromXML(everything)).dataDPC;

    }

//    @Override
//    public String toString() {
//        StringBuilder sbd = new StringBuilder();
//
//        for(PassClass pc: dataDPC.values()){
//            sbd.append(pc.toString());
//            sbd.append(";");
//        }
//
//        return sbd.toString();
//    }


    @Override
    public String toString() {
        return "DataPassClass{" +
                "dataDPC=" + dataDPC +
                '}';
    }
}
