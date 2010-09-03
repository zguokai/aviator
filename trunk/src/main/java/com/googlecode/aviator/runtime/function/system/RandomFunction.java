package com.googlecode.aviator.runtime.function.system;

import java.security.SecureRandom;
import java.util.Map;
import java.util.Random;

import com.googlecode.aviator.runtime.type.AviatorDouble;
import com.googlecode.aviator.runtime.type.AviatorFunction;
import com.googlecode.aviator.runtime.type.AviatorObject;


/**
 * rand() function to generate random double value
 * 
 * @author dennis
 * 
 */
public class RandomFunction implements AviatorFunction {

    private static Random random = new SecureRandom();


    public AviatorObject call(Map<String, Object> env, AviatorObject... args) {
        if (args.length > 0) {
            throw new IllegalArgumentException("rand() only have one argument");
        }
        return AviatorDouble.valueOf(random.nextDouble());
    }


    public String getName() {
        return "rand";
    }

}
