package main.java.org.ce.ap.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;

public interface ByteSerializable {
    byte[] getBytes();

    void fromBytes(byte[] bytes);

    void writeToFile(BufferedWriter out);

    public ByteSerializable readFromFile(BufferedReader in, String firstLine);
}
