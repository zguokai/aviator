package com.googlecode.aviator.lexer;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.LinkedList;
import java.util.Queue;

import com.googlecode.aviator.exception.CompileExpressionErrorException;
import com.googlecode.aviator.lexer.token.CharToken;
import com.googlecode.aviator.lexer.token.NumberToken;
import com.googlecode.aviator.lexer.token.StringToken;
import com.googlecode.aviator.lexer.token.Token;
import com.googlecode.aviator.lexer.token.Variable;


/**
 * Expression Lexer
 * 
 * @author dennis
 * 
 */
public class ExpressionLexer {
    private char peek;
    private final CharacterIterator iterator;
    private final SymbolTable symbolTable;
    private final Queue<Token<?>> tokenBuffer = new LinkedList<Token<?>>();


    public ExpressionLexer(String expression) {
        this.iterator = new StringCharacterIterator(expression);
        this.symbolTable = new SymbolTable();
        peek = iterator.current();
    }


    public void pushback(Token<?> token) {
        this.tokenBuffer.add(token);
    }


    public Token<?> scan() {
        if (!tokenBuffer.isEmpty()) {
            return tokenBuffer.poll();
        }
        for (;; peek = iterator.next()) {
            if (peek == CharacterIterator.DONE) {
                return null;
            }
            if (peek == ' ' || peek == '\t') {
                continue;
            }
            if (peek == '\n') {
                throw new CompileExpressionErrorException("Aviator doesn't support newline expression,index="
                        + iterator.getIndex());
            }
            else {
                break;
            }
        }

        if (Character.isDigit(peek) || peek == '.') {
            int startIndex = iterator.getIndex();
            Number value = 0L;
            boolean hasDot = false;
            double d = 10.0;
            do {
                if (peek == '.') {
                    if (hasDot) {
                        throw new CompileExpressionErrorException("Illegal Number, index=" + iterator.getIndex());
                    }
                    else {
                        hasDot = true;
                        value = new Double(value.longValue());
                        peek = iterator.next();
                    }

                }
                else {
                    if (!hasDot) {
                        value = 10 * value.longValue() + Character.digit(peek, 10);
                        peek = iterator.next();
                    }
                    else {
                        value = value.doubleValue() + Character.digit(peek, 10) / d;
                        d = d * 10;
                        peek = iterator.next();
                    }
                }
            } while (Character.isDigit(peek) || peek == '.');
            return new NumberToken(value, startIndex);
        }

        if (Character.isLetter(peek)) {
            int startIndex = iterator.getIndex();
            StringBuilder sb = new StringBuilder();
            do {
                sb.append(peek);
                peek = iterator.next();
            } while (Character.isLetterOrDigit(peek) || peek == '.');
            String lexeme = sb.toString();
            Variable variable = new Variable(lexeme, startIndex);
            if (symbolTable.contains(lexeme)) {
                return symbolTable.getVariable(lexeme);
            }
            else {
                symbolTable.reserve(lexeme, variable);
                return variable;
            }
        }

        if (isBinaryOP(peek)) {
            CharToken opToken = new CharToken(peek, iterator.getIndex());
            peek = iterator.next();
            return opToken;
        }
        // String
        if (peek == '"' || peek == '\'') {
            char left = peek;
            int startIndex = iterator.getIndex();
            StringBuilder sb = new StringBuilder();
            while ((peek = iterator.next()) != left) {
                if (peek == CharacterIterator.DONE) {
                    throw new CompileExpressionErrorException("Illegal String,start index=" + startIndex);
                }
                else {
                    sb.append(peek);
                }
            }
            peek = iterator.next();
            return new StringToken(sb.toString(), startIndex);
        }

        Token<Character> token = new CharToken(peek, iterator.getIndex());
        peek = ' ';
        return token;
    }

    static final char[] OPS = { '=', '>', '<', '+', '-', '*', '/', '%', '!', '&', '|' };


    public static boolean isBinaryOP(char ch) {
        for (char tmp : OPS) {
            if (tmp == ch) {
                return true;
            }
        }
        return false;
    }


    public static void main(String[] args) {
        String expression = "3----3  test";
        ExpressionLexer lexer = new ExpressionLexer(expression);
        Token<?> token = null;
        while ((token = lexer.scan()) != null) {
            System.out.println(token);
        }
    }
}
