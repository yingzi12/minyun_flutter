package com.xinshijie.gallery.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xinshijie.gallery.common.*;
import com.xinshijie.gallery.domain.UserAlbum;
import com.xinshijie.gallery.dto.UserImageDto;
import com.xinshijie.gallery.service.IUserAlbumService;
import com.xinshijie.gallery.service.IUserImageService;
import com.xinshijie.gallery.vo.UserAlbumVo;
import com.xinshijie.gallery.vo.UserImageVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.xinshijie.gallery.util.RequestContextUtil.getUserIdNoLogin;


/**
 * <p>
 * 用户上传的图片 前端控制器
 * </p>
 *
 * @author 作者
 * @since 2023-09-07
 */
@Slf4j
@Tag(name = " UserImageController", description = "后台- 用户上传的图片")
@RestController
@RequestMapping("/userImage")
public class UserImageController extends BaseController {

    @Autowired
    private IUserImageService userImageService;

    @Autowired
    private IUserAlbumService userAlbumService;
    @Autowired
    private RedisCache redisCache;

    /**
     * 查询详情
     *
     * @return
     */

    @GetMapping(value = "/getInfo/{id}")
    public Result<UserImageVo> getInfo(@PathVariable("id") Long id) {
        UserImageVo vo = userImageService.getInfo(id);
        return Result.success(vo);
    }


    /**
     * 查询
     *
     * @return
     */

    @GetMapping("/list")
    public Result<List<UserImageVo>> select(UserImageDto findDto) {
        Integer userId = getUserIdNoLogin();
        Integer see=2;
        String key=CacheConstants.USER_ALBUM_SEE + "-1000";
        if(userId != null) {
            key=CacheConstants.USER_ALBUM_SEE + userId;
        }
        if(!redisCache.hasKey(key)){
            Boolean isSee=userAlbumService.isCheck(findDto.getAid(), userId);
            if(isSee){
                see=1;
            }else {
                see=2;
            }
            redisCache.setCacheInteger(key,see,1,TimeUnit.DAYS);
        }else{
            see=redisCache.getCacheInteger(key);
        }
        if (findDto.getPageNum() == null) {
            findDto.setPageNum(1L);
        }
        if (findDto.getPageSize() == null) {
            findDto.setPageSize(6L);
        }
//        if (findDto.getIsFree() == null ||
//                (findDto.getIsFree() == 2 && !userAlbumService.isCheck(findDto.getAid(), userId))
//        ) {
//            throw new ServiceException(ResultCodeEnum.NOT_BUY);
//        }
        IPage<UserImageVo> vo = userImageService.selectPageUserImage(findDto);
        if(see==2) {
            for (UserImageVo image : vo.getRecords()) {
                if (image.getIsFree() == 2) {
                    image.setImgUrl(null);
                    image.setStatus(-1);
                }
            }
        }
        return Result.success(vo.getRecords(), Integer.parseInt(String.valueOf(vo.getTotal())));
    }
}
