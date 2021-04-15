package com.hunganh.mymoments.base;

import com.hunganh.mymoments.constant.InputParam;
import com.hunganh.mymoments.model.Post;
import com.hunganh.mymoments.repository.PostRepository;
import com.hunganh.mymoments.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: Tran Tuan Anh
 * @Created: Mon, 12/04/2021 3:52 PM
 **/

@Slf4j
@AllArgsConstructor
@Component
public class PostBase implements ObjectBase {
    private final RelationBaseRepository relationBaseRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    public void addObject(Object object) {
        postRepository.save((Post) object);
    }

    @Override
    public void addAssoc(Object o1, Object o2) {
        relationBaseRepository.addRelation(o1, o2);
    }

    @Override
    public void addExtendData() {

    }

    @Override
    public Map<String, Object> getInsertData(String data, long userId) {
        Map<String, Object> result = new HashMap<>();
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray itemIds = jsonObject
                    .getJSONObject(SnwRelationType.HAS_POST.getName())
                    .getJSONObject(String.valueOf(userId))
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
}
