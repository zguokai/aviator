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


    public AviatorObject match(AviatorObject other) {
        throw new ExpressionRuntimeException(toString() + " doesn't support match operation '=~'");
    }


    public AviatorObject neg() {
        throw new ExpressionRuntimeException(toString() + " doesn't support negative operation '-'");
    }


    public AviatorObject not() {
        throw new ExpressionRuntimeException(toString() + " doesn't support not operation '!'");
    }


    @Override
    public String toString() {
        return this.getAviatorType() + "(" + this.getValue() + ")";
    }


    public abstract Object getValue();


    public AviatorObject add(AviatorObject other) {
        throw new ExpressionRuntimeException("Could not add " + this + " with " + other);
    }


    public AviatorObject sub(AviatorObject other) {
        throw new ExpressionRuntimeException("Could not sub " + this + " with " + other);
    }


    public AviatorObject mod(AviatorObject other) {
        throw new ExpressionRuntimeException("Could not mod " + this + " with " + other);
    }


    public AviatorObject div(AviatorObject other) {
        throw new ExpressionRuntimeException("Could not div " + this + " with " + other);
    }


    public AviatorObject mult(AviatorObject other) {
        throw new ExpressionRuntimeException("Could not mult " + this + " with " + other);
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
