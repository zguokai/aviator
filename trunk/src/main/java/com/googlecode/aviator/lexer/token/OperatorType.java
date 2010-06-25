package com.googlecode.aviator.lexer.token;

import java.util.HashMap;
import java.util.Map;


public enum OperatorType {
    // 逻辑否
    NOT("!", 80, 1),
    // 取负
    NG("-", 80, 1),

    // 算术乘
    MULT("*", 70, 2),
    // 算术除
    DIV("/", 70, 2),
    // 算术除
    MOD("%", 70, 2),

    // 算术加
    ADD("+", 60, 2),
    // 算术减
    SUB("-", 60, 2),

    // 逻辑小于
    LT("<", 50, 2),
    // 逻辑小等于
    LE("<=", 50, 2),
    // 逻辑大于
    GT(">", 50, 2),
    // 逻辑大等于
    GE(">=", 50, 2),

    // 逻辑等
    EQ("==", 40, 2),
    // 逻辑不等
    NEQ("!=", 40, 2),

    // 逻辑与
    AND("&&", 30, 2),

    // 逻辑或
    OR("||", 20, 2),

    // 三元选择
    QUES("?", 0, 0),
    COLON(":", 0, 0),
    SELECT("?:", 0, 3);

    private static final Map<String, OperatorType> OP_RESERVE_WORD = new HashMap<String, OperatorType>();

    static {

        OP_RESERVE_WORD.put(NOT.getToken(), NOT);
        // OP_RESERVE_WORD.put(NG.getToken(), NG);

        OP_RESERVE_WORD.put(MULT.getToken(), MULT);
        OP_RESERVE_WORD.put(DIV.getToken(), DIV);
        OP_RESERVE_WORD.put(MOD.getToken(), MOD);

        OP_RESERVE_WORD.put(ADD.getToken(), ADD);
        // OP_RESERVE_WORD.put(MINUS.getToken(), MINUS);

        OP_RESERVE_WORD.put(LT.getToken(), LT);
        OP_RESERVE_WORD.put(LE.getToken(), LE);
        OP_RESERVE_WORD.put(GT.getToken(), GT);
        OP_RESERVE_WORD.put(GE.getToken(), GE);

        OP_RESERVE_WORD.put(EQ.getToken(), EQ);
        OP_RESERVE_WORD.put(NEQ.getToken(), NEQ);

        OP_RESERVE_WORD.put(AND.getToken(), AND);

        OP_RESERVE_WORD.put(OR.getToken(), OR);
        OP_RESERVE_WORD.put(SELECT.getToken(), SELECT);
        OP_RESERVE_WORD.put(QUES.getToken(), QUES);
        OP_RESERVE_WORD.put(COLON.getToken(), COLON);
    }


    /**
     * 
     * @param tokenText
     * @return
     */
    public static boolean isLegalOperatorToken(String tokenText) {
        return OP_RESERVE_WORD.containsKey(tokenText);
    }


    public static OperatorType getOperatorType(String tokenText) {
        return OP_RESERVE_WORD.get(tokenText);
    }

    private String token;

    private int priority;

    private int opType;


    OperatorType(String token, int priority, int opType) {
        this.token = token;
        this.priority = priority;
        this.opType = opType;
    }


    public String getToken() {
        return this.token;
    }


    public int getPiority() {
        return this.priority;
    }


    public int getOpType() {
        return this.opType;
    }

}
