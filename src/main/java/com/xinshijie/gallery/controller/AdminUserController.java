package com.xinshijie.gallery.controller;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
//import cn.hutool.extra.mail.MailUtil;
import com.xinshijie.gallery.common.*;
import com.xinshijie.gallery.domain.SystemUser;
import com.xinshijie.gallery.dto.SystemUserDto;
import com.xinshijie.gallery.dto.UserPasswordDto;
import com.xinshijie.gallery.service.ISystemUserService;
import com.xinshijie.gallery.util.RequestContextUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.concurrent.TimeUnit;

import static com.xinshijie.gallery.util.RequestContextUtil.getUserId;
import static com.xinshijie.gallery.util.RequestContextUtil.getUserName;

@Slf4j
@Tag(name = " AdminUserController", description = "后台- ")
@RestController
@RequestMapping("/admin/systemUser")
public class AdminUserController extends BaseController {

    @Autowired
    private ISystemUserService systemUserService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private TemplateEngine templateEngine;

    @GetMapping("/logout")
    public Result<String> logout() {
        try {
            Integer userId = getUserId();
            redisTemplate.delete(CacheConstants.LOGIN_TOKEN_KEY + userId);
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
    public Result<SystemUser> getInfo() {
        Integer userId = getUserId();
        String userName = RequestContextUtil.getUserName();
        SystemUser systemUserVo = systemUserService.info(userId);
        return Result.success(systemUserVo);
    }

    @PostMapping("/edit")
    public Result<SystemUser> edit(@RequestBody SystemUserDto userDto) {
        userDto.setId(getUserId());
        // 生成令牌
        SystemUser systemUser = systemUserService.edit(userDto);
        return Result.success(systemUser);
    }

    //    @Log(title = "发送邮箱验证链接", businessType = BusinessType.UPDATE)
    @GetMapping("/sendCheckEmail")
    public Result<String> sendCheckEmail() {
        SystemUser userVo = systemUserService.info(getUserId());
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
                    "验证码："+simpleUUID+"\n"+
                    " 点击验证:<a href=\"https://www.aiavr.com/check/email?key=" + simpleUUID + "&check=" + digestHex + "\">https://www.aiavr.com/wiki/check/email?key=" + simpleUUID + "&check=" + digestHex + "</a>\n";

//            MailUtil.send(userVo.getEmail(), "安全邮箱验证", content, true);
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
            String digestHex = md5.digestHex("安全邮箱绑定" + email);
            redisCache.setCacheString(CacheConstants.EMAIL + simpleUUID, email + ":" + digestHex + ":" + getUserId(), Constants.EMAIL_EXPIRATION, TimeUnit.MINUTES);
            redisCache.setCacheString(CacheConstants.EMAIL + email, simpleUUID, Constants.EMAIL_EXPIRATION, TimeUnit.MINUTES);

            String content = "<h1>尊敬的" + getUserName() + ":<h1>\n" +
                    "你正在进行安全邮箱的绑定操作,请点击下方链接地址,来进行邮箱的验证操作，链接30分钟有效.  \n" +
                    " 点击验证:<a href=\"https://www.aiavr.com/check/email?key=" + simpleUUID + "&check=" + digestHex + "\">https://www.aiavr.com/check/email?key=" + simpleUUID + "&check=" + digestHex + "</a>\n";

//            MailUtil.send(email, "安全邮箱验证", content, true);
        } else {
            throw new ServiceException(ResultCodeEnum.THE_EMAIL_IS_EMPTY_OR_ILLEGAL);
        }
        return Result.success("成功");
    }


    @GetMapping("/verificationEmailCode")
    public Result<String> verificationEmailCode(@RequestParam("code") String code) {

        return Result.success("成功");
    }

    @PostMapping("/upload")
    public Result<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        log.info("system update");
        String imageUrl = systemUserService.saveUploadedFiles(getUserId(), file);
        return Result.success(imageUrl);
    }

    @PostMapping("/updatePassworld")
    public Result<Boolean> updatePassworld(@RequestBody UserPasswordDto passwordDto) {
        if (StringUtils.isEmpty(passwordDto.getNewPassword()) || passwordDto.getNewPassword().length() < 6) {
            throw new ServiceException(ResultCodeEnum.PASSWORD_NULL);
        }
        // 生成令牌
        systemUserService.updatePwd(getUserId(), passwordDto.getNewPassword(), passwordDto.getOldPassword());
        return Result.success(true);
    }

    @GetMapping("/sendCheckEmailCode")
    public Result<String> sendCheckEmailCode(@RequestParam("email") String email) {
        try {
            SystemUser userVo = systemUserService.info(getUserId());

            String code=RandomUtil.randomNumbers(6);

            String simpleUUID = IdUtil.simpleUUID();
            Digester md5 = new Digester(DigestAlgorithm.MD5);
            String digestHex = md5.digestHex("安全邮箱绑定" + email);
            redisCache.setCacheString(CacheConstants.EMAIL + simpleUUID, email + ":" + digestHex + ":" + getUserId(), Constants.EMAIL_EXPIRATION, TimeUnit.MINUTES);
            redisCache.setCacheString(CacheConstants.EMAIL + email, code+":"+simpleUUID, Constants.EMAIL_EXPIRATION, TimeUnit.MINUTES);

            // Prepare the evaluation context
            final Context ctx = new Context();
            ctx.setVariable("code", code);
            ctx.setVariable("nickname", userVo.getNickname());
            ctx.setVariable("simpleUUID", simpleUUID);
            ctx.setVariable("digestHex", digestHex);

            // Prepare message using a Spring helper
            final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
            final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
            message.setSubject("邮箱验证码");
            message.setFrom("admin@aiavr.uk");
            message.setTo(email);

            // Create the HTML body using Thymeleaf
            // Generate the email content using Thymeleaf template
            final String htmlContent = this.templateEngine.process("verificationEmailCode.html", ctx);
//            final String htmlContent = this.templateEngine.process("templates/verificationEmailCode.html", ctx);
            message.setText(htmlContent, true /* isHtml */);

            // Send the email
            this.mailSender.send(mimeMessage);

            return Result.success("Email sent successfully");
        }catch(Exception e){
            e.printStackTrace();
        }
        return Result.error(ResultCodeEnum.EMAIL_ERROR);
    }

    @GetMapping("/sendEmailCode")
    public Result<String> sendCheckEmailCode(@RequestParam("email") String email,@RequestParam("code") String code) {
        try {
            String codeStr=redisCache.getCacheString(CacheConstants.EMAIL + email);
            String[] codeArr=codeStr.split(":");
            String checCode = codeArr[2];
            String simpleUUID =codeArr[1];
            if(checCode.equals(code)){
                systemUserService.updateEmail(getUserId(),email);
            }else{
                throw new ServiceException(ResultCodeEnum.EMAIL_CODE_ERROR);
            }
            return Result.success("Email check successfully");
        }catch(Exception e){
            e.printStackTrace();
        }
        return Result.error(ResultCodeEnum.EMAIL_ERROR);
    }
}
