package com.googlecode.aviator.runtime.type;

import java.util.Map;


/**
 * Aviator nil object
 * 
 * @author dennis
 * 
 */
public class AviatorNil extends AviatorObject {
    public static final AviatorNil NIL = new AviatorNil();


    private AviatorNil() {

    }


    @Override
    public int compare(AviatorObject other, Map<String, Object> env) {
        switch (other.getAviatorType()) {
        case Nil:
            return 0;
        case JavaType:
            if (other.getValue(env) == null) {
                return 0;
            }
        }
        // Any object is greater than nil except nil
        return -1;
    }


    @Override
    public AviatorType getAviatorType() {
        return AviatorType.Nil;
    }


    @Override
    public Object getValue(Map<String, Object> env) {
        return null;
    }

}
