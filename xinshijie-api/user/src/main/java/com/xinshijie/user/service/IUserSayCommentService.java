package com.xinshijie.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xinshijie.user.domain.UserSayComment;
import com.xinshijie.user.dto.UserSayCommentAddDto;
import com.xinshijie.user.dto.UserSayCommentDto;
import com.xinshijie.user.dto.UserSayCommentReplyAddDto;
import com.xinshijie.user.vo.UserSayCommentVo;

import java.util.List;

/**
 * <p>
 * 说说回复表 服务类
 * </p>
 *
 * @author 作者
 * @since 2024-06-18
 */
public interface IUserSayCommentService extends IService<UserSayComment> {


    /**
  * 查询信息表
  */
    List<UserSayCommentVo> selectUserSayCommentList(UserSayCommentDto dto);

    /**
     * 分页查询。普通方法
     * 查询图片信息表
     */
    Page<UserSayCommentVo> selectPageUserSayComment(UserSayCommentDto dto);

    /**
     * 分页查询信息表
     */
    Page<UserSayCommentVo> getPageUserSayComment(UserSayCommentDto dto);


    UserSayComment add(UserSayCommentAddDto addDto);

    UserSayComment reply(UserSayCommentReplyAddDto addDto);

    /**
     * 删除数据
     */
    Integer delById(Long id);

    /**
     * 根据id数据
     */
    UserSayCommentVo getInfo(Long id);
}
