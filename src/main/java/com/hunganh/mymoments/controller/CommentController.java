package com.hunganh.mymoments.controller;

import com.hunganh.mymoments.constant.InputParam;
import com.hunganh.mymoments.constant.ResponseConstant;
import com.hunganh.mymoments.response.SnwAddResponse;
import com.hunganh.mymoments.response.SnwErrorResponse;
import com.hunganh.mymoments.response.SnwSuccessResponse;
import com.hunganh.mymoments.service.CommentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author: Tran Tuan Anh
 * @Created: Sat, 01/05/2021 4:36 PM
 **/

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping(value = "/api", produces = "application/json")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{parentId}/comment")
    public ResponseEntity addComment(@PathVariable long parentId,
                                     @RequestBody String data) {
        try {
            Map<String, Object> result = commentService.addComment(parentId, data);
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

    @GetMapping("/{parentId}/comments")
    public void getComments(@PathVariable long parentId) {
    }

    @PutMapping("/comment/{commentId}")
    public ResponseEntity updateComment(@RequestBody String data,
                              @PathVariable long commentId) {
        try {
            boolean result = commentService.updateComment(commentId, data);
            if (result) {
                return new ResponseEntity(new SnwSuccessResponse(), HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new ResponseEntity(new SnwErrorResponse(ResponseConstant.CAN_NOT_UPDATE), HttpStatus.BAD_REQUEST);
    }


    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable long commentId) {
    }

    @PostMapping("/hide/{commentId}")
    public void hidePost(@PathVariable long commentId) {
    }

    @PostMapping("/unhide/{commentId}")
    public void unhidePost(@PathVariable long commentId) {
    }
}
