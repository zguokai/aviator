package com.googlecode.aviator.parser;

import com.googlecode.aviator.code.CodeGenerator;
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
            throw new ExpressionSyntaxErrorException("Blank expression");
        }
        this.codeGenerator = codeGenerator;
    }


    public void ternary() {
        bool();
        if (lookhead == null || expectLexeme(":")) {
            return;
        }
        if (expectLexeme("?")) {
            move(true);
            this.codeGenerator.onTernaryBoolean(lookhead);
            ternary();
            if (expectLexeme(":")) {
                move(true);
                this.codeGenerator.onTernaryLeft(lookhead);
                ternary();
                this.codeGenerator.onTernaryRight(lookhead);
            }
            else {
                reportSyntaxError();
            }
        }
        else {
            if (expectLexeme(")")) {
                if (this.parenDepth > 0) {
                    return;
                }
                else {
                    reportSyntaxError("Insert '(' to complete Expression");
                }
            }
            reportSyntaxError();
        }
    }


    public void bool() {
        join();
        while (true) {
            if (isJoinToken()) {
                move(true);
                if (isJoinToken()) {
                    move(true);
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

                else {
                    break;
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
                move(true);
                if (isAndToken()) {
                    move(true);
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
                move(true);
                if (expectLexeme("=")) {
                    move(true);
                    rel();
                    codeGenerator.onEq(lookhead);
                }
                else if (expectLexeme("~")) {
                    // It is a regular expression
                    move(true);
                    rel();
                    codeGenerator.onMatch(lookhead);
                }
                else {
                    reportSyntaxError();
                }
            }
            else if (expectLexeme("!")) {
                move(true);
                if (expectLexeme("=")) {
                    move(true);
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
                move(true);
                if (expectLexeme("=")) {
                    move(true);
                    expr();
                    codeGenerator.onLe(lookhead);
                }
                else {
                    expr();
                    codeGenerator.onLt(lookhead);
                }
            }
            else if (expectLexeme(">")) {
                move(true);
                if (expectLexeme("=")) {
                    move(true);
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


    public void expr() {
        term();
        while (true) {
            if (expectLexeme("+")) {
                move(true);
                term();
                codeGenerator.onAdd(lookhead);
            }
            else if (expectLexeme("-")) {
                move(true);
                term();
                codeGenerator.onSub(lookhead);
            }
            else {
                break;
            }
        }
    }


    public void term() {
        unary();
        while (true) {
            if (expectLexeme("*")) {
                move(true);
                unary();
                codeGenerator.onMult(lookhead);
            }
            else if (expectLexeme("/")) {
                move(true);
                unary();
                codeGenerator.onDiv(lookhead);
            }
            else if (expectLexeme("%")) {
                move(true);
                unary();
                codeGenerator.onMod(lookhead);
            }
            else {
                break;
            }
        }
    }


    public void unary() {
        if (expectLexeme("!")) {
            move(true);
            unary();
            this.codeGenerator.onNot(lookhead);
        }
        else if (expectLexeme("-")) {
            move(true);
            unary();
            this.codeGenerator.onNeg(lookhead);
        }
        else {
            factor();
        }
    }

    public static final CharToken LEFT_PAREN = new CharToken('(', -1);
    public static final CharToken RIGHT_PAREN = new CharToken(')', -1);


    public void factor() {
        if (lookhead == null) {
            reportSyntaxError();
        }
        if (expectLexeme("(")) {
            this.parenDepth++;
            move(true);
            ternary();
            if (!expectLexeme(")")) {
                reportSyntaxError("insert ')' to complete Expression");
            }
            else {
                move(true);
            }
            this.parenDepth--;
        }
        else if (lookhead.getType() == TokenType.Number || lookhead.getType() == TokenType.String
                || lookhead.getType() == TokenType.Variable || lookhead == Variable.TRUE || lookhead == Variable.FALSE) {
            codeGenerator.onConstant(lookhead);
            move(true);
        }
        else if (isPattern()) {
            pattern();
        }
        else {
            reportSyntaxError();
        }

    }


    private void pattern() {
        // It is a pattern
        int startIndex = this.lookhead.getStartIndex();
        move(true);
        this.inPattern = true;
        StringBuffer sb = new StringBuffer();
        while (lookhead != null) {
            while (!isPattern()) {
                sb.append(lookhead.getLexeme());
                move(false);
            }
            if (prevToken.getType() == TokenType.Char && ((CharToken) prevToken).getLexeme().equals("\\")) {
                sb.append("/");
                move(false);
                continue;
            }
            this.inPattern = false;
            break;
        }
        if (this.inPattern) {
            reportSyntaxError();
        }
        codeGenerator.onConstant(new PatternToken(sb.toString(), startIndex));
        move(true);
    }


    private boolean isPattern() {
        return lookhead.getType() == TokenType.Char && ((CharToken) lookhead).getLexeme().equals("/");
    }


    private void reportSyntaxError() {
        throw new ExpressionSyntaxErrorException("Syntax error:prev=" + (prevToken != null ? prevToken : "")
                + ",current=" + lookhead);
    }


    private void reportSyntaxError(String message) {
        throw new ExpressionSyntaxErrorException("Syntax error:" + message);
    }


    public void move(boolean analyse) {
        if (lookhead != null) {
            prevToken = lookhead;
            lookhead = lexer.scan(analyse);
        }
        else {
            reportSyntaxError();
        }

    }


    public Class<?> parse() {
        ternary();
        if (this.parenDepth > 0) {
            reportSyntaxError("insert ')' to complete Expression");
        }
        return codeGenerator.getResult();
    }

}
