package com.googlecode.aviator.lexer.token;

import java.util.Map;


/**
 * Variable token
 * 
 * @author dennis
 * 
 */
public class Variable extends AbstractToken<Object> {

    public static final Variable TRUE = new Variable("true", -1) {

        @Override
        public Object getValue(Map<String, Object> env) {
            return true;
        }

    };

    public static final Variable FALSE = new Variable("false", -1) {

        @Override
        public Object getValue(Map<String, Object> env) {
            return false;
        }

    };

    private final String name;


    public com.googlecode.aviator.lexer.token.Token.TokenType getType() {
        return TokenType.Variable;
    }


    public Object getValue(Map<String, Object> env) {
        if (env != null) {
            return env.get(this.name);
        }
        else {
            return name;
        }
    }


    public String getLexeme() {
        return this.name;
    }


    public Variable(String name, int startIndex) {
        super(startIndex);
        this.name = name;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        Variable other = (Variable) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        }
        else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

}
