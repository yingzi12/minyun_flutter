package com.xinshijie.gallery.controller;

import com.xinshijie.gallery.common.Result;
import com.xinshijie.gallery.domain.Image;
import com.xinshijie.gallery.dto.ImageDto;
import com.xinshijie.gallery.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.xinshijie.gallery.util.RequestContextUtil.getUserId;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/image")
public class ImageController {
    @Autowired
    private ImageService imageService;

    @GetMapping("/list")
    public Result<List<Image>> list(ImageDto dto) {
        if (dto.getPageNum() == null) {
            dto.setPageNum(1);
        }
        if(dto.getPageSize()==null) {
            dto.setPageSize(6);
        }
        dto.setOffset(dto.getPageSize() * (dto.getPageNum() - 1));
        Integer total = imageService.count(dto);

        List<Image> list = imageService.list(dto);
        return Result.success(list, total);
    }

    @GetMapping("/count")
    public Result<Integer> count(ImageDto dto) {
        if (dto.getPageNum() == null) {
            dto.setPageNum(1);
        }
        dto.setPageSize(6);
        Integer total = imageService.count(dto);
        return Result.success(total);
    }

    @PostMapping("/upload")
    public Result<String> handleFileUpload(@RequestPart(value = "file") final MultipartFile uploadfile, @RequestParam("aid") Integer aid) {
        String url = imageService.saveUploadedFiles( aid, uploadfile);
        return Result.success(url);
    }

    @PostMapping("/uploadBatch")
    public Result<String> handleFileBatchUpload(@RequestPart(value = "files") final List<MultipartFile> files, @RequestParam("aid") Integer aid) {
        for(MultipartFile uploadfile:files) {
            log.info("--------------------------upload aid:{},fileName:{},fileSize:{}" ,aid,uploadfile.getOriginalFilename(),uploadfile.getSize());
            String url = imageService.saveUploadedFiles(aid,  uploadfile);
        }
        return Result.success("success");
    }

}
