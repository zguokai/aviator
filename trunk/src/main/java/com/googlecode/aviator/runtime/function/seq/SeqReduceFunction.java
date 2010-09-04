package com.googlecode.aviator.runtime.function.seq;

import java.util.Collection;
import java.util.Map;

import com.googlecode.aviator.exception.ExpressionRuntimeException;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorFunction;
import com.googlecode.aviator.runtime.type.AviatorJavaType;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.googlecode.aviator.runtime.type.AviatorRuntimeJavaType;


/**
 * reduce(col,fun,init) function to reduce seq with function fun and initial
 * value
 * 
 * @author dennis
 * 
 */
public class SeqReduceFunction implements AviatorFunction {

    public AviatorObject call(Map<String, Object> env, AviatorObject... args) {
        if (args.length != 3) {
            throw new IllegalArgumentException(getName() + " has only three arguments");
        }
        Object first = args[0].getValue(env);
        AviatorFunction fun = FunctionUtils.getFunction(1, args, env, 1);
        if (fun == null) {
            throw new ExpressionRuntimeException("There is no function named " + ((AviatorJavaType) args[1]).getName());
        }
        if (first == null) {
            throw new NullPointerException("null seq");
        }
        AviatorObject result = args[2];
        Class<?> clazz = first.getClass();

        if (Collection.class.isAssignableFrom(clazz)) {
            for (Object obj : (Collection<?>) first) {
                result = fun.call(env, result, new AviatorRuntimeJavaType(obj));
            }
        }
        else if (clazz.isArray()) {
            Object[] seq = (Object[]) first;
            for (Object obj : seq) {
                result = fun.call(env, result, new AviatorRuntimeJavaType(obj));
            }
        }
        else {
            throw new IllegalArgumentException(args[0].desc(env) + " is not a seq");
        }

        return result;
    }


    public String getName() {
        return "reduce";
    }

}
