package com.hunganh.mymoments.base;

import com.hunganh.mymoments.constant.AttributeConstant;
import com.hunganh.mymoments.constant.InputParam;
import com.hunganh.mymoments.model.Attachment;
import com.hunganh.mymoments.model.Post;
import com.hunganh.mymoments.model.User;
import com.hunganh.mymoments.model.relationship.AttachmentOwnership;
import com.hunganh.mymoments.repository.AttachmentRepository;
import com.hunganh.mymoments.repository.PostRepository;
import com.hunganh.mymoments.repository.UserRepository;
import javafx.geometry.Pos;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @Author: Tran Tuan Anh
 * @Created: Sun, 18/04/2021 1:18 AM
 **/

@Slf4j
@AllArgsConstructor
@Component
public class AttachmentBase {
    private final AttachmentRepository attachmentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public void addObject(Object object) {
        attachmentRepository.save((Attachment) object);
    }

    public void addAssoc(Attachment attachment, Object object) {

    }

    public void addExtendData() {

    }

    public Map<String, Object> getInsertData(String data, String parentId) {
        Map<String, Object> result = new HashMap<>();
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray itemIds = jsonObject
                    .getJSONObject(SnwRelationType.HAS_ATTACHMENT.getName())
                    .getJSONObject(parentId)
                    .getJSONArray(InputParam.ITEM_IDS);
            String offlineId = itemIds.getString(0);
            JSONObject items = jsonObject.getJSONObject(SnwObjectType.ATTACHMENT.getName());
            JSONObject jsMetadata = items.getJSONObject(offlineId).getJSONObject(InputParam.DATA);
            Attachment attachment = Attachment.builder()
                    .name(jsMetadata.getString(AttributeConstant.NAME))
                    .url(jsMetadata.getString(AttributeConstant.URL))
                    .mime(jsMetadata.getString(AttributeConstant.MIME))
                    .size(jsMetadata.getInt(AttributeConstant.SIZE))
                    .length(jsMetadata.getInt(AttributeConstant.LENGTH))
                    .width(jsMetadata.getInt(AttributeConstant.WIDTH))
                    .build();
            result.put(offlineId, attachment);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return result;
    }

    public List<Attachment> getInsertExtendData(String data, String parentId) {
        List<Attachment> attachments = new ArrayList<>();
        try {
            List<Long> attachmentIds = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(data);
            JSONArray itemIds = jsonObject
                    .getJSONObject(SnwRelationType.HAS_ATTACHMENT.getName())
                    .getJSONObject(parentId)
                    .getJSONArray(InputParam.ITEM_IDS);
            if (itemIds != null) {
                for (int i = 0; i < itemIds.length(); i++) {
                    attachmentIds.add(itemIds.getLong(i));
                }
            }
            attachments = attachmentRepository.findAllById(attachmentIds);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return attachments;
    }
}
