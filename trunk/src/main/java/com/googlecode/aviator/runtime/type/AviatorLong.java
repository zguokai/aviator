package com.googlecode.aviator.runtime.type;

import java.util.Map;

import com.googlecode.aviator.exception.ExpressionRuntimeException;


public class AviatorLong extends AviatorNumber {

    public AviatorLong(Number number) {
        super(number);

    }


    @Override
    public AviatorObject neg( Map<String, Object> env) {
        return new AviatorLong(-this.number.longValue());
    }


    @Override
    public int innerCompare(AviatorObject other) {
        ensureNumber(other);
        AviatorNumber otherNum = (AviatorNumber) other;
        if (other instanceof AviatorLong) {
            if (this.number.longValue() > otherNum.longValue()) {
                return 1;
            }
            else if (this.number.longValue() < otherNum.longValue()) {
                return -1;
            }
            else {
                return 0;
            }
        }
        else if (other instanceof AviatorDouble) {
            if (this.number.doubleValue() > otherNum.doubleValue()) {
                return 1;
            }
            else if (this.number.doubleValue() < otherNum.doubleValue()) {
                return -1;
            }
            else {
                return 0;
            }
        }
        else {
            throw new ExpressionRuntimeException("Could not compare " + this + " with " + other);
        }
    }


    @Override
    public AviatorObject innerDiv(AviatorObject other) {
        ensureNumber(other);
        AviatorNumber otherNum = (AviatorNumber) other;
        if (other instanceof AviatorLong) {
            return new AviatorLong(this.number.longValue() / otherNum.longValue());
        }
        else {
            return new AviatorDouble(this.number.doubleValue() / otherNum.doubleValue());
        }
    }


    @Override
    public AviatorNumber innerAdd(AviatorNumber other) {
        ensureNumber(other);
        AviatorNumber otherNum = other;
        if (other instanceof AviatorLong) {
            return new AviatorLong(this.number.longValue() + otherNum.longValue());
        }
        else {
            return new AviatorDouble(this.number.doubleValue() + otherNum.doubleValue());
        }
    }


    @Override
    public AviatorObject innerMod(AviatorObject other) {
        ensureNumber(other);
        AviatorNumber otherNum = (AviatorNumber) other;
        if (other instanceof AviatorLong) {
            return new AviatorLong(this.number.longValue() % otherNum.longValue());
        }
        else {
            return new AviatorDouble(this.number.doubleValue() % otherNum.doubleValue());
        }
    }


    @Override
    public AviatorObject innerMult(AviatorObject other) {
        ensureNumber(other);
        AviatorNumber otherNum = (AviatorNumber) other;
        if (other instanceof AviatorLong) {
            return new AviatorLong(this.number.longValue() * otherNum.longValue());
        }
        else {
            return new AviatorDouble(this.number.doubleValue() * otherNum.doubleValue());
        }
    }


    @Override
    public AviatorObject innerSub(AviatorObject other) {
        ensureNumber(other);
        AviatorNumber otherNum = (AviatorNumber) other;
        if (other instanceof AviatorLong) {
            return new AviatorLong(this.number.longValue() - otherNum.longValue());
        }
        else {
            return new AviatorDouble(this.number.doubleValue() - otherNum.doubleValue());
        }
    }

}
