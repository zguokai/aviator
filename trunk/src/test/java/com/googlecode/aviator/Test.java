package com.googlecode.aviator;

import com.googlecode.aviator.runtime.type.AviatorObject;


public class Test {
    public static Object test(AviatorObject a, int b, int c) {
        return a.booleanValue() ? b : c;
    }
}
