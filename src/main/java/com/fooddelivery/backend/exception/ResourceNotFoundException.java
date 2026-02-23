package com.fooddelivery.backend.exception;

public class ResourceNotFoundException extends RuntimeException {

<<<<<<< HEAD
    private String resourceName;
    private String fieldName;
    private Object fieldValue;

    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : '%s'", 
                resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
=======
    public ResourceNotFoundException(String message) {
        super(message);
>>>>>>> 8c577e4e324fbcf8576365bd773e212da08a34c7
    }
}