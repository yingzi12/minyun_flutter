package com.xinshijie.gallery.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinshijie.gallery.domain.UserAlbum;
import com.xinshijie.gallery.dto.UserAlbumDto;
import com.xinshijie.gallery.mapper.UserAlbumMapper;
import com.xinshijie.gallery.service.IFileService;
import com.xinshijie.gallery.service.IUserAlbumService;
import com.xinshijie.gallery.vo.UserAlbumVo;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 用户创建的  服务实现类
 * </p>
 *
 * @author zx
 * @since 2022-09-05
 */
@Slf4j
@Service
public class UserAlbumServiceImpl extends ServiceImpl<UserAlbumMapper, UserAlbum> implements IUserAlbumService {

    @Autowired
    private UserAlbumMapper mapper;

    @Autowired
    private IFileService fileService;

    @Value("${image.sourceWeb}")
    private String imageSourceWeb;

    @Value("${image.path}")
    private String imagePath;

    /**
     * 查询图片信息表
     */
    @Override
    public List<UserAlbumVo> selectUserAlbumList(UserAlbumDto dto) {
        return mapper.selectListUserAlbum(dto);
    }

    /**
     * 分页查询图片信息表
     */
    @Override
    public Page<UserAlbumVo> selectPageUserAlbum(UserAlbumDto dto) {
        Page<UserAlbumVo> page = new Page<>();
        page.setCurrent(dto.getPageNum());
        page.setSize(dto.getPageSize());
        return mapper.selectPageUserAlbum(page, dto);
    }

    /**
     * 分页查询图片信息表
     */
    @Override
    public Page<UserAlbumVo> getPageUserAlbum(UserAlbumDto dto) {
        Page<UserAlbumVo> page = new Page<>();
        page.setCurrent(dto.getPageNum());
        page.setSize(dto.getPageSize());
        QueryWrapper<UserAlbumVo> qw = new QueryWrapper<>();
        return mapper.getPageUserAlbum(page, qw);
    }

    /**
     * 新增数据
     */
    @Override
    public UserAlbum add(UserAlbumDto dto) {
        UserAlbum value = new UserAlbum();
        org.springframework.beans.BeanUtils.copyProperties(dto, value);
        value.setCreateTime(LocalDateTime.now());
        mapper.insert(value);
        return value;
    }

    /**
     * 根据id修改数据
     */
    @Override
    public Integer edit(UserAlbumDto dto) {
        return mapper.edit(dto);
    }

    /**
     * 删除数据
     */
    @Override
    public Integer delById(Long id) {
        return mapper.delById(id);
    }

    /**
     * 根据id数据
     */
    @Override
    public UserAlbumVo getInfo(Long id) {
        //判断是否需要付费
        //是的话判断是否已付款
        //判断是否VIP
        //判断已购买VIP
        //TODO 判断是否免费
        return mapper.getInfo(id);
    }

    public Boolean saveUploadedFiles(Integer userId,MultipartFile file)  {
        try {
            if (file.isEmpty()) {
                log.error( "No image file provided");
                return false;
            }
            try {
                String mm5=fileService.getMD5(file.getInputStream());
                String imgUrl = "/user/album/" +userId+"_"+mm5 + ".jpg";
                // 假设我们将图片保存在服务器的某个位置
                File destinationFile = new File(imagePath+imgUrl);
                File parentDir = destinationFile.getParentFile();
                // 如果父目录不存在，尝试创建它
                if (parentDir != null && !parentDir.exists()) {
                    parentDir.mkdirs();
                }
                // 根据图片大小设置压缩质量
                double outputQuality = file.getSize() > 1024 * 1024 ? 0.6 : 0.8;

                // 转换图片格式为JPG并添加水印
                Thumbnails.of(file.getInputStream())
                        .size(213,320)
                        .outputQuality(outputQuality) // 设置压缩质量
                        .outputFormat("jpg")
                        .toFile(destinationFile); // 保存到文件


                // 图片验证通过，更新用户信息
//                UserAlbum user = new UserAlbum();
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
}
