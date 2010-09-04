package com.googlecode.aviator.runtime.function.seq;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

import com.googlecode.aviator.runtime.type.AviatorFunction;
import com.googlecode.aviator.runtime.type.AviatorLong;
import com.googlecode.aviator.runtime.type.AviatorObject;


/**
 * count(seq) to count seq's element count
 * 
 * @author dennis
 * 
 */
public class SeqCountFunction implements AviatorFunction {

    public AviatorObject call(Map<String, Object> env, AviatorObject... args) {
        if (args.length != 1) {
            throw new IllegalArgumentException(getName() + " has only one argument");
        }

        Object value = args[0].getValue(env);
        if (value == null) {
            throw new NullPointerException("null seq");
        }
        Class<?> clazz = value.getClass();

        int size = -1;
        if (Collection.class.isAssignableFrom(clazz)) {
            Collection<?> col = (Collection<?>) value;
            size = col.size();
        }
        else if (clazz.isArray()) {
            size = Array.getLength(value);
        }
        else {
            throw new IllegalArgumentException(args[0].desc(env) + " is not a seq");
        }
        return AviatorLong.valueOf(size);
    }


    public String getName() {
        return "count";
    }

}
