package com.googlecode.aviator.code;

import com.googlecode.aviator.lexer.token.Token;


/**
 * Code generator interface
 * 
 * @author dennis
 * 
 */
public interface CodeGenerator {

    public void onAdd(Token<?> lookhead);


    public void onSub(Token<?> lookhead);


    public void onMult(Token<?> lookhead);


    public void onDiv(Token<?> lookhead);


    public void onAnd(Token<?> lookhead);


    public void onJoin(Token<?> lookhead);


    public void onEq(Token<?> lookhead);


    public void onNeq(Token<?> lookhead);


    public void onLt(Token<?> lookhead);


    public void onLe(Token<?> lookhead);


    public void onGt(Token<?> lookhead);


    public void onGe(Token<?> lookhead);


    public void onMod(Token<?> lookhead);


    public void onNot(Token<?> lookhead, int depth);


    public void onNeg(Token<?> lookhead, int depth);


    public Class<?> getResult();


    public void onConstant(Token<?> lookhead);

}