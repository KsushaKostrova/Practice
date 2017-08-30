import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.HashMap;
import java.util.Map;

public class BaseClass implements Serializable{

    Map map;

    public void addToMap(Object key, Object value) {
        map.put(key, value);
    }

    public static BaseClass readFromFile(String fileName){

        BaseClass b = new BaseClass();
        File file = new File(fileName);

        RandomAccessFile serFile = null;
        FileChannel fileChannel = null;
        byte[] bytes = null;

        try {
            serFile = new RandomAccessFile(file, "rw");
            fileChannel = serFile.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate((int) serFile.length());
            FileLock fileLock = fileChannel.lock();
            fileChannel.read(buffer);
            fileLock.release();
            bytes = buffer.array();
            buffer.clear();
        } catch (Exception cause) {
            cause.printStackTrace();
        } finally {
            closeStream(fileChannel);
            closeStream(serFile);
        }

        ByteArrayInputStream byteArrayInputStream = null;
        ObjectInputStream objectInputStream = null;

        try {
            byteArrayInputStream = new ByteArrayInputStream(bytes);
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            b = (BaseClass) objectInputStream.readObject();
        } catch (Exception cause) {
            cause.printStackTrace();
        } finally {
            closeStream(objectInputStream);
            closeStream(byteArrayInputStream);
        }
        return b;
    }

    public void writeToFile(String fileName){
        File file = new File(fileName);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(this);
        } catch (Exception cause) {
            cause.printStackTrace();
        } finally {
            closeStream(objectOutputStream);
        }

        RandomAccessFile serFile = null;
        FileChannel fileChannel = null;

        try {
            byte[] source = byteArrayOutputStream.toByteArray();
            serFile = new RandomAccessFile(file, "rw");
            fileChannel = serFile.getChannel();
            ByteBuffer buffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, source.length);
            FileLock fileLock = fileChannel.lock();
            buffer.put(source);
            fileLock.release();
            buffer.clear();
        } catch (Exception cause) {
            cause.printStackTrace();
        } finally {
            closeStream(byteArrayOutputStream);
            closeStream(fileChannel);
            closeStream(serFile);
        }
    }

    private static void closeStream(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (Exception cause) {
            cause.printStackTrace();
        }
    }
}

