package main.java.org.ce.ap.server.util;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Generic Tree class. Keep in mind that because each node is a subtree, nodes are also made with the Tree class.
 *
 * @param <T> Type of each Tree node
 */
public class Tree<T> implements Serializable {
    //leaves of the tree
    @JsonManagedReference
    private ArrayList<Tree<T>> leaves = new ArrayList<Tree<T>>();
    //parent of the tree
    @JsonBackReference
    private Tree<T> parent = null;
    //data of the tree
    @JsonProperty
    private T data;

    /**
     * constructs a new tree object with data
     *
     * @param data data of new tree
     */
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

    /**
     * add child tree to this tree
     *
     * @param childSubTree tree to be added as leaf
     */
    public void addChild(Tree<T> childSubTree) {
        leaves.add(childSubTree);
        childSubTree.setParent(this);
    }

    /**
     * getter method for data of the tree
     *
     * @return data of the tree
     */
    public T getData() {
        return data;
    }

    /**
     * getter method for parent of the tree
     *
     * @return parent of the tree
     */
    public Tree<T> getParent() {
        return parent;
    }

    /**
     * getter function for tree leaves
     *
     * @return leaves of the tree
     */
    public ArrayList<Tree<T>> getLeaves() {
        return leaves;
    }

    /**
     * setter function for tree parent
     *
     * @param parent new parent of tree
     */
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
