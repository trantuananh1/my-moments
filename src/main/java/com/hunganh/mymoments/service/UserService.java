package com.hunganh.mymoments.service;

import com.hunganh.mymoments.base.RelationBaseRepository;
import com.hunganh.mymoments.base.SnwObject;
import com.hunganh.mymoments.base.SnwObjectType;
import com.hunganh.mymoments.base.SnwRelationType;
import com.hunganh.mymoments.constant.InputParam;
import com.hunganh.mymoments.constant.JsonConstant;
import com.hunganh.mymoments.exception.UserNotFoundException;
import com.hunganh.mymoments.model.Profile;
import com.hunganh.mymoments.model.User;
import com.hunganh.mymoments.model.relationship.Followship;
import com.hunganh.mymoments.repository.UserRepository;
import jdk.internal.util.xml.impl.Input;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: Tran Tuan Anh
 * @Created: Tue, 13/04/2021 9:57 AM
 **/

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private final RelationBaseRepository relationBaseRepository;
    private final UserRepository userRepository;
    private final AuthService authService;

    @Transactional(readOnly = true)
    public Map<String, Object> getUser(long userId, String type) {
        Map<String, Object> result = new HashMap<>();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.valueOf(userId)));
        String key;
        switch (type) {
            case InputParam.LITE:
                key = "UserLite";
                break;
            default:
                key = "User";
        }
        result.put(key, Arrays.asList(user));
        return result;
    }

    public void updateUser(String data) {
        User user = authService.getCurrentUser();
        long userId = user.getId();
        JSONObject jsUser = new JSONObject(data).getJSONObject(SnwObjectType.USER.getName());
        JSONObject jsProfile = jsUser.getJSONObject(String.valueOf(userId))
                .getJSONObject(JsonConstant.DATA)
                .getJSONObject(JsonConstant.PROFILE);
        Profile profile = Profile.builder()
                .userId(userId)
                .fullName(jsProfile.getString(JsonConstant.FULL_NAME))
                .city(jsProfile.getString(JsonConstant.CITY))
                .country(jsProfile.getString(JsonConstant.COUNTRY))
                .biography(jsProfile.getString(JsonConstant.BIOGRAPHY))
                .gender(jsProfile.getString(JsonConstant.GENDER))
                .dateOfBirth(jsProfile.getString(JsonConstant.DATE_OF_BIRTH))
                .avatarUrl(jsProfile.getString(JsonConstant.AVATAR_URL))
                .coverUrl(jsProfile.getString(JsonConstant.COVER_URL))
                .build();
        user.setProfile(profile);
        user.setVersion(user.getVersion() + 1);
        user.setDateUpdated(new Date().getTime());
        userRepository.save(user);
    }

    public void deleteUser(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.valueOf(userId)));
        userRepository.delete(user);
    }

    public void followUser(long toId) {
        User user = authService.getCurrentUser();
        User toUser = userRepository.findById(toId)
                .orElseThrow(() -> new UserNotFoundException(String.valueOf(toId)));
        if (!toUser.isPrivate()) {
            long currentTime = new Date().getTime();
            if (user.getFollowings() == null) {
                user.setFollowings(new ArrayList<>());
            }
            user.getFollowings().add(new Followship(toUser, currentTime));
            if (toUser.getFollowers() == null) {
                toUser.setFollowers(new ArrayList<>());
            }
            toUser.getFollowers().add(new Followship(user, currentTime));
            userRepository.saveAll(Arrays.asList(user, toUser));
        }
    }

    public void unfollowUser(long toId) {
        User user = authService.getCurrentUser();
        User toUser = userRepository.findById(toId)
                .orElseThrow(() -> new UserNotFoundException(String.valueOf(toId)));
        if (relationBaseRepository.hasRelationship(user, SnwRelationType.FOLLOWS.name(), toUser)) {
            user.getFollowings().removeIf(c -> c.getUser().equals(toUser));
            toUser.getFollowers().removeIf(c -> c.getUser().equals(user));
            userRepository.saveAll(Arrays.asList(user, toUser));
        }
    }

    public Map<String, Object> getFollowers(long userId) {
        Map<String, Object> result = new HashMap<>();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.valueOf(userId)));
        List<User> followers = user.getFollowers().stream()
                .map(Followship::getUser)
                .collect(Collectors.toList());
        if (followers.size() == 0) {
            return result;
        }
        result.put(SnwObjectType.USER.getName(), followers);
        return result;
    }

    public Map<String, Object> getFollowings(long userId) {
        Map<String, Object> result = new HashMap<>();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.valueOf(userId)));
        List<User> followings = user.getFollowings().stream()
                .map(Followship::getUser)
                .collect(Collectors.toList());
        if (followings.size() == 0) {
            return result;
        }
        result.put(SnwObjectType.USER.getName(), followings);
        return result;
    }
}
