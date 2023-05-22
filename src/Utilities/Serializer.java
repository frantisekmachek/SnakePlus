package Utilities;
import java.io.*;

/**
 * Can save or load an object via serialization.
 * @param <T> The generic class
 */
public class Serializer<T>{

    /**
     * Loads an object from a file.
     * @param file The file being loaded
     * @return The object loaded
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public T loadObject(File file) throws IOException, ClassNotFoundException {

        FileInputStream fileIn = new FileInputStream(file);
        ObjectInputStream objectIn = new ObjectInputStream(fileIn);

        T object = (T) objectIn.readObject();

        fileIn.close();
        objectIn.close();

        return object;
    }

    /**
     * Saves an object to a file.
     * @param object The object being saved
     * @param file The file where the object will be saved
     * @throws IOException
     */
    public void saveObject(T object, File file) throws IOException {

        FileOutputStream fileOut = new FileOutputStream(file);
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);

        objectOut.writeObject(object);

        fileOut.close();
        objectOut.close();

    }
}
