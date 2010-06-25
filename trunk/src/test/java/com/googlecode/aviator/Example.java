package com.googlecode.aviator;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;


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
        Object value = AviatorEvaluator.execute("'3.4.5'=~/[\\d.]+/", env);
        System.out.println(value);

        // value = AviatorEvaluator.execute("a+c-d/1>b", env);
    }

}
