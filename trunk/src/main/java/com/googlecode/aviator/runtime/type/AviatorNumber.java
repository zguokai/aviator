package com.googlecode.aviator.runtime.type;

import com.googlecode.aviator.exception.ExpressionRuntimeException;


/**
 * Aviator number type
 * 
 * @author dennis
 * 
 */
public abstract class AviatorNumber extends AviatorObject {
    protected Number number;


    public AviatorNumber(Number number) {
        super();
        this.number = number;
    }


    @Override
    public Object getValue() {
        return this.number;
    }


    public abstract AviatorObject neg();


    public static AviatorNumber valueOf(Object value) {
        if (value instanceof Long || value instanceof Byte || value instanceof Short || value instanceof Integer) {
            return new AviatorLong(((Number) value).longValue());

        }
        else if (value instanceof Double || value instanceof Float) {
            return new AviatorDouble(((Number) value).doubleValue());
        }
        else {
            throw new ClassCastException("Could not cast " + value.getClass().getName() + " to Number");
        }

    }


    public double doubleValue() {
        return number.doubleValue();
    }


    @Override
    public AviatorObject add(AviatorObject other) {
        switch (other.getAviatorType()) {
        case String:
            return new AviatorString(this.number.toString() + ((AviatorString) other).getLexeme());
        case Number:
            return innerAdd((AviatorNumber) other);
        case JavaType:
            AviatorJavaType otherJavaType = (AviatorJavaType) other;
            // 交换率
            return otherJavaType.add(this);
        default:
            return super.add(other);
        }

    }


    @Override
    public AviatorObject sub(AviatorObject other) {
        switch (other.getAviatorType()) {
        case Number:
            return innerSub(other);
        case JavaType:
            AviatorJavaType otherJavaType = (AviatorJavaType) other;
            if (otherJavaType.object instanceof Number) {
                return innerSub(AviatorNumber.valueOf(otherJavaType.object));
            }
            else {
                return super.sub(other);
            }
        default:
            return super.sub(other);
        }

    }


    @Override
    public AviatorObject mod(AviatorObject other) {
        switch (other.getAviatorType()) {
        case Number:
            return innerMod(other);
        case JavaType:
            AviatorJavaType otherJavaType = (AviatorJavaType) other;
            if (otherJavaType.object instanceof Number) {
                return innerMod(AviatorNumber.valueOf(otherJavaType.object));
            }
            else {
                return super.mod(other);
            }
        default:
            return super.mod(other);
        }
    }


    @Override
    public AviatorObject div(AviatorObject other) {
        switch (other.getAviatorType()) {
        case Number:
            return innerDiv(other);
        case JavaType:
            AviatorJavaType otherJavaType = (AviatorJavaType) other;
            if (otherJavaType.object instanceof Number) {
                return innerDiv(AviatorNumber.valueOf(otherJavaType.object));
            }
            else {
                return super.div(other);
            }
        default:
            return super.div(other);
        }

    }


    @Override
    public AviatorObject mult(AviatorObject other) {
        switch (other.getAviatorType()) {
        case Number:
            return innerMult(other);
        case JavaType:
            AviatorJavaType otherJavaType = (AviatorJavaType) other;
            if (otherJavaType.object instanceof Number) {
                return innerMult(AviatorNumber.valueOf(otherJavaType.object));
            }
            else {
                return super.mult(other);
            }
        default:
            return super.mult(other);
        }

    }


    @Override
    public int compare(AviatorObject other) {
        switch (other.getAviatorType()) {
        case Number:
            return innerCompare(other);
        case JavaType:
            AviatorJavaType otherJavaType = (AviatorJavaType) other;
            if (otherJavaType.object instanceof Number) {
                return innerCompare(AviatorNumber.valueOf(otherJavaType.object));
            }
            else {
                throw new ExpressionRuntimeException("Could not compare " + this + " with " + other);
            }
        default:
            throw new ExpressionRuntimeException("Could not compare " + this + " with " + other);

        }
    }


    public abstract AviatorObject innerSub(AviatorObject other);


    public abstract AviatorObject innerMult(AviatorObject other);


    public abstract AviatorObject innerMod(AviatorObject other);


    public abstract AviatorObject innerDiv(AviatorObject other);


    public abstract AviatorNumber innerAdd(AviatorNumber other);


    public abstract int innerCompare(AviatorObject other);


    @Override
    public AviatorType getAviatorType() {
        return AviatorType.Number;
    }


    public long longValue() {
        return number.longValue();
    }


    protected void ensureNumber(AviatorObject other) {
        if (other.getAviatorType() != AviatorType.Number) {
            throw new ExpressionRuntimeException("Operator only supports Number");
        }
    }
}
