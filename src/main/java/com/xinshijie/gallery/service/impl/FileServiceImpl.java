package com.xinshijie.gallery.service.impl;

import java.io.IOException;

import cn.hutool.core.util.HashUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.xinshijie.gallery.service.IFileService;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

@Slf4j
@Service
public class FileServiceImpl implements IFileService {


    @Value("${image.path}")
    private String imagePath;
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
                File destinationFile = new File(imagePath+imgUrl);
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
                        .toFile(new File(imagePath+imgUrl)); // 保存到文件

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

    public Boolean saveUploadedFilesWatermark(Integer userId,String nickName, MultipartFile file)  {
        try {

            if (file.isEmpty()) {
                log.error( "No image file provided");
                return false;
            }

            try {
                String[] imgArr = file.getOriginalFilename().split("\\.");
                String mm5=getMD5(file.getInputStream());
                String imgUrl = "/user/head/" +userId+"_"+mm5 + ".jpg";

                // 假设我们将图片保存在服务器的某个位置
                File destinationFile = new File(imagePath+imgUrl);
                File parentDir = destinationFile.getParentFile();
                // 如果父目录不存在，尝试创建它
                if (parentDir != null && !parentDir.exists()) {
                    parentDir.mkdirs();
                }
                // 创建水印图像
                BufferedImage watermarkImage = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = watermarkImage.createGraphics();

                // 设置水印文字和样式
                g2d.setPaint(new Color(255, 255, 255, 128)); // 白色半透明
                g2d.setFont(new Font("Arial", Font.BOLD, 30));
                String watermarkText = "aiavr.uk "+nickName;
                g2d.drawString(watermarkText, 10, 40);
                g2d.dispose();

                // 转换图片格式为JPG并添加水印
                Thumbnails.of(file.getInputStream())
                        .scale(1.0) // 保持原始大小
                        .outputQuality(0.8) // 设置压缩质量
                        .outputFormat("jpg")
                        .watermark(Positions.BOTTOM_RIGHT, watermarkImage, 0.5f) // 添加水印
                        .toFile(new File("path/to/destination/image.jpg")); // 保存到文件


                // 图片验证通过，更新用户信息
//                SystemUser user = new SystemUser();
//                user.setId(userId);
//                user.setImgUrl(imageSourceWeb + imgUrl);
//                mapper.updateById(user);
                return true;
            } catch (IOException e) {
                log.error("Error during image processing: " + e.getMessage());
                return false;
            }


        } catch (Exception exception) {
            // 处理异常
            return false;
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
}
