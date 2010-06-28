package com.googlecode.aviator;

import org.junit.Ignore;
import org.junit.Test;


public class PressureTest {
    final int count = 10000000;


    @Test
    @Ignore
    public void testSinlgeThread() {
        Object result = AviatorEvaluator.execute("Math.abs(-3)");
        System.out.println(result);
    }
}
