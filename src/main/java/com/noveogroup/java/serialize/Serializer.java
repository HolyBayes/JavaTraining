package com.noveogroup.java.serialize;

import java.io.*;

/**
 * blablabla
 * @author artem ryzhikov
 */
public class Serializer {

    private ObjectInputStream ois;
    private final boolean oisFlag = false;
    private ObjectOutputStream oos;

    public Serializer(final File input , final File output) {
        try {
            FileInputStream fis = new FileInputStream(input);
            FileOutputStream fos = new FileOutputStream(output);
            ois = new ObjectInputStream(fis);
            oos = new ObjectOutputStream(fos);
        } catch (IOException e){
        }
    }
    public void store(final Object obj) throws IOException {
        oos.writeObject(obj);
    }
    public Object load() throws IOException , ClassNotFoundException {
        return  ois.readObject();
    }

    @Override
    protected void finalize() throws Throwable {
        ois.close();
        oos.close();
        super.finalize();
    }

}
