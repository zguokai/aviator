package com.googlecode.aviator.parser;

import com.googlecode.aviator.code.CodeGenerator;
import com.googlecode.aviator.exception.CompileExpressionErrorException;
import com.googlecode.aviator.exception.ExpressionSyntaxErrorException;
import com.googlecode.aviator.lexer.ExpressionLexer;
import com.googlecode.aviator.lexer.token.CharToken;
import com.googlecode.aviator.lexer.token.PatternToken;
import com.googlecode.aviator.lexer.token.Token;
import com.googlecode.aviator.lexer.token.Variable;
import com.googlecode.aviator.lexer.token.Token.TokenType;


/**
 * Syntex parser for expression
 * 
 * @author dennis
 * 
 */
public class ExpressionParser {
    private final ExpressionLexer lexer;
    /*
     * Lookhead token
     */
    private Token<?> lookhead;

    private Token<?> prevToken;

    private final CodeGenerator codeGenerator;

    // Paren depth
    private int parenDepth = 0;

    private boolean inPattern = false;


    public ExpressionParser(ExpressionLexer lexer, CodeGenerator codeGenerator) {
        super();
        this.lexer = lexer;
        this.lookhead = this.lexer.scan();
        if (this.lookhead == null) {
            throw new CompileExpressionErrorException("Illegal expression");
        }
        this.codeGenerator = codeGenerator;
    }


    public void bool() {
        join();
        while (true) {
            if (isJoinToken()) {
                move();
                if (isJoinToken()) {
                    move();
                    join();
                    codeGenerator.onJoin(lookhead);
                }
                else {
                    reportSyntaxError();
                }
            }
            else {
                if (lookhead == null) {
                    break;
                }
                if (expectLexeme(")")) {
                    if (this.parenDepth > 0) {
                        break;
                    }
                    else {
                        reportSyntaxError("Insert '(' to complete Expression");
                    }
                }
                else {
                    reportSyntaxError();
                }

            }

        }
    }


    private boolean isJoinToken() {
        return expectLexeme("|");
    }


    private boolean expectLexeme(String s) {
        if (lookhead == null) {
            return false;
        }
        return lookhead.getType() == TokenType.Char && ((CharToken) lookhead).getLexeme().equals(s);
    }


    private boolean isAndToken() {
        return expectLexeme("&");
    }


    public void join() {
        equality();
        while (true) {
            if (isAndToken()) {
                move();
                if (isAndToken()) {
                    move();
                    equality();
                    codeGenerator.onAnd(lookhead);
                }
                else {
                    reportSyntaxError();
                }
            }
            else {
                break;
            }
        }

    }


    public void equality() {
        rel();
        while (true) {
            if (expectLexeme("=")) {
                move();
                if (expectLexeme("=")) {
                    move();
                    rel();
                    codeGenerator.onEq(lookhead);
                }
                else if (expectLexeme("~")) {
                    // It is a regular expression
                    move();
                    rel();
                    codeGenerator.onMatch(lookhead);
                }
                else {
                    reportSyntaxError();
                }
            }
            else if (expectLexeme("!")) {
                move();
                if (expectLexeme("=")) {
                    move();
                    rel();
                    codeGenerator.onNeq(lookhead);
                }
                else {
                    reportSyntaxError();
                }
            }
            else {
                break;
            }
        }
    }


    public void rel() {
        expr();
        while (true) {
            if (expectLexeme("<")) {
                move();
                if (expectLexeme("=")) {
                    move();
                    expr();
                    codeGenerator.onLe(lookhead);
                }
                else {
                    expr();
                    codeGenerator.onLt(lookhead);
                }
            }
            else if (expectLexeme(">")) {
                move();
                if (expectLexeme("=")) {
                    move();
                    expr();
                    codeGenerator.onGe(lookhead);
                }
                else {
                    expr();
                    codeGenerator.onGt(lookhead);
                }
            }
            else {
                break;
            }
        }
    }


    private void back() {
        lexer.pushback(lookhead);
        lookhead = prevToken;
    }


    public void expr() {
        term();
        while (true) {
            if (expectLexeme("+")) {
                move();
                term();
                codeGenerator.onAdd(lookhead);
            }
            else if (expectLexeme("-")) {
                move();
                term();
                codeGenerator.onSub(lookhead);
            }
            else {
                break;
            }
        }
    }


    public void term() {
        unary(0);
        while (true) {
            if (expectLexeme("*")) {
                move();
                unary(0);
                codeGenerator.onMult(lookhead);
            }
            else if (expectLexeme("/")) {
                move();
                unary(0);
                codeGenerator.onDiv(lookhead);
            }
            else if (expectLexeme("%")) {
                move();
                unary(0);
                codeGenerator.onMod(lookhead);
            }
            else {
                break;
            }
        }
    }


    public void unary(int depth) {
        if (expectLexeme("!")) {
            move();
            unary(depth++);
            this.codeGenerator.onNot(lookhead, depth);
        }
        else if (expectLexeme("-")) {
            move();
            unary(depth++);
            this.codeGenerator.onNeg(lookhead, depth);
        }
        else {
            factor();
        }
    }

    public static final CharToken LEFT_PAREN = new CharToken('(', -1);
    public static final CharToken RIGHT_PAREN = new CharToken(')', -1);


    public void factor() {
        if (expectLexeme("(")) {
            this.parenDepth++;
            move();
            bool();
            if (!expectLexeme(")")) {
                reportSyntaxError("insert ')' to complete Expression");
            }
            else {
                move();
            }
            this.parenDepth--;
        }
        else if (lookhead.getType() == TokenType.Number || lookhead.getType() == TokenType.String
                || lookhead.getType() == TokenType.Variable || lookhead == Variable.TRUE || lookhead == Variable.FALSE) {
            codeGenerator.onConstant(lookhead);
            move();
        }
        else if (isPattern()) {
            // It is a pattern
            int startIndex = this.lookhead.getStartIndex();
            move();
            this.inPattern = true;
            StringBuffer sb = new StringBuffer();
            while (lookhead != null) {
                while (!isPattern()) {
                    sb.append(lookhead.getLexeme());
                    move();
                }
                if (prevToken.getType() == TokenType.Char && ((CharToken) prevToken).getLexeme().equals("\\")) {
                    sb.append("/");
                    move();
                    continue;
                }
                this.inPattern = false;
                break;
            }
            if (this.inPattern) {
                reportSyntaxError();
            }
            codeGenerator.onConstant(new PatternToken(sb.toString(), startIndex));
            move();
        }
        else {
            reportSyntaxError();
        }

    }


    private boolean isPattern() {
        return lookhead.getType() == TokenType.Char && ((CharToken) lookhead).getLexeme().equals("/");
    }


    private void reportSyntaxError() {
        throw new ExpressionSyntaxErrorException("Syntax error:" + (prevToken != null ? prevToken : "") + lookhead);
    }


    private void reportSyntaxError(String message) {
        throw new ExpressionSyntaxErrorException("Syntax error:" + message);
    }


    public void move() {
        if (lookhead != null) {
            prevToken = lookhead;
            lookhead = lexer.scan();
        }
        else {
            reportSyntaxError();
        }

    }


    public Class<?> parse() {
        bool();
        if (this.parenDepth > 0) {
            reportSyntaxError("insert ')' to complete Expression");
        }
        return codeGenerator.getResult();
    }

}
