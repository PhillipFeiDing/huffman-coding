package com.phillipfeiding.fileIO;

import java.io.*;
import java.util.Map;
import org.json.simple.*;

public class ByteFileWriter {

    /**
     * write the data along with its encoding scheme to a designated path
     * @param path the path to write the encoded file
     * @param data encoded data
     * @param scheme the scheme used for encoding
     * @param finalCut the final cutting position
     * @throws IOException when error occurs operating on the file
     */
    public static void write(String path, byte[] data,
                             Map<Character, String> scheme, int finalCut)
    throws IOException {
        JSONObject json = new JSONObject();
        for (Character c : scheme.keySet()) {
            json.put(c.toString(), scheme.get(c));
        }

        byte[] dict = json.toJSONString().getBytes();

        FileOutputStream fos = new FileOutputStream(path);
        BufferedOutputStream out = new BufferedOutputStream(fos);
        out.write(dict);
        out.write((byte) 0);
        out.write(data);
        out.write((byte) finalCut);
        out.flush();
        fos.close();
    }
}
