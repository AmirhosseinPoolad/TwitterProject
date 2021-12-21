package main.java.org.ce.ap.server.util;

import main.java.org.ce.ap.server.services.ByteSerializable;
import main.java.org.ce.ap.server.entity.Tweet;

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
        Tree<T> head = null;
        Tweet t = new Tweet(null, null,0);
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
