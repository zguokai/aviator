package com.googlecode.aviator.runtime.type;

import java.util.Map;

/**
 * A aviator function
 * 
 * @author dennis
 * 
 */
public interface AviatorFunction {
    public String getName();
    public AviatorObject call(Map<String, Object> env, AviatorObject... args);
}
