package com.xinshijie.gallery.controller;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.IdUtil;
import com.google.code.kaptcha.Producer;
import com.xinshijie.gallery.common.*;
import com.xinshijie.gallery.vo.CaptchaVo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 验证码操作处理
 *
 * @author xinshijie
 */
@Slf4j
@RestController
public class CaptchaController {
    @Resource(name = "captchaProducer")
    private Producer captchaProducer;


    @Autowired
    private RedisCache redisCache;


    /**
     * 生成验证码
     */
    @GetMapping("/captchaImage")
    public CaptchaVo getCode() {
        CaptchaVo ajax = new CaptchaVo();
        // 保存验证码信息
        String uuid = IdUtil.fastUUID();
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;

        String code = captchaProducer.createText();
        BufferedImage image = captchaProducer.createImage(code);
        redisCache.setCacheString(verifyKey, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", os);
        } catch (IOException e) {
            throw new ServiceException(ResultCodeEnum.SYSTEM_INNER_ERROR.getCode(), e.getMessage());
        }

        ajax.setUuid(uuid);
        ajax.setImg(Base64.encode(os.toByteArray()));
        return ajax;
    }
}
