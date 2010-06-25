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

    protected final String lexeme;


    public AbstractToken(int startIndex, String lexeme) {
        super();
        this.startIndex = startIndex;
        this.lexeme = lexeme;
    }


    public String getLexeme() {
        return this.lexeme;
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
        result = prime * result + ((lexeme == null) ? 0 : lexeme.hashCode());
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
        AbstractToken other = (AbstractToken) obj;
        if (lexeme == null) {
            if (other.lexeme != null) {
                return false;
            }
        }
        else if (!lexeme.equals(other.lexeme)) {
            return false;
        }
        if (startIndex != other.startIndex) {
            return false;
        }
        return true;
    }



}
