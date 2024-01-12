package com.xinshijie.gallery.controller;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import cn.hutool.extra.mail.MailUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinshijie.gallery.common.*;
import com.xinshijie.gallery.domain.SystemUser;
import com.xinshijie.gallery.domain.UserAttention;
import com.xinshijie.gallery.dto.*;
import com.xinshijie.gallery.service.ISystemUserService;
import com.xinshijie.gallery.service.IUserAttentionService;
import com.xinshijie.gallery.util.RequestContextUtil;
import com.xinshijie.gallery.util.SecurityUtils;
import com.xinshijie.gallery.vo.LoginUserVo;
import com.xinshijie.gallery.vo.SystemUserIntroVo;
import com.xinshijie.gallery.vo.SystemUserVo;
import com.xinshijie.gallery.vo.UserAttentionVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.xinshijie.gallery.util.RequestContextUtil.getUserId;
import static com.xinshijie.gallery.util.RequestContextUtil.getUserIdNoLogin;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 作者
 * @since 2023-09-07
 */
@Slf4j
@Tag(name = " SystemUserController", description = "后台- ")
@RestController
@RequestMapping("/systemUser")
public class SystemUserController extends BaseController {

    @Autowired
    private ISystemUserService systemUserService;
    @Autowired
    private IUserAttentionService attentionService;
    @Autowired
    private RedisCache redisCache;

    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    public LoginUserVo login(@RequestBody LoginDto loginBody) {
        // 生成令牌
        LoginUserVo ajax = systemUserService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
                loginBody.getUuid());

