package com.googlecode.aviator.runtime.type;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.googlecode.aviator.exception.ExpressionRuntimeException;


/**
 * A Aviator regular expression pattern
 * 
 * @author dennis
 * 
 */
public class AviatorPattern extends AviatorObject {

    final Pattern pattern;


    public AviatorPattern(String expression) {
        super();
        this.pattern = Pattern.compile(expression);
    }


    @Override
    public AviatorObject add(AviatorObject other) {
        switch (other.getAviatorType()) {
        case String:
            return new AviatorString(this.pattern.pattern() + ((AviatorString) other).lexeme);
        case JavaType:
            AviatorJavaType javaType = (AviatorJavaType) other;
            if (javaType.getObject() instanceof String || javaType.getObject() instanceof Character) {
                return new AviatorString(this.pattern.pattern() + javaType.getObject().toString());
            }
            else {
                return super.add(other);
            }
        default:
            return super.add(other);

        }
    }


    @Override
    public AviatorObject match(AviatorObject other) {
        switch (other.getAviatorType()) {
        case String:
            AviatorString aviatorString = (AviatorString) other;
            Matcher m = this.pattern.matcher(aviatorString.lexeme);
            if (m.matches()) {
                return AviatorBoolean.TRUE;
            }
            else {
                return AviatorBoolean.FALSE;
            }
        case JavaType:
            AviatorJavaType javaType = (AviatorJavaType) other;
            if (javaType.getObject() instanceof String) {
                return match(new AviatorString((String) javaType.getObject()));
            }
            else if (javaType.getObject() instanceof Character) {
                return match(new AviatorString(String.valueOf(javaType.getObject())));
            }
            else {
                throw new ExpressionRuntimeException(this + " could not match " + other);
            }
        default:
            throw new ExpressionRuntimeException(this + " could not match " + other);
        }

    }


    @Override
    public int compare(AviatorObject other) {
        if (other.getAviatorType() != AviatorType.Pattern) {
            throw new ExpressionRuntimeException("Could not compare Pattern with " + other.getAviatorType());
        }
        return this.pattern.pattern().compareTo(((AviatorPattern) other).pattern.pattern());
    }


    @Override
    public AviatorType getAviatorType() {
        return AviatorType.Pattern;
    }


    @Override
    public Object getValue() {
        return pattern.pattern();
    }

}
