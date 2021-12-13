package main.java.org.ce.ap.server.util;

import java.util.Iterator;
import java.util.Stack;

public class TreeIterator<T> implements Iterator<T> {
    //source tree to be iterated
    private Tree<T> source;
    //stack that's used to hold levels of tree
    private Stack<Tree<T>> treeStack;

    /**
     * constructs new tree iterator
     *
     * @param tree tree to be iterated
     */
    public TreeIterator(Tree<T> tree) {
        source = tree;
        treeStack = new Stack<Tree<T>>();
        treeStack.push(source);
    }

    @Override
    public boolean hasNext() {
        return !(treeStack.empty());
    }

    @Override
    public T next() {
        return nextTree().getData();
    }

    public Tree<T> nextTree() {
        Tree<T> top = treeStack.pop();
        for (Tree<T> subtree : top.getLeaves()) {
            treeStack.push(subtree);
        }
        return top;
    }
}
