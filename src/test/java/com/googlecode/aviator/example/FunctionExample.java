package com.googlecode.aviator.example;

import com.googlecode.aviator.AviatorEvaluator;


public class FunctionExample {
    public static void main(String[] args) {
        System.out.println(AviatorEvaluator.execute("sysdate()"));
        System.out.println(AviatorEvaluator.execute("string.contains('hello','h')"));
        System.out.println(AviatorEvaluator.execute("string.startsWith('hello','h')"));
        System.out.println(AviatorEvaluator.execute("string.endsWith('hello','llo')"));
        System.out.println(AviatorEvaluator.execute("string.substring('hello',1,2)"));
    }

}
