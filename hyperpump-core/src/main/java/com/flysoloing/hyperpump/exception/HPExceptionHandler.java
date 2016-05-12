package com.flysoloing.hyperpump.exception;

/**
 * @author laitao
 * @since 2016-05-13 00:55:13
 */
public class HPExceptionHandler {

    public static void handleException(Exception exception) {
        throw new HPException(exception);
    }
}
