package com.xinshijie.user.service.impl;

import cn.hutool.core.util.HashUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.xinshijie.common.enums.ResultCodeEnum;
import com.xinshijie.common.exception.ServiceException;
import com.xinshijie.common.utils.S3Utils;
import com.xinshijie.user.service.IFileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;


@Slf4j
@Service
public class FileServiceImpl implements IFileService {
    @Value("${image.localPath}")
    private String imageLocalPath;

    @Value("${image.sourceWeb}")
    private String imageSourceWeb;

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36";

    private Map<String,String> refererMap= Map.ofEntries(
            Map.entry("cdn.v2ph.com", "https://cdn.v2ph.com"),
            Map.entry("img.xchina.biz", "https://img.xchina.biz")
    );
    /**
     * 照片
     */
    @Override
    public String saveUploadedFilesWatermark( String title,String md5, MultipartFile file) {
        String titleMd5=DigestUtil.md5Hex(title);
        String nowStr= LocalDate.now().toString();
        //e2路径
        String key= "/image/" + Math.abs(HashUtil.apHash(title)) % 1000 + "/" +  titleMd5;
        int dotIndex = file.getOriginalFilename().lastIndexOf('.');
        // 获取原始后缀
        String extension = file.getOriginalFilename().substring(dotIndex);
        String fileName=md5+extension;
        //本地路径
        String fileSavePath=imageLocalPath+ File.separator+nowStr+ File.separator+titleMd5;
        try {
            if (file.isEmpty()) {
                log.error("No image file provided");
                return null;
            }
            log.info("file:" + file.getOriginalFilename());

            mkdirParentDir(fileSavePath);
            // 检查文件大小或格式
            if (file.getSize() <= 300 * 1024 || isWebPFormat(file)) {
                String fileFullPath=fileSavePath+File.separator+fileName;
                key=key+"/"+fileName;
                return saveFile(file,key, fileFullPath);
            } else {
                fileName=md5+".webp";
                key=key+"/"+fileName;
                String fileFullPath=fileSavePath+File.separator+fileName;
                return convertWebp(key,fileFullPath,file);
            }
        } catch (Exception e) {
            log.error("Error during image processing: ", e);
            key=key+"/"+fileName;
            return saveFile(file,key, fileSavePath+File.separator+fileName);
        }

    }

    @Override
    public  String convertWebp(String key,String fileSavePath,MultipartFile file) {
        try {
            // 转换为WebP格式
            float outputQuality = 0.8f; // 可以根据需要设置压缩质量
            ImageWriter writer = ImageIO.getImageWritersByMIMEType("image/webp").next();
            BufferedImage image = ImageIO.read(file.getInputStream());
            ImageWriteParam param = writer.getDefaultWriteParam();
            param.setCompressionQuality(outputQuality);

            // 确保输出文件存在
            File outputFile = new File(fileSavePath);
            outputFile.getParentFile().mkdirs(); // 创建父目录
            outputFile.createNewFile(); // 创建文件

            ImageOutputStream ios = ImageIO.createImageOutputStream(outputFile);
            if (ios == null) {
                log.error("转换文件出现错误：{}",fileSavePath);
                throw new ServiceException("无法创建 ImageOutputStream");
            }
            try {
                writer.setOutput(ios);
                writer.write(null, new IIOImage(image, null, null), param);
            } finally {
                ios.close();
                writer.dispose();
            }
            if(outputFile.isFile()){
                S3Utils.putObjectByFile("xinshijie",key,outputFile);
            }
            return key;
        } catch (Exception exception) {
            log.error("转换文件出现错误",exception);
            throw new ServiceException("无法创建 ImageOutputStream");
        }
    }

    @Override
    public boolean isWebPFormat(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.equals("image/webp");
    }

    @Override
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

    @Override
    public String uploadFile() {
        return null;
    }

