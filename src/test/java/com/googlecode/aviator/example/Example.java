package com.googlecode.aviator.example;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.googlecode.aviator.AviatorEvaluator;


public class Example {
    public static void main(String[] args) throws Exception {
        Map<String, Object> env = new HashMap<String, Object>();
        env.put("a", 3f);
        env.put("b", 2);
        env.put("c", "99");
        env.put("d", 27.901d);
        env.put("map", new HashMap<String, Object>());
        ((Map<String, Object>) env.get("map")).put("e", "hello");

        Pattern.compile("a\\.b\\/c");
        Object value = AviatorEvaluator.execute("c=~/[\\d.]+/ && 3>1 || 1/2>1", env);
        System.out.println(value);

        // value = AviatorEvaluator.execute("a+c-d/1>b", env);
    }

}
