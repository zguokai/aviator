package com.googlecode.aviator.runtime.type;

import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

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


    public AviatorJavaType(Map<String, Object> env, String name) {
        super();
        try {
            this.object = PropertyUtils.getProperty(env, name);
        }
        catch (Exception e) {
            throw new ExpressionRuntimeException("Could not get variable " + name + " value", e);
        }
    }


    public Object getObject() {
        return object;
    }


    @Override
    public AviatorObject div(AviatorObject other) {
        switch (other.getAviatorType()) {
        case Number:
            if (this.object instanceof Number) {
                AviatorNumber aviatorNumber = AviatorNumber.valueOf(this.object);
                return aviatorNumber.div(other);
            }
            else {
                return super.div(other);
            }
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
            if (this.object instanceof Number) {
                AviatorNumber aviatorNumber = AviatorNumber.valueOf(this.object);
                return aviatorNumber.mod(other);
            }
            else {
                return super.mod(other);
            }
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
            if (this.object instanceof Number) {
                AviatorNumber aviatorNumber = AviatorNumber.valueOf(this.object);
                return aviatorNumber.sub(other);
            }
            else {
                return super.sub(other);
            }
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
            AviatorNumber aviatorNumber = (AviatorNumber) other;
            return -aviatorNumber.compare(this);
        case String:
            AviatorString aviatorString = (AviatorString) other;
            return -aviatorString.compare(this);
        case Boolean:
            AviatorBoolean aviatorBoolean = (AviatorBoolean) other;
            return -aviatorBoolean.compare(this);
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
                else if (this.object instanceof Character) {
                    AviatorString thisAviatorString = new AviatorString(String.valueOf(object));
                    return thisAviatorString.compare(other);
                }
                else if (this.object instanceof Boolean) {
                    AviatorBoolean thisAviatorBoolean = new AviatorBoolean((Boolean) this.object);
                    return thisAviatorBoolean.compare(other);
                }
                else {
                    try {
                        return ((Comparable) object).compareTo(otherJavaType.object);
                    }
                    catch (Throwable t) {
                        throw new ExpressionRuntimeException("Compare " + this + " with " + other + " error", t);
                    }
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
            if (this.object instanceof Number) {
                AviatorNumber aviatorNumber = AviatorNumber.valueOf(this.object);
                return aviatorNumber.mult(other);
            }
            else {
                return super.mult(other);
            }
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
    public AviatorObject neg() {
        if (this.object instanceof Number) {
            return AviatorNumber.valueOf(this.object).neg();
        }
        else {
            return super.neg();
        }
    }


    @Override
    public AviatorObject not() {
        if (this.object instanceof Boolean) {
            return new AviatorBoolean((Boolean) this.object).not();
        }
        else {
            return super.not();
        }
    }


    @Override
    public AviatorObject add(AviatorObject other) {
        if (this.object instanceof String) {
            AviatorString aviatorString = new AviatorString((String) object);
            return aviatorString.add(other);
        }
        else if (this.object instanceof Character) {
            AviatorString aviatorString = new AviatorString(String.valueOf(object));
            return aviatorString.add(other);
        }
        else if (this.object instanceof Number) {
            AviatorNumber aviatorNumber = AviatorNumber.valueOf(object);
            return aviatorNumber.add(other);
        }
        else if (this.object instanceof Boolean) {
            return new AviatorBoolean((Boolean) object).add(other);
        }
        else {
            return super.add(other);
        }
    }

}
