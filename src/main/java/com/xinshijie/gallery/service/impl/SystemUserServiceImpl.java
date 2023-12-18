package com.xinshijie.gallery.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.jwt.JWT;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinshijie.gallery.common.CacheConstants;
import com.xinshijie.gallery.common.Constants;
import com.xinshijie.gallery.common.ResultCodeEnum;
import com.xinshijie.gallery.common.ServiceException;
import com.xinshijie.gallery.domain.SystemUser;
import com.xinshijie.gallery.dto.FindSystemUserDto;
import com.xinshijie.gallery.dto.SystemUserDto;
import com.xinshijie.gallery.mapper.SystemUserMapper;
import com.xinshijie.gallery.service.IFileService;
import com.xinshijie.gallery.service.ISystemUserService;
import com.xinshijie.gallery.util.SecurityUtils;
import com.xinshijie.gallery.vo.AlbumCollectionVo;
import com.xinshijie.gallery.vo.LoginUserVo;
import com.xinshijie.gallery.vo.SystemUserIntroVo;
import com.xinshijie.gallery.vo.SystemUserVo;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.xinshijie.gallery.common.CacheConstants.PWD_ERR_CNT_KEY;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zx
 * @since 2022-09-05
 */
@Slf4j
@Service
public class SystemUserServiceImpl extends ServiceImpl<SystemUserMapper, SystemUser> implements ISystemUserService {

    @Autowired
    private SystemUserMapper mapper;

    @Autowired
    private IFileService fileService;


    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${image.sourceWeb}")
    private String imageSourceWeb;

    @Value("${image.path}")
    private String imagePath;

    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @param code     验证码
     * @param uuid     唯一标识
     * @return 结果
     */
    public LoginUserVo login(String username, String password, String code, String uuid) {
        // 验证码
        validateCaptcha(username, code, uuid);
        SystemUser systemUser = isLogin(username, password);
        // 用户验证
        LoginUserVo ajax = new LoginUserVo();
        ajax.setCode(200);
        ajax.setCode(200);
        ajax.setToken(getToken(systemUser));
        ajax.setUser(systemUser);
        ajax.setAccessToken(ajax.getToken());
        ajax.setRefreshToken(ajax.getToken());
        redisTemplate.opsForValue().set(CacheConstants.LOGIN_TOKEN_KEY + ajax.getUser().getId(), ajax.getToken(), 1, TimeUnit.HOURS);
        // 生成token
        return ajax;
    }

    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @return 结果
     */
    public LoginUserVo loginModile(String username, String password) {
        SystemUser systemUser = isLogin(username, password);
        // 用户验证
        LoginUserVo ajax = new LoginUserVo();
        ajax.setCode(200);
        ajax.setCode(200);
        ajax.setToken(getToken(systemUser));
        ajax.setUser(systemUser);
        ajax.setAccessToken(ajax.getToken());
        ajax.setRefreshToken(ajax.getToken());
        redisTemplate.opsForValue().set(CacheConstants.LOGIN_TOKEN_KEY + ajax.getUser().getId(), ajax.getToken(), 1, TimeUnit.HOURS);
        // 生成token
        return ajax;
    }

    public SystemUser isLogin(String username, String password) {

        QueryWrapper<SystemUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", username).or().eq("email", username);
        SystemUser systemUser = mapper.selectOne(queryWrapper);
        if (systemUser != null) {
            if (redisTemplate.hasKey(PWD_ERR_CNT_KEY + ":" + systemUser.getId())) {
                int count = Integer.parseInt(redisTemplate.opsForValue().get(PWD_ERR_CNT_KEY + ":" + systemUser.getId()).toString());
                if (count > 3) {
                    throw new ServiceException(ResultCodeEnum.USER_ACCOUNT_ERROR_LONG_TIME);
                }
            }
            if (!SecurityUtils.matchesPassword(password, systemUser.getPassword())) {
                if (redisTemplate.hasKey(PWD_ERR_CNT_KEY + ":" + systemUser.getId())) {
                    int count = Integer.parseInt(redisTemplate.opsForValue().get(PWD_ERR_CNT_KEY + ":" + systemUser.getId()).toString());
                    redisTemplate.opsForValue().set(PWD_ERR_CNT_KEY + ":" + systemUser.getId(), count + 1, 1, TimeUnit.HOURS);
                } else {
                    redisTemplate.opsForValue().set(PWD_ERR_CNT_KEY + ":" + systemUser.getId(), 1, 1, TimeUnit.HOURS);
                }
                throw new ServiceException(ResultCodeEnum.USER_ACCOUNT_ERROR);
            }
        } else {
            throw new ServiceException(ResultCodeEnum.USER_ACCOUNT_ERROR);
        }
        systemUser.setPassword("");
        systemUser.setSalt("");
        return systemUser;
    }

