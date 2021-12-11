package main.java.org.ce.ap.server.util;

import java.util.Iterator;
import java.util.Stack;

public class TreeIterator<T> implements Iterator<T> {
    private Tree<T> source;
    private Stack<Tree<T>> treeStack;

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
