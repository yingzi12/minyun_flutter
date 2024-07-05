package com.xinshijie.socket.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinshijie.socket.domain.TestMessage;
import com.xinshijie.socket.dto.MessageDto;
import com.xinshijie.socket.mapper.TestMessageMapper;
import com.xinshijie.socket.service.TestMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 管理员 服务实现类
 * </p>
 *
 * @author zx
 * @since 2022-08-27
 */
@Slf4j
@Service
public class TestMessageServiceImpl extends ServiceImpl<TestMessageMapper, TestMessage> implements TestMessageService {

    @Autowired
    private TestMessageMapper messageMapper;
    /**
     * 新增数据
     */
    @Override
    public TestMessage add(MessageDto dto) {
        TestMessage value = new TestMessage();
        org.springframework.beans.BeanUtils.copyProperties(dto, value);
        value.setCreateTime(LocalDateTime.now());
        value.setReceiveTime(LocalDateTime.now());
        value.setIsDelete(2);
        value.setIsRead(2);
        messageMapper.insert(value);
        return value;
    }

}
