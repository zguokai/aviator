package com.googlecode.aviator.lexer.token;

public class PatternToken extends StringToken {

    public PatternToken(String lexeme, int startIndex) {
        super(lexeme, startIndex);
    }


    @Override
    public com.googlecode.aviator.lexer.token.Token.TokenType getType() {
        return TokenType.Pattern;
    }

}
