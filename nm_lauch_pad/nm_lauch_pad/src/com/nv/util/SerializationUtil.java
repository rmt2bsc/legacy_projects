package com.nv.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Utility class that provides common functionality for persisting object
 * (Serialization/deserialization).
 * 
 * @author rterrell
 *
 */
public class SerializationUtil {

    /**
     * Creates a AppUtil.java object.
     */
    public SerializationUtil() {
        return;
    }

    /**
     * Saves the state of an object to disk.
     * 
     * @param obj
     *            the object that is to be serialized
     * @param fileName
     *            the path and file name where the object is to be persisted
     * @throws ObjectSerializationException
     */
    public static final void backupObject(Serializable obj, String fileName)
            throws ObjectSerializationException {
        String msg = null;
        try {
            // Serialize to a file
            ObjectOutput out = new ObjectOutputStream(new FileOutputStream(
                    fileName));
            out.writeObject(obj);
            out.close();
        } catch (IOException e) {
            throw new ObjectSerializationException(msg, e);
        }
    }

    /**
     * Restores the state of an object from disk.
     * 
     * @param fileName
     *            the path and file name of the object serialized source.
     * @return an instance of {@link Serializable}
     * @throws ObjectSerializationException
     */
    public static final Serializable restoreObject(String fileName)
            throws ObjectSerializationException {
        Serializable obj = null;
        String msg = null;

        // Read from disk using FileInputStream
        try {
            FileInputStream f_in = new FileInputStream(fileName);
            // Read object using ObjectInputStream
            ObjectInputStream obj_in = new ObjectInputStream(f_in);

            // Read an object
            obj = (Serializable) obj_in.readObject();
            return obj;
        } catch (FileNotFoundException e) {
            throw new ObjectSerializationException(msg, e);
        } catch (ClassNotFoundException e) {
            throw new ObjectSerializationException(msg, e);
        } catch (IOException e) {
            throw new ObjectSerializationException(msg, e);
        }
    }

}
