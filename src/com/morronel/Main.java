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

public class Main {

    public static boolean verify(String password, String path) {
        try {
            ZipFile zipFile = new ZipFile(new File(path));
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

    public static void main(String[] args) {
        //got to ask this from console
        String path;
        List<Character> list = new ArrayList<>();

        path = "/home/uucyc/Desktop/password.x";
        //adding lower case chars
        for (int i = 97; i < 123; i++) {
            list.add((char) i);
        }

        //adding numbers
//        for (int i = 48; i < 58; i++) {
//            list.add((char) i);
//        }

        //1 symbol checking loop
        int i = 0;
        for (Character symbol : list) {
            System.out.println(++i);
            if (verify(symbol.toString(), path)) {
                System.out.println("The pass is " + symbol.toString());
                return;
            }
        }
        //2 symbols checking loop
        for (Character symbol1 : list) {
            for (Character symbol2 : list) {
                System.out.println(++i);
                if (verify(symbol1.toString() + symbol2.toString(), path)) {
                    System.out.println("The pass is " + symbol1.toString() + symbol2.toString());
                    return;
                }
            }
        }//3 symbols checking loop
        for (Character symbol1 : list) {
            for (Character symbol2 : list) {
                for (Character symbol3 : list) {
                    System.out.println(++i);
                    if (verify(symbol1.toString() + symbol2.toString() + symbol3.toString(), path)) {
                        System.out.println("The pass is " + symbol1.toString() + symbol2.toString() + symbol3.toString());
                        return;
                    }
                }
            }
        }//4 symbols checking loop
        for (Character symbol1 : list) {
            for (Character symbol2 : list) {
                for (Character symbol3 : list) {
                    for (Character symbol4 : list) {
                        System.out.println(++i);
                        if (verify(symbol1.toString() + symbol2.toString() + symbol3.toString() + symbol4.toString(), path)) {
                            System.out.println("The pass is " + symbol1.toString() + symbol2.toString() + symbol3.toString() + symbol4.toString());
                            return;
                        }
                    }
                }
            }
        }
    }
}
