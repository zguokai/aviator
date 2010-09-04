package com.googlecode.aviator.runtime.function.seq;

import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import com.googlecode.aviator.runtime.type.AviatorFunction;
import com.googlecode.aviator.runtime.type.AviatorNil;
import com.googlecode.aviator.runtime.type.AviatorObject;


/**
 * sort(list) function to sort java.util.List or array
 * 
 * @author dennis
 * 
 */
public class SeqSortFunction implements AviatorFunction {

    @SuppressWarnings("unchecked")
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
            ListIterator i = list.listIterator();
            for (int j = 0; j < a.length; j++) {
                i.next();
                i.set(a[j]);
            }
        }
        else if (clazz.isArray()) {
            Object[] array = (Object[]) first;
            Arrays.sort(array);
        }
        else {
            throw new IllegalArgumentException(args[0].desc(env) + " is not a seq");
        }
        return AviatorNil.NIL;
    }


    public String getName() {
        return "sort";
    }

}
