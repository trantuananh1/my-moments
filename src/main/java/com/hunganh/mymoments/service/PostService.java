package com.hunganh.mymoments.service;

import com.hunganh.mymoments.base.PostBase;
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

    public Map<String, Object> addPost(String data, User user) {
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> postMap = postBase.getInsertData(data, user.getId());
        String offlineId = postMap.keySet().iterator().next();
        Post post = (Post) postMap.get(offlineId);
        if (post == null) {
            return result;
        }
        //object
        postBase.addObject(post);
        //assoc
        postBase.addAssoc(user, post);
        //extend data
        postBase.addExtendData();

        result.put(InputParam.OFFLINE_ID, offlineId);
        result.put(InputParam.ID, post.getId());
        return result;
    }
}
