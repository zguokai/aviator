package com.googlecode.aviator;

import java.util.HashMap;
import java.util.Map;


public class Example {
    public static void main(String[] args) throws Exception {
        Map<String, Object> env = new HashMap<String, Object>();
        env.put("a", 3f);
        env.put("b", 2);
        env.put("c", "99");
        env.put("d", 27.901d);
        Object value = AviatorEvaluator.execute("-true", env);
        System.out.println(value);

        // value = AviatorEvaluator.execute("a+c-d/1>b", env);
    }

}
