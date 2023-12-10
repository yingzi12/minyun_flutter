package com.xinshijie.gallery.service.impl;

import java.io.*;

import cn.hutool.core.util.HashUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.xinshijie.gallery.common.Result;
import com.xinshijie.gallery.common.ResultCodeEnum;
import com.xinshijie.gallery.common.ServiceException;
import com.xinshijie.gallery.service.IFileService;
import com.xinshijie.gallery.util.MediaUtil;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;

@Slf4j
@Service
public class FileServiceImpl implements IFileService {


    @Value("${image.path}")
    private String savePath;

    @Value("${image.path}")
    private String saveHCPath;
    @Override
    public String saveUploadedFilesWatermark(String headPath,String title, MultipartFile file)  {
        try {
            if (file.isEmpty()) {
                log.error( "No image file provided");
                return null;
            }
            try {
                String imgUrl = headPath + Math.abs(HashUtil.apHash(title)) % 1000 + "/" + DigestUtil.md5Hex(title)+"/"+ HashUtil.apHash(file.getOriginalFilename())+ ".jpg";

                // 假设我们将图片保存在服务器的某个位置
                File destinationFile = new File(savePath+imgUrl);
                File parentDir = destinationFile.getParentFile();
                // 如果父目录不存在，尝试创建它
                if (parentDir != null && !parentDir.exists()) {
                    parentDir.mkdirs();
                }
                double outputQuality = file.getSize() > 1024 * 1024 ? 0.6 : 0.8;
                // 转换图片格式为JPG并添加水印
                Thumbnails.of(file.getInputStream())
                        .scale(1.0) // 保持原始大小
                        .outputQuality(outputQuality) // 设置压缩质量
                        .outputFormat("jpg")
                        //.watermark(Positions.BOTTOM_RIGHT, watermarkImage, 0.5f) // 添加水印
                        .toFile(new File(savePath+imgUrl)); // 保存到文件

                return imgUrl;
            } catch (IOException e) {
                log.error("Error during image processing: " + e.getMessage());
                return "";
            }


        } catch (Exception exception) {
            log.error("Error during image processing: " + exception.getMessage());
            // 处理异常
            return "";
        }
    }

    public String getMD5(InputStream is)  {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[1024];
            int read;
            while ((read = is.read(buffer)) != -1) {
                md.update(buffer, 0, read);
            }

            byte[] md5sum = md.digest();
            BigInteger bigInt = new BigInteger(1, md5sum);
            return bigInt.toString(16);
        }catch (Exception ex){
            ex.printStackTrace();
            return "";
        }
    }


    public Boolean moveFile(String headPath,String title,String sourcePath) {
        try {
//            MediaUtil.splitMp4ToTsSegmentsWithIndex(new File("/Users/luhuang/Documents/1700641117058.mp4")
//                    ,new File("./1700641117058") ,new File("./1700641117058/1700641117058.m3u8"));
            Path chunkFile = Paths.get(headPath + LocalDate.now()+"/"+ title);

            File destinationFile = new File(headPath +LocalDate.now()+"/"+ title);
            File parentDir = destinationFile.getParentFile();
            // 如果父目录不存在，尝试创建它
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }

            Path path=Paths.get(sourcePath);
            // Rename the file to indicate it has been uploaded
            Files.move(path,chunkFile );

        } catch (Exception ex) {
            log.error("Error in file upload: " + title, ex);
            throw  new ServiceException(ResultCodeEnum.SYSTEM_INNER_ERROR);
        }
        return true;
    }

    public String chargeVideoFile(String headPath,String title,String sourcePathUrl) {
        try {
            //            MediaUtil.splitMp4ToTsSegmentsWithIndex(new File("/Users/luhuang/Documents/1700641117058.mp4")
//                    ,new File("./1700641117058") ,new File("./1700641117058/1700641117058.m3u8"));
            Path sourcePath=Paths.get(sourcePathUrl);
            String[] fileNameArr=sourcePath.getFileName().toString().split("\\.");
            String vedioPath=""+"/"+LocalDate.now()+"/"+fileNameArr[0];
            String m3u8Path=""+"/"+LocalDate.now()+"/"+fileNameArr[0]+"/"+fileNameArr[0]+".m3u8";

            MediaUtil.splitMp4ToTsSegmentsWithIndex(new File(sourcePathUrl)
                    ,new File(vedioPath) ,new File(m3u8Path));
            return m3u8Path;
        } catch (Exception ex) {
            log.error("Error in file upload: " + title, ex);
            throw  new ServiceException(ResultCodeEnum.SYSTEM_INNER_ERROR);
        }
//        return Result.success(true);
    }


}
