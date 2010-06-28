package com.googlecode.aviator.runtime.function;

import java.util.Map;

import com.googlecode.aviator.runtime.type.AviatorObject;

public class FunctionUtils {

    public static final String getStringValue(int index, AviatorObject[] args, Map<String, Object> env) {
        String target = null;
    
        final Object leftValue = args[index].getValue(env);
        if (leftValue instanceof Character) {
            target = leftValue.toString();
        }
        else {
            target = (String) leftValue;
        }
        return target;
    }
    
}
