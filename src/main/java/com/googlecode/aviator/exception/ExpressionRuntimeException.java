package com.googlecode.aviator.exception;

/**
 * Expression runtime exception
 * 
 * @author dennis
 * 
 */
public class ExpressionRuntimeException extends RuntimeException {
    static final long serialVersionUID = -1;


    public ExpressionRuntimeException() {
        super();

    }


    public ExpressionRuntimeException(String message, Throwable cause) {
        super(message, cause);

    }


    public ExpressionRuntimeException(String message) {
        super(message);

    }


    public ExpressionRuntimeException(Throwable cause) {
        super(cause);

    }

}
