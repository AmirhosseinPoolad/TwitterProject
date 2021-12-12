package main.java.org.ce.ap.server.util;

import main.java.org.ce.ap.server.ByteSerializable;
import main.java.org.ce.ap.server.Tweet;

import java.io.*;

public class TreeIO<T extends ByteSerializable> {
    /**
     * serializes a tree in the out stream
     *
     * @param tree tree to be serialized
     * @param out  output stream
     */
    public void writeTree(Tree<T> tree, BufferedWriter out) {
        try {
            /*out.write(tree.getData().getBytes());
            out.write((tree.getLeaves().size()));
            for (Tree<T> leaves : tree.getLeaves()) {
                writeTree(leaves, out);*/
            /*out.writeObject(tree);
            out.writeObject(3);
            for (Tree<T> leaves : tree.getLeaves()) {
                writeTree(leaves, out);
            }
            out.writeObject("___ENDOFSTREAM___");*/
            tree.getData().writeToFile(out);
            out.write(String.valueOf(tree.getLeaves().size()));
            out.newLine();
            for (Tree<T> leaves : tree.getLeaves()) {
                writeTree(leaves, out);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * deserializes a tree from the in stream
     *
     * @param in input stream
     * @return tree object
     */
    public Tree<T> readTree(BufferedReader in, String firstLine) {
        /*Tree<T> head = null;
        try {
            Object obj = in.readObject();
            if (obj instanceof String && ((String) obj).equals("___ENDOFSTREAM___")) {
                return head;
            }
            head = (Tree<T>) obj;
            Integer childCount = (Integer) in.readObject();
            for (int i = 0; i < childCount; i++) {
                head.addChild(readTree(in));
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return head;*/
        Tree<T> head = null;
        Tweet t = new Tweet(null,null);
        try {
            String line;
            line = firstLine;
            int childs = 0;
            if (line != null) {
                head = (Tree<T>) new Tree<>(t.readFromFile(in, line));
                childs = Integer.parseInt(in.readLine());
            } else {
                return null;
            }
            for (int i = 0; i < childs; i++) {
                String fLine = in.readLine();
                head.addChild(readTree(in, fLine));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return head;
    }
}
