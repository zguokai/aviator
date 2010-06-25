package com.googlecode.aviator.lexer.token;

import java.util.Map;


/**
 * Lex token
 * 
 * @author dennis
 * 
 * @param <T>
 */
public interface Token<T> {
    public enum TokenType {
        String,
        Variable,
        Number,
        Char,
        Operator
    }


    public T getValue(Map<String, Object> env);


    public TokenType getType();


    public String getLexeme();


    public int getStartIndex();
}
