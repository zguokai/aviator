package com.googlecode.aviator;

import org.apache.commons.beanutils.PropertyUtils;
import org.junit.Ignore;
import org.junit.Test;


public class PressureTest {
    final int count = 10000000;


    public static void main(String[] args) throws Exception {
        int[] a = new int[2];
        System.out.println(PropertyUtils.getProperty(a, "1"));

    }


    @Test
    @Ignore
    public void testSinlgeThread() {
        Object result = AviatorEvaluator.execute("Math.abs(-3)");
        System.out.println(result);
    }
}
