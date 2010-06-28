package com.googlecode.aviator.runtime.function;

import java.util.Map;

import com.googlecode.aviator.runtime.type.AviatorFunction;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.googlecode.aviator.runtime.type.AviatorString;


public class StringSubStringFunction implements AviatorFunction {
    public String getName() {
        return "string.substring";
    }


    public AviatorObject call(Map<String, Object> env, AviatorObject... args) {
        if (args.length != 2 && args.length != 3) {
            throw new IllegalArgumentException("string.endsWith(string,int[,int]) just need two or three arguments");
        }
        String target = FunctionUtils.getStringValue(0, args, env);
        switch (args.length) {
        case 2:
            Number beginIndex = (Number) args[1].getValue(env);
            return new AviatorString(target.substring(beginIndex.intValue()));
        case 3:
            beginIndex = (Number) args[1].getValue(env);
            Number endIndex = (Number) args[2].getValue(env);
            return new AviatorString(target.substring(beginIndex.intValue(), endIndex.intValue()));

        }
        return null;
    }
}
