package com.xinshijie.socket.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xinshijie.socket.domain.TestMessage;
import com.xinshijie.socket.dto.MessageDto;

/**
 * <p>
 * 管理员 服务类
 * </p>
 *
 * @author zx
 * @since 2022-08-27
 */
public interface TestMessageService extends IService<TestMessage> {


    /**
     * 新增数据
     */
    TestMessage add(MessageDto dto);

}
