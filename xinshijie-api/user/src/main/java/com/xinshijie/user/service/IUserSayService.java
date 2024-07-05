package com.xinshijie.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xinshijie.user.domain.UserSay;
import com.xinshijie.user.dto.UserSayDto;
import com.xinshijie.user.vo.UserSayVo;

import java.util.List;

/**
 * <p>
 * 用户说说， 服务类
 * </p>
 *
 * @author 作者
 * @since 2024-06-18
 */
public interface IUserSayService extends IService<UserSay> {


    /**
  * 查询信息表
  */
    List<UserSayVo> selectUserSayList(UserSayDto dto);

    /**
     * 分页查询。普通方法
     * 查询图片信息表
     */
    Page<UserSayVo> selectPageUserSay(UserSayDto dto);

    /**
     * 分页查询信息表
     */
    Page<UserSayVo> getPageUserSay(UserSayDto dto);

    /**
     * 新增数据
     */
    UserSay add(UserSayDto id);

    /**
     * 根据id修改数据
     */
    Integer edit(UserSayDto dto);

    /**
     * 删除数据
     */
    Integer delById(Long id);

    /**
     * 根据id数据
     */
    UserSayVo getInfo(Long id);
}
