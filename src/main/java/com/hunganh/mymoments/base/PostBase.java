package com.hunganh.mymoments.base;

import com.hunganh.mymoments.constant.InputParam;
import com.hunganh.mymoments.model.Attachment;
import com.hunganh.mymoments.model.Post;
import com.hunganh.mymoments.model.User;
import com.hunganh.mymoments.model.relationship.AttachmentOwnership;
import com.hunganh.mymoments.model.relationship.PostOwnership;
import com.hunganh.mymoments.repository.PostRepository;
import com.hunganh.mymoments.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @Author: Tran Tuan Anh
 * @Created: Mon, 12/04/2021 3:52 PM
 **/

@Slf4j
@AllArgsConstructor
@Component
public class PostBase {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AttachmentBase attachmentBase;

    public void addObject(Object object) {
        postRepository.save((Post) object);
    }

    public void addAssoc(Post post, SnwRelationType snwRelationType, Object object) {
        long currentTime = new Date().getTime();
        switch (snwRelationType) {
            case HAS_ATTACHMENT: {
                Attachment attachment = (Attachment) object;
                if (post.getAttachmentOwnerships() == null) {
                    post.setAttachmentOwnerships(new ArrayList<>());
                }
                post.getAttachmentOwnerships().add(new AttachmentOwnership(attachment, currentTime));
                break;
            }
        }
        postRepository.save(post);
    }

    public void addExtendData(Post post, String offlineId, String data, long userId) {
        // attachment
        List<Attachment> attachments = attachmentBase.getInsertExtendData(data, offlineId);
        for (Attachment attachment : attachments) {
            addAssoc(post, SnwRelationType.HAS_ATTACHMENT, attachment);
        }
    }

    public Map<String, Object> getInsertData(String data, long parentId) {
        Map<String, Object> result = new HashMap<>();
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray itemIds = jsonObject
                    .getJSONObject(SnwRelationType.HAS_POST.getName())
                    .getJSONObject(String.valueOf(parentId))
                    .getJSONArray(InputParam.ITEM_IDS);
            String offlineId = itemIds.getString(0);
            JSONObject items = jsonObject.getJSONObject(SnwObjectType.POST.getName());
            JSONObject metaData = items.getJSONObject(offlineId).getJSONObject(InputParam.DATA);
            Post post = Post.builder()
                    .caption(metaData.getString(InputParam.CAPTION))
                    .build();
            result.put(offlineId, post);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return result;
    }

    public void addRelationships(Post post) {

    }
}
