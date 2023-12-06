package com.xinshijie.gallery.controller;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import cn.hutool.extra.mail.MailUtil;
import com.xinshijie.gallery.common.*;
import com.xinshijie.gallery.domain.SystemUser;
import com.xinshijie.gallery.dto.SystemUserDto;
import com.xinshijie.gallery.service.ISystemUserService;
import com.xinshijie.gallery.util.RequestContextUtil;
import com.xinshijie.gallery.vo.SystemUserVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

import static com.xinshijie.gallery.util.RequestContextUtil.getUserId;
import static com.xinshijie.gallery.util.RequestContextUtil.getUserName;

@Slf4j
@Tag(name = " AdminUserController", description = "后台- ")
@RestController
@RequestMapping("/admin/systemUser")
public class  AdminUserController  extends BaseController {

    @Autowired
    private ISystemUserService systemUserService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedisCache redisCache;

    @PostMapping("/logout")
    public Result<String> logout() {
        try {
            Integer userId = getUserId();
            redisTemplate.delete(userId);
        } catch (Exception ex) {
            log.info("退出异常:{}", ex);
        }
        return Result.success();
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public SystemUserVo getInfo() {
        Integer userId = getUserId();
        String userName = RequestContextUtil.getUserName();
        SystemUserVo systemUserVo = systemUserService.info(userId);
        return systemUserVo;
    }

    @PostMapping("/edit")
    public Result<Boolean> edit(@RequestBody SystemUserDto userDto) {
        userDto.setId(getUserId());
        // 生成令牌
        systemUserService.edit(userDto);
        return Result.success(true);
    }

//    @Log(title = "发送邮箱验证链接", businessType = BusinessType.UPDATE)
    @GetMapping("/sendCheckEmail")
    public Result<String> sendCheckEmail() {
        SystemUserVo userVo = systemUserService.info(getUserId());
        boolean isEmail = Validator.isEmail(userVo.getEmail());
        if (StringUtils.isNotEmpty(userVo.getEmail()) && isEmail) {
            if (redisCache.hasKey(CacheConstants.EMAIL + userVo.getEmail())) {
                throw new ServiceException(ResultCodeEnum.EMAIL_ALREADY_SENT);
            }
            String simpleUUID = IdUtil.simpleUUID();
            Digester md5 = new Digester(DigestAlgorithm.MD5);
            // 5393554e94bf0eb6436f240a4fd71282
            String digestHex = userVo.getEmail() + ":" + md5.digestHex(userVo.getEmail());
            redisCache.setCacheString(CacheConstants.EMAIL + simpleUUID, userVo.getEmail() + ":" + digestHex, Constants.EMAIL_EXPIRATION, TimeUnit.MINUTES);
            redisCache.setCacheString(CacheConstants.EMAIL + userVo.getEmail(), simpleUUID, Constants.EMAIL_EXPIRATION, TimeUnit.MINUTES);
            String content = "<h1>尊敬的" + RequestContextUtil.getUserName() + ":<h1>\n" +
                    "你正在进行安全邮箱的绑定操作,请点击下方链接地址,来进行邮箱的验证操作,链接30分钟有效.  \n" +
                    " 点击验证:<a href=\"https://www.aiavr.com/check/email?key=" + simpleUUID + "&check=" + digestHex + "\">https://www.aiavr.com/wiki/check/email?key=" + simpleUUID + "&check=" + digestHex + "</a>\n";

            MailUtil.send(userVo.getEmail(), "安全邮箱验证", content, true);
        } else {
            throw new ServiceException(ResultCodeEnum.THE_EMAIL_IS_EMPTY_OR_ILLEGAL);
        }
//        MailUtil.send(account, CollUtil.newArrayList("xun335610@163.com"), "测试", content, true);
        return Result.success("成功");
    }

//    @Log(title = "发送邮箱验证链接", businessType = BusinessType.UPDATE)
    @GetMapping("/sendUpdateCheckEmail")
    public Result<String> sendUpdateCheckEmail(@RequestParam("email") String email) {
        boolean isEmail = Validator.isEmail(email);
        if (StringUtils.isNotEmpty(email) && isEmail) {
            //TODO 逻辑待修改
            Boolean ok = systemUserService.checkEmailUnique(email);
            if (!ok) {
                throw new ServiceException(ResultCodeEnum.THE_EMAIL_DOES_NOT_EXIST);
            }
            if (redisCache.hasKey(CacheConstants.EMAIL + email)) {
                throw new ServiceException(ResultCodeEnum.EMAIL_ALREADY_SENT);
            }
            String simpleUUID = IdUtil.simpleUUID();
            Digester md5 = new Digester(DigestAlgorithm.MD5);
            // 5393554e94bf0eb6436f240a4fd71282
            String digestHex = md5.digestHex("安全邮箱绑定" + email);
            redisCache.setCacheString(CacheConstants.EMAIL + simpleUUID, email + ":" + digestHex +":" + getUserId(), Constants.EMAIL_EXPIRATION, TimeUnit.MINUTES);
            redisCache.setCacheString(CacheConstants.EMAIL + email, simpleUUID, Constants.EMAIL_EXPIRATION, TimeUnit.MINUTES);

            String content = "<h1>尊敬的" + getUserName() + ":<h1>\n" +
                    "你正在进行安全邮箱的绑定操作,请点击下方链接地址,来进行邮箱的验证操作，链接30分钟有效.  \n" +
                    " 点击验证:<a href=\"https://www.aiavr.com/check/email?key=" + simpleUUID + "&check=" + digestHex + "\">https://www.aiavr.com/check/email?key=" + simpleUUID + "&check=" + digestHex + "</a>\n";

            MailUtil.send(email, "安全邮箱验证", content, true);
        } else {
            throw new ServiceException(ResultCodeEnum.THE_EMAIL_IS_EMPTY_OR_ILLEGAL);
        }
        return Result.success("成功");
    }

}
