//package com.xinshijie.gallery.controller;
//
//import com.xinshijie.common.config.XinshijieConfig;
//import com.xinshijie.common.constant.Constants;
//import com.xinshijie.common.core.vo.Result;
//import com.xinshijie.common.enums.ResultCodeEnum;
//import com.xinshijie.common.utils.StringUtils;
//import com.xinshijie.common.utils.file.AliOSS;
//import com.xinshijie.common.utils.file.FileUploadUtils;
//import com.xinshijie.common.utils.file.FileUtils;
//import com.xinshijie.framework.config.ServerConfig;
//import com.xinshijie.wiki.dto.ImageDto;
//import com.xinshijie.wiki.service.IImageService;
//import com.xinshijie.wiki.vo.ImageVo;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.codec.digest.DigestUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * 通用请求处理
// *
// * @author xinshijie
// */
//@Slf4j
//@RestController
//@RequestMapping("/common")
//public class CommonController {
//    //private static final Logger log = LoggerFactory.getLogger(CommonController.class);
//
//
//    @Autowired
//    private IImageService imageService;
//
//    private static final String FILE_DELIMETER = ",";
//
//    /**
//     * 通用下载请求
//     *
//     * @param fileName 文件名称
//     * @param delete   是否删除
//     */
//    @GetMapping("/download")
//    public void fileDownload(String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request) {
//        try {
//            if (!FileUtils.checkAllowDownload(fileName)) {
//                throw new Exception(StringUtils.format("文件名称({})非法，不允许下载。 ", fileName));
//            }
//            String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
//            String filePath = XinshijieConfig.getDownloadPath() + fileName;
//
//            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
//            FileUtils.setAttachmentResponseHeader(response, realFileName);
//            FileUtils.writeBytes(filePath, response.getOutputStream());
//            if (delete) {
//                FileUtils.deleteFile(filePath);
//            }
//        } catch (Exception e) {
//            log.error("下载文件失败", e);
//        }
//    }
//
//    /**
//     * 通用上传请求（单个）
//     */
//    @PostMapping("/upload")
//    public Result<Map<String, Object>> uploadFile(MultipartFile file) throws Exception {
//        try {
//            String md5 = DigestUtils.md5Hex(file.getInputStream());
//            ImageVo imageVo = imageService.getInfoByMd5(md5);
//            if (imageVo != null) {
//                Map ajax = new HashMap();
//                ajax.put("id", imageVo.getId());
//                ajax.put("url", serverConfig.getUrl() + imageVo.getFileName());
//                ajax.put("fileName", imageVo.getFileName());
//                ajax.put("uploaded", true);
//
//                ajax.put("newFileName", FileUtils.getName(imageVo.getFileName()));
//                ajax.put("originalFilename", file.getOriginalFilename());
//                return Result.success(ajax);
//            }
//            // 上传文件路径
//            String filePath = XinshijieConfig.getUploadPath();
//            // 上传并返回新文件名称
//            String fileName = FileUploadUtils.upload(filePath, file);
////            String md5=DigestUtils.md5Hex(file.getInputStream());
//            String url = serverConfig.getUrl() + fileName;
//            ImageDto image = new ImageDto();
//            image.setFileName(fileName);
//            image.setFileUrl(serverConfig.getUrl());
//            image.setFileSize(file.getSize());
//            image.setFileType(FileUploadUtils.getExtension(file));
//            image.setMd5(md5);
//            image.setOriginalFilename(file.getOriginalFilename());
//            imageService.add(image);
//            Map ajax = new HashMap();
//            ajax.put("id", image.getId());
//            ajax.put("url", url);
//            ajax.put("fileName", fileName);
//            ajax.put("newFileName", FileUtils.getName(fileName));
//            ajax.put("originalFilename", file.getOriginalFilename());
//            return Result.success(ajax);
//        } catch (Exception e) {
//            return Result.error(ResultCodeEnum.SYSTEM_INNER_ERROR.getCode(), e.getMessage());
//        }
//    }
//
//    /**
//     * 上传编辑器的文件到OSs ,图片会被压缩是缩小
//     *
//     * @param file
//     * @return
//     */
//    @PostMapping("/uploadImage")
//    public Result<Map<String, Object>> uploadEditFile(@RequestParam("upload") MultipartFile file) {
//        try {
//            //获取上传文件的MD5,如果文件已经存在,直接返回路径
//            String md5 = DigestUtils.md5Hex(file.getInputStream());
//            ImageVo imageVo = imageService.getInfoByMd5(md5);
//            if (imageVo != null) {
//                Map ajax = new HashMap();
//                ajax.put("id", imageVo.getId());
//                ajax.put("url", imageVo.getFileUrl() + imageVo.getFileName());
//                ajax.put("fileName", imageVo.getFileName());
//                ajax.put("uploaded", true);
//
//                ajax.put("newFileName", FileUtils.getName(imageVo.getFileName()));
//                ajax.put("originalFilename", file.getOriginalFilename());
//                return Result.success(ajax);
//            }
//            // 上传文件路径
//            String filePath = XinshijieConfig.getUploadPath();
//            // 上传并返回新文件名称
//            String fileName = FileUploadUtils.uploadImageOss(filePath, file);
//            ImageDto image = new ImageDto();
//            image.setFileName(fileName);
//            image.setFileUrl(AliOSS.OSSURL);
//            image.setFileSize(file.getSize());
//            image.setFileType(FileUploadUtils.getExtension(file));
//            image.setMd5(md5);
//            image.setOriginalFilename(file.getOriginalFilename());
//            imageService.add(image);
//
//            String url = serverConfig.getUrl() + fileName;
//            Map ajax = new HashMap();
//
//            ajax.put("id", image.getId());
//            ajax.put("url", image.getFileUrl() + image.getFileName());
//            ajax.put("fileName", fileName);
//            ajax.put("uploaded", true);
//
//            ajax.put("newFileName", FileUtils.getName(fileName));
//            ajax.put("originalFilename", file.getOriginalFilename());
//            return Result.success(ajax);
//        } catch (Exception e) {
//            return Result.error(ResultCodeEnum.SYSTEM_INNER_ERROR.getCode(), e.getMessage());
//        }
//    }
//
//    /**
//     * 通用上传请求（多个）
//     */
//    @PostMapping("/uploads")
//    public Result<Map<String, Object>> uploadFiles(List<MultipartFile> files) throws Exception {
//        try {
//            // 上传文件路径
//            String filePath = XinshijieConfig.getUploadPath();
//            List<String> urls = new ArrayList<String>();
//            List<String> fileNames = new ArrayList<String>();
//            List<String> newFileNames = new ArrayList<String>();
//            List<String> originalFilenames = new ArrayList<String>();
//            for (MultipartFile file : files) {
//                // 上传并返回新文件名称
//                String fileName = FileUploadUtils.upload(filePath, file);
//                String url = serverConfig.getUrl() + fileName;
//                urls.add(url);
//                fileNames.add(fileName);
//                newFileNames.add(FileUtils.getName(fileName));
//                originalFilenames.add(file.getOriginalFilename());
//            }
//            Map ajax = new HashMap();
//            ajax.put("urls", StringUtils.join(urls, FILE_DELIMETER));
//            ajax.put("fileNames", StringUtils.join(fileNames, FILE_DELIMETER));
//            ajax.put("newFileNames", StringUtils.join(newFileNames, FILE_DELIMETER));
//            ajax.put("originalFilenames", StringUtils.join(originalFilenames, FILE_DELIMETER));
//            return Result.success(ajax);
//        } catch (Exception e) {
//            return Result.error(ResultCodeEnum.SYSTEM_INNER_ERROR.getCode(), e.getMessage());
//        }
//    }
//
//
//    /**
//     * 本地资源通用下载
//     */
//    @GetMapping("/download/resource")
//    public void resourceDownload(String resource, HttpServletRequest request, HttpServletResponse response)
//            throws Exception {
//        try {
//            if (!FileUtils.checkAllowDownload(resource)) {
//                throw new Exception(StringUtils.format("资源文件({})非法，不允许下载。 ", resource));
//            }
//            // 本地资源路径
//            String localPath = XinshijieConfig.getProfile();
//            // 数据库资源地址
//            String downloadPath = localPath + StringUtils.substringAfter(resource, Constants.RESOURCE_PREFIX);
//            // 下载名称
//            String downloadName = StringUtils.substringAfterLast(downloadPath, "/");
//            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
//            FileUtils.setAttachmentResponseHeader(response, downloadName);
//            FileUtils.writeBytes(downloadPath, response.getOutputStream());
//        } catch (Exception e) {
//            log.error("下载文件失败", e);
//        }
//    }
//}
