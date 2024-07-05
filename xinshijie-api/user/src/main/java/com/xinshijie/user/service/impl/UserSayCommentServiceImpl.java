package com.xinshijie.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinshijie.base.utils.SecurityUtils;
import com.xinshijie.common.enums.ResultCodeEnum;
import com.xinshijie.common.exception.ServiceException;
import com.xinshijie.user.domain.UserSayComment;
import com.xinshijie.user.dto.UserSayCommentAddDto;
import com.xinshijie.user.dto.UserSayCommentDto;
import com.xinshijie.user.dto.UserSayCommentReplyAddDto;
import com.xinshijie.user.mapper.UserSayCommentMapper;
import com.xinshijie.user.service.IUserSayCommentService;
import com.xinshijie.user.service.IUserSayService;
import com.xinshijie.user.vo.UserSayCommentVo;
import com.xinshijie.user.vo.UserSayVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 说说回复表  服务实现类
 * </p>
 *
 * @author zx
 * @since 2022-09-05
 */
@Slf4j
@Service
public class UserSayCommentServiceImpl extends ServiceImpl<UserSayCommentMapper, UserSayComment> implements IUserSayCommentService {

    @Autowired
    private UserSayCommentMapper mapper;
    @Autowired
    private IUserSayService userSayService;
    /**
     * 查询图片信息表
     */
    @Override
    public List<UserSayCommentVo> selectUserSayCommentList(UserSayCommentDto dto) {
        return mapper.selectListUserSayComment(dto);
    }

    /**
     * 分页查询图片信息表
     */
    @Override
    public Page<UserSayCommentVo> selectPageUserSayComment(UserSayCommentDto dto) {
        Page<UserSayCommentVo> page = new Page<>();
        page.setCurrent(dto.getPageNum());
        page.setSize(dto.getPageSize());
        return mapper.selectPageUserSayComment(page, dto);
    }

    /**
     * 分页查询图片信息表
     */
    @Override
    public Page<UserSayCommentVo> getPageUserSayComment(UserSayCommentDto dto) {
        Page<UserSayCommentVo> page = new Page<>();
    page.setCurrent(dto.getPageNum());
    page.setSize(dto.getPageSize());
        QueryWrapper<UserSayCommentVo> qw = new QueryWrapper<>();
        return mapper.getPageUserSayComment(page, qw);
    }

    /**
     * 新增数据
     */
    @Override
    public UserSayComment add(UserSayCommentAddDto addDto) {
        UserSayComment dto = new UserSayComment();
        Long userid = SecurityUtils.getUserId();
        String username = SecurityUtils.getUsername();
        UserSayVo sayVo = userSayService.getInfo(addDto.getUsid());
        if (sayVo == null) {
            throw new ServiceException(ResultCodeEnum.THE_DISCUSS_DOES_NOT_EXIST);
        }
        dto.setUpid(0L);
        dto.setRanks(0);
        dto.setStatus(1);
        dto.setPid(0L);
        dto.setCreateName(username);
        dto.setCreateId(userid);
        dto.setNickname(SecurityUtils.getNickName());
        dto.setComment(addDto.getComment());
        dto.setCircleUrl(SecurityUtils.getAvatar());
        mapper.insert(dto);
        return dto;
    }

    /**
     * 新增数据
     */
    @Override
    public UserSayComment reply(UserSayCommentReplyAddDto addDto) {
        UserSayComment dto = new UserSayComment();
        Long userid = SecurityUtils.getUserId();
        String username = SecurityUtils.getUsername();

        UserSayVo sayVo = userSayService.getInfo(addDto.getUsid());
        if (sayVo == null) {
            throw new ServiceException(ResultCodeEnum.THE_DISCUSS_DOES_NOT_EXIST);
        }
        UserSayComment prante = mapper.selectById(addDto.getUpid());
        if (prante == null) {
            throw new ServiceException(ResultCodeEnum.THE_COMMIT_DOES_NOT_EXIST);
        }
        dto.setUsid(sayVo.getId());
        dto.setUpid(prante.getId());
        if (prante.getPid() == 0) {
            dto.setPid(prante.getId());
        } else {
            dto.setPid(prante.getPid());
        }
        dto.setRanks(prante.getRanks() + 1);
        dto.setStatus(1);
        dto.setCreateName(username);
        dto.setCreateId(userid);
        dto.setReply(prante.getComment());
        dto.setNickname(SecurityUtils.getNickName());
        dto.setComment(addDto.getComment());
        dto.setCircleUrl(SecurityUtils.getAvatar());

        dto.setReplyUserId(prante.getCreateId());
        dto.setReplyUserName(prante.getCreateName());
        dto.setReplyUserNickname(prante.getNickname());
        mapper.insert(dto);
        return dto;
    }


    /**
     * 删除数据
     */
    @Override
    public Integer delById(Long id) {
        return mapper.delById(id);
    }

    /**
     * 根据id数据
     */
    @Override
    public UserSayCommentVo getInfo(Long id) {
        return mapper.getInfo(id);
    }

}
