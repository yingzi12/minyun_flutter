package com.xinshijie.user.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.util.List;

public interface IFileService {
    String saveUploadedFilesWatermark( String title, String md5,MultipartFile file);

    Boolean moveFile(String headPath, String title, String sourcePath);


    /**
     * 下载文件
     *
     * @param file
     * @return
     */
    String saveUploadedFilesDown(String key,String saveLocalPath, MultipartFile file);

    String saveFile(MultipartFile file,String key ,String saveLocalPath);

    String  uploadFile();

    void mkdirParentDir(String path);

    boolean isWebPFormat(MultipartFile file);

    String convertWebp(String key,String imgUrl,MultipartFile file);

    /**
     * 根据url下载图片，调用downloadImageLocal
     * @param key
     * @param url
     * @return
     */
    String saveImageUrlLocal(String albumName,String key,String fileLocalSavePath, String url);

    /**
     * 根据url下载图片
     * @param key
     * @param url
     * @return
     */
    String downloadImageLocal(String albumName,String url,String key, String fileLocalSavePath,Integer count);

    String downloadFile(String fileURL,String  referer,String key,String fileSavePath);

    String getMD5ByInput(InputStream is);

    String getMD5ByFile(File file);

    String saveUploadedFilesWatermarkByFile(String headPath, String title,String md5, File file);

    List<String> uploadedBatchFiles(String title, Long sid, List<MultipartFile> files);

    String uploadedFiles(String title, Long sid, MultipartFile file);

}
