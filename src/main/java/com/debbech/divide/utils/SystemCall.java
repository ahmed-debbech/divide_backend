package com.debbech.divide.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class SystemCall {

    @Value("${divide.image_upload_path}")
    private String upload_path;

    public void createFile(String name, byte[] bytes) throws Exception{
        String full = upload_path+name;
        File file = new File(full);
        try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
            outputStream.write(bytes);
        } catch (Exception e) {
            throw new Exception("could not create file");
        }
    }

    public String getFullPath(String name){
        String full = upload_path+name;
        return full;
    }
    public byte[] getFile(String name) throws Exception{
        byte[] fileContent;
        try {
            fileContent = Files.readAllBytes(Paths.get(getFullPath(name)));
        }catch (Exception e){
            throw new Exception("could not find image to create thumbnail for");
        }
        return fileContent;
    }
}
