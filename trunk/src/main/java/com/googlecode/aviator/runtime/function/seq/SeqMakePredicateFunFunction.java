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
    private final AviatorObject value;


    public SeqMakePredicateFunFunction(String name, OperatorType opType) {
        this(name, opType, null);
    }


    public SeqMakePredicateFunFunction(String name, OperatorType opType, AviatorObject value) {
        super();
        this.name = name;
        this.opType = opType;
        this.value = value;
    }


    public AviatorObject call(Map<String, Object> env, AviatorObject... args) {

        // generate a temp function object as predicate
        AviatorFunction fun = new SeqPredicateFunction(name, opType, value == null ? args[0] : value);
        final String funName = name + "_tmp_" + System.nanoTime();
        env.put(funName, fun);
        return new AviatorJavaType(funName);
    }


    public String getName() {
        return name;
    }

}
