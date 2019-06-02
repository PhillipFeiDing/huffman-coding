package com.phillipfeiding.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanTree {

    private TreeNode root;
    private Map<Character, Integer> dict;
    private Map<Character, String> scheme;
    private boolean encoding;

    /**
     * The constructor for a com.phillipfeiding.domain.HuffmanTree called by an encoder
     * @param message the message used for constructing a Huffman tree
     */
    public HuffmanTree(String message) {
        if (message == null || message.isEmpty()) {
            throw new IllegalArgumentException("The given message is null"
                    + " or empty.");
        }

        Map<Character, Integer> dict = new HashMap<>();
        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);
            if (dict.containsKey(c)) {
                dict.put(c, dict.get(c) + 1);
            } else {
                dict.put(c, 1);
            }
        }

        this.dict = dict;

        PriorityQueue<TreeNode> pq = new PriorityQueue<>();
        for (Character symbol : dict.keySet()) {
            Integer count = dict.get(symbol);
            if (count == null || symbol == null) {
                throw new IllegalArgumentException("The given Map for character"
                + " counts contains null keys or entries.");
            }
            pq.add(new TreeNode(symbol, dict.get(symbol)));
        }

        while (pq.size() > 1) {
            TreeNode left = pq.remove();
            TreeNode right = pq.remove();
            TreeNode parent = new TreeNode(left.getCount() + right.getCount());
            parent.setLeft(left);
            parent.setRight(right);
            pq.add(parent);
        }

        root = pq.remove();
        if (root.getSymbol() != null) {
            TreeNode newRoot = new TreeNode(root.getCount());
            newRoot.setLeft(root);
            root = newRoot;
        }

        scheme = new HashMap<>();
        traverse(root, scheme, "");
        encoding = true;
    }

    /**
     * The constructor for a Huffman tree called by a decoder
     */
    public HuffmanTree(Map<Character, String> scheme) {
        if (scheme == null || scheme.size() == 0) {
            throw new IllegalArgumentException(
                    "Scheme given is empty or null.");
        }
        this.scheme = scheme;

        root = new TreeNode(null, 0);
        for (Character c : scheme.keySet()) {
            String s = scheme.get(c);
            TreeNode curr = root;
            for (int i = 0; i < s.length(); i++) {
                Character symbol = (i == s.length() - 1) ? c : null;
                if (s.charAt(i) == '0') {
                    if (curr.getLeft() == null) {
                        curr.setLeft(new TreeNode(symbol, 0));
                    }
                    curr = curr.getLeft();
                } else {
                    if (curr.getRight() == null) {
                        curr.setRight(new TreeNode(symbol, 0));
                    }
                    curr = curr.getRight();
                }
            }
        }

        encoding = false;
    }

    /**
     * getter for the root of the Huffman tree
     * @return the root of the Huffman tree
     */
    public TreeNode getRoot() {
        return root;
    }

    /**
     * get the encoding scheme (a map from character to binary string) given
     * the associated Huffman tree
     * @return the encoding scheme
     */
    public Map<Character, String> getEncodingScheme() {
        return scheme;
    }

    /**
     * private recursive helper method for calculating encoding scheme, traverse
     * the whole Huffman tree to get the encoding scheme
     * @param curr the currnet node traversed
     * @param scheme the scheme
     * @param prefix current prefix for encoding to consider
     */
    private void traverse(TreeNode curr, Map<Character, String> scheme,
                          String prefix) {
        if (curr == null) {
            return;
        }
        if (curr.getSymbol() != null) { // real base case
            scheme.put(curr.getSymbol(), prefix);
        } else {
            traverse(curr.getLeft(), scheme, prefix + "0");
            traverse(curr.getRight(), scheme, prefix + "1");
        }
    }

    /**
     * get the length of the byte array needed to encode the message
     * @return the length of the byte array needed
     */
    public int getByteCount() {
        if (!encoding) {
            throw new IllegalArgumentException("getByteCount can only be called"
            + " on a Huffman tree constructed by encoder.");
        }
        int bitCount = 0;
        for (Character c : dict.keySet()) {
            bitCount += dict.get(c) * scheme.get(c).length();
        }
        // System.out.println("Bit count: " + bitCount);
        return (int) Math.ceil((double) bitCount / 8);
    }
}
