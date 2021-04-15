package com.hunganh.mymoments.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @Author: Tran Tuan Anh
 * @Created: Mon, 05/04/2021 4:02 AM
 **/
@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class EmailNotExistsException extends RuntimeException{
    public EmailNotExistsException(String message) {
        super(message);
    }
}
