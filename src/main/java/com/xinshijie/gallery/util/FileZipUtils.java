//package com.xinshijie.gallery.util;
//
//import lombok.extern.slf4j.Slf4j;
//
//import java.io.*;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.util.zip.GZIPInputStream;
//import java.util.zip.GZIPOutputStream;
//
//@Slf4j
//public class FileZipUtils {
//
//    /**
//     * 压缩保存文件
//     * @param wid
//     * @param sid
//     * @param content
//     */
//    public static String  compressAndSaveStringToFile(String chapterSavePath,String saveSource,Integer wid,Long sid,String title,String content,boolean isMd5) {
//        if(content==null){
//            return null;
//        }
//        String md5=title;
//        if(isMd5){
//            md5 = getMd5(title);
//        }
//        String filePath="/"+saveSource+"/"+wid%100+"/"+wid+"/"+sid;
//        try {
//            // 创建文件对象
//            try {
//                FileUtils.forceMkdir(new File(chapterSavePath+filePath));
//            } catch (IOException e) {
//                log.error("无法创建目录：{} ",chapterSavePath+filePath,e.getMessage());
//            }
////            // 如果文件的目录不存在，就创建目录
//
//            File file = new File(chapterSavePath+filePath+"/"+md5);
//            File parentDir = file.getParentFile();
//            if (!parentDir.exists()) {
//                parentDir.mkdirs();
//            }
//
//            // 创建文件输出流
//            OutputStream outputStream = new FileOutputStream(file);
//
//            // 创建GZIP压缩输出流
//            GZIPOutputStream gzipOutputStream = new GZIPOutputStream(outputStream);
//
//            // 将字符串写入压缩输出流
//            byte[] bytes = content.getBytes("UTF-8");
//            gzipOutputStream.write(bytes);
//
//            // 关闭压缩输出流和文件输出流
//            gzipOutputStream.close();
//            outputStream.close();
//            return filePath+"/"+md5;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "";
//    }
//    public static String  compressAndSaveStringToFile(String chapterSavePath,String source,String content) {
//        if(content==null){
//            return null;
//        }
//        String filePath=source;
//        try {
//            // 创建文件对象
//            File file = new File(chapterSavePath+filePath);
//
//            // 如果文件的目录不存在，就创建目录
//            File parentDir = file.getParentFile();
//            if (!parentDir.exists()) {
//                parentDir.mkdirs();
//            }
//
//            // 创建文件输出流
//            OutputStream outputStream = new FileOutputStream(file);
//
//            // 创建GZIP压缩输出流
//            GZIPOutputStream gzipOutputStream = new GZIPOutputStream(outputStream);
//
//            // 将字符串写入压缩输出流
//            byte[] bytes = content.getBytes("UTF-8");
//            gzipOutputStream.write(bytes);
//
//            // 关闭压缩输出流和文件输出流
//            gzipOutputStream.close();
//            outputStream.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return filePath;
//    }
//
//    /**
//     * 解压读取
//     * @param filePath
//     * @return
//     */
//
//    public static String readAndDecompressFile(String chapterSavePath,String filePath) {
//        if(StringUtils.isEmpty(filePath)){
//            return "";
//        }
//        try {
//            // 创建文件对象
//            File file = new File(chapterSavePath+filePath);
//            // 如果文件的目录不存在，就创建目录
//            File parentDir = file.getParentFile();
//            if (!parentDir.exists()) {
//                parentDir.mkdirs();
//            }
//            // 创建文件输入流
//            InputStream inputStream = new FileInputStream(file);
//
//            // 创建GZIP解压输入流
//            GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream);
//
//            // 读取解压后的数据
//            ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
//            byte[] buffer = new byte[1024];
//            int bytesRead;
//            while ((bytesRead = gzipInputStream.read(buffer)) != -1) {
//                byteOutput.write(buffer, 0, bytesRead);
//            }
//
//            // 关闭解压输入流和文件输入流
//            gzipInputStream.close();
//            inputStream.close();
//
//            // 将解压后的数据转换为字符串
//            return new String(byteOutput.toByteArray(), "UTF-8");
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public static String decompressAndReadStringToFile(String chapterSavePath,Integer wid,Long sid,String title,boolean isMd5) {
//        String md5=title;
//        if(isMd5){
//            md5 = getMd5(title);
//        }
//        String filePath="/"+wid%100+"/"+wid+"/"+sid+"/"+md5;
//        if(StringUtils.isEmpty(filePath)){
//            return "";
//        }
//        try {
//            // 创建文件对象
//            File file = new File(chapterSavePath+filePath);
//            if (!file.exists()) {
//               return "";
//            }
//            // 创建文件输入流
//            InputStream inputStream = new FileInputStream(file);
//
//            // 创建GZIP解压输入流
//            GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream);
//
//            // 读取解压后的数据
//            ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
//            byte[] buffer = new byte[1024];
//            int bytesRead;
//            while ((bytesRead = gzipInputStream.read(buffer)) != -1) {
//                byteOutput.write(buffer, 0, bytesRead);
//            }
//
//            // 关闭解压输入流和文件输入流
//            gzipInputStream.close();
//            inputStream.close();
//
//            // 将解压后的数据转换为字符串
//            return new String(byteOutput.toByteArray(), "UTF-8");
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "";
//        }
//    }
//
//    public static Boolean updateFileName(String oldFilePath,String newFilePath){
//        // 创建原文件对象
//        File oldFile = new File(oldFilePath);
//        // 创建新文件对象
//        File newFile = new File(newFilePath);
//
//        // 使用renameTo()方法修改文件名称
//        boolean isRenamed = oldFile.renameTo(newFile);
//
//        return isRenamed;
//    }
//
//    public static String getMd5(String title){
//        try {
//            MessageDigest md5 = MessageDigest.getInstance("MD5");
//            byte[] digest = md5.digest(title.getBytes());
//
//            // 将MD5哈希转换为十六进制字符串
//            StringBuilder hexString = new StringBuilder();
//            for (byte b : digest) {
//                String hex = Integer.toHexString(0xFF & b);
//                if (hex.length() == 1) {
//                    hexString.append('0');
//                }
//                hexString.append(hex);
//            }
//
//            String md5Hash = hexString.toString();
//            return md5Hash;
//        } catch (NoSuchAlgorithmException e) {
//            log.error("title:{} 计算md5失败，",title,e);
//        }
//        return null;
//    }
//
//    public static String setCould(String fileName){
////        AliOSS.uploadUrl();
//       return "";
//    }
//
//    public static String getCould(String fileName){
//
//        return "";
//    }
//}
