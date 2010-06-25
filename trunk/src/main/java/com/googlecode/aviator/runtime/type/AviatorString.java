package com.googlecode.aviator.runtime.type;

import com.googlecode.aviator.exception.ExpressionRuntimeException;


public class AviatorString extends AviatorObject {
    final String lexeme;


    @Override
    public AviatorType getAviatorType() {
        return AviatorType.String;
    }


    @Override
    public Object getValue() {
        return this.lexeme;
    }


    public AviatorString(String lexeme) {
        super();
        this.lexeme = lexeme;
    }


    @Override
    public AviatorObject add(AviatorObject other) {
        switch (other.getAviatorType()) {
        case String:
            AviatorString otherString = (AviatorString) other;
            return new AviatorString(this.lexeme + otherString.lexeme);
        case Boolean:
            AviatorBoolean otherBoolean = (AviatorBoolean) other;
            return new AviatorString(this.lexeme + otherBoolean.value);
        case Number:
            AviatorNumber otherNumber = (AviatorNumber) other;
            return new AviatorString(this.lexeme + otherNumber.number);
        case JavaType:
            AviatorJavaType otherJavaType = (AviatorJavaType) other;
            return new AviatorString(this.lexeme + otherJavaType.object);
        default:
            throw new ExpressionRuntimeException("Illegal operand type for string to add  " + other.getAviatorType());
        }
    }


    @Override
    public int compare(AviatorObject other) {
        if (other.getAviatorType() != AviatorType.String) {
            throw new ExpressionRuntimeException("Could not compare String with" + other.getClass().getName());
        }
        AviatorString otherString = (AviatorString) other;
        return this.lexeme.compareTo(otherString.lexeme);
    }


    public String getLexeme() {
        return lexeme;
    }

}
