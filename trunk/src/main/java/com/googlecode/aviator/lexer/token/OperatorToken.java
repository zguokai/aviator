package com.googlecode.aviator.lexer.token;

import java.util.Map;


public class OperatorToken extends AbstractToken<OperatorType> {

    public OperatorToken(int startIndex, OperatorType operatorType) {
        super(startIndex);
        this.operatorType = operatorType;
    }

    public OperatorType getOperatorType() {
        return operatorType;
    }

    private final OperatorType operatorType;


    public String getLexeme() {
        return operatorType.getToken();
    }


    public com.googlecode.aviator.lexer.token.Token.TokenType getType() {
        return TokenType.Operator;
    }


    public OperatorType getValue(Map<String, Object> env) {
        return this.operatorType;
    }
}
