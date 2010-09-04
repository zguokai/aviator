package com.googlecode.aviator.runtime.function.system;

import java.util.Map;

import com.googlecode.aviator.exception.ExpressionRuntimeException;
import com.googlecode.aviator.lexer.token.OperatorType;
import com.googlecode.aviator.runtime.type.AviatorFunction;
import com.googlecode.aviator.runtime.type.AviatorObject;


/**
 * Binary function,includes +,-,*,/,%,!
 * 
 * @author dennis
 * 
 */
public class BinaryFunction implements AviatorFunction {
    private final OperatorType opType;


    public BinaryFunction(OperatorType opType) {
        super();
        this.opType = opType;
    }


    public String getName() {
        return opType.getToken();
    }


    public OperatorType getOpType() {
        return opType;
    }


    public AviatorObject call(Map<String, Object> env, AviatorObject... args) {
        if (args.length != this.opType.getOperandCount()) {
            throw new IllegalArgumentException("println has only one argument");
        }
        AviatorObject left = args[0];
        switch (opType) {
        case ADD:
            AviatorObject right = args[1];
            return left.add(right, env);
        case SUB:
            right = args[1];
            return left.sub(right, env);
        case MULT:
            right = args[1];
            return left.mult(right, env);
        case DIV:
            right = args[1];
            return left.div(right, env);
        case MOD:
            right = args[1];
            return left.mod(right, env);
        case NOT:
            return left.not(env);
        case NEG:
            return left.neg(env);
        default:
            throw new ExpressionRuntimeException(getName() + " is not a binary operation");

        }
    }

}
