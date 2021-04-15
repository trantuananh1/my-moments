package com.hunganh.mymoments.response;

import lombok.Data;

/**
 * @Author: Tran Tuan Anh
 * @Created: Wed, 31/03/2021 11:25 PM
 **/

@Data
public class SnwErrorResponse {
    private String error;
    public SnwErrorResponse(String message){
        this.error=message;
    }
}
