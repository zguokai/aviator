package com.googlecode.aviator.lexer.token;

import java.util.Map;


/**
 * Variable token
 * 
 * @author dennis
 * 
 */
public class Variable extends AbstractToken<Object> {

    /**
     * Boolean value true
     */
    public static final Variable TRUE = new Variable("true", -1) {

        @Override
        public Object getValue(Map<String, Object> env) {
            return true;
        }

    };

    /**
     * Boolean value false
     */
    public static final Variable FALSE = new Variable("false", -1) {

        @Override
        public Object getValue(Map<String, Object> env) {
            return false;
        }

    };

    /**
     * Boolean value false
     */
    public static final Variable NIL = new Variable("nil", -1) {

        @Override
        public Object getValue(Map<String, Object> env) {
            return null;
        }

    };


    public com.googlecode.aviator.lexer.token.Token.TokenType getType() {
        return TokenType.Variable;
    }


    public Object getValue(Map<String, Object> env) {
        if (env != null) {
            return env.get(this.lexeme);
        }
        else {
            return this.lexeme;
        }
    }


    public Variable(String name, int startIndex) {
        super(startIndex, name);
    }

}
