package com.hunganh.mymoments.service;

import com.hunganh.mymoments.base.CommentBase;
import com.hunganh.mymoments.base.PostBase;
import com.hunganh.mymoments.base.SnwRelationType;
import com.hunganh.mymoments.base.UserBase;
import com.hunganh.mymoments.constant.InputParam;
import com.hunganh.mymoments.exception.PostNotFoundException;
import com.hunganh.mymoments.model.Comment;
import com.hunganh.mymoments.model.Post;
import com.hunganh.mymoments.model.User;
import com.hunganh.mymoments.repository.PostRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Tran Tuan Anh
 * @Created: Sat, 01/05/2021 4:39 PM
 **/

@Service
@AllArgsConstructor
@Slf4j
public class CommentService {
    private final CommentBase commentBase;
    private final PostBase postBase;
    private final AuthService authService;
    private final PostRepository postRepository;

    public Map<String, Object> addComment(long parentId, String data) {
        Map<String, Object> result = new HashMap<>();
        User user = authService.getCurrentUser();
        Post post = postRepository.findById(parentId)
                .orElseThrow(() -> new PostNotFoundException(String.valueOf(parentId)));
        long userId = user.getId();
        Map<String, Object> commentMap = commentBase.getInsertData(data, parentId);
        String offlineId = commentMap.keySet().iterator().next();
        Comment comment = (Comment) commentMap.get(offlineId);
        if (comment == null) {
            return result;
        }
        //object
        commentBase.createObject(comment);
        //assoc
        postBase.addAssoc(post, SnwRelationType.HAS_COMMENT, comment);
        //extend data
        commentBase.addExtendData(comment, offlineId, data);

        result.put(InputParam.OFFLINE_ID, offlineId);
        result.put(InputParam.ID, comment.getId());
        return result;
    }

    public boolean updateComment(long commentId, String data){
        Map<String, Object> commentMap = commentBase.getUpdateData(data, commentId);
        Comment comment = (Comment) commentMap.get(String.valueOf(commentId));
        if (comment == null) {
            return false;
        }
        //object
        commentBase.updateObject(comment);
        //extend data
        commentBase.updateExtendData(comment, String.valueOf(commentId), data);

        return true;
    }

    public void deletePost(long postId){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(String.valueOf(postId)));
        postRepository.delete(post);
    }
}
