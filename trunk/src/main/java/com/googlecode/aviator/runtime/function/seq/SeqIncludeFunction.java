package com.googlecode.aviator.runtime.function.seq;

import java.util.Collection;
import java.util.Map;

import com.googlecode.aviator.runtime.type.AviatorBoolean;
import com.googlecode.aviator.runtime.type.AviatorFunction;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.googlecode.aviator.runtime.type.AviatorRuntimeJavaType;


/**
 * include(seq,obj) function to check if collection contains object
 * 
 * @author dennis
 * 
 */
public class SeqIncludeFunction implements AviatorFunction {

    public AviatorObject call(Map<String, Object> env, AviatorObject... args) {
        if (args.length != 2) {
            throw new IllegalArgumentException(getName() + " has only two arguments");
        }
        Object first = args[0].getValue(env);
        AviatorObject second = args[1];
        if (first == null) {
            throw new NullPointerException("null seq");
        }
        Class<?> clazz = first.getClass();
        boolean contains = false;
        if (Collection.class.isAssignableFrom(clazz)) {
            Collection<?> seq = (Collection<?>) first;
            for (Object obj : seq) {
                if (new AviatorRuntimeJavaType(obj).compare(second, env) == 0) {
                    contains = true;
                    break;
                }
            }
        }
        else if (clazz.isArray()) {
            Object[] seq = (Object[]) first;
            for (Object obj : seq) {
                if (new AviatorRuntimeJavaType(obj).compare(second, env) == 0) {
                    contains = true;
                    break;
                }
            }
        }
        else {
            throw new IllegalArgumentException(args[0].desc(env) + " is not a seq");
        }

        return AviatorBoolean.valueOf(contains);
    }


    public String getName() {
        return "include";
    }

}
