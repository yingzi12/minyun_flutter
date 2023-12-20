package com.xinshijie.gallery.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinshijie.gallery.common.ResultCodeEnum;
import com.xinshijie.gallery.common.ServiceException;
import com.xinshijie.gallery.dao.Album;
import com.xinshijie.gallery.domain.PaymentOrder;
import com.xinshijie.gallery.domain.UserAlbum;
import com.xinshijie.gallery.domain.UserBuyAlbum;
import com.xinshijie.gallery.domain.UserVip;
import com.xinshijie.gallery.dto.UserAlbumDto;
import com.xinshijie.gallery.enmus.AlbumChargeEnum;
import com.xinshijie.gallery.enmus.AlbumStatuEnum;
import com.xinshijie.gallery.enmus.PaymentKindEnum;
import com.xinshijie.gallery.mapper.UserAlbumMapper;
import com.xinshijie.gallery.service.*;
import com.xinshijie.gallery.vo.UserAlbumVo;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
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
    private IUserVipService userVipService;
    @Autowired
    private IPaymentOrderService paymentOrderService;
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
    public IPage<UserAlbum> selectPageUserAlbum(UserAlbumDto dto) {
        Page<UserAlbum> page = new Page<>();
        if (dto.getPageNum() == null) {
            dto.setPageNum(1L);
        }
        if (dto.getPageSize() == null) {
            dto.setPageSize(20L);
        }
        page.setSize(dto.getPageSize());
        page.setCurrent(dto.getPageNum());

        return mapper.selectPageUserAlbum(page, dto);
    }

    /**
     * 分页查询图片信息表
     */
    @Override
    public IPage<UserAlbum> getPageUserAlbum(UserAlbumDto dto) {
        Page<UserAlbum> page = new Page<>();
        if (dto.getPageNum() == null) {
            dto.setPageNum(1L);
        }
        if (dto.getPageSize() == null) {
            dto.setPageSize(20L);
        }
        page.setSize(dto.getPageSize());
        page.setCurrent(dto.getPageNum());

        QueryWrapper<UserAlbum> qw = new QueryWrapper<>();
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
        value.setCountBuy(0);
        value.setCountSee(0);
        value.setCountCollection(0);
        value.setStatus(AlbumStatuEnum.NORMAL.getCode());
        setPrice(value, value.getCharge(), value.getPrice(), value.getVipPrice());
        mapper.insert(value);
        return value;
    }

    /**
     * 根据id修改数据
     */
    @Override
    public Boolean edit(UserAlbumDto dto) {
        UserAlbum userAlbum = new UserAlbum();
        userAlbum.setCharge(dto.getCharge());
        userAlbum.setIntro(dto.getIntro());
        userAlbum.setIntroduce(dto.getIntroduce());
        userAlbum.setTitle(dto.getTitle());
        userAlbum.setGirl(dto.getGirl());
        userAlbum.setTags(dto.getTags());
        userAlbum.setPrice(dto.getPrice());
        userAlbum.setVipPrice(dto.getVipPrice());
        userAlbum.setUpdateTime(LocalDateTime.now());
        setPrice(userAlbum, dto.getCharge(), dto.getPrice(), dto.getVipPrice());
        QueryWrapper<UserAlbum> qw = new QueryWrapper<>();
        qw.eq("user_id", dto.getUserId());
        qw.eq("id", dto.getId());

        int i = mapper.update(userAlbum, qw);
        return i == 1;
    }

    /**
     * 删除数据
     */
    @Override
    public Integer delById(Integer userId, Integer id) {
        return mapper.delById(userId, id);
    }

    /**
     * 根据id数据
     */
    @Override
    public UserAlbum getInfo(Integer userId, Integer id) {
        UserAlbum userAlbum = mapper.selectById(id);

        return userAlbum;
    }

    public Boolean isSee(UserAlbumVo userAlbum, Integer userId) {
        userAlbum.setAmount(0.0);
        //'1 免费', '2 VIP免费', '3 VIP折扣', '4 VIP独享' 5.统一
        //判断是否需要付费
        if (AlbumChargeEnum.FREE.getCode().equals(userAlbum.getCharge())) {
            userAlbum.setAmount(0.0);
            userAlbum.setIsSee(true);
            return true;
        }
        if (userId == null) {
            userAlbum.setIsSee(false);
            return false;
        }
        if (userAlbum.getUserId() == userId) {
            userAlbum.setIsSee(true);
            return true;
        }
        //判断用户是否是VIP
        UserVip userVip = userVipService.getInfo(userAlbum.getUserId(), userId);
        if (userVip != null) {
            userAlbum.setIsVip(1);
        } else {
            userAlbum.setIsVip(0);
        }
        //判断是否vip免费
        if (AlbumChargeEnum.VIP_FREE.getCode().equals(userAlbum.getCharge())) {
            if (userVip != null) {
                userAlbum.setAmount(0.0);
                userAlbum.setIsSee(true);
                return true;
            }else {
                userAlbum.setAmount(userAlbum.getPrice());
            }
        }
        //判断是否是否已经购买
        PaymentOrder paymentOrder = paymentOrderService.selectByDonePay(userId, PaymentKindEnum.USER_ALBUM.getCode(), userAlbum.getId());
        if (paymentOrder != null) {
            userAlbum.setAmount(0.0);
            userAlbum.setIsSee(true);
            return true;
        } else {
            if (AlbumChargeEnum.VIP_DISCOUNT.getCode().equals(userAlbum.getCharge())) {
                if (userVip != null) {
                    userAlbum.setAmount(userAlbum.getVipPrice());
                    userAlbum.setPrice(userAlbum.getVipPrice());
                }else{
                    userAlbum.setAmount(userAlbum.getPrice());
                }
            }
            if (AlbumChargeEnum.VIP_EXCLUSIVE.getCode().equals(userAlbum.getCharge())) {
                if (userVip != null) {
                    userAlbum.setPrice(userAlbum.getVipPrice());
                }
                userAlbum.setAmount(userAlbum.getPrice());
            }
        }
        userAlbum.setIsSee(false);
        return false;
    }

    @Override
    public Album previousChapter(Integer id) {
        return mapper.previousChapter(id);
    }

    @Override
    public Album nextChapter(Integer id) {
        return mapper.nextChapter(id);
    }


    public Boolean isCheck(Integer aid, Integer userId) {
        UserAlbum userAlbum = mapper.selectById(aid);
        //'1 免费', '2 VIP免费', '3 VIP折扣', '4 VIP独享' 5.统一
        //判断是否需要付费
        if (AlbumChargeEnum.FREE.getCode().equals(userAlbum.getCharge())) {
            return true;
        }
        if (userId == null) {
            return false;
        }
        if (userAlbum.getUserId() == userId) {
            return true;
        }
        //判断用户是否是VIP
        UserVip userVip = userVipService.getInfo(userAlbum.getUserId(), userId);
        //判断是否vip免费
        if (AlbumChargeEnum.VIP_FREE.getCode().equals(userAlbum.getCharge())) {
            if (userVip != null) {
                return true;
            }
        }
        //判断是否是否已经购买
        PaymentOrder paymentOrder = paymentOrderService.selectByDonePay(userId, PaymentKindEnum.USER_ALBUM.getCode(), userAlbum.getId());
        return paymentOrder != null;
    }

    @Override
    public Boolean updateCharge(Integer userId, Long id, Integer charge, Double price, Double vipPrice) {
        UserAlbum userAlbum = new UserAlbum();

        setPrice(userAlbum, charge, price, vipPrice);
        QueryWrapper<UserAlbum> qw = new QueryWrapper<>();
        qw.eq("user_id", userId);
        qw.eq("id", id);

        int i = mapper.update(userAlbum, qw);
        return i == 1;
    }

    /**
     * 设置价格
     *
     * @param userAlbum
     * @param charge
     * @param price
     * @param vipPrice
     */
    public void setPrice(UserAlbum userAlbum, Integer charge, Double price, Double vipPrice) {
        if (charge == null) {
            throw new ServiceException(ResultCodeEnum.ALBUM_FREE_STATUS);
        }
        if (AlbumChargeEnum.FREE.getCode().equals(charge)) {
            userAlbum.setPrice(0.0);
            userAlbum.setVipPrice(0.0);
        }

        if (AlbumChargeEnum.VIP_FREE.getCode().equals(charge)) {
            if (price != null && price > 0.5 && price < 1000) {
                userAlbum.setPrice(price);
                userAlbum.setVipPrice(0.0);
            } else {
                throw new ServiceException(ResultCodeEnum.ALBUM_FREE_STATUS);
            }
        }
        if (AlbumChargeEnum.VIP_DISCOUNT.getCode().equals(charge)) {
            if (price != null && price > 0.5 && price < 1000) {
                userAlbum.setPrice(price);
            } else {
                throw new ServiceException(ResultCodeEnum.ALBUM_FREE_STATUS);
            }
            if (vipPrice != null && vipPrice > 0.5 && vipPrice < 1000) {
                userAlbum.setVipPrice(vipPrice);
            } else {
                throw new ServiceException(ResultCodeEnum.ALBUM_FREE_STATUS);
            }
        }
        if (AlbumChargeEnum.VIP_EXCLUSIVE.getCode().equals(charge)) {
            if (vipPrice != null && vipPrice > 0.5 && vipPrice < 1000) {
                userAlbum.setPrice(0.0);
                userAlbum.setVipPrice(vipPrice);
            } else {
                throw new ServiceException(ResultCodeEnum.ALBUM_FREE_STATUS);
            }
        }
        if (AlbumChargeEnum.UNIFICATION.getCode().equals(charge)) {
            if (price != null && price > 0.5 && price < 1000) {
                userAlbum.setPrice(price);
                userAlbum.setVipPrice(price);
            } else {
                throw new ServiceException(ResultCodeEnum.ALBUM_FREE_STATUS);
            }
        }
        userAlbum.setCharge(charge);
    }

    public Double getAmount(Integer aid, Integer userId, Integer charge, Double price, Double vipPrice) {
        UserAlbum userAlbum = mapper.selectById(aid);
        //判断用户是否是VIP
        UserVip userVip = userVipService.getInfo(userAlbum.getUserId(), userId);
        if (userAlbum.getCharge() == 1) {
            return 0.0;
        }
        //判断是否是否已经购买
        PaymentOrder paymentOrder = paymentOrderService.selectByDonePay(userId, PaymentKindEnum.USER_ALBUM.getCode(), userAlbum.getId());
        if (paymentOrder != null) {
            return 0.0;
        }
        //判断是否vip免费
        if (AlbumChargeEnum.VIP_FREE.getCode().equals(userAlbum.getCharge())) {
            if (userVip != null) {
                return 0.0;
            } else {
                return userAlbum.getPrice();
            }
        }
        if (AlbumChargeEnum.VIP_DISCOUNT.getCode().equals(userAlbum.getCharge())) {
            if (userVip != null) {
                return userAlbum.getVipPrice();
            } else {
                return userAlbum.getPrice();
            }
        }
        if (AlbumChargeEnum.VIP_EXCLUSIVE.getCode().equals(userAlbum.getCharge())) {
            if (userVip != null) {
                return userAlbum.getVipPrice();
            } else {
                return 0.0;
            }
        }
        if (AlbumChargeEnum.UNIFICATION.getCode().equals(userAlbum.getCharge())) {
            return userAlbum.getPrice();
        }
        return 0.0;
    }

    @Override
    public Boolean updateStatus(Integer userId, Long id, Integer status) {
        QueryWrapper<UserAlbum> qw = new QueryWrapper<>();
        qw.eq("user_id", userId);
        qw.eq("id", id);

        UserAlbum userAlbum = new UserAlbum();
        userAlbum.setStatus(status);
        int i = mapper.update(userAlbum, qw);
        return i == 1;
    }

    public String saveUploadedFiles(Integer userId, MultipartFile file) {
        try {
            if (file.isEmpty()) {
                log.error("No image file provided");
                throw new ServiceException(ResultCodeEnum.ALBUM_IMGURL_NULL);
            }
            try {
                String mm5 = fileService.getMD5(file.getInputStream());
                String imgUrl = "/user/album/" + userId + "_" + mm5 + ".jpg";
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
    public Integer updateCountImage(Integer id) {
        return mapper.updateCountImage(id);
    }

    @Override
    public Integer updateCountVideo(Integer id) {
        return mapper.updateCountVideo(id);
    }

    @Override
    public Integer updateCountSee(Integer id, String updateDate) {
        return mapper.updateCountSee(id,updateDate);
    }

}
