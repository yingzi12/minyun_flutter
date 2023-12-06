package com.xinshijie.gallery.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinshijie.gallery.common.CacheConstants;
import com.xinshijie.gallery.common.RedisCache;
import com.xinshijie.gallery.common.ResultCodeEnum;
import com.xinshijie.gallery.common.ServiceException;
import com.xinshijie.gallery.domain.SystemUser;
import com.xinshijie.gallery.dto.SystemUserDto;
import com.xinshijie.gallery.mapper.SystemUserMapper;
import com.xinshijie.gallery.service.ISystemUserService;
import com.xinshijie.gallery.util.SecurityUtils;
import com.xinshijie.gallery.vo.LoginUserVo;
import com.xinshijie.gallery.vo.SystemUserVo;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.xinshijie.gallery.common.CacheConstants.PWD_ERR_CNT_KEY;

/**
 * <p>
 *   服务实现类
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
    private RedisCache redisCache;

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
        SystemUser systemUser=isLogin(username,password);
        // 用户验证
        LoginUserVo ajax = new LoginUserVo();
        ajax.setCode(200);
        ajax.setCode(200);
        ajax.setToken(getToken(systemUser));
        ajax.setUser(systemUser);
        ajax.setAccessToken(ajax.getToken());
        ajax.setRefreshToken(ajax.getToken());
        redisCache.setCacheString(CacheConstants.LOGIN_TOKEN_KEY+ajax.getUser().getId(),ajax.getToken(),1, TimeUnit.HOURS);
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
        SystemUser systemUser=isLogin(username,password);
        // 用户验证
        LoginUserVo ajax = new LoginUserVo();
        ajax.setCode(200);
        ajax.setCode(200);
        ajax.setToken(getToken(systemUser));
        ajax.setUser(systemUser);
        ajax.setAccessToken(ajax.getToken());
        ajax.setRefreshToken(ajax.getToken());
        redisCache.setCacheString(CacheConstants.LOGIN_TOKEN_KEY+ajax.getUser().getId(),ajax.getToken(),1, TimeUnit.HOURS);
        // 生成token
        return ajax;
    }

    public SystemUser isLogin(String username, String password){

        QueryWrapper<SystemUser> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("name",username).or().eq("email",username);
        SystemUser systemUser= mapper.selectOne(queryWrapper);
        if(systemUser!=null){
            if(redisCache.hasKey(PWD_ERR_CNT_KEY+":"+systemUser.getId())){
                int count=redisCache.getCacheInteger(PWD_ERR_CNT_KEY+":"+systemUser.getId());
                if(count>3){
                    throw new ServiceException(ResultCodeEnum.USER_ACCOUNT_ERROR_LONG_TIME);
                }
            }
            if(!SecurityUtils.matchesPassword(password,systemUser.getPassword())){
                if(redisCache.hasKey(PWD_ERR_CNT_KEY+":"+systemUser.getId())){
                    int count=redisCache.getCacheInteger(PWD_ERR_CNT_KEY+":"+systemUser.getId());
                    redisCache.setCacheInteger(PWD_ERR_CNT_KEY + ":" + systemUser.getId(), count+1, 1, TimeUnit.HOURS);
                }else {
                    redisCache.setCacheInteger(PWD_ERR_CNT_KEY + ":" + systemUser.getId(), 1, 1, TimeUnit.HOURS);
                }
                throw new ServiceException(ResultCodeEnum.USER_ACCOUNT_ERROR);
            }
        }else {
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
        String captcha = redisCache.getCacheString(verifyKey);
        redisCache.deleteObject(verifyKey);
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
        QueryWrapper<SystemUser> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("name",username);
        Long count= mapper.selectCount(queryWrapper);

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
        QueryWrapper<SystemUser> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("email",email);
        Long count= mapper.selectCount(queryWrapper);
        if (count > 0) {
            return false;
        }
        return true;
    }


    /**
     * 重置用户密码
     *
     * @return 结果
     */
    @Override
    public Integer resetPwd(Integer userId,String newPassword,String oldPassword) {
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
    public SystemUserVo info(Integer userId) {
        SystemUserVo systemUserVo= mapper.getInfo(userId);
        systemUserVo.setPassword("");
        systemUserVo.setSalt("");
        return systemUserVo;
    }

    @Override
    public Boolean add(SystemUserDto userDto) {
       boolean emailOk= checkEmailUnique(userDto.getEmail());
       if(!emailOk){
           throw new ServiceException(ResultCodeEnum.ALREADY_BOUND_ACCOUNT);
       }
        boolean userOk= checkUserNameUnique(userDto.getName());
        if(!userOk){
            throw new ServiceException(ResultCodeEnum.USER_ALREADY_EXISTS);
        }
        SystemUser systemUser=new SystemUser();
        BeanUtils.copyProperties(userDto,systemUser);
        systemUser.setPassword(SecurityUtils.encryptPassword(userDto.getPassword()));
        systemUser.setIsEmail(2);
        systemUser.setNickname(userDto.getName());
        systemUser.setSalt(RandomUtil.randomNumbers(10)+"");
        mapper.insert(systemUser);
        return true;
    }

    @Override
    public Boolean edit(SystemUserDto userDto) {
        SystemUser systemUser=mapper.selectById(userDto.getId());
        BeanUtils.copyProperties(userDto,systemUser);
        systemUser.setPassword(SecurityUtils.encryptPassword(userDto.getPassword()));
        if(!systemUser.getEmail().equals(userDto.getEmail())) {
            QueryWrapper<SystemUser> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("email",userDto.getEmail());
            SystemUser user=mapper.selectOne(queryWrapper);
            if(user==null || user.getId() == userDto.getId()) {
                systemUser.setIsEmail(2);
            }else {
                throw new ServiceException(ResultCodeEnum.ALREADY_BOUND_ACCOUNT);
            }
        }
        systemUser.setNickname(userDto.getName());
        systemUser.setSalt(null);
        systemUser.setPassword(null);
        mapper.updateById(systemUser);
        return true;
    }

    public String getToken(SystemUser systemUser)  {
        // Build the token
        String jws = Jwts.builder()
                .setSubject(JSONObject.toJSONString(systemUser))
                .setIssuer("发行者")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 设置过期时间（例如1小时后）
               // .signWith(SignatureAlgorithm.HS256,systemUser.getSalt().getBytes("utf-8"))
                .compact();
        return jws;
    }

    @Override
    public Integer updateEmail(Integer userId,String email) {
        SystemUser userDto = new SystemUser();
        userDto.setId(userId);
        userDto.setEmail(email);
        userDto.setIsEmail(1);
        return mapper.updateById(userDto);
    }

    @Override
    public SystemUser selectByEmail(String email) {
        QueryWrapper<SystemUser> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("email",email);
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

}
