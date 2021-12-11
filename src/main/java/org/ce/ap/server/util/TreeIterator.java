package main.java.org.ce.ap.server.util;

import java.util.Iterator;
import java.util.Stack;

public class TreeIterator<T> implements Iterator<T> {
    private Tree<T> source;
    private Tree<T> currentNode;
    private Stack<Tree<T>> treeStack;

    public TreeIterator(Tree<T> tree) {
        source = tree;
        currentNode = source;
        treeStack = new Stack<Tree<T>>();
        treeStack.push(source);
    }

    @Override
    public boolean hasNext() {
        return !(currentNode == null);
    }

    @Override
    public T next() {
        Tree<T> top = treeStack.pop();
        for (Tree<T> subtree : top.getLeaves()) {
            treeStack.push(subtree);
        }
        return top.getData();
    }
}
