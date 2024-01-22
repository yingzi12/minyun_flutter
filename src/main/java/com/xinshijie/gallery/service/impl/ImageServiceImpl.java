package com.xinshijie.gallery.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinshijie.gallery.common.ResultCodeEnum;
import com.xinshijie.gallery.common.ServiceException;
import com.xinshijie.gallery.domain.*;
import com.xinshijie.gallery.domain.Image;
import com.xinshijie.gallery.dto.ImageDto;
import com.xinshijie.gallery.mapper.ImageMapper;
import com.xinshijie.gallery.service.IAlbumService;
import com.xinshijie.gallery.service.IAllImageService;
import com.xinshijie.gallery.service.IFileService;
import com.xinshijie.gallery.service.ImageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ImageServiceImpl extends ServiceImpl<ImageMapper, Image> implements ImageService {
    @Autowired
    private ImageMapper imageMapper;
    @Autowired
    private IAlbumService albumService;
    @Autowired
    private IFileService fileService;
    @Autowired
    private IAllImageService allImageService;
    @Value("${image.sourceWeb}")
    private String imageSourceWeb;
    @Value("${image.path}")
    private String imagePath;
    private final String headPath = "/user/album/";
    @Override
    public List<Image> list(ImageDto dto) {
        return imageMapper.list(dto);
    }

    @Override
    public Integer count(ImageDto dto) {
        return imageMapper.count(dto);
    }

    @Override
    public Integer delAlum(Integer aid) {
        QueryWrapper<Image> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("aid", aid);
        return imageMapper.delete(queryWrapper);
    }

    @Override
    public Integer addBatch(List<Image> list) {
        return imageMapper.addBatch(list);
    }

    @Override
    public List<Image> listAll(Integer aid) {
        QueryWrapper<Image> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("aid", aid);
        return imageMapper.selectList(queryWrapper);
    }

    @Override
    public Integer updateSourceUrl(Image dto) {
        return imageMapper.updateSourceUrl(dto);
    }

    @Override
    public Integer delCfAid(Integer aid) {
        return imageMapper.delCfAid(aid);
    }

    @Override
    public String saveUploadedFiles(Integer aid,  MultipartFile file) {
        Album album=albumService.getById(aid);
        try {
            String md5 = fileService.getMD5(file.getInputStream());
            AllImage allImage = allImageService.getMD5(md5);
            if (allImage != null) {
                QueryWrapper<Image> qw = new QueryWrapper<>();
                qw.eq("md5", md5);
                qw.eq("aid", aid);
                Image value = imageMapper.selectOne(qw);
                if (value != null) {
                    return value.getUrl();
                } else {
                    Image image = new Image();
                    image.setAid(aid);
                    image.setSourceUrl(allImage.getSourceUrl());
                    image.setUrl(allImage.getSourceUrl());
                    image.setSourceWeb(imageSourceWeb);

                    imageMapper.insert(image);
                    albumService.updateCountImage(aid);
                    return image.getUrl();
                }
            } else {
                allImage = new AllImage();
                allImage.setMd5(md5);
                allImage.setSize(file.getSize());
                allImage.setTitle(album.getTitle());
                //保存图片到本地
                String imgUrl = saveImage(allImage, md5, file);
                if (StringUtils.isEmpty(imgUrl)) {
                    throw new ServiceException(ResultCodeEnum.UPLOAD_IMAGE_ERROR);
                }
                Image image = new Image();
                image.setAid(aid);
                image.setSourceUrl(allImage.getSourceUrl());
                image.setUrl(allImage.getSourceUrl());
                image.setSourceWeb(imageSourceWeb);
                imageMapper.insert(image);
                albumService.updateCountImage(aid);
                return image.getUrl();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServiceException(ResultCodeEnum.UPLOAD_IMAGE_ERROR);
        }
    }

    public String saveImage(AllImage allImage, String md5, MultipartFile file) {
        String url = fileService.saveUploadedFilesWatermark(headPath, allImage.getTitle(), file);
        try {
            if (StringUtils.isNotEmpty(url)) {
                allImage.setSourceWeb(imageSourceWeb);
                allImage.setSourceUrl(url);
                allImageService.save(allImage);
            }
        } catch (Exception ex) {
            //保存出问题。要么是md5出现重复，要么就数据库异常。
            allImage = allImageService.getMD5(md5);
            return allImage.getSourceUrl();
        }
        return url;
    }

}
