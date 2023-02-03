package com.example.jobbox.config.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtil {

    public static String uploadFile(MultipartFile file,  String hashFilename) {
        String path = "src/main/resources/static/";
        try{

            File targetFile = new File(path);

            if (targetFile.exists()) {
                targetFile.mkdirs();
            }
            path += hashFilename;

            FileOutputStream out = new FileOutputStream(path);
            out.write(file.getBytes());
            out.flush();
            out.close();
        } catch (IOException e){
            e.printStackTrace();
        }

        return path;
    }

    public static void deleteFile(String path) {
        try {
            Files.delete(Paths.get(path));
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }
}
