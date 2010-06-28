package com.googlecode.aviator.runtime.function;

import java.util.Date;
import java.util.Map;

import com.googlecode.aviator.runtime.type.AviatorFunction;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.googlecode.aviator.runtime.type.AviatorRuntimeJavaType;


public class NowFunction implements AviatorFunction {

    public String getName() {
        return "sysdate";
    }


    public AviatorObject call(Map<String, Object> env, AviatorObject... args) {
        if (args.length > 0) {
            throw new IllegalArgumentException("now() doesn't need any arguments");
        }
        return new AviatorRuntimeJavaType(new Date());
    }

}
