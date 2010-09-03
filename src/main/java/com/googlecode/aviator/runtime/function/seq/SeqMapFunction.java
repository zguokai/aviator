package com.googlecode.aviator.runtime.function.seq;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.googlecode.aviator.exception.ExpressionRuntimeException;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorFunction;
import com.googlecode.aviator.runtime.type.AviatorJavaType;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.googlecode.aviator.runtime.type.AviatorRuntimeJavaType;


/**
 * map(col,fun) function
 * 
 * @author dennis
 * 
 */
public class SeqMapFunction implements AviatorFunction {

    @SuppressWarnings("unchecked")
    public AviatorObject call(Map<String, Object> env, AviatorObject... args) {
        if (args.length != 2) {
            throw new IllegalArgumentException(getName() + " has only two arguments");
        }
        Iterable<?> seq = FunctionUtils.getSeq(0, args, env);
        AviatorFunction fun = FunctionUtils.getFunction(1, args, env);
        if (fun == null) {
            throw new ExpressionRuntimeException("There is no function named " + ((AviatorJavaType) args[1]).getName());
        }
        List result = new ArrayList();
        for (Object obj : seq) {
            result.add(fun.call(env, new AviatorRuntimeJavaType(obj)).getValue(env));
        }
        return new AviatorRuntimeJavaType(result);
    }


    public String getName() {
        return "map";
    }

}
