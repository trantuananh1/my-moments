package com.hunganh.mymoments.response;

import lombok.Data;

/**
 * @Author: Tran Tuan Anh
 * @Created: Wed, 31/03/2021 12:16 PM
 **/

@Data
public class SnwSuccessResponse {
    private boolean success;
   public SnwSuccessResponse(){
       this.success=true;
   }
}
