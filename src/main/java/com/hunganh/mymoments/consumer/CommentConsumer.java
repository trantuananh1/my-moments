package com.hunganh.mymoments.consumer;

import com.hunganh.mymoments.base.ConsumerBase;
import com.hunganh.mymoments.base.SnwActionType;
import com.hunganh.mymoments.base.SnwObjectType;
import com.hunganh.mymoments.config.MessagingConfig;
import com.hunganh.mymoments.dto.SnwMessage;
import com.hunganh.mymoments.model.Comment;
import com.hunganh.mymoments.repository.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: Tran Tuan Anh
 * @Created: Sun, 02/05/2021 11:22 PM
 **/

@Component
@AllArgsConstructor
public class CommentConsumer extends ConsumerBase {
    private final CommentRepository commentRepository;

    @RabbitListener(queues = MessagingConfig.QUEUE)
    public void consumeMessageFromQueue(SnwMessage message) {
        super.init(message);
        if (isExistEvent(SnwObjectType.POST, SnwActionType.DELETE)) {
            deletePost(message);
        } else if (isExistEvent(SnwObjectType.POST, SnwActionType.ADD)) {
//            deletePost(inputs);
        } else if (isExistEvent(SnwObjectType.POST, SnwActionType.UPDATE)) {
//            deletePost(inputs);
        }
    }

    public void deletePost(SnwMessage message) {
        //delete comments
        List<Comment> comments = message.getComments();
        for (Comment comment : comments) {
            commentRepository.delete(comment);
        }
    }
}
