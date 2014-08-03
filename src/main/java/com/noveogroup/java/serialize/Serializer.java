package com.noveogroup.java.serialize;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * blablabla
 * @author artem ryzhikov
 */
public class Serializer {

    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    private final static Logger LOG = Logger.getLogger(Serializer.class.getName());

    public Serializer(final File input , final File output) {
        try {
            ois = new ObjectInputStream(new FileInputStream(input));
            oos = new ObjectOutputStream(new FileOutputStream(output));
        } catch (IOException e){
            LOG.log(Level.SEVERE, "IOException Serializer() :", e);
        }
    }
    public void store(final Object obj) throws IOException {
        if(oos == null) {
            throw new IOException("ObjectOutputStream is null");
        }
        else {
            oos.writeObject(obj);
        }
    }
    public Object load() throws IOException , ClassNotFoundException {
        if(ois == null){
            throw new IOException("ObjectInputStream is null");
        }
        return  ois.readObject();
    }

    public void close() {
        try{
            ois.close();
            oos.close();
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "IOException in ObjectStream.close(): ", e);
        }
    }

}
