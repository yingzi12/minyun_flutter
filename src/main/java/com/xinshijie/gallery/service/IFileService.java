package com.xinshijie.gallery.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface IFileService {
    String saveUploadedFilesWatermark(String headPath, String title, MultipartFile file);

    String getMD5(InputStream is);

    Boolean moveFile(String headPath, String title, String sourcePath);

    /**
     * 转换成ts文件
     *
     * @param headPath
     * @param title
     * @param sourcePath
     * @return
     */
    String chargeVideoFile(String headPath, String title, String sourcePath);

    /**
     * 下载文件
     *
     * @param headPath
     * @param title
     * @param file
     * @return
     */
    String saveUploadedFilesDown(String headPath, String title, MultipartFile file);

    /**
     * 转换ts
     *
     * @param headPath
     * @param title
     * @param sourcePathUrl
     * @return
     */
    String chargeVideoThreadFile(String headPath, String title, String sourcePathUrl);
}
