package com.googlecode.aviator;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.aviator.runtime.method.AviatorMethod;
import com.googlecode.aviator.runtime.type.AviatorObject;


public class Test {
    public static Object test(AviatorMethod method, AviatorObject a, AviatorObject b) {
        List<AviatorObject> list = new ArrayList<AviatorObject>();
        list.add(a);
        list.add(b);
        return method.invoke(null, list);

    }
}
