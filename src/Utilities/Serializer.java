package Utilities;
import java.io.*;

public class Serializer<T>{

    public T loadObject(File file) throws IOException, ClassNotFoundException {

        FileInputStream fileIn = new FileInputStream(file);
        ObjectInputStream objectIn = new ObjectInputStream(fileIn);

        T object = (T) objectIn.readObject();

        fileIn.close();
        objectIn.close();

        return object;
    }
    public void saveObject(T object, File file) throws IOException {

        FileOutputStream fileOut = new FileOutputStream(file);
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);

        objectOut.writeObject(object);

        fileOut.close();
        objectOut.close();

    }
}
