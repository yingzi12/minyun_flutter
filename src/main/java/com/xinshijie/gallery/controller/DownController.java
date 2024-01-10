package com.xinshijie.gallery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/download")
public class DownController {

//    private final String fileStorageLocation = "your_file_directory_path_here"; // 设置文件存储目录
    private final ResourceLoader resourceLoader;
    @Value("${image.path}")
    private String savePath;
    @Autowired
    public DownController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @GetMapping("/down")
    public ResponseEntity<Resource> downloadFile(@RequestParam("path") String fileName) {
            // 构建本地文件路径
            Path filePath = Paths.get("/Users/luhuang/Documents/git/gallery2/data/e2"+fileName);

            // 使用ResourceLoader加载本地文件资源
            Resource resource = resourceLoader.getResource("file:" + filePath.toString());

            // 设置响应头
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);

            // 返回文件内容
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
    }
}
