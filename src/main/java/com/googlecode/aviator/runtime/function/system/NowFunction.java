package com.googlecode.aviator.runtime.function.system;

import java.util.Map;

import com.googlecode.aviator.runtime.type.AviatorFunction;
import com.googlecode.aviator.runtime.type.AviatorLong;
import com.googlecode.aviator.runtime.type.AviatorObject;


/**
 * now() function to invoke System.currentTimeMillis()
 * 
 * @author dennis
 * 
 */
public class NowFunction implements AviatorFunction {

    public AviatorObject call(Map<String, Object> env, AviatorObject... args) {
        if (args.length > 0) {
            throw new IllegalArgumentException("now() only have one argument");
        }
        return AviatorLong.valueOf(System.currentTimeMillis());
    }


    public String getName() {
        return "now";
    }

}
