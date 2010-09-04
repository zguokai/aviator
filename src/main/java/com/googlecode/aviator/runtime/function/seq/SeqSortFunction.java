package com.googlecode.aviator.runtime.function.seq;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.googlecode.aviator.runtime.type.AviatorFunction;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.googlecode.aviator.runtime.type.AviatorRuntimeJavaType;


/**
 * sort(list) function to sort java.util.List or array,return a sorted duplicate
 * object
 * 
 * @author dennis
 * 
 */
public class SeqSortFunction implements AviatorFunction {

    public AviatorObject call(Map<String, Object> env, AviatorObject... args) {
        if (args.length != 1) {
            throw new IllegalArgumentException(getName() + " has only one argument");
        }

        Object first = args[0].getValue(env);
        if (first == null) {
            throw new NullPointerException("null seq");
        }
        Class<?> clazz = first.getClass();

        if (List.class.isAssignableFrom(clazz)) {
            List<?> list = (List<?>) first;
            Object[] a = list.toArray();
            Arrays.sort(a);
            return new AviatorRuntimeJavaType(Arrays.asList(a));
        }
        else if (clazz.isArray()) {
            Object[] array = (Object[]) first;
            Object[] dup = (Object[]) Array.newInstance(array.getClass().getComponentType(), array.length);
            System.arraycopy(array, 0, dup, 0, dup.length);
            Arrays.sort(dup);
            return new AviatorRuntimeJavaType(dup);
        }
        else {
            throw new IllegalArgumentException(args[0].desc(env) + " is not a seq");
        }
    }


    public String getName() {
        return "sort";
    }

}
