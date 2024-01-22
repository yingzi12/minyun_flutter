package com.xinshijie.gallery.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinshijie.gallery.common.ResultCodeEnum;
import com.xinshijie.gallery.common.ServiceException;
import com.xinshijie.gallery.domain.Album;
import com.xinshijie.gallery.domain.UserAlbum;
import com.xinshijie.gallery.dto.AlbumDto;
import com.xinshijie.gallery.dto.UserAlbumDto;
import com.xinshijie.gallery.mapper.AlbumMapper;
import com.xinshijie.gallery.service.IAlbumService;
import com.xinshijie.gallery.service.IFileService;
import com.xinshijie.gallery.vo.AlbumVo;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author User
 * @description 针对表【album】的数据库操作Service实现
 * @createDate 2023-10-27 11:26:58
 */
@Service
public class AlbumServiceImpl extends ServiceImpl<AlbumMapper, Album> implements IAlbumService {
    @Autowired
    private AlbumMapper albumMapper;
    @Autowired
    private IFileService fileService;
    @Value("${image.sourceWeb}")
    private String imageSourceWeb;
    @Value("${image.path}")
    private String imagePath;
    @Override
    public IPage<Album> list(AlbumDto dto) {
        Page<Album> page = new Page<>();
        if (dto.getPageNum() == null) {
            dto.setPageNum(1);
        }
        if (dto.getPageSize() == null) {
            dto.setPageSize(10);
        }
        page.setSize(dto.getPageSize());
        page.setCurrent(dto.getPageNum());
        return albumMapper.list(page,dto);
    }

    @Override
    public Integer count(AlbumDto dto) {
        return albumMapper.count(dto);
    }

    @Override
    public AlbumVo getInfo(Integer id) {
        AlbumVo albumVo = new AlbumVo();
        albumMapper.updateCountSee(id, LocalDate.now().toString());
        Album pre = albumMapper.previousChapter(id);
        Album next = albumMapper.nextChapter(id);
        Album album = albumMapper.getInfo(id);
        if (album == null) {
            throw new ServiceException("图集不存在！");
        }
        BeanUtils.copyProperties(album, albumVo);
        albumVo.setPre(pre);
        albumVo.setNext(next);
        return albumVo;
    }

    @Override
    public Album getInfoBytitle(String title) {
//        albumMapper.updateCountSee(id);
        return albumMapper.getInfoByTitle(title);
    }

    @Override
    public void add(Album album) {

        albumMapper.insert(album);
    }

    @Override
    public void updateError(Integer id) {
        albumMapper.updateError(id);
    }

    @Override
    public List<Album> findRandomStories(Integer pageSize) {
        Integer maxId = albumMapper.findMaxId(); //
        Integer minId = albumMapper.findMinId(); //
        Integer randomId = ThreadLocalRandom.current().nextInt(minId, maxId - 30);
        return albumMapper.findRandomStories(randomId, pageSize);
    }

    public Integer updateSourceUrl(Album dto) {
        return albumMapper.updateSourceUrl(dto);
    }

    /**
     * 删除数据
     */
    @Override
    public Integer delById( Integer id) {
        return albumMapper.deleteById( id);
    }

    @Override
    public Integer updateCountImage(Integer aid) {
        return albumMapper.updateCountImage(aid);
    }

    public String saveUploadedFiles(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                log.error("No image file provided");
                throw new ServiceException(ResultCodeEnum.ALBUM_IMGURL_NULL);
            }
            try {
                String mm5 = fileService.getMD5(file.getInputStream());
                String imgUrl = "/user/album/"  + mm5 + ".jpg";
                // 假设我们将图片保存在服务器的某个位置
                File destinationFile = new File(imagePath + imgUrl);
                File parentDir = destinationFile.getParentFile();
                // 如果父目录不存在，尝试创建它
                if (parentDir != null && !parentDir.exists()) {
                    parentDir.mkdirs();
                }
                // 根据图片大小设置压缩质量
                double outputQuality = file.getSize() > 1024 * 1024 ? 0.6 : 0.8;

                // 转换图片格式为JPG并添加水印
                Thumbnails.of(file.getInputStream())
                        .size(213, 320)
                        .outputQuality(outputQuality) // 设置压缩质量
                        .outputFormat("jpg")
                        .toFile(destinationFile); // 保存到文件

                return imgUrl;
            } catch (IOException e) {
                log.error("Error during image processing: {}," + e.getMessage(), e);
                throw new ServiceException(ResultCodeEnum.ALBUM_IMGURL_UPLOAD_ERROR);
            }


        } catch (Exception exception) {
            log.error("Error during image processing: {}," + exception.getMessage(), exception);
            // 处理异常
            throw new ServiceException(ResultCodeEnum.ALBUM_IMGURL_UPLOAD_ERROR);
        }
    }

    @Override
    public Boolean edit(Album dto) {
        Album album = new Album();
        album.setIntro(dto.getIntro());
        album.setCountSee(RandomUtil.randomLong(100));
        album.setTitle(dto.getTitle());
        album.setGirl(dto.getGirl());
        album.setTags(dto.getTags());
        if(Strings.isNotEmpty(dto.getImgUrl())){
            album.setImgUrl(dto.getImgUrl());
            album.setSourceWeb(imageSourceWeb);
            album.setSourceUrl(dto.getImgUrl());

        }
        album.setUpdateTime(LocalDate.now().toString());
        QueryWrapper<Album> qw = new QueryWrapper<>();
        qw.eq("id", dto.getId());

        int i = albumMapper.update(album, qw);
        return i == 1;
    }

}




