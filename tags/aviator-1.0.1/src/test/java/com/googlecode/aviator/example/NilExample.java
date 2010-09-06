package com.googlecode.aviator.example;

import com.googlecode.aviator.AviatorEvaluator;


public class NilExample {
    public static void main(String[] args) {

        AviatorEvaluator.execute("nil == nil");
        AviatorEvaluator.execute(" 3> nil");
        AviatorEvaluator.execute(" ' '>nil ");
        AviatorEvaluator.execute(" a==nil ");
    }
}
