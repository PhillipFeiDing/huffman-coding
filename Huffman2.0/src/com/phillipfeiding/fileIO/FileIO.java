package com.phillipfeiding.fileIO;

import java.io.*;

import com.phillipfeiding.domain.*;


public class FileIO {

    public static String read(String path) throws IOException {
        File file = new File(path);
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String newLine = reader.readLine();
        while (newLine != null) {
            builder.append(newLine);
            builder.append(System.getProperty("line.separator"));
            newLine = reader.readLine();
        }
        reader.close();
        return builder.toString();
    }

    public static void write(String path, String s) throws IOException {
        File file = new File(path);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(s);
        writer.close();
    }



    public static void main(String[] args) {
        String inputNoExtension = "special";
        String inputFileName = inputNoExtension + ".txt";
        String compressedName = inputNoExtension + "_compressed.dat";
        String outputFileName = inputNoExtension + "_regenerated.txt";
        long start = 0;
        long end = 0;

        start = System.currentTimeMillis();
        String message = null;
        try {
            message = FileIO.read(inputFileName);
        } catch (IOException e) {
            System.out.println("ERROR: " + e);
            System.exit(1);
        } finally {
            if (message == null) {
                System.out.println(
                        "Failed to read the file for unknown reasons.");
            }
        }
        end = System.currentTimeMillis();
        System.out.println("Read file: " + (end - start) + " ms.");

        start = System.currentTimeMillis();
        Encoder encoder = new Encoder();
        encoder.encode(message);
        end = System.currentTimeMillis();
        System.out.println("Encode: " + (end - start) + " ms.");

        start = System.currentTimeMillis();
        try {
            ByteFileWriter.write(compressedName, encoder.getEncoded(),
                    encoder.getScheme(), encoder.getFinalCut());
            // System.out.println(Arrays.toString(encoder.getEncoded()));
            // System.out.println(encoder.getScheme());
        } catch (IOException e) {
            System.out.println("ERROR: " + e);
            System.exit(1);
        }
        end = System.currentTimeMillis();
        System.out.println("Write encoded: " + (end - start) + " ms.");

        start = System.currentTimeMillis();
        String s = null;
        try {
            Decoder decoder = new Decoder(ByteFileReader.read(compressedName));
            s = decoder.decode();
        } catch (IOException e) {
            System.out.println("ERROR: " + e);
            System.exit(1);
        } finally {
            if (s == null) {
                System.out.println("Failed to decode the message");
            }
        }
        end = System.currentTimeMillis();
        System.out.println("Decode: " + (end - start) + " ms.");

        start = System.currentTimeMillis();
        try {
            FileIO.write(outputFileName, s);
            // System.out.println(s);
        } catch (IOException e) {
            System.out.println("ERROR: " + e);
            System.exit(1);
        }
        end = System.currentTimeMillis();
        System.out.println("Write decoded: " + (end - start) + " ms.");
    }
}
