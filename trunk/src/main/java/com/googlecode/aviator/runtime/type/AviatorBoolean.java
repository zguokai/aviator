package com.googlecode.aviator.runtime.type;

import com.googlecode.aviator.exception.ExpressionRuntimeException;


public class AviatorBoolean extends AviatorObject {

    Boolean value;

    public static final AviatorBoolean TRUE = new AviatorBoolean(Boolean.TRUE);

    public static final AviatorBoolean FALSE = new AviatorBoolean(Boolean.FALSE);


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
        if (other.getAviatorType() != AviatorType.Boolean) {
            throw new ExpressionRuntimeException("Could not compare boolean with " + other.getClass().getName());
        }
        AviatorBoolean otherBoolean = (AviatorBoolean) other;
        return this.value.compareTo(otherBoolean.value);
    }

}
