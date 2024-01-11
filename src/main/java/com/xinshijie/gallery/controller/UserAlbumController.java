package com.xinshijie.gallery.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xinshijie.gallery.common.*;
import com.xinshijie.gallery.dao.Album;
import com.xinshijie.gallery.domain.UserAlbum;
import com.xinshijie.gallery.domain.UserCollection;
import com.xinshijie.gallery.domain.UserImage;
import com.xinshijie.gallery.domain.UserVideo;
import com.xinshijie.gallery.dto.AlbumDto;
import com.xinshijie.gallery.dto.UserAlbumDto;
import com.xinshijie.gallery.dto.UserImageDto;
import com.xinshijie.gallery.enmus.AlbumChargeEnum;
import com.xinshijie.gallery.enmus.AlbumStatuEnum;
import com.xinshijie.gallery.service.IUserAlbumService;
import com.xinshijie.gallery.service.IUserCollectionService;
import com.xinshijie.gallery.service.IUserImageService;
import com.xinshijie.gallery.service.IUserVideoService;
import com.xinshijie.gallery.vo.AmountVo;
import com.xinshijie.gallery.vo.UserAlbumVo;
import com.xinshijie.gallery.vo.UserCollectionVo;
import com.xinshijie.gallery.vo.UserImageVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.xinshijie.gallery.util.RequestContextUtil.getUserId;
import static com.xinshijie.gallery.util.RequestContextUtil.getUserIdNoLogin;


/**
 * <p>
 * 用户创建的 前端控制器
 * </p>
 *
 * @author 作者
 * @since 2023-09-07
 */
@Slf4j
@Tag(name = " UserAlbumController", description = "后台- 用户创建的")
@RestController
@RequestMapping("/userAlbum")
public class UserAlbumController extends BaseController {

    @Autowired
    private IUserAlbumService userAlbumService;
    @Autowired
    private IUserCollectionService userCollectionService;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private IUserVideoService userVideoService;
    @Autowired
    private IUserImageService userImageService;
    /**
     * 查询详情
     *
     * @return
     */
    @GetMapping(value = "/getInfo/{id}")
    public Result<UserAlbumVo> getInfo(@PathVariable("id") Integer id) {
        Integer userId = getUserIdNoLogin();

        UserAlbum userAlbum = userAlbumService.getInfo( id);
        if (userAlbum == null) {
            throw new ServiceException(ResultCodeEnum.DATA_NOT_FOUND);
        }

        UserImageDto findDto=new UserImageDto();
        findDto.setAid(id);
        findDto.setPageSize(10L);
        IPage<UserImageVo> pageUserImage = userImageService.selectPageUserImage(findDto);

        List<UserImageVo> imageList = pageUserImage.getRecords();
        List<UserVideo> videoList = userVideoService.selectAllAid(id,null );
        UserAlbumVo vo = new UserAlbumVo();
        BeanUtils.copyProperties(userAlbum, vo);
        userAlbumService.updateCountSee(id, LocalDate.now().toString());
        Album pre = userAlbumService.previousChapter(id);
        Album next = userAlbumService.nextChapter(id);

        Boolean isSee=userAlbumService.isCheck(findDto.getAid(), userId);
        vo.setPre(pre);
        vo.setNext(next);

        vo.setIsCollection(2);
        vo.setAmount(0.0);
        vo.setIsVip(2);
        //是否有权限可看
        vo.setIsSee(isSee);
        //判断是否有权限观看
        if(userId != null) {
            UserCollection userCollection = userCollectionService.getInfo(userId, userAlbum.getId(), 2);
            if (userCollection != null) {
                vo.setIsCollection(1);
            }
            if (vo.getUserId() == userId) {
                vo.setIsVip(1);
                vo.setIsSee(true);
                return Result.success(vo);
            } else {
                try {
                    Double amount = userAlbumService.getAmount(id, userId, vo.getCharge(), vo.getPrice(), vo.getVipPrice());
                    vo.setAmount(amount);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }else{
            if(userAlbum.getCharge().equals(AlbumChargeEnum.FREE.getCode())){
                vo.setIsSee(true);
            }
        }
        if(userId != null) {
            redisCache.setCacheInteger(CacheConstants.USER_ALBUM_SEE + userId,vo.getIsSee()?1:2  ,1, TimeUnit.DAYS);
        }else{
            redisCache.setCacheInteger(CacheConstants.USER_ALBUM_SEE + "-1000",vo.getIsSee()?1:2 ,1, TimeUnit.DAYS );
        }
        if(!vo.getIsSee()){
            for(UserVideo video:videoList){
                if(video.getIsFree()==2) {
                    video.setUrl(null);
                    video.setStatus(-1);
                }
            }
            for(UserImageVo image:imageList){
                if(image.getIsFree()==2) {
                    image.setImgUrl(null);
                    image.setStatus(-1);
                }
            }
        }
        vo.setVideoList(videoList);
        vo.setImageList(imageList);

        return Result.success(vo);
    }


    /**
     * 查询
     *
     * @return
     */
    @GetMapping("/list")
    public Result<List<UserAlbum>> list(UserAlbumDto findDto) {
        findDto.setStatus(AlbumStatuEnum.NORMAL.getCode());
        IPage<UserAlbum> vo = userAlbumService.selectPageUserAlbum(findDto);
        return Result.success(vo.getRecords(), Integer.parseInt(String.valueOf(vo.getTotal())));
    }


    @GetMapping("/random")
    public Result<List<UserAlbum>> random(@RequestParam(value = "pageSize", required = false) Integer pageSize) {
        if (pageSize == null) {
            pageSize = 8;
        }
        if (pageSize < 10) {
            pageSize = 8;
        }
        List<UserAlbum> list = userAlbumService.findRandomStories(pageSize);

        return Result.success(list);
    }

    @GetMapping("/listSee")
    public Result<List<UserAlbum>> listSee(UserAlbumDto findDto) {
        findDto.setStatus(AlbumStatuEnum.NORMAL.getCode());
        findDto.setOrder("count_see");
        IPage<UserAlbum> vo = userAlbumService.selectPageUserAlbum(findDto);
        return Result.success(vo.getRecords(), Integer.parseInt(String.valueOf(vo.getTotal())));
    }
}
