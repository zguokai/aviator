package com.googlecode.aviator.runtime;

import java.util.Map;


/**
 * Runtime type converter
 * 
 * @author dennis
 * 
 */
public class TypeConverter {
    public static Double toDouble(Object obj) {
        if (obj instanceof Double) {
            return (Double) obj;
        }
        else if (obj instanceof Float) {
            return ((Float) obj).doubleValue();
        }
        else if (obj instanceof Long) {
            return ((Long) obj).doubleValue();
        }
        else if (obj instanceof Byte) {
            return ((Byte) obj).doubleValue();
        }
        else if (obj instanceof Short) {
            return ((Short) obj).doubleValue();
        }
        else if (obj instanceof Integer) {
            return ((Integer) obj).doubleValue();
        }
        else {
            throw new ClassCastException(obj + " can not be casted to double");
        }
    }


    public static boolean isDouble(Object obj) {
        if (obj instanceof Double || obj instanceof Float) {
            return true;
        }
        return false;
    }


    public static Object getVariable(Map<String, Object> env, String name) {
        return env.get(name);
    }


    public static boolean isString(Object obj) {
        return obj instanceof String;
    }

}
