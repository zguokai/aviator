package com.googlecode.aviator.runtime.type;

import java.util.Map;

import com.googlecode.aviator.exception.ExpressionRuntimeException;


/**
 * Aviator root object
 * 
 * @author dennis
 * 
 */
public abstract class AviatorObject {
    public abstract int compare(AviatorObject other, Map<String, Object> env);


    public abstract AviatorType getAviatorType();


    public AviatorObject match(AviatorObject other, Map<String, Object> env) {
        throw new ExpressionRuntimeException(desc(env) + " doesn't support match operation '=~'");
    }


    public AviatorObject neg(Map<String, Object> env) {
        throw new ExpressionRuntimeException(desc(env) + " doesn't support negative operation '-'");
    }


    public AviatorObject not(Map<String, Object> env) {
        throw new ExpressionRuntimeException(desc(env) + " doesn't support not operation '!'");
    }


    public String desc(Map<String, Object> env) {
        return this.getAviatorType() + "(" + this.getValue(env) + ")";
    }


    public abstract Object getValue(Map<String, Object> env);


    public AviatorObject add(AviatorObject other, Map<String, Object> env) {
        throw new ExpressionRuntimeException("Could not add " + this.desc(env) + " with " + other.desc(env));
    }


    public AviatorObject sub(AviatorObject other, Map<String, Object> env) {
        throw new ExpressionRuntimeException("Could not sub " + this.desc(env) + " with " + other.desc(env));
    }


    public AviatorObject mod(AviatorObject other, Map<String, Object> env) {
        throw new ExpressionRuntimeException("Could not mod " + this.desc(env) + " with " + other.desc(env));
    }


    public AviatorObject div(AviatorObject other, Map<String, Object> env) {
        throw new ExpressionRuntimeException("Could not div " + this.desc(env) + " with " + other.desc(env));
    }


    public AviatorObject mult(AviatorObject other, Map<String, Object> env) {
        throw new ExpressionRuntimeException("Could not mult " + this.desc(env) + " with " + other.desc(env));
    }


    public Number numberValue(Map<String, Object> env) {
        if (!(this.getValue(env) instanceof Number)) {
            throw new ExpressionRuntimeException(this.desc(env) + " is not a number value");
        }
        return (Number) this.getValue(env);
    }


    public String stringValue(Map<String, Object> env) {
        if (!(this.getValue(env) instanceof String) && !(this.getValue(env) instanceof Character)) {
            throw new ExpressionRuntimeException(this.desc(env) + " is not a string value");
        }
        return String.valueOf(this.getValue(env));
    }


    public boolean booleanValue(Map<String, Object> env) {
        if (!(this.getValue(env) instanceof Boolean)) {
            throw new ExpressionRuntimeException(this.desc(env) + " is not a boolean value");
        }
        return (Boolean) this.getValue(env);
    }
}
