package main.java.org.ce.ap.server.util;

import java.util.Iterator;
import java.util.Stack;

/**
 * Iterates a tree depth first
 *
 * @param <T>
 */
public class TreeIterator<T> implements Iterator<T> {
    //source tree to be iterated
    private Tree<T> source;
    //stack that's used to hold levels of tree
    private Stack<Tree<T>> treeStack;
    //stacks that's used to hold depth of each level
    private Stack<Integer> depthStack;

    /**
     * constructs new tree iterator
     *
     * @param tree tree to be iterated
     */
    public TreeIterator(Tree<T> tree) {
        source = tree;
        treeStack = new Stack<Tree<T>>();
        treeStack.push(source);
        depthStack = new Stack<Integer>();
        depthStack.push(0);
    }

    @Override
    public boolean hasNext() {
        return !(treeStack.empty());
    }

    @Override
    public T next() {
        return nextTree().getData();
    }

    /**
     * returns depth of nextTree() without iterating forward
     *
     * @return depth of nextTree()
     */
    public int getNextDepth() {
        return depthStack.peek();
    }

    public Tree<T> nextTree() {
        Tree<T> top = treeStack.pop();
        int lastDepth = depthStack.pop();
        for (Tree<T> subtree : top.getLeaves()) {
            treeStack.push(subtree);
            depthStack.push(lastDepth + 1);
        }
        return top;
    }
}
