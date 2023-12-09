package com.xinshijie.gallery.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface IFileService {
    String saveUploadedFilesWatermark(String headPath,String title, MultipartFile file);

    String getMD5(InputStream is);
}