    @Override
    public String saveUploadedFilesDown(String key,String saveLocalPath, MultipartFile file) {
        try {
            if (file.isEmpty()) {
                log.error("No image file provided");
                return null;
            }
            try {
                // 假设我们将视频保存在服务器的某个位置
                File destinationFile = new File(saveLocalPath);
                File parentDir = destinationFile.getParentFile();
                // 如果父目录不存在，尝试创建它
                if (parentDir != null && !parentDir.exists()) {
                    parentDir.mkdirs();
                }
                try (InputStream inputStream = file.getInputStream();
                     FileOutputStream outputStream = new FileOutputStream(saveLocalPath)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                }
                File outputFile=new File(saveLocalPath);
                if(outputFile.isFile()){
                    S3Utils.putObjectByFile("xinshijie",key,outputFile);
                }
                return key;
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

    @Override
    public String saveFile(MultipartFile file,String key ,String saveLocalPath) {
        mkdirParentDir(saveLocalPath);
        try (InputStream inputStream = file.getInputStream();
             FileOutputStream outputStream = new FileOutputStream(saveLocalPath)) {
            log.info("保存文件 saveFile path:{}",saveLocalPath);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (Exception ex) {
            log.error("保存文件 ex:",ex);
            return "";
        }
        File outputFile=new File(saveLocalPath);
        if(outputFile.isFile()){
            S3Utils.putObjectByFile("xinshijie",key,outputFile);
        }
        return key;
    }

    @Override
    public void mkdirParentDir(String path)  {
        File destinationFile = new File(path);
        File parentDir = destinationFile.getParentFile();
        //log.info("父目录的绝对路径: " + parentDir.getAbsolutePath());
        // 如果父目录不存在，尝试创建它
        if (parentDir != null && !parentDir.exists()) {
            boolean created = parentDir.mkdirs();
            if (!created) {
                log.error("无法创建目录 path:{}",path);
                throw new ServiceException("无法创建目录: " + parentDir.getAbsolutePath());
            }
        }
    }

    @Override
    public String saveImageUrlLocal(String albumName,String key,String fileSavePath, String url) {
        try {
            if (StringUtils.isEmpty(url)) {
                return "";
            }
            String fileName = url.split("\\?")[0].substring(url.lastIndexOf('/') + 1);
            String md5=DigestUtil.md5Hex(fileName);

            String path = fileSavePath+  File.separator + fileName;
            if (fileName.split("\\.").length > 1) {
                return downloadImageLocal(albumName,url, key+"/"+fileName,path,0);
            } else {
                fileName=fileName+ ".jpg";
                path = path + ".jpg";
                return downloadImageLocal(albumName,url,key+"/"+fileName, path,0);
            }
        } catch (Exception ex) {
            log.error("判断getImageUrl是否可以访问：name:{} ,imagePath:{}, url:{}", fileSavePath, url);
            ex.printStackTrace();
            return "";
        }
    }

    @Override
    public String downloadImageLocal(String albumName,String url,String key, String fileLocalSavePath, Integer count) {
        if (count > 3) {
            log.error("同步url 下载图片，url:{}", url);
            return "";
        }
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(url);

        // 创建 URL 对象
        URL urlHost = null;
        try {
            urlHost = new URL(url);
        } catch (MalformedURLException e) {
            log.error("路径错误：{}",url,e);
            throw new ServiceException("路径错误");
        }
        // 获取域名
        String domain = urlHost.getHost();
        String referer = refererMap.getOrDefault(domain,"https://"+domain);
        if(count==0 && "https://img.xchina.biz".equals(referer)){
            url=url.replace("_600x0.webp",".jpg");
            key=key.replace("_600x0.webp",".jpg");
            fileLocalSavePath=fileLocalSavePath.replace("_600x0.webp",".jpg");
        }
        request.addHeader("User-Agent", USER_AGENT);

//        String
        if(refererMap.containsKey(domain)) {
            request.addHeader("Referer",refererMap.get(domain));
        }
        File outputFile = new File(fileLocalSavePath);

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                File parentDir = outputFile.getParentFile();

                // 如果父目录不存在，尝试创建它
                if (parentDir != null && !parentDir.exists()) {
                    parentDir.mkdirs();
                }
                try (InputStream inputStream = entity.getContent();
                     FileOutputStream outputStream = new FileOutputStream(outputFile)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                }

                // 验证图片是否正常
                BufferedImage image = ImageIO.read(outputFile);
                if (image == null) {
                    String imgUrl= downloadFile(url,referer,key,fileLocalSavePath);
                    if(StringUtils.isEmpty(imgUrl)){
                        downloadImageLocal(albumName,url, key, fileLocalSavePath, count + 1);
                    }
                } else {
                    EntityUtils.consume(entity);
                }
            }
            if(outputFile.isFile()){
                String md5=getMD5ByFile(outputFile);
                S3Utils.putObjectByFile("xinshijie", key, outputFile);

//                AllImage allImage = allImageService.getMD5(md5);
//                if(allImage == null) {
//                    allImage = new AllImage();
//                    allImage.setTitle(outputFile.getName());
//                    allImage.setSourceUrl(key);
//                    allImage.setSourceWeb(imageSourceWeb);
//                    allImage.setSize(outputFile.length());
//                    allImage.setMd5(md5);
//                    allImage.setAlbumName(albumName);
//                    S3Utils.putObjectByFile("xinshijie", key, outputFile);
//                    allImageService.save(allImage);
//                }else{
//                    return allImage.getSourceUrl();
//                }
            }
            return key;
        } catch (IOException e) {
            e.printStackTrace();
            downloadImageLocal(albumName,url, key,fileLocalSavePath ,count + 1);
        }
        return "";
    }

    @Override
    public String downloadFile(String fileURL,String  referer,String key,String fileSavePath) {
        try {

            URL url = new URL(fileURL);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set request headers
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", USER_AGENT);
            connection.setRequestProperty("Referer", referer);
            connection.setInstanceFollowRedirects(true); // Follow redirects

            // Some websites may require additional headers or cookies to bypass Cloudflare protection

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (InputStream inputStream = connection.getInputStream();
                     FileOutputStream outputStream = new FileOutputStream(fileSavePath)) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    log.info("File downloaded successfully!");
                }
//                File outputFile = new File(fileSavePath+"/"+filename);
//                if(outputFile.isFile()){
//                    S3Utils.putObjectByFile("xinshijie",key,outputFile);
//                }
            } else {
                log.info("Failed to download file. Response code: " + responseCode);
            }
        } catch (IOException e) {
            log.error("下载失败：{}，出现异常",fileURL,e);
            return "";
        }
        return referer;
    }



