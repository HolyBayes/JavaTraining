package com.noveogroup.java.serialize;

import java.io.*;

/**
 * blablabla
 * @author artem ryzhikov
 */
public class Serializer {
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public Serializer(final File input , final File output) {
        try {
            ois = new ObjectInputStream(new FileInputStream(input));
            oos = new ObjectOutputStream(new FileOutputStream(output));
        } catch (IOException e) {
            System.out.print("Wrong input/output");
        }
    }
    public void store(final Object obj) throws IOException {
        oos.writeObject(obj);
    }
    public Object load() throws IOException , ClassNotFoundException {
        return  ois.readObject();
    }
}
