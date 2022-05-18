package com.westjet.profilemanagement.exception;

public class InvalidInputXmlException extends Exception {

    public InvalidInputXmlException() {
        super();
    }

    public InvalidInputXmlException(String message) {
        super(message);
    }

    public InvalidInputXmlException(String message, Throwable ex) {
        super(message, ex);

    }
}
