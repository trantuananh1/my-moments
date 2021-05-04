package com.hunganh.mymoments.dto;

import com.hunganh.mymoments.base.SnwActionType;
import com.hunganh.mymoments.base.SnwObjectType;
import com.hunganh.mymoments.model.Attachment;
import com.hunganh.mymoments.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Author: Tran Tuan Anh
 * @Created: Sun, 02/05/2021 11:36 PM
 **/

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class SnwMessage implements Serializable{
    protected SnwObjectType objectType;
    protected SnwActionType actionType;

    private long postId;
    private List<Comment> comments;
    private List<Attachment> attachments;
}
