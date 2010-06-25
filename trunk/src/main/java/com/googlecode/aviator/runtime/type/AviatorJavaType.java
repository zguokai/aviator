package com.googlecode.aviator.runtime.type;

import com.googlecode.aviator.exception.ExpressionRuntimeException;


/*
 * Aviator variable
 */
public class AviatorJavaType extends AviatorObject {
    protected final Object object;


    @Override
    public AviatorType getAviatorType() {
        return AviatorType.JavaType;
    }


    public AviatorJavaType(Object object) {
        super();
        this.object = object;
    }


    public Object getObject() {
        return object;
    }


    @Override
    public AviatorObject div(AviatorObject other) {
        switch (other.getAviatorType()) {
        case Number:
            AviatorNumber aviatorNumber = AviatorNumber.valueOf(this.object);
            return aviatorNumber.div(other);
        case JavaType:
            if (this.object instanceof Number) {
                AviatorNumber thisAviatorNumber = AviatorNumber.valueOf(object);
                return thisAviatorNumber.div(other);
            }
            else {
                return super.div(other);
            }
        default:
            return super.div(other);
        }
    }


    @Override
    public Object getValue() {
        return this.object;
    }


    @Override
    public AviatorObject mod(AviatorObject other) {
        switch (other.getAviatorType()) {
        case Number:
            AviatorNumber aviatorNumber = AviatorNumber.valueOf(this.object);
            return aviatorNumber.mod(other);
        case JavaType:
            if (this.object instanceof Number) {
                AviatorNumber thisAviatorNumber = AviatorNumber.valueOf(object);
                return thisAviatorNumber.mod(other);
            }
            else {
                return super.mod(other);
            }
        default:
            return super.mod(other);
        }
    }


    @Override
    public AviatorObject sub(AviatorObject other) {
        switch (other.getAviatorType()) {
        case Number:
            AviatorNumber aviatorNumber = AviatorNumber.valueOf(this.object);
            return aviatorNumber.sub(other);
        case JavaType:
            if (this.object instanceof Number) {
                AviatorNumber thisAviatorNumber = AviatorNumber.valueOf(object);
                return thisAviatorNumber.sub(other);
            }
            else {
                return super.sub(other);
            }
        default:
            return super.sub(other);
        }
    }


    @Override
    @SuppressWarnings("unchecked")
    public int compare(AviatorObject other) {
        switch (other.getAviatorType()) {
        case Number:
            AviatorNumber aviatorNumber = AviatorNumber.valueOf(this.object);
            return aviatorNumber.compare(other);
        case String:
            AviatorString aviatorString = new AviatorString((String) object);
            return aviatorString.compare(other);
        case Boolean:
            AviatorBoolean aviatorBoolean = new AviatorBoolean((Boolean) object);
            return aviatorBoolean.compare(other);
        case JavaType:
            AviatorJavaType otherJavaType = (AviatorJavaType) other;
            if (this.object.equals(otherJavaType.object)) {
                return 0;
            }
            else {
                if (this.object instanceof Number) {
                    AviatorNumber thisAviatorNumber = AviatorNumber.valueOf(object);
                    return thisAviatorNumber.compare(other);
                }
                else if (this.object instanceof String) {
                    AviatorString thisAviatorString = new AviatorString((String) object);
                    return thisAviatorString.compare(other);
                }
                else {
                    return ((Comparable) object).compareTo(otherJavaType);
                }
            }
        default:
            throw new ExpressionRuntimeException("Unknow aviator type");
        }
    }


    @Override
    public AviatorObject mult(AviatorObject other) {
        switch (other.getAviatorType()) {
        case Number:
            AviatorNumber aviatorNumber = AviatorNumber.valueOf(this.object);
            return aviatorNumber.mult(other);
        case JavaType:
            if (this.object instanceof Number) {
                AviatorNumber thisAviatorNumber = AviatorNumber.valueOf(object);
                return thisAviatorNumber.mult(other);
            }
            else {
                return super.mult(other);
            }
        default:
            return super.mult(other);
        }
    }


    @Override
    public AviatorObject add(AviatorObject other) {
        if (this.object instanceof String) {
            AviatorString aviatorString = new AviatorString((String) object);
            return aviatorString.add(other);
        }
        else if (this.object instanceof Number) {
            AviatorNumber aviatorNumber = AviatorNumber.valueOf(object);
            return aviatorNumber.add(other);
        }
        else {
            throw new ExpressionRuntimeException("'+' operator only supports String and Number");
        }
    }

}