    /**
     * 校验验证码
     *
     * @param username 用户名
     * @param code     验证码
     * @param uuid     唯一标识
     * @return 结果
     */
    public void validateCaptcha(String username, String code, String uuid) {
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;
        if (!redisTemplate.hasKey(verifyKey)) {
            throw new ServiceException(ResultCodeEnum.USER_CODE_ERROR);
        }
        String captcha = redisTemplate.opsForValue().get(verifyKey).toString();
        redisTemplate.delete(verifyKey);
        if (captcha == null) {
            throw new ServiceException(ResultCodeEnum.USER_CODE_ERROR);
        }
        if (!code.equalsIgnoreCase(captcha)) {
            throw new ServiceException(ResultCodeEnum.USER_CODE_ERROR);
        }
    }

    /**
     * 校验用户名称是否唯一
     *
     * @return 结果
     */
    @Override
    public Boolean checkUserNameUnique(String username) {
        QueryWrapper<SystemUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", username);
        Long count = mapper.selectCount(queryWrapper);

        if (count > 0) {
            throw new ServiceException(ResultCodeEnum.USERNAME_ALREADY_EXISTS);
        }
        return true;
    }

    /**
     * 校验email是否唯一
     * * @return
     */
    @Override
    public Boolean checkEmailUnique(String email) {
        QueryWrapper<SystemUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        Long count = mapper.selectCount(queryWrapper);
        return count <= 0;
    }


    /**
     * 修改用户用户密码
     *
     * @return 结果
     */
    @Override
    public Integer updatePwd(Integer userId, String newPassword, String oldPassword) {
        SystemUser userVo = mapper.selectById(userId);
        if (userVo == null) {
            throw new ServiceException(ResultCodeEnum.OPERATOR_ERROR);
        }
        if (!SecurityUtils.matchesPassword(oldPassword, newPassword)) {
            throw new ServiceException(ResultCodeEnum.FAILED_TO_CHANGE_PASSWORD);
        }
        SystemUser userDto = new SystemUser();
        userDto.setId(userId);
        userDto.setPassword(SecurityUtils.encryptPassword(newPassword));
        userDto.setUpdateTime(LocalDateTime.now());
        return mapper.updateById(userDto);
    }

    /**
     * 重置用户密码
     *
     * @param password 密码
     * @return 结果
     */
    @Override
    public Integer resetUserPwd(Integer userId, String password) {
        SystemUser userVo = mapper.selectById(userId);
        if (userVo == null) {
            throw new ServiceException(ResultCodeEnum.OPERATOR_ERROR);
        }
        SystemUser userDto = new SystemUser();
        userDto.setId(userId);
        userDto.setPassword(SecurityUtils.encryptPassword(password));
        userDto.setUpdateTime(LocalDateTime.now());
        return mapper.updateById(userDto);
    }

    @Override
    public SystemUser info(Integer userId) {
        SystemUser systemUserVo = mapper.selectById(userId);
        systemUserVo.setPassword("");
        systemUserVo.setSalt("");
        return systemUserVo;
    }

