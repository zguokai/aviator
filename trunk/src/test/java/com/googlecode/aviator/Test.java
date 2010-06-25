package com.googlecode.aviator;

import java.util.Map;



public class Test {
    public static Object test(Object a) {
       return ((Map<String, Object>)a).get("c");
    }
}
