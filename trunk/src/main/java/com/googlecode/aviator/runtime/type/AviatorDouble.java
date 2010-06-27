package com.googlecode.aviator.runtime.type;

import java.util.Map;

public class AviatorDouble extends AviatorNumber {

    public AviatorDouble(Number number) {
        super(number);
    }


    @Override
    public int innerCompare(AviatorObject other) {
        ensureNumber(other);
        AviatorNumber otherNum = (AviatorNumber) other;
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


    @Override
    public AviatorObject neg( Map<String, Object> env) {
        return new AviatorDouble(-this.number.doubleValue());
    }


    @Override
    public AviatorObject innerDiv(AviatorObject other) {
        ensureNumber(other);
        AviatorNumber otherNum = (AviatorNumber) other;
        return new AviatorDouble(this.number.doubleValue() / otherNum.doubleValue());
    }


    @Override
    public AviatorNumber innerAdd(AviatorNumber other) {
        ensureNumber(other);
        AviatorNumber otherNum = other;
        return new AviatorDouble(this.number.doubleValue() + otherNum.doubleValue());
    }


    @Override
    public AviatorObject innerMod(AviatorObject other) {
        ensureNumber(other);
        AviatorNumber otherNum = (AviatorNumber) other;
        return new AviatorDouble(this.number.doubleValue() % otherNum.doubleValue());
    }


    @Override
    public AviatorObject innerMult(AviatorObject other) {
        ensureNumber(other);
        AviatorNumber otherNum = (AviatorNumber) other;
        return new AviatorDouble(this.number.doubleValue() * otherNum.doubleValue());
    }


    @Override
    public AviatorObject innerSub(AviatorObject other) {
        ensureNumber(other);
        AviatorNumber otherNum = (AviatorNumber) other;
        return new AviatorDouble(this.number.doubleValue() - otherNum.doubleValue());
    }
}
