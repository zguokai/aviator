package com.googlecode.aviator.lexer.token;

import java.util.Map;


/**
 * String token
 * 
 * @author dennis
 * 
 */
public class StringToken extends AbstractToken<java.lang.String> {

    private final String lexeme;


    public StringToken(String lexeme, int startIndex) {
        super(startIndex);
        this.lexeme = lexeme;
    }


    public TokenType getType() {
        return TokenType.String;
    }


    public java.lang.String getValue(Map<String, Object> env) {
        return lexeme;
    }


    public String getLexeme() {
        return this.lexeme;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((lexeme == null) ? 0 : lexeme.hashCode());
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
        StringToken other = (StringToken) obj;
        if (lexeme == null) {
            if (other.lexeme != null) {
                return false;
            }
        }
        else if (!lexeme.equals(other.lexeme)) {
            return false;
        }
        return true;
    }

}
