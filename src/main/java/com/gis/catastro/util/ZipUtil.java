package com.gis.catastro.util;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtil {
    public static void createZip(List<String> pSourceFileNames, String destineFileName, String destineFilePath) throws IOException {
        FileOutputStream fos = new FileOutputStream(destineFilePath+ File.separator+destineFileName);
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        for (String srcFile : pSourceFileNames) {
            File fileToZip = new File(srcFile);
            FileInputStream fis = new FileInputStream(fileToZip);
            ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
            zipOut.putNextEntry(zipEntry);
            byte[] bytes = new byte[1024];
            int length;
            while((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
            fis.close();
        }
        zipOut.close();
        fos.close();


//        String sourceFile = sourceFilePath+ File.separator+sourceFileName;
//        FileOutputStream fos = new FileOutputStream(destineFilePath+File.separator+destineFileName);
//        ZipOutputStream zipOut = new ZipOutputStream(fos);
//        File fileToZip = new File(sourceFile);
//        FileInputStream fis = new FileInputStream(fileToZip);
//        ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
//        zipOut.putNextEntry(zipEntry);
//        byte[] bytes = new byte[1024];
//        int length;
//        while((length = fis.read(bytes)) >= 0) {
//            zipOut.write(bytes, 0, length);
//        }
//        zipOut.close();
//        fis.close();
//        fos.close();
    }
}
