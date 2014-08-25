package com.noveogroup.java.validator;

/**
 * @author artem ryzhikov
 */
public class ValidateException extends Exception {
    private final String fieldName;
    private final Object fieldValue;

    public ValidateException(final String message , final String fieldName , final Object fieldValue) {
        super(message);
        this.fieldValue = fieldValue;
        this.fieldName = fieldName;
    }
    public String getFieldName() {
        return this.fieldName;
    }
    public Object getFieldValue() {
        return fieldValue;
    }
    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }
}
