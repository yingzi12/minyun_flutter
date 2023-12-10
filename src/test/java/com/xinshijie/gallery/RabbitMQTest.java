package com.xinshijie.gallery;

import com.xinshijie.gallery.mq.MessageProducer;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RabbitMQTest {

    @Autowired
    private MessageProducer producer;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testSendMessage() {
        String message = "Hello, RabbitMQ!";
//        producer.sendMessage(message);

        // 这里可以加入逻辑来验证消息是否被正确发送和接收
        // 注意：实际测试中可能需要一些同步机制来等待消息处理
    }
}

