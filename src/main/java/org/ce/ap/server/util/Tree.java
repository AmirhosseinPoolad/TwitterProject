package main.java.org.ce.ap.server.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * Generic Tree class. Keep in mind that because each node is a subtree, nodes are also made with the Tree class.
 *
 * @param <T> Type of each Tree node
 */
public class Tree<T> {
    private ArrayList<Tree<T>> leaves = new ArrayList<Tree<T>>();
    private Tree<T> parent = null;
    private T data;

    public Tree(T data) {
        this.data = data;
        this.parent = null;
    }

    /**
     * search for data in the current Tree object
     *
     * @param data data to search for
     * @return Tree with data if found, null if not found.
     */
    public Tree<T> get(T data) {
        TreeIterator it = new TreeIterator(this);
        while (it.hasNext()) {
            Tree<T> next = it.nextTree();
            if (next.getData().equals(data))
                return next;
        }
        return null;
    }

    public void addChild(Tree<T> childSubTree) {
        leaves.add(childSubTree);
        childSubTree.setParent(this);
    }

    public T getData() {
        return data;
    }

    public Tree<T> getParent() {
        return parent;
    }

    public ArrayList<Tree<T>> getLeaves() {
        return leaves;
    }

    public void setParent(Tree<T> parent) {
        this.parent = parent;
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