        return ajax;
    }


    @PostMapping("/loginModile")
    public LoginUserVo loginModile(@RequestBody LoginDto loginBody) {
        // 生成令牌
        LoginUserVo ajax = systemUserService.loginModile(loginBody.getUsername(), loginBody.getPassword());

        return ajax;
    }

    @PostMapping("/regis")
    public Result<Boolean> regis(@RequestBody SystemUserDto userDto) {
        if (StringUtils.isEmpty(userDto.getPassword()) || userDto.getPassword().length() < 6) {
            throw new ServiceException(ResultCodeEnum.PASSWORD_NULL);
        }
        // 生成令牌
        systemUserService.add(userDto);
        return Result.success(true);
    }




    @GetMapping("/updateCheckEmail")
    public Result<String> updateCheckEmail(@Validated @Param("key") String key, @Param("check") String check) {
        if (!redisCache.hasKey(CacheConstants.EMAIL + key)) {
            throw new ServiceException(ResultCodeEnum.EXPIRED);
        }
        String emailMd5 = redisCache.getCacheString(CacheConstants.EMAIL + key);
        String[] varArr = emailMd5.split(":");
        String email = varArr[0];
        String md5 = varArr[1];
        String id = varArr[2];
        Digester digester = new Digester(DigestAlgorithm.MD5);

        // 5393554e94bf0eb6436f240a4fd71282
        String digestHex = digester.digestHex("安全邮箱绑定" + email);
        if (md5.equals(digestHex)) {
            systemUserService.updateEmail(Integer.getInteger(id), email);
            redisCache.deleteObject(CacheConstants.EMAIL + key);
        } else {
            throw new ServiceException(ResultCodeEnum.OPERATOR_ERROR);
        }
        System.out.println(key + "::::::" + check);
        return Result.success("绑定成功");
    }

    @GetMapping("/sendCheckPassword")
    public Result<String> sendCheckPassword(@RequestParam("email") String email) {
        boolean isEmail = Validator.isEmail(email);
        if (StringUtils.isNotEmpty(email) && isEmail) {
            SystemUser info = systemUserService.selectByEmail(email);
            if (info == null || info.getIsEmail() == 0) {
                throw new ServiceException(ResultCodeEnum.THE_EMAIL_DOES_NOT_EXIST_OR_IS_NOT_VERIFIED);
            }
            String simpleUUID = IdUtil.simpleUUID();
            Digester md5 = new Digester(DigestAlgorithm.MD5);
            // 5393554e94bf0eb6436f240a4fd71282
            String digestHex = md5.digestHex("找回密码" + email);
            redisCache.setCacheString(CacheConstants.PASSWORD + simpleUUID, email + ":" + digestHex, Constants.EMAIL_EXPIRATION, TimeUnit.MINUTES);
            redisCache.setCacheString(CacheConstants.PASSWORD + email, simpleUUID, Constants.EMAIL_EXPIRATION, TimeUnit.MINUTES);

            String content = "<h1>尊敬的用户<h1>\n" +
                    "你正在进行密码找回操作,请点击下方链接地址,来进行重置密码操作，链接30分钟有效.  \n" +
                    " 点击验证:<a href=\"https://www.aiavr.com/check/password?key=" + simpleUUID + "&check=" + digestHex + "\">https://www.aiavr.com/check/password?key=" + simpleUUID + "&check=" + digestHex + "</a>\n";
            MailUtil.send(email, "密码找回", content, true);
            log.info("{}发送重置密码邮件成功", email);
        } else {
            throw new ServiceException(ResultCodeEnum.THE_EMAIL_IS_EMPTY_OR_ILLEGAL);
        }
        return Result.success();
    }

    @GetMapping("/checkPasswortUrl")
    public Result<String> checkPasswortUrl(@Validated @Param("key") String key, @Param("check") String check) {
        if (redisCache.hasKey(CacheConstants.EMAIL + key)) {
            throw new ServiceException(ResultCodeEnum.EXPIRED);
        }
        String emailMd5 = redisCache.getCacheString(CacheConstants.EMAIL + key);
        String[] varArr = emailMd5.split(":");
        String email = varArr[0];
        String md5 = varArr[1];

        Digester digester = new Digester(DigestAlgorithm.MD5);

        // 5393554e94bf0eb6436f240a4fd71282
        String digestHex = digester.digestHex("找回密码" + email);
        if (!md5.equals(digestHex)) {
            throw new ServiceException(ResultCodeEnum.OPERATOR_ERROR);
        }
        return Result.success("绑定成功");
    }

    @PutMapping("/restPasswort")
    public Result<String> restPasswort(@RequestBody UserPasswordDto dto) {
        if (!redisCache.hasKey(CacheConstants.PASSWORD + dto.getKey())) {
            throw new ServiceException(ResultCodeEnum.EXPIRED);
        }
        String emailMd5 = redisCache.getCacheString(CacheConstants.PASSWORD + dto.getKey());
        String[] varArr = emailMd5.split(":");
        String email = varArr[0];
        String md5 = varArr[1];

        Digester digester = new Digester(DigestAlgorithm.MD5);

        // 5393554e94bf0eb6436f240a4fd71282
        String digestHex = digester.digestHex("找回密码" + email);
        if (!md5.equals(digestHex)) {
            throw new ServiceException(ResultCodeEnum.OPERATOR_ERROR);
        } else {
            SystemUser userVo = systemUserService.selectByEmail(email);
            SystemUser userDto = new SystemUser();
            userDto.setId(userVo.getId());
            userDto.setPassword(SecurityUtils.encryptPassword(dto.getNewPassword()));
            userDto.setUpdateTime(LocalDateTime.now());
            systemUserService.updateById(userDto);
            redisCache.deleteObject(CacheConstants.PASSWORD + dto.getKey());
            redisCache.setCacheInteger(systemUserService.getCacheKey(userVo.getName()), 0, 60 * 15, TimeUnit.MINUTES);
        }
        return Result.success("重置成功");
    }

    @GetMapping("/list")
    public Result<List<SystemUserIntroVo>> list(FindSystemUserDto findDto) {
        IPage<SystemUserIntroVo> vo = systemUserService.selectPage(findDto);
        return Result.success(vo.getRecords(), Integer.parseInt(String.valueOf(vo.getTotal())));
    }

    @GetMapping("/listSee")
    public Result<List<SystemUserIntroVo>> listSee(FindSystemUserDto findDto) {
        IPage<SystemUserIntroVo> vo = systemUserService.selectPage(findDto);
        return Result.success(vo.getRecords(), Integer.parseInt(String.valueOf(vo.getTotal())));
    }

    @GetMapping("/random")
    public Result<List<SystemUserIntroVo>> random() {
        List<SystemUserIntroVo> vo = systemUserService.findRandomStories(6);
        return Result.success(vo);
    }

    @GetMapping("info")
    public Result<SystemUserIntroVo> getInfo(@RequestParam("userId")Integer userId) {
        Integer loUserId=getUserIdNoLogin();
        SystemUser systemUser = systemUserService.info(userId);
        SystemUserIntroVo introVo=new SystemUserIntroVo();
        BeanUtils.copyProperties(systemUser,introVo);
        introVo.setIsAttention(2);
        if(userId!=null) {
            UserAttention attention = attentionService.getInfoByAtten(loUserId,userId);
            if(attention != null){
                introVo.setIsAttention(1);
            }
        }
        return Result.success(introVo);
    }
}
