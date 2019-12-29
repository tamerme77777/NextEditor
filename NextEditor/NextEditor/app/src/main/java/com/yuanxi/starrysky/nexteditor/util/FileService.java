package com.yuanxi.starrysky.nexteditor.util;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class FileService {

    private FileService(){

    }

    public static String read(File file) throws IOException {
        String str = "";
        if(file.exists()){
            FileInputStream fileInputStream=new FileInputStream(file);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len =0;
            while((len=fileInputStream.read(buffer))!=-1){
                outputStream.write(buffer, 0, len);
            }
            byte[] bytes = outputStream.toByteArray();
            str = new String(bytes);
            fileInputStream.close();
        }
        return str;
    }

    public static void write(String content, File file) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(fos));
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
