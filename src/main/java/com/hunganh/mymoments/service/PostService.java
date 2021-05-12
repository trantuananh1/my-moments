package com.hunganh.mymoments.service;

import com.hunganh.mymoments.base.*;
import com.hunganh.mymoments.constant.InputParam;
import com.hunganh.mymoments.dto.SnwMessage;
import com.hunganh.mymoments.dto.SnwScoreRange;
import com.hunganh.mymoments.exception.PostNotFoundException;
import com.hunganh.mymoments.exception.UserNotFoundException;
import com.hunganh.mymoments.model.Comment;
import com.hunganh.mymoments.model.Post;
import com.hunganh.mymoments.model.User;
import com.hunganh.mymoments.output.AssocBaseOutput;
import com.hunganh.mymoments.repository.PostRepository;
import com.hunganh.mymoments.repository.UserRepository;
import com.hunganh.mymoments.util.MessagingUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Tran Tuan Anh
 * @Created: Mon, 12/04/2021 3:48 PM
 **/

@Service
@AllArgsConstructor
@Slf4j
public class PostService {
    private final PostBase postBase;
    private final UserBase userBase;
    private final AuthService authService;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final MessagingUtil messagingUtil;
    private final RabbitTemplate template;

    public Map<String, Object> addPost(long parentId, String data) {
        Map<String, Object> result = new HashMap<>();
        User user = authService.getCurrentUser();
        Map<String, Object> postMap = postBase.getInsertData(data, parentId);
        String offlineId = postMap.keySet().iterator().next();
        Post post = (Post) postMap.get(offlineId);
        if (post == null) {
            return result;
        }
        //object
        postBase.createObject(post);
        //assoc
        userBase.addAssoc(user, SnwRelationType.HAS_POST, post);
        //extend data
        postBase.addExtendData(post, offlineId, data);

        result.put(InputParam.OFFLINE_ID, offlineId);
        result.put(InputParam.ID, post.getId());
        return result;
    }

    public boolean updatePost(long postId, String data){
        User user = authService.getCurrentUser();
        Map<String, Object> postMap = postBase.getUpdateData(data, postId);
        Post post = (Post) postMap.get(String.valueOf(postId));
        if (post == null) {
            return false;
        }
        //object
        postBase.updateObject(post);
        //extend data
        postBase.updateExtendData(post, String.valueOf(postId), data);

        return true;
    }

    public Map<String, Object> getPosts(long parentId, long minScore, long maxScore, int limit){
        User user = userRepository.findById(parentId)
                .orElseThrow(()->new UserNotFoundException(""));
        SnwScoreRange snwScoreRange = new SnwScoreRange(minScore, maxScore);

        Map<String, Object> outputData = postBase.getOutputData(user, snwScoreRange, limit);
        List<Post> posts = (List<Post>) outputData.get(InputParam.POSTS);
        List<Long> postIds = (List<Long>) outputData.get(InputParam.POST_IDS);
        SnwScoreRange newScoreRange = (SnwScoreRange) outputData.get(InputParam.SNW_SCORE_RANGE);
        AssocBaseOutput assocBaseOutput = new AssocBaseOutput(parentId, newScoreRange, postIds.size(), postIds);
        List<AssocBaseOutput> assocBaseOutputs = new ArrayList<>();
        assocBaseOutputs.add(assocBaseOutput);

        Map<String, Object> result=new HashMap<>();
        result.put(SnwRelationType.HAS_POST.getName(), assocBaseOutputs);
        result.put(SnwObjectType.POST.getName(), posts);
        return result;
    }
    public void deletePost(long postId){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(String.valueOf(postId)));
        List<Comment> comments = postBase.getComments(post);
        postRepository.delete(post);
        //post event
        SnwMessage messageEvent = SnwMessage.builder()
                .comments(comments)
                .postId(postId)
                .build();
        messagingUtil.postEvent(SnwObjectType.POST, SnwActionType.DELETE, messageEvent);
    }
}
