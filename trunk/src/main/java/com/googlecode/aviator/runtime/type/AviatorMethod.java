package com.googlecode.aviator.runtime.type;

import java.util.List;
import java.util.Map;

import com.googlecode.aviator.exception.ExpressionRuntimeException;


/**
 * A aviator function
 * 
 * @author dennis
 * 
 */
public class AviatorMethod extends AviatorObject {
    private final String methodName;
    private volatile AviatorFunction cachedFunction;


    public AviatorMethod(String methodName) {
        super();
        this.methodName = methodName;
    }


    public AviatorObject invoke(Map<String, Object> env, List<AviatorObject> list) {
        if (cachedFunction == null) {
            cachedFunction = (AviatorFunction) env.get(this.methodName);
        }
        if (cachedFunction == null) {
            throw new ExpressionRuntimeException("Could not find method named " + methodName);
        }
        final AviatorObject result = cachedFunction.call(env, list.toArray(new AviatorObject[list.size()]));
        return result == null ? AviatorNil.NIL : result;
    }


    @Override
    public int compare(AviatorObject other, Map<String, Object> env) {
        throw new UnsupportedOperationException("Method could not be compared");
    }


    @Override
    public AviatorType getAviatorType() {
        return AviatorType.Method;
    }


    @Override
    public Object getValue(Map<String, Object> env) {
        throw new UnsupportedOperationException("Method has no value");
    }

}
