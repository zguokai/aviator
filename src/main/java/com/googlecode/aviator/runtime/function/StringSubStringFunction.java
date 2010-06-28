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
        switch (args.length) {
        case 2:
            String target = (String) args[0].getValue(env);
            Number beginIndex = (Number) args[1].getValue(env);
            return new AviatorString(target.substring(beginIndex.intValue()));
        case 3:
            target = (String) args[0].getValue(env);
            beginIndex = (Number) args[1].getValue(env);
            Number endIndex = (Number) args[2].getValue(env);
            return new AviatorString(target.substring(beginIndex.intValue(), endIndex.intValue()));

        }
        return null;
    }
}
