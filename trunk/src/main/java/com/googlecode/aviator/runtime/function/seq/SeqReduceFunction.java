package com.googlecode.aviator.runtime.function.seq;

import java.util.Map;

import com.googlecode.aviator.exception.ExpressionRuntimeException;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorFunction;
import com.googlecode.aviator.runtime.type.AviatorJavaType;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.googlecode.aviator.runtime.type.AviatorRuntimeJavaType;


/**
 * seq.reduce(col,fun,init) function
 * 
 * @author dennis
 * 
 */
public class SeqReduceFunction implements AviatorFunction {

    public AviatorObject call(Map<String, Object> env, AviatorObject... args) {
        if (args.length != 3) {
            throw new IllegalArgumentException(getName() + " has only three arguments");
        }
        Iterable<?> seq = FunctionUtils.getSeq(0, args, env);
        AviatorFunction fun = FunctionUtils.getFunction(1, args, env);
        AviatorObject init = args[2];
        if (fun == null) {
            throw new ExpressionRuntimeException("There is no function named " + ((AviatorJavaType) args[1]).getName());
        }
        for (Object obj : seq) {
            init = fun.call(env, init, new AviatorRuntimeJavaType(obj));
        }
        return init;
    }


    public String getName() {
        return "seq.reduce";
    }

}
