package com.noveogroup.java.serialize;

import java.io.*;
import java.util.List;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * blablabla
 * @author artem ryzhikov
 */
public class Serializer {

//    private ObjectInputStream ois;
//    private ObjectOutputStream oos;
    private final File input;
    private final File output;
    ObjectInputStream ois;
    private final static Logger LOG = Logger.getLogger(Serializer.class.getName());

    public Serializer(final File input , final File output) {

        this.input = input;
        this.output = output;try {
            ois = new ObjectInputStream(new FileInputStream(input));
        } catch (FileNotFoundException e) {
            LOG.log(Level.SEVERE , e.getMessage() , e);
        } catch (IOException e) {
            LOG.log(Level.SEVERE , e.getMessage() , e);
        }
    }
    public void store(final Queue<Object> obj) throws IOException {
        FileOutputStream fos = new FileOutputStream(output , true);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        try {
            for(int i = 0 , limit = obj.size(); i < limit; i++) {
                oos.writeObject(obj.poll());
            }
        } finally {
            if (oos !=null) {
                oos.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
    }
    public Object load() throws IOException , ClassNotFoundException {
        Object obj;
        obj = ois.readObject();
        return  ois.readObject();
    }
    public void close() {
        try {
            if(ois != null) {
                ois.close();
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE , e.getMessage() , e);
        }
    }
}
