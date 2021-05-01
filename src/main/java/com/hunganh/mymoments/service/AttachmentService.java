package com.hunganh.mymoments.service;

import com.hunganh.mymoments.base.AttachmentBase;
import com.hunganh.mymoments.base.SnwObjectType;
import com.hunganh.mymoments.base.SnwRelationType;
import com.hunganh.mymoments.base.UserBase;
import com.hunganh.mymoments.constant.AttributeConstant;
import com.hunganh.mymoments.constant.InputParam;
import com.hunganh.mymoments.constant.JsonConstant;
import com.hunganh.mymoments.exception.AttachmentNotFoundException;
import com.hunganh.mymoments.exception.UserNotFoundException;
import com.hunganh.mymoments.model.Attachment;
import com.hunganh.mymoments.model.Post;
import com.hunganh.mymoments.model.User;
import com.hunganh.mymoments.model.relationship.AttachmentOwnership;
import com.hunganh.mymoments.model.relationship.Followship;
import com.hunganh.mymoments.repository.AttachmentRepository;
import com.hunganh.mymoments.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: Tran Tuan Anh
 * @Created: Sun, 18/04/2021 12:59 AM
 **/

@Service
@AllArgsConstructor
@Slf4j
public class AttachmentService {
    private final AuthService authService;
    private final AttachmentRepository attachmentRepository;
    private final UserRepository userRepository;
    private final AttachmentBase attachmentBase;
    private final UserBase userBase;

    public Map<String, Object> createAttachment(String data){
        Map<String, Object> result = new HashMap<>();
        User user = authService.getCurrentUser();
        Map<String, Object> attachmentMap = attachmentBase.getInsertData(data, String.valueOf(user.getId()));
        String offlineId = attachmentMap.keySet().iterator().next();
        Attachment attachment = (Attachment) attachmentMap.get(offlineId);
        if (attachment == null) {
            return result;
        }
        //object
        attachmentBase.addObject(attachment);
        //assoc
        userBase.addAssoc(user, SnwRelationType.HAS_ATTACHMENT, attachment);

        result.put(InputParam.OFFLINE_ID, offlineId);
        result.put(InputParam.ID, attachment.getId());
        return result;
    }

    public Map<String, Object> getAttachment(long attachmentId){
        Map<String, Object> result = new HashMap<>();
        Attachment attachment = attachmentRepository.findById(attachmentId)
                .orElseThrow(() -> new AttachmentNotFoundException(String.valueOf(attachmentId)));
        result.put(SnwObjectType.ATTACHMENT.getName(), Arrays.asList(attachment));
        return result;
    }

    public void deleteAttachment(long attachmentId){
        Attachment attachment = attachmentRepository.findById(attachmentId)
                .orElseThrow(() -> new AttachmentNotFoundException(String.valueOf(attachmentId)));
        attachmentRepository.delete(attachment);
    }

    public Map<String, Object> getAttachments(long userId){
        Map<String, Object> result = new HashMap<>();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.valueOf(userId)));
        List<Attachment> attachments = user.getAttachmentOwnerships().stream()
                .map(AttachmentOwnership::getAttachment)
                .collect(Collectors.toList());
        if (attachments.size() == 0) {
            return result;
        }
        result.put(SnwObjectType.ATTACHMENT.getName(), attachments);
        return result;
    }
}
