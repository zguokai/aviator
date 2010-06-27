package com.googlecode.aviator.runtime.type;

import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

import com.googlecode.aviator.exception.ExpressionRuntimeException;


/*
 * Aviator variable
 */
public class AviatorJavaType extends AviatorObject {

    final private String name;


    @Override
    public AviatorType getAviatorType() {
        return AviatorType.JavaType;
    }


    public AviatorJavaType(String name) {
        super();
        this.name = name;
    }


    @Override
    public AviatorObject div(AviatorObject other, Map<String, Object> env) {
        final Object value = getValue(env);
        switch (other.getAviatorType()) {
        case Number:
            if (value instanceof Number) {
                AviatorNumber aviatorNumber = AviatorNumber.valueOf(value);
                return aviatorNumber.div(other, env);
            }
            else {
                return super.div(other, env);
            }
        case JavaType:
            if (value instanceof Number) {
                AviatorNumber thisAviatorNumber = AviatorNumber.valueOf(value);
                return thisAviatorNumber.div(other, env);
            }
            else {
                return super.div(other, env);
            }
        default:
            return super.div(other, env);
        }
    }


    @Override
    public Object getValue(Map<String, Object> env) {
        try {
            if (env != null) {
                return PropertyUtils.getProperty(env, name);
            }
            return null;
        }
        catch (Throwable t) {
            throw new ExpressionRuntimeException("Could not find variable " + name, t);
        }
    }


    @Override
    public AviatorObject mod(AviatorObject other, Map<String, Object> env) {
        final Object value = getValue(env);
        switch (other.getAviatorType()) {
        case Number:
            if (value instanceof Number) {
                AviatorNumber aviatorNumber = AviatorNumber.valueOf(value);
                return aviatorNumber.mod(other, env);
            }
            else {
                return super.mod(other, env);
            }
        case JavaType:
            if (value instanceof Number) {
                AviatorNumber thisAviatorNumber = AviatorNumber.valueOf(value);
                return thisAviatorNumber.mod(other, env);
            }
            else {
                return super.mod(other, env);
            }
        default:
            return super.mod(other, env);
        }
    }


    @Override
    public AviatorObject sub(AviatorObject other, Map<String, Object> env) {
        final Object value = getValue(env);
        switch (other.getAviatorType()) {
        case Number:
            if (value instanceof Number) {
                AviatorNumber aviatorNumber = AviatorNumber.valueOf(value);
                return aviatorNumber.sub(other, env);
            }
            else {
                return super.sub(other, env);
            }
        case JavaType:
            if (value instanceof Number) {
                AviatorNumber thisAviatorNumber = AviatorNumber.valueOf(value);
                return thisAviatorNumber.sub(other, env);
            }
            else {
                return super.sub(other, env);
            }
        default:
            return super.sub(other, env);
        }
    }


    @Override
    @SuppressWarnings("unchecked")
    public int compare(AviatorObject other, Map<String, Object> env) {
        switch (other.getAviatorType()) {
        case Number:
            AviatorNumber aviatorNumber = (AviatorNumber) other;
            return -aviatorNumber.compare(this, env);
        case String:
            AviatorString aviatorString = (AviatorString) other;
            return -aviatorString.compare(this, env);
        case Boolean:
            AviatorBoolean aviatorBoolean = (AviatorBoolean) other;
            return -aviatorBoolean.compare(this, env);
        case JavaType:
            AviatorJavaType otherJavaType = (AviatorJavaType) other;
            final Object value = getValue(env);
            if (value.equals(otherJavaType.getValue(env))) {
                return 0;
            }
            else {
                if (value instanceof Number) {
                    AviatorNumber thisAviatorNumber = AviatorNumber.valueOf(value);
                    return thisAviatorNumber.compare(other, env);
                }
                else if (value instanceof String) {
                    AviatorString thisAviatorString = new AviatorString((String) value);
                    return thisAviatorString.compare(other, env);
                }
                else if (value instanceof Character) {
                    AviatorString thisAviatorString = new AviatorString(String.valueOf(value));
                    return thisAviatorString.compare(other, env);
                }
                else if (value instanceof Boolean) {
                    AviatorBoolean thisAviatorBoolean = new AviatorBoolean((Boolean) value);
                    return thisAviatorBoolean.compare(other, env);
                }
                else {
                    try {
                        return ((Comparable) value).compareTo(otherJavaType.getValue(env));
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
    public AviatorObject mult(AviatorObject other, Map<String, Object> env) {
        final Object value = getValue(env);
        switch (other.getAviatorType()) {
        case Number:
            if (value instanceof Number) {
                AviatorNumber aviatorNumber = AviatorNumber.valueOf(value);
                return aviatorNumber.mult(other, env);
            }
            else {
                return super.mult(other, env);
            }
        case JavaType:
            if (value instanceof Number) {
                AviatorNumber thisAviatorNumber = AviatorNumber.valueOf(value);
                return thisAviatorNumber.mult(other, env);
            }
            else {
                return super.mult(other, env);
            }
        default:
            return super.mult(other, env);
        }
    }


    @Override
    public AviatorObject neg(Map<String, Object> env) {
        final Object value = getValue(env);
        if (value instanceof Number) {
            return AviatorNumber.valueOf(value).neg(env);
        }
        else {
            return super.neg(env);
        }
    }


    @Override
    public AviatorObject not(Map<String, Object> env) {
        final Object value = getValue(env);
        if (value instanceof Boolean) {
            return new AviatorBoolean((Boolean) value).not(env);
        }
        else {
            return super.not(env);
        }
    }


    @Override
    public AviatorObject add(AviatorObject other, Map<String, Object> env) {
        final Object value = getValue(env);
        if (value instanceof String) {
            AviatorString aviatorString = new AviatorString((String) value);
            return aviatorString.add(other, env);
        }
        else if (value instanceof Character) {
            AviatorString aviatorString = new AviatorString(String.valueOf(value));
            return aviatorString.add(other, env);
        }
        else if (value instanceof Number) {
            AviatorNumber aviatorNumber = AviatorNumber.valueOf(value);
            return aviatorNumber.add(other, env);
        }
        else if (value instanceof Boolean) {
            return new AviatorBoolean((Boolean) value).add(other, env);
        }
        else {
            return super.add(other, env);
        }
    }

}
