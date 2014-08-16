package com.noveogroup.java.validator;

/**
 * @author artem ryzhikov
 */
public class ValidateException extends Exception {
    private String fieldName = "";
    private Object fieldValue = "";

    public ValidateException(final String message , final String fieldName , final Object fieldValue) {
        super(message);
        this.fieldValue = fieldValue;
        this.fieldName = fieldName;
    }
    public String getFieldName() {
        return this.fieldName;
    }
    public void setFieldName(final String fieldName) {
        this.fieldName = fieldName;
    }
    public Object getFieldValue() {
        return fieldValue;
    }
    public void setFieldValue(final Object fieldValue) {
        this.fieldValue = fieldValue;
    }

}
