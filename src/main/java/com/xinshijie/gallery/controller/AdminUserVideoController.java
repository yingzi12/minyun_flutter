package com.xinshijie.gallery.controller;

import cn.hutool.core.util.HashUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinshijie.gallery.common.*;
import com.xinshijie.gallery.domain.AllVideo;
import com.xinshijie.gallery.domain.UserAlbum;
import com.xinshijie.gallery.domain.UserVideo;
import com.xinshijie.gallery.dto.UserVideoDto;
import com.xinshijie.gallery.dto.UserVideoFindDto;
import com.xinshijie.gallery.enmus.AlbumStatuEnum;
import com.xinshijie.gallery.mq.MessageProducer;
import com.xinshijie.gallery.service.IUserAlbumService;
import com.xinshijie.gallery.service.IUserVideoService;
import com.xinshijie.gallery.vo.UserVideoVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.List;

import static com.xinshijie.gallery.util.RequestContextUtil.getUserId;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 作者
 * @since 2023-09-07
 */
@Slf4j
@Tag(name = " AdminUserVideoController", description = "后台- ")
@RestController
@RequestMapping("/admin/userVideo")
public class AdminUserVideoController extends BaseController {

    @Autowired
    private IUserVideoService userVideoService;

    @Autowired
    private IUserAlbumService userAlbumService;

    @PostMapping("/add")
    public Result<UserVideo> add(@RequestBody UserVideo dto) {
        dto.setUserId(getUserId());
        UserVideo vo = userVideoService.add(dto);
        return Result.success(vo);
    }

    /**
     * 修改
     *
     * @return
     */

    @PostMapping("/edit")
    public Result<Integer> edit(@RequestBody UserVideo dto) {
        dto.setUserId(getUserId());
        Integer vo = userVideoService.edit(dto);
        return Result.success(vo);
    }

    /**
     * 删除
     *
     * @return
     */
    @GetMapping("/remove/{id}")
    public Result<Integer> del(@PathVariable("id") Long id) {
        UserAlbum userAlbum = userAlbumService.getById(id);
        if(userAlbum==null){
            throw new ServiceException(ResultCodeEnum.DATA_IS_WRONG);
        }
        if(userAlbum.getStatus()!= AlbumStatuEnum.NORMAL.getCode()){
            throw new ServiceException(ResultCodeEnum.NOT_POST_STATUS);
        }
        Integer vo = userVideoService.delById(getUserId(), id);
        return Result.success(vo);
    }

    @GetMapping(value = "/updateIsFree")
    public Result<Integer> updateIsFree(@RequestParam("id") Long id, @RequestParam("isFree") Integer isFree) {
        UserAlbum userAlbum = userAlbumService.getById(id);
        if(userAlbum==null){
            throw new ServiceException(ResultCodeEnum.DATA_IS_WRONG);
        }
        if(userAlbum.getStatus()!= AlbumStatuEnum.NORMAL.getCode()){
            throw new ServiceException(ResultCodeEnum.NOT_POST_STATUS);
        }
        Integer vo = userVideoService.updateIsFree(getUserId(), id, isFree);
        return Result.success(vo);
    }


    /**
     * 查询详情
     *
     * @return
     */

    @GetMapping(value = "/getInfo/{id}")
    public Result<UserVideoVo> getInfo(@PathVariable("id") Long id) {
        UserVideoVo vo = userVideoService.getInfo(id);
        return Result.success(vo);
    }


    /**
     * 查询
     *
     * @return
     */
    @GetMapping("/list")
    public Result<List<UserVideoVo>> list(UserVideoFindDto findDto) {
        findDto.setUserId(getUserId());

        UserVideoDto videoDto=new UserVideoDto();
        BeanUtils.copyProperties(findDto,videoDto);
        List<UserVideoVo> vo = userVideoService.selectUserVideoList(videoDto);
        Long count=userVideoService.selectCount(videoDto);
        return Result.success(vo, count.intValue());
    }

    @GetMapping("/checkAllMd5")
    public Result<AllVideo> checkAllMd5(String md5) {
//        findDto.setUserId(getUserId());
        AllVideo vo = userVideoService.checkAllMd5(md5);
        if(vo==null){
            return Result.error(ResultCodeEnum.DATA_NOT_FOUND);
        }else {
            return Result.success(vo);
        }
    }
    @PostMapping("/upload")
    public Result<String> handleFileUpload(@RequestPart(value = "file") final MultipartFile uploadfile, @RequestParam("aid") Integer aid, @RequestParam("isFree") Integer isFree) {
        log.info("upload aid:" + aid);
        String url = userVideoService.saveUploadedFiles(getUserId(), aid, isFree, uploadfile);
        return Result.success(url);
    }

