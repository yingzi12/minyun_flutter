package com.xinshijie.gallery.mq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送信息
     * @param message
     */
    public void sendMessage(Integer aid,String md5) {
        String message=aid+":"+md5;
        rabbitTemplate.convertAndSend("com.gallery.upload.video", message);
    }
}
