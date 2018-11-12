package com.morronel;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.exception.ZipExceptionConstants;
import net.lingala.zip4j.model.FileHeader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MyRunnable implements Runnable {

    private List<Character> charactersArrayList, splitedArray;
    private final String path = "/home/uucyc/Desktop/gli.zip";

    MyRunnable(List<Character> characterArrayList, List<Character> splitedrray) {
        this.charactersArrayList = characterArrayList;
        this.splitedArray = splitedrray;
    }


    public boolean verify(String password, String path) {
        try {
            net.lingala.zip4j.core.ZipFile zipFile = new ZipFile(new File(path));
            if (zipFile.isEncrypted()) {
                zipFile.setPassword(password);
            }
            List<FileHeader> fileHeaders = zipFile.getFileHeaders();

            for (FileHeader fileHeader : fileHeaders) {
                try {
                    InputStream is = zipFile.getInputStream(fileHeader);
                    byte[] b = new byte[4 * 4096];
                    while (is.read(b) != -1) {
                        //Do nothing as we just want to verify password
                    }
                    is.close();
                } catch (ZipException e) {
                    if (e.getCode() == ZipExceptionConstants.WRONG_PASSWORD) {
                        return false;
                    } else {
                        //Corrupt file
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    return false;
                }
            }

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public void run() {

        for (Character symbol : splitedArray) {
            System.out.println(symbol.toString());
            if (verify(symbol.toString(), path)) {
                System.out.println("The pass is " + symbol.toString());
                return;
            }
        }
        for (Character symbol1 : splitedArray) {
            for (Character symbol2 : charactersArrayList) {
                System.out.println(symbol1.toString() + symbol2.toString());
                if (verify(symbol1.toString() + symbol2.toString(), path)) {
                    System.out.println("The pass is " + symbol1.toString() + symbol2.toString());
                    return;
                }
            }
        }
    }
}

