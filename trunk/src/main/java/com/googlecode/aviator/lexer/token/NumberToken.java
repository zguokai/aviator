package com.googlecode.aviator.lexer.token;

import java.util.Map;


/**
 * A Number token
 * 
 * @author dennis
 * 
 */
public class NumberToken extends AbstractToken<Number> {

    private Number value;


    public NumberToken(Number value, String lexeme) {
        super(-1, lexeme);
        this.value = value;
    }


    public NumberToken(Number value, String lexeme, int startIndex) {
        super(startIndex, lexeme);
        this.value = value;
    }


    public void setNumber(Number number) {
        this.value = number;
    }


    public Number getNumber() {
        return this.value;
    }


    public Number getValue(Map<String, Object> env) {
        return value;
    }


    public TokenType getType() {
        return TokenType.Number;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        NumberToken other = (NumberToken) obj;
        if (value == null) {
            if (other.value != null) {
                return false;
            }
        }
        else if (!value.equals(other.value)) {
            return false;
        }
        return true;
    }

}
