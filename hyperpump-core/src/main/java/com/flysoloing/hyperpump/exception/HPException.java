package com.flysoloing.hyperpump.exception;

/**
 * @author laitao
 * @date 2016-05-13 00:57:56
 */
public class HPException extends RuntimeException {

    public HPException(Exception exception) {
        super(exception);
    }
}
