package com.noveogroup.java.serialize;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * blablabla.
 * @author artem ryzhikov
 */
public class Serializer {
    private static final Logger LOG = Logger.getLogger(Serializer.class.getName());
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    public Serializer(final File input, final File output) {
        try {
            oos = new ObjectOutputStream(new FileOutputStream(output));
            ois = new ObjectInputStream(new FileInputStream(input));
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "IOException Serializer() :", e);
        }
    }
    public void store(final Object obj) throws IOException {
        if (oos == null) {
            throw new IOException("ObjectOutputStream is null");
        } else {
            oos.writeObject(obj);
        }
    }
    public Object load() throws IOException , ClassNotFoundException {
        if (ois == null) {
            throw new IOException("ObjectInputStream is null");
        }
        final Object result = ois.readObject();
        return result;
    }
    public void close() {
        try {
            ois.close();
            oos.close();
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "IOException in ObjectStream.close(): ", e);
        }
    }
}
