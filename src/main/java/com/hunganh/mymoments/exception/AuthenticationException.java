package com.hunganh.mymoments.exception;

/**
 * @Author: Tran Tuan Anh
 * @Created: Mon, 12/04/2021 12:53 PM
 **/

public class AuthenticationException extends RuntimeException {
    public AuthenticationException(String message) {
        super(message);
    }
}
