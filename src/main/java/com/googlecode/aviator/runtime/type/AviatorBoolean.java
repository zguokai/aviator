package com.googlecode.aviator.runtime.type;

import com.googlecode.aviator.exception.ExpressionRuntimeException;


public class AviatorBoolean extends AviatorObject {

    Boolean value;

    public static final AviatorBoolean TRUE = new AviatorBoolean(Boolean.TRUE);

    public static final AviatorBoolean FALSE = new AviatorBoolean(Boolean.FALSE);


    @Override
    public AviatorObject add(AviatorObject other) {
        switch (other.getAviatorType()) {
        case String:
            return new AviatorString(this.value.toString() + ((AviatorString) other).lexeme);
        case JavaType:
            AviatorJavaType javaType = (AviatorJavaType) other;
            if (javaType.getObject() instanceof String || javaType.getObject() instanceof Character) {
                return new AviatorString(this.value.toString() + javaType.getObject().toString());
            }
            else {
                return super.add(other);
            }
        default:
            return super.add(other);
        }

    }


    @Override
    public AviatorType getAviatorType() {
        return AviatorType.Boolean;
    }


    @Override
    public Object getValue() {
        return this.value;
    }


    public AviatorBoolean(Boolean value) {
        super();
        this.value = value;
    }


    @Override
    public int compare(AviatorObject other) {
        switch (other.getAviatorType()) {
        case Boolean:
            AviatorBoolean otherBoolean = (AviatorBoolean) other;
            return this.value.compareTo(otherBoolean.value);
        case JavaType:
            AviatorJavaType javaType = (AviatorJavaType) other;
            if (javaType.getObject() instanceof Boolean) {
                return this.value.compareTo((Boolean) javaType.getObject());
            }
            else {
                throw new ExpressionRuntimeException("Could not compare " + this + " with " + other);
            }
        default:
            throw new ExpressionRuntimeException("Could not compare " + this + " with " + other);
        }

    }

}
