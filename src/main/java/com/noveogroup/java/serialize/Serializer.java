package com.noveogroup.java.serialize;

import java.io.*;
import java.util.Stack;

/**
 * Created by artem on 24.07.14.
 */
public class Serializer{
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public void store(Object obj) throws IOException{
        oos.writeObject(obj);
    }
    public Object load() throws IOException,ClassNotFoundException{
        return  ois.readObject();
    }
    public Serializer(File input,File output){
        try {
            ois = new ObjectInputStream(new FileInputStream(input));
            oos = new ObjectOutputStream(new FileOutputStream(output));
        }
        catch (IOException e){
            System.out.print("Wrong input/output");
        }
    }
}
