package com.phillipfeiding.domain;

import com.phillipfeiding.fileIO.ByteFileReader;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class Decoder {

    private HuffmanTree tree;
    private byte[] data;
    private int finalCut;
    private boolean[] cache;
    private static final String ERR_MSG =
            "给定文件不是由配套压缩器压缩的，\n"
            + "或者其内容被修改了。";

    /**
     * Constructor
     * @param raw encoded file in byte array
     */
    public Decoder(byte[] raw) {
        if (raw == null) {
            throw new IllegalArgumentException("给定文件可能是空的，无法解压空文件。");
        }

        int index = findZero(raw);
        byte[] front = Arrays.copyOf(raw, index);
        data = Arrays.copyOfRange(raw, index + 1, raw.length - 1);
        finalCut = raw[raw.length - 1];
        if (finalCut < 0 || finalCut >= 8) {
            throw new IllegalArgumentException(ERR_MSG);
        }
        String s = new String(front);
        JSONParser parser = new JSONParser();

        JSONObject json = null;
        try {
            json = (JSONObject) parser.parse(s);
        } catch (ParseException e) {
            throw new IllegalArgumentException(ERR_MSG);
        }

        Map<Character, String> scheme = new HashMap<>();
        for (Object key : json.keySet()) {
            Object value = json.get(key);
            if (key.toString().length() != 1) {
                throw new IllegalArgumentException(ERR_MSG);
            }
            scheme.put(key.toString().charAt(0), value.toString());
        }

        tree = new HuffmanTree(scheme);
        cache = new boolean[8];
    }

    /**
     * decode the encoded the file
     * @return the String content of the file
     */
    public String decode() {

        // System.out.println(finalCut);

        Queue<Boolean> queue = new ArrayDeque<>();
        StringBuilder builder = new StringBuilder();
        int idx = 0;
        while (idx < data.length || !queue.isEmpty()) {
            TreeNode curr = tree.getRoot();
            while (curr.getSymbol() == null) {
                if (queue.isEmpty()) {
                    enqueue(data, idx, queue);
                    idx++;
                }
                curr = queue.remove() ? curr.getRight() : curr.getLeft();
                if (curr == null) {
                    throw new IllegalArgumentException(ERR_MSG);
                }
            }
            builder.append(curr.getSymbol());
        }

        return builder.toString();
    }

    /**
     * to enqueue a byte if the queue is empty
     * @param data the byte array
     * @param index the index of the element to be enqueued
     * @param queue the queue used for decoding
     */
    private void enqueue(byte[] data, int index, Queue<Boolean> queue) {
        try {
            toBooleanArray((int) data[index] + 128);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException(ERR_MSG);
        }

        int start = (index == data.length - 1) ? 8 - finalCut : 0;
        for (int i = start; i < 8; i++) {
            queue.add(cache[i]);
        }
    }

    /**
     * Convert a byte into a boolean array and store bits in "cache"
     * @param b the byte to be converted
     */
    private void toBooleanArray(int b) {
        // check each bit in the byte. if 1 set to true, if 0 set to false
        for (int i = 0; i < 8; i++) {
            cache[8 - i - 1] = b % 2 == 1;
            b = b / 2;
        }
    }

    /**
     * Find the white space separator used to separate the dictionary and actual
     * content in the byte array
     * @param raw the byte array given
     * @return the position of the white space separator
     */
    private int findZero(byte[] raw) {
        for (int i = 1; i < raw.length; i++) {
            if (raw[i] == 0 && raw[i - 1] == '}') {
                return i;
            }
        }
        throw new IllegalArgumentException(ERR_MSG);
    }

    public static void main(String[] args) {
        try {
            byte[] data = ByteFileReader.read("data.dat");
            Decoder decoder = new Decoder(data);
            System.out.println(decoder.decode());
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
