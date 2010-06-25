package com.googlecode.aviator.lexer;

import static org.junit.Assert.*;

import org.junit.Test;

import com.googlecode.aviator.exception.CompileExpressionErrorException;
import com.googlecode.aviator.lexer.token.Token;
import com.googlecode.aviator.lexer.token.Token.TokenType;


public class ExpressionLexerUnitTest {
    private ExpressionLexer lexer;


    @Test
    public void testSimpleExpression() {
        this.lexer = new ExpressionLexer("1+2");
        Token<?> token = lexer.scan();
        assertEquals(TokenType.Number, token.getType());
        assertEquals(1, token.getValue(null));
        assertEquals(0, token.getStartIndex());

        token = lexer.scan();
        assertEquals(TokenType.Char, token.getType());
        assertEquals('+', token.getValue(null));
        assertEquals(1, token.getStartIndex());

        token = lexer.scan();
        assertEquals(TokenType.Number, token.getType());
        assertEquals(2, token.getValue(null));
        assertEquals(2, token.getStartIndex());

        assertNull(lexer.scan());
    }


    @Test
    public void testSimpleExpression_WithSpace() {
        this.lexer = new ExpressionLexer(" 1 + 2 ");
        Token<?> token = lexer.scan();
        assertEquals(TokenType.Number, token.getType());
        assertEquals(1, token.getValue(null));
        assertEquals(1, token.getStartIndex());

        token = lexer.scan();
        assertEquals(TokenType.Char, token.getType());
        assertEquals('+', token.getValue(null));
        assertEquals(3, token.getStartIndex());

        token = lexer.scan();
        assertEquals(TokenType.Number, token.getType());
        assertEquals(2, token.getValue(null));
        assertEquals(5, token.getStartIndex());

        assertNull(lexer.scan());
    }


    @Test
    public void testExpression_WithDouble() {
        this.lexer = new ExpressionLexer("3.0+4-5.9");
        Token<?> token = lexer.scan();
        assertEquals(TokenType.Number, token.getType());
        assertEquals(3.0, token.getValue(null));
        assertEquals(0, token.getStartIndex());

        token = lexer.scan();
        assertEquals(TokenType.Char, token.getType());
        assertEquals('+', token.getValue(null));
        assertEquals(3, token.getStartIndex());

        token = lexer.scan();
        assertEquals(TokenType.Number, token.getType());
        assertEquals(4, token.getValue(null));
        assertEquals(4, token.getStartIndex());

        token = lexer.scan();
        assertEquals(TokenType.Char, token.getType());
        assertEquals('-', token.getValue(null));
        assertEquals(5, token.getStartIndex());

        token = lexer.scan();
        assertEquals(TokenType.Number, token.getType());
        assertEquals(5.9, token.getValue(null));
        assertEquals(6, token.getStartIndex());

        assertNull(lexer.scan());

    }


    @Test(expected = CompileExpressionErrorException.class)
    public void testExpression_WithIllegalDouble() {
        this.lexer = new ExpressionLexer("3.0+4-5.9.2");
        while (lexer.scan() != null) {
            ;
        }

    }


    @Test
    public void testExpression_WithString() {
        this.lexer = new ExpressionLexer("'hello world'");
        Token<?> token = lexer.scan();
        assertEquals(TokenType.String, token.getType());
        assertEquals("hello world", token.getValue(null));
        assertEquals(0, token.getStartIndex());
        assertNull(lexer.scan());
    }


    @Test
    public void testExpression_WithNestedString() {
        this.lexer = new ExpressionLexer("'hello \"good\" world'");
        Token<?> token = lexer.scan();
        assertEquals(TokenType.String, token.getType());
        assertEquals("hello \"good\" world", token.getValue(null));
        assertEquals(0, token.getStartIndex());
        assertNull(lexer.scan());
    }


    @Test(expected = CompileExpressionErrorException.class)
    public void testExpression_WithIllegalString() {
        this.lexer = new ExpressionLexer("'hello \" world");
        Token<?> token = lexer.scan();
        assertEquals(TokenType.String, token.getType());
        assertEquals("hello \" world", token.getValue(null));
        assertEquals(0, token.getStartIndex());
    }


    @Test
    public void testExpressionWithParen() {
        this.lexer = new ExpressionLexer("2.0+(2+2)*99");
        Token<?> token = lexer.scan();
        assertEquals(TokenType.Number, token.getType());
        assertEquals(2.0, token.getValue(null));
        assertEquals(0, token.getStartIndex());

        token = lexer.scan();
        assertEquals(TokenType.Char, token.getType());
        assertEquals('+', token.getValue(null));
        assertEquals(3, token.getStartIndex());

        token = lexer.scan();
        assertEquals(TokenType.Char, token.getType());
        assertEquals('(', token.getValue(null));
        assertEquals(4, token.getStartIndex());

        token = lexer.scan();
        assertEquals(TokenType.Number, token.getType());
        assertEquals(2, token.getValue(null));
        assertEquals(5, token.getStartIndex());

        token = lexer.scan();
        assertEquals(TokenType.Char, token.getType());
        assertEquals('+', token.getValue(null));
        assertEquals(6, token.getStartIndex());

        token = lexer.scan();
        assertEquals(TokenType.Number, token.getType());
        assertEquals(2, token.getValue(null));
        assertEquals(7, token.getStartIndex());

        token = lexer.scan();
        assertEquals(TokenType.Char, token.getType());
        assertEquals(')', token.getValue(null));
        assertEquals(8, token.getStartIndex());

        token = lexer.scan();
        assertEquals(TokenType.Char, token.getType());
        assertEquals('*', token.getValue(null));
        assertEquals(9, token.getStartIndex());

        token = lexer.scan();
        assertEquals(TokenType.Number, token.getType());
        assertEquals(99, token.getValue(null));
        assertEquals(10, token.getStartIndex());

        assertNull(lexer.scan());
    }
}
