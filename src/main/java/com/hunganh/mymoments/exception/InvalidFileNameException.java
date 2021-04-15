package com.hunganh.mymoments.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @Author: Tran Tuan Anh
 * @Created: Mon, 22/03/2021 1:04 AM
 **/

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidFileNameException extends RuntimeException {

    public InvalidFileNameException(String message) {
        super(message);
    }

    public InvalidFileNameException(String message, Throwable cause) {
        super(message, cause);
    }
}