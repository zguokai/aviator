package com.googlecode.aviator.parser;

import com.googlecode.aviator.code.CodeGenerator;
import com.googlecode.aviator.lexer.token.Token;


/**
 * Fake CodeGenerator,transform infix expression to postfix expression
 * 
 * @author dennis
 * 
 */
public class FakeCodeGenerator implements CodeGenerator {
    private StringBuffer sb = new StringBuffer();

    private boolean wasFirst = true;


    public void reset() {
        this.sb = new StringBuffer();
        this.wasFirst = true;
    }


    public Class<?> getResult() {
        return null;
    }


    public String getPostFixExpression() {
        return this.sb.toString();
    }


    public void onAdd(Token<?> lookhead) {
        appendToken("+");
    }


    private void appendToken(String s) {
        if (wasFirst) {
            wasFirst = false;
            sb.append(s);
        }
        else {
            sb.append(" ").append(s);
        }
    }


    public void onAnd(Token<?> lookhead) {
        appendToken("&&");
    }


    public void onConstant(Token<?> lookhead) {
        appendToken(lookhead.getLexeme());
    }


    public void onDiv(Token<?> lookhead) {
        appendToken("/");

    }


    public void onEq(Token<?> lookhead) {
        appendToken("==");

    }


    public void onGe(Token<?> lookhead) {
        appendToken(">=");

    }


    public void onGt(Token<?> lookhead) {
        appendToken(">");

    }


    public void onJoin(Token<?> lookhead) {
        appendToken("||");

    }


    public void onLe(Token<?> lookhead) {
        appendToken("<=");

    }


    public void onLt(Token<?> lookhead) {
        appendToken("<");

    }


    public void onMatch(Token<?> lookhead) {
        appendToken("=~");

    }


    public void onMod(Token<?> lookhead) {
        appendToken("%");

    }


    public void onMult(Token<?> lookhead) {
        appendToken("*");

    }


    public void onNeg(Token<?> lookhead, int depth) {
        appendToken("-");

    }


    public void onNeq(Token<?> lookhead) {
        appendToken("!=");

    }


    public void onNot(Token<?> lookhead, int depth) {
        appendToken("!");

    }


    public void onSub(Token<?> lookhead) {
        appendToken("-");
    }

}
