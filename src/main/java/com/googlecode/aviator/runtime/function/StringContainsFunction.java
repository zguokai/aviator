package com.googlecode.aviator.runtime.function;

import java.util.Map;

import com.googlecode.aviator.runtime.type.AviatorBoolean;
import com.googlecode.aviator.runtime.type.AviatorFunction;
import com.googlecode.aviator.runtime.type.AviatorObject;


public class StringContainsFunction implements AviatorFunction {

    public String getName() {
        return "string.contains";
    }


    public AviatorObject call(Map<String, Object> env, AviatorObject... args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("string.contains(string,string) just need two arguments");
        }

        String target = FunctionUtils.getStringValue(0, args, env);
        String param = FunctionUtils.getStringValue(1, args, env);

        return target.indexOf(param) >= 0 ? AviatorBoolean.TRUE : AviatorBoolean.FALSE;
    }

}
