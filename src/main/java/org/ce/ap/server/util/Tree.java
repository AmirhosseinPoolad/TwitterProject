package main.java.org.ce.ap.server.util;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Generic Tree class. Keep in mind that because each node is a subtree, nodes are also made with the Tree class.
 *
 * @param <T> Type of each Tree node
 */
public class Tree<T> {
    private ArrayList<Tree<T>> leaves = new ArrayList<Tree<T>>();
    private Tree<T> parent = null;
    private T data;

    public Tree(T data, Tree parent) {
        this.data = data;
        this.parent = parent;
    }

    /**
     * search for data in the current Tree object
     *
     * @param data data to search for
     * @return Tree with data if found, null if not found.
     */
    public Tree<T> get(T data) {
        if (this.data.equals(data))
            return this;
        Tree<T> result = null;
        for(int i = 0; (result != null) && (i < leaves.size()); i++){
            result = leaves.get(i).get(data);
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tree<?> tree = (Tree<?>) o;
        return data.equals(tree.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }
}
