package com.hunganh.mymoments.base;

import com.hunganh.mymoments.constant.BaseConstant;
import com.hunganh.mymoments.model.Attachment;
import com.hunganh.mymoments.model.Post;
import com.hunganh.mymoments.model.User;
import com.hunganh.mymoments.model.relationship.AttachmentOwnership;
import com.hunganh.mymoments.model.relationship.PostOwnership;
import com.hunganh.mymoments.model.relationship.PostOwnershipComparator;
import com.hunganh.mymoments.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.xml.stream.events.Attribute;
import java.util.*;

/**
 * @Author: Tran Tuan Anh
 * @Created: Sat, 01/05/2021 1:37 AM
 **/

@Slf4j
@AllArgsConstructor
@Component
public class UserBase {
    private final UserRepository userRepository;
    public void addObject(Object object) {
        userRepository.save((User) object);
    }

    public void addAssoc(User user, SnwRelationType snwRelationType, Object object) {
        long currentTime = new Date().getTime();
        switch (snwRelationType) {
            case HAS_POST: {
                Post post = (Post) object;
                if (user.getPostOwnerships()==null){
                    user.setPostOwnerships(new ArrayList<>());
                }
                user.getPostOwnerships().add(new PostOwnership(post, currentTime));
                break;
            }
            case HAS_ATTACHMENT: {
                Attachment attachment = (Attachment) object;
                if (user.getAttachmentOwnerships()==null){
                    user.setAttachmentOwnerships(new ArrayList<>());
                }
                user.getAttachmentOwnerships().add(new AttachmentOwnership(attachment, currentTime));
                break;
            }
        }
        userRepository.save(user);
    }



    public void addExtendData(String offlineId, Object object, String data, long userId) {

    }
}
