package com.googlecode.aviator.runtime.function.seq;

import java.util.Map;

import com.googlecode.aviator.exception.ExpressionRuntimeException;
import com.googlecode.aviator.lexer.token.OperatorType;
import com.googlecode.aviator.runtime.type.AviatorBoolean;
import com.googlecode.aviator.runtime.type.AviatorFunction;
import com.googlecode.aviator.runtime.type.AviatorObject;


public class SeqPredicateFunction implements AviatorFunction {
    private final String name;
    private final OperatorType opType;
    private final AviatorObject value;


    public SeqPredicateFunction(String name, OperatorType opType, AviatorObject value) {
        super();
        this.name = name;
        this.opType = opType;
        this.value = value;
    }


    public AviatorObject call(Map<String, Object> env, AviatorObject... args) {
        if (args.length != opType.getOperandCount() - 1) {
            throw new ExpressionRuntimeException(getName() + " only have " + opType.getOperandCount() + " operands");
        }
        switch (opType) {
        case EQ:
            return args[0].compare(value, env) == 0 ? AviatorBoolean.TRUE : AviatorBoolean.FALSE;
        case NEQ:
            return args[0].compare(value, env) != 0 ? AviatorBoolean.TRUE : AviatorBoolean.FALSE;
        case LT:
            return args[0].compare(value, env) < 0 ? AviatorBoolean.TRUE : AviatorBoolean.FALSE;
        case LE:
            return args[0].compare(value, env) <= 0 ? AviatorBoolean.TRUE : AviatorBoolean.FALSE;
        case GE:
            return args[0].compare(value, env) >= 0 ? AviatorBoolean.TRUE : AviatorBoolean.FALSE;
        case GT:
            return args[0].compare(value, env) > 0 ? AviatorBoolean.TRUE : AviatorBoolean.FALSE;
        default:
            throw new ExpressionRuntimeException(getName() + " is not a relation operator");
        }
    }


    public String getName() {
        return name;
    }

}