    @Override
    public Boolean add(SystemUserDto userDto) {
        boolean emailOk = checkEmailUnique(userDto.getEmail());
        if (!emailOk) {
            throw new ServiceException(ResultCodeEnum.ALREADY_BOUND_ACCOUNT);
        }
        boolean userOk = checkUserNameUnique(userDto.getName());
        if (!userOk) {
            throw new ServiceException(ResultCodeEnum.USER_ALREADY_EXISTS);
        }
        SystemUser systemUser = new SystemUser();
        BeanUtils.copyProperties(userDto, systemUser);
        systemUser.setPassword(SecurityUtils.encryptPassword(userDto.getPassword()));
        systemUser.setIsEmail(2);
        systemUser.setNickname(userDto.getName());
        systemUser.setSalt(RandomUtil.randomNumbers(10));
        systemUser.setCreateTime(LocalDateTime.now());

        mapper.insert(systemUser);
        return true;
    }

    @Override
    public SystemUser edit(SystemUserDto userDto) {
        SystemUser systemUser = mapper.selectById(userDto.getId());
        BeanUtils.copyProperties(userDto, systemUser);
//        systemUser.setPassword(SecurityUtils.encryptPassword(userDto.getPassword()));
        if (!systemUser.getEmail().equals(userDto.getEmail())) {
            QueryWrapper<SystemUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("email", userDto.getEmail());
            SystemUser user = mapper.selectOne(queryWrapper);
            if (user == null || user.getId() == userDto.getId()) {
                systemUser.setIsEmail(2);
            } else {
                throw new ServiceException(ResultCodeEnum.ALREADY_BOUND_ACCOUNT);
            }
        }
        systemUser.setNickname(userDto.getNickname());
        systemUser.setSalt(null);
        systemUser.setPassword(null);
        systemUser.setUpdateTime(LocalDateTime.now());
        mapper.updateById(systemUser);
        return systemUser;
    }

    public String getToken(SystemUser systemUser) {
        String token = JWT.create()
                .setIssuer("登录")
                .setPayload("user", JSONObject.toJSONString(systemUser))
                .setExpiresAt(new Date(System.currentTimeMillis() + 3600000)) // 设置过期时间（例如1小时后）
                .setKey(Constants.TOKEN_KEY)
                .sign();
        return token;
    }

    @Override
    public Integer updateEmail(Integer userId, String email) {
        SystemUser userDto = new SystemUser();
        userDto.setId(userId);
        userDto.setEmail(email);
        userDto.setIsEmail(1);
        return mapper.updateById(userDto);
    }

    @Override
    public SystemUser selectByEmail(String email) {
        QueryWrapper<SystemUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        return mapper.selectOne(queryWrapper);
    }

    /**
     * 登录账户密码错误次数缓存键名
     *
     * @param username 用户名
     * @return 缓存键key
     */
    @Override
    public String getCacheKey(String username) {
        return PWD_ERR_CNT_KEY + username;
    }


    public Boolean saveUploadedFiles(Integer userId, MultipartFile file) {
        try {
            if (file.isEmpty()) {
                log.error("No image file provided");
                return false;
            }
            try {
                String mm5 = fileService.getMD5(file.getInputStream());
                String imgUrl = "/user/head/" + userId + "_" + mm5 + ".jpg";
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
                        .size(100, 100)
                        .outputQuality(outputQuality) // 设置压缩质量
                        .outputFormat("jpg")
                        .toFile(destinationFile); // 保存到文件


                // 图片验证通过，更新用户信息
                SystemUser user = new SystemUser();
                user.setId(userId);
                user.setImgUrl(imageSourceWeb + imgUrl);
                mapper.updateById(user);
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

    @Override
    public void updatIncome(Integer userId, Double amount) {
        mapper.updatIncome(userId,amount);
    }

    @Override
    public IPage<SystemUserIntroVo> selectPage(FindSystemUserDto findDto) {

        Page<SystemUserVo> page = new Page<>();
        if (findDto.getPageNum() == null) {
            findDto.setPageNum(1L);
        }
        if (findDto.getPageSize() == null) {
            findDto.setPageSize(20L);
        }
        page.setSize(findDto.getPageSize());
        page.setCurrent(findDto.getPageNum());
        return mapper.selectPageSystemUser(page,findDto);
    }


}
