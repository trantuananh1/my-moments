package com.hunganh.mymoments.base;

import com.hunganh.mymoments.constant.InputParam;
import com.hunganh.mymoments.constant.JsonConstant;
import com.hunganh.mymoments.exception.GeneralException;
import com.hunganh.mymoments.exception.PostNotFoundException;
import com.hunganh.mymoments.exception.UserNotFoundException;
import com.hunganh.mymoments.model.Attachment;
import com.hunganh.mymoments.model.Comment;
import com.hunganh.mymoments.model.Post;
import com.hunganh.mymoments.model.User;
import com.hunganh.mymoments.model.relationship.AttachmentOwnership;
import com.hunganh.mymoments.model.relationship.CommentOwnership;
import com.hunganh.mymoments.model.relationship.Followship;
import com.hunganh.mymoments.model.relationship.PostOwnership;
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
 * @Created: Mon, 12/04/2021 3:52 PM
 **/

@Slf4j
@AllArgsConstructor
@Component
public class PostBase {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final AttachmentBase attachmentBase;

    public void createObject(Object object) {
        postRepository.save((Post) object);
    }

    public void updateObject(Object object) {
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
            case HAS_COMMENT: {
                Comment comment = (Comment) object;
                if (post.getCommentOwnerships() == null) {
                    post.setCommentOwnerships(new ArrayList<>());
                }
                post.getCommentOwnerships().add(new CommentOwnership(comment, currentTime));
                break;
            }
        }
        postRepository.save(post);
    }

    public void deleteAssoc(Post post, SnwRelationType snwRelationType, Object object) {
        switch (snwRelationType) {
            case HAS_ATTACHMENT: {
                Attachment attachment = (Attachment) object;
                post.getAttachmentOwnerships().removeIf(c -> c.getAttachment().equals(attachment));
                break;
            }
            case HAS_COMMENT: {
                Comment comment = (Comment) object;
                post.getCommentOwnerships().removeIf(c -> c.getComment().equals(comment));
                break;
            }
        }
        postRepository.save(post);
    }

    public void addExtendData(Post post, String objectId, String data) {
        // attachment
        List<Attachment> attachments = attachmentBase.getInsertExtendData(data, objectId);
        for (Attachment attachment : attachments) {
            addAssoc(post, SnwRelationType.HAS_ATTACHMENT, attachment);
        }
    }

    public void updateExtendData(Post post, String objectId, String data) {
        // attachment
        List<Attachment> newAttachments = attachmentBase.getInsertExtendData(data, objectId);
        List<Attachment> oldAttachments = post.getAttachmentOwnerships().stream()
                .map(AttachmentOwnership::getAttachment)
                .collect(Collectors.toList());
        if (newAttachments != oldAttachments) {
            for (Attachment attachment : oldAttachments) {
                deleteAssoc(post, SnwRelationType.HAS_ATTACHMENT, attachment);
            }
            for (Attachment attachment : newAttachments) {
                addAssoc(post, SnwRelationType.HAS_ATTACHMENT, attachment);
            }
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
            long currentTime = new Date().getTime();
            Post post = Post.builder()
                    .caption(metaData.getString(InputParam.CAPTION))
                    .version(1)
                    .dateCreated(currentTime)
                    .dateUpdated(currentTime)
                    .build();
            result.put(offlineId, post);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return result;
    }

    public Map<String, Object> getUpdateData(String data, long objectId) {
        Map<String, Object> result = new HashMap<>();
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONObject items = jsonObject.getJSONObject(SnwObjectType.POST.getName());
            JSONObject metaData = items.getJSONObject(String.valueOf(objectId)).getJSONObject(InputParam.DATA);
            long currentTime = new Date().getTime();
            Post post = postRepository.findById(objectId)
                    .orElseThrow(() -> new PostNotFoundException(String.valueOf(objectId)));
            post.setCaption(metaData.getString(InputParam.CAPTION));
            post.setVersion(post.getVersion() + 1);
            post.setDateUpdated(currentTime);
            result.put(String.valueOf(objectId), post);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return result;
    }

    public List<Comment> getComments(Post post){
        List<Comment> comments = post.getCommentOwnerships().stream()
                .map(CommentOwnership::getComment)
                .collect(Collectors.toList());
        return comments;
    }

    public List<Long> getCommentIds(Post post){
        List<Comment> comments = post.getCommentOwnerships().stream()
                .map(CommentOwnership::getComment)
                .collect(Collectors.toList());
        List<Long> commentIds = comments.stream()
                .map(Comment::getId)
                .collect(Collectors.toList());
        return commentIds;
    }
}
