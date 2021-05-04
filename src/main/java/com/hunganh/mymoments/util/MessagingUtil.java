package com.hunganh.mymoments.util;

import com.hunganh.mymoments.base.SnwActionType;
import com.hunganh.mymoments.base.SnwObjectType;
import com.hunganh.mymoments.config.MessagingConfig;
import com.hunganh.mymoments.dto.SnwMessage;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

/**
 * @Author: Tran Tuan Anh
 * @Created: Sun, 02/05/2021 11:13 PM
 **/

@Service
@AllArgsConstructor
public class MessagingUtil {
    private final RabbitTemplate template;

    public void postEvent(SnwObjectType objectType, SnwActionType actionType, SnwMessage message) {
        message.setObjectType(objectType);
        message.setActionType(actionType);
        template.convertAndSend(MessagingConfig.EXCHANGE, MessagingConfig.ROUTING_KEY, message);
    }

}
