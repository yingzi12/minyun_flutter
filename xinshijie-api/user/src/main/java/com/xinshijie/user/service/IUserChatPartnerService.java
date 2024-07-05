package com.xinshijie.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xinshijie.user.domain.UserChatPartner;
import com.xinshijie.user.dto.UserChatPartnerDto;
import com.xinshijie.user.vo.UserChatPartnerVo;
import java.util.List;

/**
 * <p>
 * 用户聊天对象表 服务类
 * </p>
 *
 * @author 作者
 * @since 2024-06-21
 */
public interface IUserChatPartnerService extends IService<UserChatPartner> {


    /**
  * 查询信息表
  */
    List<UserChatPartnerVo> selectUserChatPartnerList(UserChatPartnerDto dto);

    /**
     * 分页查询。普通方法
     * 查询图片信息表
     */
    Page<UserChatPartnerVo> selectPageUserChatPartner(UserChatPartnerDto dto);

    /**
     * 分页查询信息表
     */
    Page<UserChatPartnerVo> getPageUserChatPartner(UserChatPartnerDto dto);

    /**
     * 新增数据
     */
    UserChatPartner add(UserChatPartnerDto id);

    /**
     * 根据id修改数据
     */
    Integer edit(UserChatPartnerDto dto);

    /**
     * 删除数据
     */
    Integer delById(Long id);

    /**
     * 根据id数据
     */
    UserChatPartnerVo getInfo(Long id);
}
