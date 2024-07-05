package com.xinshijie.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xinshijie.user.domain.UserChatContent;
import com.xinshijie.user.dto.UserChatContentDto;
import com.xinshijie.user.vo.UserChatContentVo;
import java.util.List;

/**
 * <p>
 * 用户聊天记录 服务类
 * </p>
 *
 * @author 作者
 * @since 2024-06-21
 */
public interface IUserChatContentService extends IService<UserChatContent> {


    /**
  * 查询信息表
  */
    List<UserChatContentVo> selectUserChatContentList(UserChatContentDto dto);

    /**
     * 分页查询。普通方法
     * 查询图片信息表
     */
    Page<UserChatContentVo> selectPageUserChatContent(UserChatContentDto dto);

    /**
     * 分页查询信息表
     */
    Page<UserChatContentVo> getPageUserChatContent(UserChatContentDto dto);

    /**
     * 新增数据
     */
    UserChatContent add(UserChatContentDto id);

    /**
     * 根据id修改数据
     */
    Integer edit(UserChatContentDto dto);

    /**
     * 删除数据
     */
    Integer delById(Long id);

    /**
     * 根据id数据
     */
    UserChatContentVo getInfo(Long id);
}
