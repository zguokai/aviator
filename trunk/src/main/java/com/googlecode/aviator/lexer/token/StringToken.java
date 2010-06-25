package com.googlecode.aviator.lexer.token;

import java.util.Map;


/**
 * String token
 * 
 * @author dennis
 * 
 */
public class StringToken extends AbstractToken<java.lang.String> {

    public StringToken(String lexeme, int startIndex) {
        super(startIndex, lexeme);
    }


    public TokenType getType() {
        return TokenType.String;
    }


    public java.lang.String getValue(Map<String, Object> env) {
        return lexeme;
    }

}
