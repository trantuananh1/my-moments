package com.hunganh.mymoments.base;

import com.hunganh.mymoments.constant.InputParam;
import com.hunganh.mymoments.constant.JsonConstant;
import com.hunganh.mymoments.exception.PostNotFoundException;
import com.hunganh.mymoments.model.Attachment;
import com.hunganh.mymoments.model.Comment;
import com.hunganh.mymoments.model.Post;
import com.hunganh.mymoments.model.relationship.AttachmentOwnership;
import com.hunganh.mymoments.repository.CommentRepository;
import com.hunganh.mymoments.repository.PostRepository;
import com.hunganh.mymoments.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: Tran Tuan Anh
 * @Created: Sat, 01/05/2021 4:41 PM
 **/

@Slf4j
@AllArgsConstructor
@Component
public class CommentBase {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AttachmentBase attachmentBase;
    private final CommentRepository commentRepository;

    public void createObject(Object object) {
        commentRepository.save((Comment) object);
    }

    public void updateObject(Object object) {
        commentRepository.save((Comment) object);
    }

    public void addAssoc(Comment comment, SnwRelationType snwRelationType, Object object) {
        long currentTime = new Date().getTime();
        switch (snwRelationType) {
            case HAS_ATTACHMENT: {
                Attachment attachment = (Attachment) object;
                if (comment.getAttachmentOwnerships() == null) {
                    comment.setAttachmentOwnerships(new ArrayList<>());
                }
                comment.getAttachmentOwnerships().add(new AttachmentOwnership(attachment, currentTime));
                break;
            }
        }
        commentRepository.save(comment);
    }

    public void deleteAssoc(Comment comment, SnwRelationType snwRelationType, Object object) {
        switch (snwRelationType) {
            case HAS_ATTACHMENT: {
                Attachment attachment = (Attachment) object;
                comment.getAttachmentOwnerships().removeIf(c -> c.getAttachment().equals(attachment));
                break;
            }
        }
        commentRepository.save(comment);
    }

    public void addExtendData(Comment comment, String offlineId, String data) {
        // attachment
        List<Attachment> attachments = attachmentBase.getInsertExtendData(data, offlineId);
        for (Attachment attachment : attachments) {
            addAssoc(comment, SnwRelationType.HAS_ATTACHMENT, attachment);
        }
    }

    public void updateExtendData(Comment comment, String objectId, String data) {
        // attachment
        List<Attachment> newAttachments = attachmentBase.getInsertExtendData(data, objectId);
        List<Attachment> oldAttachments = comment.getAttachmentOwnerships().stream()
                .map(AttachmentOwnership::getAttachment)
                .collect(Collectors.toList());
        if (newAttachments != oldAttachments) {
            for (Attachment attachment : oldAttachments) {
                deleteAssoc(comment, SnwRelationType.HAS_ATTACHMENT, attachment);
            }
            for (Attachment attachment : newAttachments) {
                addAssoc(comment, SnwRelationType.HAS_ATTACHMENT, attachment);
            }
        }
    }

    public Map<String, Object> getInsertData(String data, long parentId) {
        Map<String, Object> result = new HashMap<>();
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray itemIds = jsonObject
                    .getJSONObject(SnwRelationType.HAS_COMMENT.getName())
                    .getJSONObject(String.valueOf(parentId))
                    .getJSONArray(InputParam.ITEM_IDS);
            String offlineId = itemIds.getString(0);
            JSONObject items = jsonObject.getJSONObject(SnwObjectType.COMMENT.getName());
            JSONObject metaData = items.getJSONObject(offlineId).getJSONObject(InputParam.DATA);
            long currentTime = new Date().getTime();
            Comment comment = Comment.builder()
                    .content(metaData.getString(JsonConstant.CONTENT))
                    .version(1)
                    .dateCreated(currentTime)
                    .dateUpdated(currentTime)
                    .build();
            result.put(offlineId, comment);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return result;
    }

    public Map<String, Object> getUpdateData(String data, long objectId) {
        Map<String, Object> result = new HashMap<>();
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONObject items = jsonObject.getJSONObject(SnwObjectType.COMMENT.getName());
            JSONObject metaData = items.getJSONObject(String.valueOf(objectId)).getJSONObject(InputParam.DATA);
            long currentTime = new Date().getTime();
            Comment comment = commentRepository.findById(objectId)
                    .orElseThrow(() -> new PostNotFoundException(String.valueOf(objectId)));
            comment.setContent(metaData.getString(InputParam.CONTENT));
            comment.setVersion(comment.getVersion() + 1);
            comment.setDateUpdated(currentTime);
            result.put(String.valueOf(objectId), comment);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return result;
    }
}
