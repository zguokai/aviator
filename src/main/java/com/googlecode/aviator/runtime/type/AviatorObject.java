package com.googlecode.aviator.runtime.type;

import com.googlecode.aviator.exception.ExpressionRuntimeException;


/**
 * Aviator root object
 * 
 * @author dennis
 * 
 */
public abstract class AviatorObject {
    public abstract int compare(AviatorObject other);


    public abstract AviatorType getAviatorType();


    public abstract Object getValue();


    public AviatorObject add(AviatorObject other) {
        throw new ExpressionRuntimeException("Could not add " + this.getAviatorType() + " with "
                + other.getAviatorType());
    }


    public AviatorObject sub(AviatorObject other) {
        throw new ExpressionRuntimeException("Could not sub " + this.getAviatorType() + " with "
                + other.getAviatorType());
    }


    public AviatorObject mod(AviatorObject other) {
        throw new ExpressionRuntimeException("Could not mod " + this.getAviatorType() + " with "
                + other.getAviatorType());
    }


    public AviatorObject div(AviatorObject other) {
        throw new ExpressionRuntimeException("Could not div " + this.getAviatorType() + " with "
                + other.getAviatorType());
    }


    public AviatorObject mult(AviatorObject other) {
        throw new ExpressionRuntimeException("Could not mult " + this.getAviatorType() + " with "
                + other.getAviatorType());
    }


    public Number numberValue() {
        return ((AviatorNumber) this).number;
    }


    public String stringValue() {
        return ((AviatorString) this).lexeme;
    }


    public boolean booleanValue() {
        return ((AviatorBoolean) this).value;
    }
}
