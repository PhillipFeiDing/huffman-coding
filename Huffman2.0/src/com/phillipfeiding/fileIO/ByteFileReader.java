package com.phillipfeiding.fileIO;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

public class ByteFileReader {

    /**
     * Open a byte file and return its byte representation
     * @param path the path to the file
     * @return byte array representing its content
     * @throws IOException if file operation is interrupted
     */
    public static byte[] read(String path) throws IOException {
        File file = new File(path);
        FileInputStream input = new FileInputStream(file);
        byte[] content = new byte[(int) file.length()];
        input.read(content);
        return content;
    }

    public static void main(String[] args) {
        String path = "data.dat";
        try {
            byte[] arr = ByteFileReader.read(path);
            System.out.println(Arrays.toString(arr));
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
