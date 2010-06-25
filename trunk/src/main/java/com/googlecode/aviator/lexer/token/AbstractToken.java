package com.googlecode.aviator.lexer.token;

/**
 * Base token class
 * 
 * @author dennis
 * 
 * @param <T>
 */
public abstract class AbstractToken<T> implements Token<T> {

    private final int startIndex;


    public AbstractToken(int startIndex) {
        super();
        this.startIndex = startIndex;
    }


    public int getStartIndex() {
        return this.startIndex;
    }


    @Override
    public String toString() {
        return "[" + getType().name() + " " + getLexeme() + "]";
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + startIndex;
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        AbstractToken<?> other = (AbstractToken<?>) obj;
        if (startIndex != other.startIndex) {
            return false;
        }
        return true;
    }

}
