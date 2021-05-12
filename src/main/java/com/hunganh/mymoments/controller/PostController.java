package com.hunganh.mymoments.controller;

import com.hunganh.mymoments.constant.BaseConstant;
import com.hunganh.mymoments.constant.InputParam;
import com.hunganh.mymoments.constant.ResponseConstant;
import com.hunganh.mymoments.response.SnwAddResponse;
import com.hunganh.mymoments.response.SnwErrorResponse;
import com.hunganh.mymoments.response.SnwSuccessResponse;
import com.hunganh.mymoments.service.AuthService;
import com.hunganh.mymoments.service.PostService;
import com.hunganh.mymoments.util.TemplateUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;


/**
 * @Author: Tran Tuan Anh
 * @Created: Mon, 12/04/2021 3:47 PM
 **/

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping(value = "/api", produces = "application/json")
public class PostController {
    private final PostService postService;
    private final AuthService authService;

    @PostMapping("/{parentId}/post")
    public ResponseEntity createPost(@RequestBody String data,
                                     @PathVariable long parentId) {
        try {
            Map<String, Object> result = postService.addPost(parentId, data);
            if (result != null && result.size() != 0) {
                String offlineId = (String) result.get(InputParam.OFFLINE_ID);
                Long id = (Long) result.get(InputParam.ID);
                return new ResponseEntity(new SnwAddResponse(offlineId, String.valueOf(id)), HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity(new SnwErrorResponse(ResponseConstant.CAN_NOT_ADD), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/post/{postId}")
    public ResponseEntity updatePost(@RequestBody String data, @PathVariable long postId) {
        try {
            boolean result = postService.updatePost(postId, data);
            if (result) {
                return new ResponseEntity(new SnwSuccessResponse(), HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new ResponseEntity(new SnwErrorResponse(ResponseConstant.CAN_NOT_UPDATE), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{parentId}/posts")
    public ResponseEntity getPosts(@PathVariable long parentId,
                                   @RequestParam(defaultValue = BaseConstant.MIN_SCORE_DEFAULT + "") long minScore,
                                   @RequestParam(defaultValue = BaseConstant.MAX_SCORE_DEFAULT + "") long maxScore,
                                   @RequestParam(defaultValue = BaseConstant.LIMIT_DEFAULT + "") int limit) {
        try {
            Map<String, Object> result = postService.getPosts(parentId, minScore, maxScore, limit);
            if (result!=null && result.size()!=0){
                return new ResponseEntity(TemplateUtil.generateJson(result), HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new ResponseEntity(new SnwErrorResponse(ResponseConstant.CAN_NOT_GET), BAD_REQUEST);
    }

    @DeleteMapping("/post/{postId}")
    public ResponseEntity deletePost(@PathVariable long postId) {
        try {
            postService.deletePost(postId);
            return new ResponseEntity(new SnwSuccessResponse(), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new ResponseEntity(new SnwErrorResponse(ResponseConstant.CAN_NOT_DELETE), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/hide/{postId}")
    public void hidePost(@PathVariable long postId) {
    }

    @PostMapping("/unhide/{postId}")
    public void unhidePost(@PathVariable long postId) {
    }
}