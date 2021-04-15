package com.hunganh.mymoments.controller;

import com.hunganh.mymoments.constant.InputParam;
import com.hunganh.mymoments.constant.ResponseConstant;
import com.hunganh.mymoments.model.User;
import com.hunganh.mymoments.response.SnwAddResponse;
import com.hunganh.mymoments.response.SnwErrorResponse;
import com.hunganh.mymoments.service.AuthService;
import com.hunganh.mymoments.service.PostService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author: Tran Tuan Anh
 * @Created: Mon, 12/04/2021 3:47 PM
 **/

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping(value = "/api/posts", produces = "application/json")
public class PostController {
    private final PostService postService;
    private final AuthService authService;

    @PostMapping
    public ResponseEntity addPost(@RequestBody String data) {
        User user = authService.getCurrentUser();
        try {
            Map<String, Object> result = postService.addPost(data, user);
            if (result != null && result.size() != 0) {
                String offlineId = (String) result.get(InputParam.OFFLINE_ID);
                Long id = (Long) result.get(InputParam.ID);
                return new ResponseEntity(new SnwAddResponse(offlineId, String.valueOf(id)), HttpStatus.CREATED);
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return new ResponseEntity(new SnwErrorResponse(ResponseConstant.CAN_NOT_ADD), HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{postId}")
    public void updatePost(@RequestBody String data, @PathVariable long postId) {
    }

    @GetMapping("/{postId}")
    public void getPost(@PathVariable long postId) {
    }

    @GetMapping("/by_user/{userId}")
    public void getPostByUser(@PathVariable long userId) {
    }

    @GetMapping("/by_tag/{tagId}")
    public void getPostByTag(@PathVariable long tagId) {
    }

    @GetMapping("/by_hashtag/{hashtagId}")
    public void getPostByHashtag(@PathVariable long hashtagId) {
    }

    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable long postId) {
    }

    @PostMapping("/hide/{postId}")
    public void hidePost(@PathVariable long postId) {
    }

    @PostMapping("/unhide/{postId}")
    public void unhidePost(@PathVariable long postId) {
    }
}