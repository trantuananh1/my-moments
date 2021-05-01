package com.hunganh.mymoments.controller;

import com.hunganh.mymoments.constant.InputParam;
import com.hunganh.mymoments.constant.ResponseConstant;
import com.hunganh.mymoments.response.SnwErrorResponse;
import com.hunganh.mymoments.response.SnwSuccessResponse;
import com.hunganh.mymoments.service.UserService;
import com.hunganh.mymoments.util.TemplateUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author: Tran Tuan Anh
 * @Created: Tue, 13/04/2021 9:54 AM
 **/

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping(value = "/api/users", produces = "application/json")
public class UserController {
    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity getUser(@PathVariable("userId") long userId,
                                  @RequestParam(value = "type", defaultValue = InputParam.DEFAULT) String type) {
        try {
            Map<String, Object> result = userService.getUser(userId, type);
            return new ResponseEntity(TemplateUtil.generateJson(result), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity(new SnwErrorResponse(ResponseConstant.CAN_NOT_GET), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity updateUser(@RequestBody String data) {
        try {
            userService.updateUser(data);
            return new ResponseEntity(new SnwSuccessResponse(), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity(new SnwErrorResponse(ResponseConstant.CAN_NOT_UPDATE), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity deleteUser(@PathVariable("userId") long userId) {
        try {
            userService.deleteUser(userId);
            return new ResponseEntity(new SnwSuccessResponse(), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity(new SnwErrorResponse(ResponseConstant.CAN_NOT_DELETE), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/follow/{toId}")
    public ResponseEntity followUser(@PathVariable("toId") long toId) {
        try{
            userService.followUser(toId);
            return new ResponseEntity(new SnwSuccessResponse(), HttpStatus.OK);
        }catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity(new SnwErrorResponse(ResponseConstant.CAN_NOT_FOLLOW), HttpStatus.OK);
        }
    }

    @PostMapping("/unfollow/{toId}")
    public ResponseEntity unfollowUser(@PathVariable("toId") long toId) {
        try{
            userService.unfollowUser(toId);
            return new ResponseEntity(new SnwSuccessResponse(), HttpStatus.OK);
        }catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity(new SnwErrorResponse(ResponseConstant.CAN_NOT_UNFOLLOW), HttpStatus.OK);
        }
    }

    @GetMapping("/{userId}/followers")
    public ResponseEntity getFollowers(@PathVariable("userId") long userId){
        try{
            Map<String, Object> result = userService.getFollowers(userId);
            if (result.size() == 0){
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(TemplateUtil.generateJson(result), HttpStatus.OK);
        }catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity(new SnwErrorResponse(ResponseConstant.CAN_NOT_GET), HttpStatus.OK);
        }
    }
    @GetMapping("/{userId}/followings")
    public ResponseEntity getFollowings(@PathVariable("userId") long userId){
        try{
            Map<String, Object> result = userService.getFollowings(userId);
            if (result.size() == 0){
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(TemplateUtil.generateJson(result), HttpStatus.OK);
        }catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity(new SnwErrorResponse(ResponseConstant.CAN_NOT_GET), HttpStatus.OK);
        }
    }

}
