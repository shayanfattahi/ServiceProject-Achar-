package com.example.upgradeservice.service;

import com.example.upgradeservice.model.PhotoTec;
import com.example.upgradeservice.repository.PhotoTecRepo;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class PhotoTecService {
    final PhotoTecRepo photoTecRepo;

    public PhotoTecService(PhotoTecRepo photoTecRepo) {
        this.photoTecRepo = photoTecRepo;
    }

    public void insertPhoto(PhotoTec photoTec){
        File file = new File("C:/Users/Ava/Desktop/Untitled.png");
//        String Filename = file.getName();
//        int i = file.toString().lastIndexOf(".");
//        String format;
//        if (i == -1){
//            format = null;
//        }else {
//            format = Filename.substring(i + 1);
//        }
//        if(format.equals("jpg")) {
//            photoTec.setImage(imageTOByte(file));
//        }
        photoTec.setImage(imageTOByte(file));
        photoTecRepo.save(photoTec);

    }

    public static byte[] imageTOByte(File imageFile){
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(imageFile);
        }catch (FileNotFoundException e){
            throw new RuntimeException(e);
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        try{
            for (int i ; (i = fis.read(buf)) != -1;){
                bos.write(buf , 0 , i);
            }
        }catch (IOException e){

        }
        return bos.toByteArray();
    }
}
