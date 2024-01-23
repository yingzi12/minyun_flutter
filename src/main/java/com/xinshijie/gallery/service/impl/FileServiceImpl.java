package com.xinshijie.gallery.service.impl;

import cn.hutool.core.util.HashUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.xinshijie.gallery.common.Constants;
import com.xinshijie.gallery.common.ResultCodeEnum;
import com.xinshijie.gallery.common.ServiceException;
import com.xinshijie.gallery.service.IFileService;
import com.xinshijie.gallery.util.MediaUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.*;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
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
import java.util.Arrays;
import java.util.Iterator;


@Slf4j
@Service
public class FileServiceImpl implements IFileService {


    @Value("${image.path}")
    private String savePath;

    public static void main(String[] args) {
        String[] readFormats = ImageIO.getReaderFormatNames();
        String[] writeFormats = ImageIO.getWriterFormatNames();
        System.out.println("支持的Readers: " + Arrays.asList(readFormats));
        System.out.println("支持的Writers: " + Arrays.asList(writeFormats));
        Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("WBMP");
        while (readers.hasNext()) {
            System.out.println("reader: " + readers.next());
        }
        ImageWriter writer = ImageIO.getImageWritersByMIMEType("image/webp").next();

        System.out.println("writers: " + writer);

    }

    /**
     * 照片
     */
    @Override
    public String saveUploadedFilesWatermark(String headPath, String title, MultipartFile file) {
        try {
            if (file.isEmpty()) {
                log.error("No image file provided");
                return null;
            }
            log.info("file:" + file.getOriginalFilename());
            String imgUrl = headPath + Math.abs(HashUtil.apHash(title)) % 1000 + "/" + DigestUtil.md5Hex(title) + "/" + HashUtil.apHash(file.getOriginalFilename()) + ".webp";
            mkdirParentDir(savePath + imgUrl);
            // 检查文件大小或格式
            if (file.getSize() <= 300 * 1024 || isWebPFormat(file)) {
                return saveFile(file, headPath, title);
            } else {
                convertWebp(imgUrl,file);
            }
            return imgUrl;
        } catch (Exception e) {
            log.error("Error during image processing: ", e);
            return saveFile(file, headPath, title);
        }
    }

    public  void convertWebp(String imgUrl,MultipartFile file) {
        try {
            // 转换为WebP格式
            float outputQuality = 0.8f; // 可以根据需要设置压缩质量
            ImageWriter writer = ImageIO.getImageWritersByMIMEType("image/webp").next();
            BufferedImage image = ImageIO.read(file.getInputStream());
            ImageWriteParam param = writer.getDefaultWriteParam();
            param.setCompressionQuality(outputQuality);

            // 确保输出文件存在
            File outputFile = new File(savePath + imgUrl);
            outputFile.getParentFile().mkdirs(); // 创建父目录
            outputFile.createNewFile(); // 创建文件

            ImageOutputStream ios = ImageIO.createImageOutputStream(outputFile);
            if (ios == null) {
                log.error("转换文件出现错误：{}",imgUrl);
                throw new ServiceException("无法创建 ImageOutputStream");
            }
            try {
                writer.setOutput(ios);
                writer.write(null, new IIOImage(image, null, null), param);
            } finally {
                ios.close();
                writer.dispose();
            }
        } catch (Exception exception) {
            log.error("转换文件出现错误",exception);
            throw new ServiceException("无法创建 ImageOutputStream");
        }
    }


    private boolean isWebPFormat(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.equals("image/webp");
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

//    private static final Semaphore semaphore = new Semaphore(5);

    public String chargeVideoFile(String headPath, String title, String sourcePathUrl) {
        try {
            Path sourcePath = Paths.get(sourcePathUrl);
            String[] fileNameArr = sourcePath.getFileName().toString().split("\\.");
            String vedioPath = "/" + LocalDate.now() + "/" + fileNameArr[0];
            String m3u8Path = "/" + LocalDate.now() + "/" + fileNameArr[0] + "/" + fileNameArr[0] + ".m3u8";

            MediaUtil.splitMp4ToTsSegmentsWithIndex(new File(sourcePathUrl)
                    , new File(vedioPath), new File(m3u8Path));
            return m3u8Path;
        } catch (Exception ex) {
            log.error("Error in file upload: " + title, ex);
            throw new ServiceException(ResultCodeEnum.SYSTEM_INNER_ERROR);
        }
    }

    /**
     * 拆分视频，最多同步5个
     *
     * @param headPath
     * @param title
     * @param sourcePathUrl
     * @return
     */
    public String chargeVideoThreadFile(String headPath, String title, String sourcePathUrl) {
        try {

            // 你的方法逻辑
            Path sourcePath = Paths.get(Constants.videoHcPath + sourcePathUrl);
            String day = LocalDate.now().toString();
            String[] fileNameArr = sourcePath.getFileName().toString().split("\\.");
            String vedioPath = "/video/" + day + "/" + fileNameArr[0];
            String m3u8Path = vedioPath + "/" + fileNameArr[0] + ".m3u8";
            File destinationFile = new File(savePath + m3u8Path);
            File parentDir = destinationFile.getParentFile();
            // 如果父目录不存在，尝试创建它
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }
            MediaUtil.splitMp4ToTsSegmentsWithIndex(new File(Constants.videoHcPath + sourcePathUrl), new File(savePath + vedioPath), new File(savePath + m3u8Path));
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
                String[] fileNameArr = file.getOriginalFilename().split("\\.");
                String fileUrl = headPath + Math.abs(HashUtil.apHash(title)) % 1000 + "/" + DigestUtil.md5Hex(title) + "/" + HashUtil.apHash(file.getOriginalFilename()) + "." + fileNameArr[fileNameArr.length - 1];

                // 假设我们将视频保存在服务器的某个位置
                File destinationFile = new File(Constants.videoHcPath + fileUrl);
                File parentDir = destinationFile.getParentFile();
                // 如果父目录不存在，尝试创建它
                if (parentDir != null && !parentDir.exists()) {
                    parentDir.mkdirs();
                }
                try (InputStream inputStream = file.getInputStream();
                     FileOutputStream outputStream = new FileOutputStream(Constants.videoHcPath + fileUrl)) {
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

    public String saveFile(MultipartFile file, String headPath, String title) {
        String[] hzArr = file.getOriginalFilename().split("\\.");
        String hz = hzArr[hzArr.length - 1];
        String imgUrlPath = headPath + Math.abs(HashUtil.apHash(title)) % 1000 + "/" + DigestUtil.md5Hex(title) + "/" + HashUtil.apHash(file.getOriginalFilename()) + "." + hz;
        mkdirParentDir(savePath + imgUrlPath);
        try (InputStream inputStream = file.getInputStream();
             FileOutputStream outputStream = new FileOutputStream(savePath + imgUrlPath)) {
            log.info("保存图片 saveImage path:{}",savePath + imgUrlPath);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
        return imgUrlPath;
    }

    public void mkdirParentDir(String path)  {
        File destinationFile = new File(path);
        File parentDir = destinationFile.getParentFile();
        log.info("父目录的绝对路径: " + parentDir.getAbsolutePath());
        // 如果父目录不存在，尝试创建它
        if (parentDir != null && !parentDir.exists()) {
            boolean created = parentDir.mkdirs();
            if (!created) {
                log.error("无法创建目录 path:{}",path);
                throw new ServiceException("无法创建目录: " + parentDir.getAbsolutePath());
            }
        }
    }


}
