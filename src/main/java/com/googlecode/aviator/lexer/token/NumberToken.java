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

    private boolean visited;


    public boolean isVisited() {
        return visited;
    }


    public void setVisited(boolean visited) {
        this.visited = visited;
    }


    public NumberToken(Number value, boolean visited) {
        super(-1);
        this.visited = true;
        this.value = value;
    }


    public NumberToken(Number value, int startIndex) {
        super(startIndex);
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


    public String getLexeme() {
        return this.value.toString();
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        result = prime * result + (visited ? 1231 : 1237);
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
        if (visited != other.visited) {
            return false;
        }
        return true;
    }

}
