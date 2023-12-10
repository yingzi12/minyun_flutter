package com.xinshijie.gallery.service.impl;

import cn.hutool.core.util.HashUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.xinshijie.gallery.common.Constants;
import com.xinshijie.gallery.common.ResultCodeEnum;
import com.xinshijie.gallery.common.ServiceException;
import com.xinshijie.gallery.service.IFileService;
import com.xinshijie.gallery.util.MediaUtil;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.time.LocalDate;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class FileServiceImpl implements IFileService {


    @Value("${image.path}")
    private String savePath;

    /**
     * 视频上传缓存路径
     */


    @Override
    public String saveUploadedFilesWatermark(String headPath, String title, MultipartFile file) {
        try {
            if (file.isEmpty()) {
                log.error("No image file provided");
                return null;
            }
            try {
                String imgUrl = headPath + Math.abs(HashUtil.apHash(title)) % 1000 + "/" + DigestUtil.md5Hex(title) + "/" + HashUtil.apHash(file.getOriginalFilename()) + ".jpg";

                // 假设我们将图片保存在服务器的某个位置
                File destinationFile = new File(savePath + imgUrl);
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
                        .toFile(new File(savePath + imgUrl)); // 保存到文件

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

    public String getMD5(InputStream is) {
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
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }


    public Boolean moveFile(String headPath, String title, String sourcePath) {
        try {
            Path chunkFile = Paths.get(headPath + LocalDate.now() + "/" + title);

            File destinationFile = new File(headPath + LocalDate.now() + "/" + title);
            File parentDir = destinationFile.getParentFile();
            // 如果父目录不存在，尝试创建它
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }

            Path path = Paths.get(sourcePath);
            Files.move(path, chunkFile);

        } catch (Exception ex) {
            log.error("Error in file upload: " + title, ex);
            throw new ServiceException(ResultCodeEnum.SYSTEM_INNER_ERROR);
        }
        return true;
    }

    public String chargeVideoFile(String headPath, String title, String sourcePathUrl) {
        try {
            Path sourcePath = Paths.get(sourcePathUrl);
            String[] fileNameArr = sourcePath.getFileName().toString().split("\\.");
            String vedioPath = "" + "/" + LocalDate.now() + "/" + fileNameArr[0];
            String m3u8Path = "" + "/" + LocalDate.now() + "/" + fileNameArr[0] + "/" + fileNameArr[0] + ".m3u8";

            MediaUtil.splitMp4ToTsSegmentsWithIndex(new File(sourcePathUrl)
                    , new File(vedioPath), new File(m3u8Path));
            return m3u8Path;
        } catch (Exception ex) {
            log.error("Error in file upload: " + title, ex);
            throw new ServiceException(ResultCodeEnum.SYSTEM_INNER_ERROR);
        }
    }

//    private static final Semaphore semaphore = new Semaphore(5);

    /**
     * 拆分视频，最多同步5个
     *
     * @param headPath
     * @param title
     * @param sourcePathUrl
     * @return
     */
    public String chargeVideoThreadFile(String headPath, String title, String sourcePathUrl) {
//        boolean acquired = false;
        try {
            // 尝试获取许可，最多等待一定时间
//            acquired = semaphore.tryAcquire(1000, TimeUnit.MILLISECONDS);
//            if (!acquired) {
//                // 无法获得许可，可能是因为已达到最大并发数
//                throw new ServiceException("System is busy, please try again later.");
//            }

            // 你的方法逻辑
            Path sourcePath = Paths.get(Constants.videoHcPath+sourcePathUrl);
            String day=LocalDate.now().toString();
            String[] fileNameArr = sourcePath.getFileName().toString().split("\\.");
            String vedioPath ="/video/" + day+ "/" + fileNameArr[0];
            String m3u8Path = vedioPath+"/" + fileNameArr[0] + ".m3u8";
            File destinationFile = new File(savePath + m3u8Path);
            File parentDir = destinationFile.getParentFile();
            // 如果父目录不存在，尝试创建它
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }
            MediaUtil.splitMp4ToTsSegmentsWithIndex(new File(Constants.videoHcPath+sourcePathUrl), new File(savePath +vedioPath), new File(savePath +m3u8Path));
            return m3u8Path;

        } catch (Exception ex) {
            log.error("Error in file upload: " + title, ex);
            throw new ServiceException(ResultCodeEnum.SYSTEM_INNER_ERROR);
        }
    }

    @Override
    public String saveUploadedFilesDown(String headPath, String title, MultipartFile file) {
        try {
            if (file.isEmpty()) {
                log.error("No image file provided");
                return null;
            }
            try {
                // 你的方法逻辑
                String[] fileNameArr = file.getOriginalFilename().toString().split("\\.");
                String fileUrl = headPath + Math.abs(HashUtil.apHash(title)) % 1000 + "/" + DigestUtil.md5Hex(title) + "/" + HashUtil.apHash(file.getOriginalFilename()) + "." + fileNameArr[fileNameArr.length - 1];

                // 假设我们将视频保存在服务器的某个位置
                File destinationFile = new File(Constants.videoHcPath + fileUrl);
                File parentDir = destinationFile.getParentFile();
                // 如果父目录不存在，尝试创建它
                if (parentDir != null && !parentDir.exists()) {
                    parentDir.mkdirs();
                }
                try (InputStream inputStream = file.getInputStream();
                     FileOutputStream outputStream = new FileOutputStream(fileUrl)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                }

                return fileUrl;
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
}
