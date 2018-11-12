package com.morronel;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.exception.ZipExceptionConstants;
import net.lingala.zip4j.model.FileHeader;

import java.io.*;
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
        List<Character> characterArrayList = new ArrayList<>();

        path = "/home/uucyc/Desktop/gli.zip";

        File fileToCopy  = new File(path);



        try {
            copyFile(fileToCopy, new File("/home/uucyc/Desktop/testJavazip.zip"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //adding lowercase chars
        for (int i = 97; i < 123; i++) {
            characterArrayList.add((char) i);
        }

        //adding uppercase chars
        for (int i = 65; i < 91; i++) {
            characterArrayList.add((char) i);
        }

        //adding numbers
        for (int i = 48; i < 58; i++) {
            characterArrayList.add((char) i);
        }

        List<Character> firstThreadList = characterArrayList.subList(0, characterArrayList.size()/2);
        List<Character> secondThreadList = characterArrayList.subList(characterArrayList.size()/2, characterArrayList.size());

        Runnable myRunnable = new MyRunnable(characterArrayList,firstThreadList);
        Runnable mySecondRunnable = new MyRunnable(characterArrayList,secondThreadList);
        Thread myThread = new Thread(myRunnable);
        Thread mySecondThread = new Thread(mySecondRunnable);
        myThread.start();
        mySecondThread.start();

        //1st symbol checking loop for 1st thread
      /*  int i = 0;
        for (Character symbol : firstThreadList) {
            System.out.println(++i);
            if (verify(symbol.toString(), path)) {
                System.out.println("The pass is " + symbol.toString());
                return;
            }
        }
        //1st symbol checking loop for 2st thread
        for (Character symbol : secondThreadList) {
            System.out.println(++i);
            if (verify(symbol.toString(), path)) {
                System.out.println("The pass is " + symbol.toString());
                return;
            }
        }
        //2 symbols checking loop for 1st thread
        for (Character symbol1 : firstThreadList) {
            for (Character symbol2 : characterArrayList) {
                System.out.println(++i);
                if (verify(symbol1.toString() + symbol2.toString(), path)) {
                    System.out.println("The pass is " + symbol1.toString() + symbol2.toString());
                    return;
                }
            }
        }
        //2 symbols checking loop for 2nd thread
        for (Character symbol1 : secondThreadList) {
            for (Character symbol2 : characterArrayList) {
                System.out.println(++i);
                if (verify(symbol1.toString() + symbol2.toString(), path)) {
                    System.out.println("The pass is " + symbol1.toString() + symbol2.toString());
                    return;
                }
            }
        }*/
//        //3 symbols checking loop
//        for (Character symbol1 : list) {
//            for (Character symbol2 : list) {
//                for (Character symbol3 : list) {
//                    System.out.println(++i);
//                    if (verify(symbol1.toString() + symbol2.toString() + symbol3.toString(), path)) {
//                        System.out.println("The pass is " + symbol1.toString() + symbol2.toString() + symbol3.toString());
//                        return;
//                    }
//                }
//            }
//        }
//        //4 symbols checking loop
//        for (Character symbol1 : list) {
//            for (Character symbol2 : list) {
//                for (Character symbol3 : list) {
//                    for (Character symbol4 : list) {
//                        System.out.println(++i);
//                        if (verify(symbol1.toString() + symbol2.toString() + symbol3.toString() + symbol4.toString(), path)) {
//                            System.out.println("The pass is " + symbol1.toString() + symbol2.toString() + symbol3.toString() + symbol4.toString());
//                            return;
//                        }
//                    }
//                }
//            }
//        }
    }

   public static void copyFile(File in, File out) throws Exception {
       int BUF_SIZE = 1024;
       FileInputStream fis  = new FileInputStream(in);
       FileOutputStream fos = new FileOutputStream(out);
       try {
           byte[] buf = new byte[BUF_SIZE];
           int i = 0;
           while ((i = fis.read(buf)) != -1) {
               fos.write(buf, 0, i);
           }
       }
       catch (Exception e) {
           throw e;
       }
       finally {
           if (fis != null) fis.close();
           if (fos != null) fos.close();
       }
   }


}
