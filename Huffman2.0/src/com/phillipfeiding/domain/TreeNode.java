package com.phillipfeiding.domain;

/**
 * The node class for constructing a Huffman tree.
 */
public class TreeNode implements Comparable<TreeNode> {

    private Character symbol;
    private int count;
    private TreeNode left;
    private TreeNode right;

    /**
     * constructor for a single symbol
     * @param symbol the symbol to represent
     * @param count the count of the symbol
     */
    public TreeNode(Character symbol, int count) {
        this.symbol = symbol;
        this.count = count;
        left = null;
        right = null;
    }

    /**
     * constructor for a composite symbol
     * @param count the count of the the composite symbol
     */
    public TreeNode(int count) {
        this(null, count);
    }

    // getters and setters

    /**
     * getter for the symbol the node represents
     * @return the symbol the node represents
     */
    public Character getSymbol() {
        return symbol;
    }
    /**
     * getter for the count of the symbol
     * @return the count of the symbol
     */
    public int getCount() {
        return count;
    }

    /**
     * getter for the left child of the node
     * @return left child of the node
     */
    public TreeNode getLeft() {
        return left;
    }

    /**
     * getter for the right child of the node
     * @return right child of the node
     */
    public TreeNode getRight() {
        return right;
    }

    /**
     * convert a node to String
     * @return the string representation to the node
     */
    @Override
    public String toString() {
        return symbol == null ? "* : " + count : symbol + " : " + count;
    }

    /**
     * setter for the left child of the node
     * @param left new left child of the node
     */
    public void setLeft(TreeNode left) {
        this.left = left;
    }

    /**
     * setter for the right child of the node
     * @param right new right child of the node
     */
    public void setRight(TreeNode right) {
        this.right = right;
    }

    @Override
    public int compareTo(TreeNode other) {
        if (count != other.count) {
            return count - other.count;
        }
        if (symbol == null && other.symbol != null) {
            return 1;
        }
        if (symbol != null && other.symbol == null) {
            return -1;
        }
        if (symbol == null && other.symbol == null) {
            return 0;
        }
        return symbol - other.symbol;
    }
}