    @Override
    public String getMD5ByInput(InputStream is) {
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

    @Override
    public String getMD5ByFile(File file)    {
        try (FileInputStream fis = new FileInputStream(file)) {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] dataBytes = new byte[1024];
            int nread;
            while ((nread = fis.read(dataBytes)) != -1) {
                md.update(dataBytes, 0, nread);
            }
            byte[] mdBytes = md.digest();
            return Hex.encodeHexString(mdBytes);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


    public String saveUploadedFilesWatermarkByFile(String headPath, String title,String md5, File file) {
        try {
            log.info("file:" + file.getName());
            String imgUrl = headPath + Math.abs(HashUtil.apHash(title)) % 1000 + "/" + DigestUtil.md5Hex(title) + "/" + md5 + ".webp";
            // 检查文件大小或格式
            if (file.length() <= 300 * 1024 || ".webp".endsWith(file.getName())) {
                return saveFile(file,headPath, title,md5);
            } else {
                convertWebp(imgUrl,file);
            }
            return imgUrl;
        } catch (Exception e) {
            log.error("Error during image processing: ", e);
            return saveFile(file,headPath,title,md5);
        }
    }


    public String saveFile(File file,String headPath, String title, String md5) {
        String[] hzArr = file.getName().split("\\.");
        String hz = hzArr[hzArr.length - 1];
        String imgUrlPath = headPath + Math.abs(HashUtil.apHash(title)) % 1000 + "/" + DigestUtils.md5Hex(title) + "/" + md5 + "." + hz;

        S3Utils.putObjectByFile("xinshijie",imgUrlPath,file);
        return imgUrlPath;
    }

    public void convertWebp(String imgUrl, File sourceFile) {
        try (FileInputStream fis = new FileInputStream(sourceFile)) {
            float outputQuality = 0.8f; // 可以根据需要设置压缩质量
            ImageWriter writer = ImageIO.getImageWritersByMIMEType("image/webp").next();
            BufferedImage image = ImageIO.read(fis);
            ImageWriteParam param = writer.getDefaultWriteParam();
            param.setCompressionQuality(outputQuality);

            // 确保输出文件存在
            File outputFile = new File(imageLocalPath + imgUrl);
            outputFile.getParentFile().mkdirs(); // 创建父目录
            if (!outputFile.exists()) {
                outputFile.createNewFile(); // 创建文件
            }

            try (ImageOutputStream ios = ImageIO.createImageOutputStream(outputFile)) {
                if (ios == null) {
                    log.error("转换文件出现错误：{}", imgUrl);
                    throw new ServiceException("无法创建 ImageOutputStream");
                }
                writer.setOutput(ios);
                writer.write(null, new IIOImage(image, null, null), param);
            } finally {
                writer.dispose();
            }
            File file=new File(imageLocalPath + imgUrl);
            S3Utils.putObjectByFile("xinshijie",imgUrl,file);
        } catch (Exception exception) {
            log.error("转换文件出现错误", exception);
            throw new ServiceException("无法创建 ImageOutputStream");
        }
    }

    @Override
    public List<String> uploadedBatchFiles(String title, Long sid, List<MultipartFile> files) {
        List<String> urlList=new ArrayList<>();
        for(MultipartFile file:files) {
            try {
                String titleMd5=DigestUtil.md5Hex(title);
                String nowStr= LocalDate.now().toString();
                //e2路径
                String key= "/image/" + Math.abs(HashUtil.apHash(title)) % 1000 + "/" +  titleMd5;
//                int dotIndex = file.getOriginalFilename().lastIndexOf('.');
                // 获取原始后缀
//                String extension = file.getOriginalFilename().substring(dotIndex);
//                String fileName=titleMd5+extension;
                //本地路径
                String fileSavePath=imageLocalPath+ File.separator+nowStr+ File.separator+titleMd5;
                urlList.add(saveFile(file,key, fileSavePath));
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new ServiceException(ResultCodeEnum.DATA_IS_WRONG);
            }
        }
        return urlList;

    }

    @Override
    public String uploadedFiles(String title, Long sid, MultipartFile file) {
        try {
            String titleMd5=DigestUtil.md5Hex(title);
            String nowStr= LocalDate.now().toString();
            //e2路径
            String key= "/image/" + Math.abs(HashUtil.apHash(title)) % 1000 + "/" +  titleMd5;
//            int dotIndex = file.getOriginalFilename().lastIndexOf('.');
            // 获取原始后缀
//            String extension = file.getOriginalFilename().substring(dotIndex);
//            String fileName=titleMd5+extension;
            //本地路径
            String fileSavePath=imageLocalPath+ File.separator+nowStr+ File.separator+titleMd5;
            return saveFile(file,key, fileSavePath);
        } catch (Exception ex) {
            throw new ServiceException(ResultCodeEnum.DATA_IS_WRONG);
        }
    }

}
