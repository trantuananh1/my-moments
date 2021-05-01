package com.hunganh.mymoments.service;

import com.hunganh.mymoments.base.PostBase;
import com.hunganh.mymoments.base.SnwRelationType;
import com.hunganh.mymoments.base.UserBase;
import com.hunganh.mymoments.constant.InputParam;
import com.hunganh.mymoments.model.Post;
import com.hunganh.mymoments.model.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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

    public Map<String, Object> createPost(String data) {
        Map<String, Object> result = new HashMap<>();
        User user = authService.getCurrentUser();
        long userId = user.getId();
        Map<String, Object> postMap = postBase.getInsertData(data, user.getId());
        String offlineId = postMap.keySet().iterator().next();
        Post post = (Post) postMap.get(offlineId);
        if (post == null) {
            return result;
        }
        //object
        postBase.addObject(post);
        //assoc
        userBase.addAssoc(user, SnwRelationType.HAS_POST, post);
        //extend data
        postBase.addExtendData(post, offlineId, data, userId);

        result.put(InputParam.OFFLINE_ID, offlineId);
        result.put(InputParam.ID, post.getId());
        return result;
    }
}
