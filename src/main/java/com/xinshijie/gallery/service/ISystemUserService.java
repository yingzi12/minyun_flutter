package com.xinshijie.gallery.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xinshijie.gallery.domain.SystemUser;
import com.xinshijie.gallery.domain.UserAlbum;
import com.xinshijie.gallery.dto.FindSystemUserDto;
import com.xinshijie.gallery.dto.SystemUserDto;
import com.xinshijie.gallery.vo.LoginUserVo;
import com.xinshijie.gallery.vo.SystemUserIntroVo;
import com.xinshijie.gallery.vo.SystemUserVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 作者
 * @since 2023-12-06
 */
public interface ISystemUserService extends IService<SystemUser> {

    LoginUserVo login(String username, String password, String code, String uuid);

    LoginUserVo loginModile(String username, String password);

    Integer updatePwd(Integer userId, String newPassword, String oldPassword);

    Boolean checkUserNameUnique(String username);

    Boolean checkEmailUnique(String email);

    Integer resetUserPwd(Integer userId, String password);

    SystemUser info(Integer userId);

    Boolean add(SystemUserDto userDto);

    SystemUser edit(SystemUserDto userDto);

    Integer updateEmail(Integer userId, String email);

    SystemUser selectByEmail(String email);

    String getCacheKey(String username);

    String saveUploadedFiles(Integer userId, MultipartFile file);

    Integer updateIncome(Integer userId, Double amount);

    Integer updateWithdraw(Integer userId, Double withdraw);

    IPage<SystemUserIntroVo> selectPage(FindSystemUserDto findDto);

    List<SystemUserIntroVo> findRandomStories(Integer pageSize);

}
