package com.googlecode.aviator.runtime.function.seq;

import java.util.Map;

import com.googlecode.aviator.lexer.token.OperatorType;
import com.googlecode.aviator.runtime.type.AviatorFunction;
import com.googlecode.aviator.runtime.type.AviatorJavaType;
import com.googlecode.aviator.runtime.type.AviatorObject;


/**
 * function to make predicate function for filter
 * 
 * @author dennis
 * 
 */
public class SeqMakePredicateFunFunction implements AviatorFunction {
    private final String name;
    private final OperatorType opType;


    public SeqMakePredicateFunFunction(String name, OperatorType opType) {
        super();
        this.name = name;
        this.opType = opType;
    }


    public AviatorObject call(Map<String, Object> env, AviatorObject... args) {
        AviatorFunction fun = new SeqPredicateFunction(name, opType, args[0]);
        final String funName = name + "_tmp_" + System.nanoTime();
        env.put(funName, fun);
        return new AviatorJavaType(funName);
    }


    public String getName() {
        return name;
    }

}
