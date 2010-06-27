package com.googlecode.aviator.runtime.type;

import java.util.Map;

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
    public Object getValue(Map<String, Object> env) {
        return this.lexeme;
    }


    public AviatorString(String lexeme) {
        super();
        this.lexeme = lexeme;
    }


    @Override
    public AviatorObject add(AviatorObject other, Map<String, Object> env) {
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
            return new AviatorString(this.lexeme + otherJavaType.getValue(env));
        case Pattern:
            AviatorPattern otherPatterh = (AviatorPattern) other;
            return new AviatorString(this.lexeme + otherPatterh.pattern.pattern());
        default:
            return super.add(other, env);
        }
    }


    @Override
    public int compare(AviatorObject other, Map<String, Object> env) {
        switch (other.getAviatorType()) {
        case String:
            AviatorString otherString = (AviatorString) other;
            return this.lexeme.compareTo(otherString.lexeme);
        case JavaType:
            AviatorJavaType javaType = (AviatorJavaType) other;
            final Object javaValue = javaType.getValue(env);
            if (javaValue instanceof String) {
                return this.lexeme.compareTo((String) javaValue);
            }
            else if (javaValue instanceof Character) {
                return this.lexeme.compareTo(String.valueOf(javaValue));
            }
            else {
                throw new ExpressionRuntimeException("Could not compare " + this + " with " + other);
            }
        case Nil:
            return 1;
        default:
            throw new ExpressionRuntimeException("Could not compare " + this + " with " + other);
        }
    }


    public String getLexeme() {
        return lexeme;
    }

}
