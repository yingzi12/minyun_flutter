package com.xinshijie.gallery.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinshijie.gallery.common.ResultCodeEnum;
import com.xinshijie.gallery.common.ServiceException;
import com.xinshijie.gallery.domain.PaymentOrder;
import com.xinshijie.gallery.domain.UserAlbum;
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
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static com.xinshijie.gallery.util.RequestContextUtil.getUserId;

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
            dto.setPageSize(10L);
        }
        page.setSize(dto.getPageSize());
        page.setCurrent(dto.getPageNum());
        return mapper.selectPageUserAlbum(page, dto);
    }

    @Override
    public List<UserAlbum> findRandomStories(Integer pageSize) {
        Integer maxId = mapper.findMaxId(); //
        Integer minId = mapper.findMinId();
        QueryWrapper<UserAlbum> qw=new QueryWrapper<>();
        qw.eq("status",AlbumStatuEnum.NORMAL.getCode());
        qw.eq("device",0);
        Long count=mapper.selectCount(qw);//
        if(30<count) {
            Integer randomId = ThreadLocalRandom.current().nextInt(minId, maxId - 30);
            return mapper.findRandomStories(randomId, null,pageSize);
        }else{
            Integer randomId = ThreadLocalRandom.current().nextInt(minId, maxId);
            return mapper.findRandomStories(randomId,null, pageSize);
        }
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
        if (dto.getDevice() == null) {
            qw.eq("device",0);
        }else{
            qw.eq("device",dto.getDevice());
        }
        return mapper.getPageUserAlbum(page, qw);
    }

    /**
     * 新增数据
     */
    @Override
    public UserAlbum add(UserAlbumDto dto) {
        UserAlbum value = new UserAlbum();

        org.springframework.beans.BeanUtils.copyProperties(dto, value);
        if(Strings.isNotEmpty(dto.getImgUrl())){
            value.setImgUrl(dto.getImgUrl());
        }
        value.setCreateTime(LocalDateTime.now());
        value.setCountBuy(0);
        value.setCountSee(0);
        if(dto.getDevice()==null){
            value.setDevice(0);
        }else{
            value.setDevice(dto.getDevice());
        }
        value.setCountCollection(0);
        value.setStatus(AlbumStatuEnum.WAIT.getCode());
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
        if(Strings.isNotEmpty(dto.getImgUrl())){
            userAlbum.setImgUrl(dto.getImgUrl());
        }
        userAlbum.setDevice(dto.getDevice());
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
    public UserAlbum getInfo( Integer id) {
        UserAlbum userAlbum = mapper.selectById(id);

        return userAlbum;
    }

    @Override
    public UserAlbum previousChapter(Integer id) {
        return mapper.previousChapter(id,null);
    }

    @Override
    public UserAlbum nextChapter(Integer id) {
        return mapper.nextChapter(id,null);
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

    public String saveUploadedFiles(String day, MultipartFile file) {
        try {
            if (file.isEmpty()) {
                log.error("No image file provided");
                throw new ServiceException(ResultCodeEnum.ALBUM_IMGURL_NULL);
            }
            String md5 = fileService.getMD5(file.getInputStream());
            String imgUrl = fileService.saveUploadedFilesWatermark("/user/head/" , file.getOriginalFilename(), md5,file);
            return imgUrl;
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

    public UserAlbum isCheckOperate(Integer aid){
        UserAlbum userAlbum= mapper.selectById(aid);
        if(userAlbum==null){
            throw new ServiceException(ResultCodeEnum.OPERATOR_ERROR);
        }
        if(userAlbum.getStatus() != AlbumStatuEnum.WAIT.getCode()){
            throw new ServiceException(ResultCodeEnum.USER_ALBUM_STATUS_ERROR);
        }
        //判断是否是否已经购买
        PaymentOrder paymentOrder = paymentOrderService.selectByDonePay(getUserId(), PaymentKindEnum.USER_ALBUM.getCode(), userAlbum.getId());
        if(paymentOrder!=null){
            throw new ServiceException(ResultCodeEnum.USER_ALBUM_SELL_ERROR);
        }
        return userAlbum;
    }
}
