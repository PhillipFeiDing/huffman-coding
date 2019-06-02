package com.phillipfeiding.domain;

import java.util.Map;

public class Encoder {

    private byte[] encoded = null;
    private Map<Character, String> scheme = null;
    private int finalCut = 8;

    /**
     * Encode a message to byte array using Huffman encoding scheme
     * @param message the string message to be encoded
     */
    public void encode(String message) {

        HuffmanTree tree = new HuffmanTree(message);
        scheme = tree.getEncodingScheme();

        // * For debugging purpose only
        // System.out.println("Encoding scheme:");
        // System.out.println(scheme);

        // System.out.println("Encoded message:");
        // String bitString = "";
        // for (int i = 0; i < message.length(); i++) {
        //     bitString += scheme.get(message.charAt(i));
        // }
        // for (int i = 0; i < bitString.length(); i += 8) {
        //     System.out.print(
        //             bitString.substring(i, Math.min(i + 8, bitString.length()))
        //             + " "
        //     );
        // }
        // System.out.println();

        encoded = new byte[tree.getByteCount()];
        int curr = 0;
        int currByte = 0;
        int bitCount = 0;
        for (int i = 0; i < message.length(); i++) {
            String s = scheme.get(message.charAt(i));
            for (int j = 0; j < s.length(); j++) {
                boolean bit = s.charAt(j) != '0';
                currByte *= 2;
                currByte += bit ? 1 : 0;
                bitCount++;
                if (bitCount == 8
                        || i == message.length() - 1 && j == s.length() - 1) {
                    encoded[curr] = (byte) (currByte - 128);
                    currByte = 0;
                    finalCut = bitCount;
                    bitCount = 0;
                    curr++;
                }
            }
        }
    }

    /**
     * getter for the encoded message
     * @return encoded message as byte array
     */
    public byte[] getEncoded() {
        return encoded;
    }

    /**
     * getter for the encoding scheme
     * @return encoding scheme as a character to string map
     */
    public Map<Character, String> getScheme() {
        return scheme;
    }

    /**
     * get the final cut position for the encoded message
     * @return the final cut position
     */
    public int getFinalCut() {
        return finalCut;
    }
}
