package com.googlecode.aviator;

import java.util.Map;

import com.googlecode.aviator.runtime.type.AviatorJavaType;


public class Test {
    public static Object test(Map<String, Object> a, String name) {
       return new AviatorJavaType(a,name);
    }
}