    /**
     * 分段上传
     *
     * @param file
     * @param chunkNumber
     * @param totalChunks
     * @param identifier
     * @param aid
     * @return
     */
    @PostMapping("/uploadSection")
    public Result<Boolean> handleFileSectionUpload(@RequestParam("file") MultipartFile file,
                                                   @RequestParam("chunkNumber") int chunkNumber,
                                                   @RequestParam("totalChunks") int totalChunks,
                                                   @RequestParam("identifier") String identifier,
                                                   @RequestParam("aid") Integer aid,
                                                   @RequestParam("day") String day,
                                                   @RequestParam("isFree") Integer isFree,
                                                   @RequestParam("md5") String md5) {
        try {
            Long count= userVideoService.getCount(aid,isFree);
            if(isFree==1 && count>3){
               throw new ServiceException(ResultCodeEnum.VEDIO_UPLOAD_MAX);
            }
            if(isFree==2 && count>10){
                throw new ServiceException(ResultCodeEnum.VEDIO_UPLOAD_MAX);
            }

            String[] nameArr = identifier.split("\\.");
            String hz = nameArr[nameArr.length - 1];
            String hashFileName = HashUtil.hfHash(identifier) + "." + hz;
            //本地缓存地址
            String soruceSavePath = day;
            log.info("upload  chunkNumber:{}  totalChunks:{} identifier:{},idHash:{} filename:{}", chunkNumber, totalChunks, identifier, hashFileName, file.getOriginalFilename());
            String chunkFileName = hashFileName + "-" + chunkNumber;
            Path chunkFile = Paths.get(Constants.videoHcPath + soruceSavePath + "/" + chunkFileName);

            File destinationFile = new File(Constants.videoHcPath + soruceSavePath + "/" + chunkFileName);
            File parentDir = destinationFile.getParentFile();
            // 如果父目录不存在，尝试创建它
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }
            try (OutputStream outputStream = new BufferedOutputStream(Files.newOutputStream(chunkFile, StandardOpenOption.CREATE))) {
                outputStream.write(file.getBytes());
            }

            // Rename the file to indicate it has been uploaded
            Files.move(chunkFile, chunkFile.resolveSibling("uploaded_" + chunkFileName));

            // Check if all chunks have been uploaded
            if (allChunksUploaded(day,hashFileName, totalChunks)) {
                log.info("All chunks uploaded, starting to merge file: " + hashFileName);

                 mergeFile(soruceSavePath,hashFileName, totalChunks);

                userVideoService.updateUploadedFiles(getUserId(), aid, isFree, 0L, md5, soruceSavePath, hashFileName);
            }
        } catch (Exception ex) {
            log.error("Error in file upload: " + identifier, ex);
            return Result.error(ResultCodeEnum.SYSTEM_INNER_ERROR);
        }
        return Result.success(true);
    }

    private boolean allChunksUploaded(String day,String idHash, int totalChunks) {
        for (int i = 0; i < totalChunks; i++) {
            Path chunkFile = Paths.get(Constants.videoHcPath + day + "/" + "uploaded_" + idHash + "-" + i);
            if (!Files.exists(chunkFile)) {
                log.info("Missing chunk: " + chunkFile.toString());
                return false;
            }
        }
        return true;
    }

    @GetMapping("/check")
    public Result<String> checkChunkExists(@RequestParam("identifier") String identifier,
                                           @RequestParam("chunkNumber") int chunkNumber,
                                           @RequestParam("day") String day
                                           ) {
        Path chunkFile = Paths.get(Constants.videoHcPath +day+ "/" + "uploaded_" + identifier + "-" + chunkNumber);
        if (Files.exists(chunkFile)) {
            return Result.success(ResultCodeEnum.SUCCESS);
        } else {
            return Result.error(ResultCodeEnum.FAIL);
        }
    }

    private String getFileMD5(Path path) throws IOException, NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        try (InputStream is = Files.newInputStream(path);
             DigestInputStream dis = new DigestInputStream(is, md)) {
            byte[] buffer = new byte[4096];
            while (dis.read(buffer) != -1) {
                // 读取文件并更新 MD5 摘要
            }
        }
        // 将生成的 MD5 值转换为十六进制字符串
        byte[] md5Bytes = md.digest();
        BigInteger bi = new BigInteger(1, md5Bytes);
        return String.format("%032x", bi);
    }

    //合并文件，同时返回文件的hash值
    private String mergeFile(String soruceSavePath,String hashFileName, int totalChunks) throws IOException, NoSuchAlgorithmException {
        Path fileOutput = Paths.get(Constants.videoHcPath + soruceSavePath + "/" + hashFileName);
        MessageDigest md = MessageDigest.getInstance("MD5");
        long fileSize = 0;

        try (OutputStream mergeFile = new BufferedOutputStream(Files.newOutputStream(fileOutput, StandardOpenOption.CREATE))) {
            for (int i = 0; i < totalChunks; i++) {
                Path chunkFile = Paths.get(Constants.videoHcPath +soruceSavePath + "/" + "uploaded_" + hashFileName + "-" + i);
                byte[] buffer = new byte[4096];
                int len;
                try (InputStream is = Files.newInputStream(chunkFile)) {
                    while ((len = is.read(buffer)) != -1) {
                        mergeFile.write(buffer, 0, len);
                        fileSize += len;
                    }
                }
                Files.delete(chunkFile); // 删除分块文件
            }
        }

        // 计算简化的哈希.改成前段
//        try (RandomAccessFile file = new RandomAccessFile(fileOutput.toFile(), "r")) {
//            long[] samplePoints = new long[]{0, fileSize / 2, fileSize - Math.min(fileSize, 4096)};
//            for (long point : samplePoints) {
//                file.seek(point);
//                byte[] bytes = new byte[Math.min((int) (fileSize - point), 4096)];
//                int readSize = file.read(bytes);
//                md.update(bytes, 0, readSize);
//            }
//        }

//        byte[] md5Bytes = md.digest();
//        BigInteger bi = new BigInteger(1, md5Bytes);
//        String md5 = String.format("%032x", bi);

//        log.info("File merged successfully: {}, MD5: {}", fileOutput, md5);
        return "";
    }

}
