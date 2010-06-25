package com.googlecode.aviator.exception;

/**
 * Compile expression exception
 * 
 * @author dennis
 * 
 */
public class CompileExpressionErrorException extends RuntimeException {

	static final long serialVersionUID = -1;

	public CompileExpressionErrorException() {
		super();

	}

	public CompileExpressionErrorException(String message, Throwable cause) {
		super(message, cause);

	}

	public CompileExpressionErrorException(String message) {
		super(message);

	}

	public CompileExpressionErrorException(Throwable cause) {
		super(cause);

	}

}
