package com.googlecode.aviator.exception;

/**
 * Expression syntax exception
 * 
 * @author dennis
 * 
 */
public class ExpressionSyntaxErrorException extends RuntimeException {
    static final long serialVersionUID = -1;


    public ExpressionSyntaxErrorException() {
        super();

    }


    public ExpressionSyntaxErrorException(String message, Throwable cause) {
        super(message, cause);

    }


    public ExpressionSyntaxErrorException(String message) {
        super(message);

    }


    public ExpressionSyntaxErrorException(Throwable cause) {
        super(cause);

    }

}
