package com.noveogroup.java.validator;

/**
 * @author artem ryzhikov
 */
public class ValidateException extends Exception {
    public ValidateException() {
    }
    public ValidateException(String message) {
        super(message);
    }
}
