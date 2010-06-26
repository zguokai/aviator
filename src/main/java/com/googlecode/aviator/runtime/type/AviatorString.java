package com.googlecode.aviator.runtime.type;

import com.googlecode.aviator.exception.ExpressionRuntimeException;


/**
 * A aviator string
 * 
 * @author dennis
 * 
 */
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
        case Pattern:
            AviatorPattern otherPatterh = (AviatorPattern) other;
            return new AviatorString(this.lexeme + otherPatterh.pattern.pattern());
        default:
            return super.add(other);
        }
    }


    @Override
    public int compare(AviatorObject other) {
        switch (other.getAviatorType()) {
        case String:
            AviatorString otherString = (AviatorString) other;
            return this.lexeme.compareTo(otherString.lexeme);
        case JavaType:
            AviatorJavaType javaType = (AviatorJavaType) other;
            if (javaType.getObject() instanceof String) {
                return this.lexeme.compareTo((String) javaType.getObject());
            }
            else if (javaType.getObject() instanceof Character) {
                return this.lexeme.compareTo(String.valueOf(javaType.getObject()));
            }
            else {
                throw new ExpressionRuntimeException("Could not compare " + this + " with " + other);
            }
        default:
            throw new ExpressionRuntimeException("Could not compare " + this + " with " + other);
        }
    }


    public String getLexeme() {
        return lexeme;
    }

}
