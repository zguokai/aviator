package com.googlecode.aviator.runtime.type;

import java.util.Map;

import com.googlecode.aviator.exception.ExpressionRuntimeException;


public class AviatorBoolean extends AviatorObject {

    Boolean value;

    public static final AviatorBoolean TRUE = new AviatorBoolean(Boolean.TRUE);

    public static final AviatorBoolean FALSE = new AviatorBoolean(Boolean.FALSE);


    @Override
    public AviatorObject not(Map<String, Object> env) {
        return this.value.booleanValue() ? FALSE : TRUE;
    }


    @Override
    public AviatorObject add(AviatorObject other, Map<String, Object> env) {
        switch (other.getAviatorType()) {
        case String:
            return new AviatorString(this.value.toString() + ((AviatorString) other).lexeme);
        case JavaType:
            AviatorJavaType javaType = (AviatorJavaType) other;
            final Object otherJavaValue = javaType.getValue(env);
            if (otherJavaValue instanceof String || otherJavaValue instanceof Character) {
                return new AviatorString(this.value.toString() + otherJavaValue.toString());
            }
            else {
                return super.add(other, env);
            }
        default:
            return super.add(other, env);
        }

    }


    @Override
    public AviatorType getAviatorType() {
        return AviatorType.Boolean;
    }


    @Override
    public Object getValue(Map<String, Object> env) {
        return this.value;
    }


    public AviatorBoolean(Boolean value) {
        super();
        this.value = value;
    }


    @Override
    public int compare(AviatorObject other, Map<String, Object> env) {
        switch (other.getAviatorType()) {
        case Boolean:
            AviatorBoolean otherBoolean = (AviatorBoolean) other;
            return this.value.compareTo(otherBoolean.value);
        case JavaType:
            AviatorJavaType javaType = (AviatorJavaType) other;
            final Object otherValue = javaType.getValue(env);
            if (otherValue instanceof Boolean) {
                return this.value.compareTo((Boolean) otherValue);
            }
            else {
                throw new ExpressionRuntimeException("Could not compare " + desc(env) + " with " + other.desc(env));
            }
        default:
            throw new ExpressionRuntimeException("Could not compare " + this.desc(env) + " with " + other.desc(env));
        }

    }

}
