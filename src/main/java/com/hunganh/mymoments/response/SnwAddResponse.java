package com.hunganh.mymoments.response;

import lombok.Data;

import java.util.Date;

/**
 * @Author: Tran Tuan Anh
 * @Created: Wed, 31/03/2021 11:27 PM
 **/

@Data
public class SnwAddResponse {
    private String offlineId;
    private String id;
    private long createdTime=new Date().getTime();

    public SnwAddResponse(String offlineId, String id){
        this.offlineId=offlineId;
        this.id=id;
    }
}
