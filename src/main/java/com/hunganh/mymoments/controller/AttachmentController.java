package com.hunganh.mymoments.controller;

import com.hunganh.mymoments.constant.InputParam;
import com.hunganh.mymoments.constant.ResponseConstant;
import com.hunganh.mymoments.response.SnwAddResponse;
import com.hunganh.mymoments.response.SnwErrorResponse;
import com.hunganh.mymoments.response.SnwSuccessResponse;
import com.hunganh.mymoments.service.AttachmentService;
import com.hunganh.mymoments.util.TemplateUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author: Tran Tuan Anh
 * @Created: Sun, 18/04/2021 12:56 AM
 **/

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping(value = "/api/attachments", produces = "application/json")
public class AttachmentController {
    private final AttachmentService attachmentService;
    @PostMapping
    public ResponseEntity createAttachment(@RequestBody String data) {
        try {
            Map<String, Object> result = attachmentService.createAttachment(data);
            if (result != null && result.size() != 0) {
                String offlineId = (String) result.get(InputParam.OFFLINE_ID);
                Long id = (Long) result.get(InputParam.ID);
                return new ResponseEntity(new SnwAddResponse(offlineId, String.valueOf(id)), HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity(new SnwErrorResponse(ResponseConstant.CAN_NOT_ADD), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/{attachmentId}")
    public ResponseEntity getAttachment(@PathVariable("attachmentId") long attachmentId){
        try{
            Map<String, Object> result=attachmentService.getAttachment(attachmentId);
            if (result!=null || result.size() !=0) {
                return new ResponseEntity(TemplateUtil.generateJson(result), HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity(new SnwErrorResponse(ResponseConstant.CAN_NOT_GET), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/by_user/{userId}")
    public ResponseEntity getAttachments(@PathVariable("userId") long userId){
        try{
            Map<String, Object> result=attachmentService.getAttachments(userId);
            if (result!=null || result.size() !=0) {
                return new ResponseEntity(TemplateUtil.generateJson(result), HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity(new SnwErrorResponse(ResponseConstant.CAN_NOT_GET), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{attachmentId}")
    public ResponseEntity deleteAttachment(@PathVariable("attachmentId")long attachmentId){
        try{
            attachmentService.deleteAttachment(attachmentId);
            return new ResponseEntity(new SnwSuccessResponse(), HttpStatus.OK);
        }catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity(new SnwErrorResponse(ResponseConstant.CAN_NOT_DELETE), HttpStatus.BAD_REQUEST);
        }
    }
}
