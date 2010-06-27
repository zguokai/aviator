package com.googlecode.aviator.test.function;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.googlecode.aviator.AviatorEvaluator;


public class FunctionTest {
    @Test
    public void testArithmeticExpression() {
        assertEquals(1 + 2 + 3 + 4 + 5 + 6 + 7 + 8 + 9 + 10, AviatorEvaluator.execute("1+2+3+4+5+6+7+8+9+10"));
        assertEquals(0, AviatorEvaluator.execute("1+2-3"));
        assertEquals(120, AviatorEvaluator.execute("1*2*3*4*5"));
        assertEquals(-4, AviatorEvaluator.execute("1-2-3"));
        assertEquals(2, AviatorEvaluator.execute("1-(2-3)"));

        assertEquals(50, AviatorEvaluator.execute("100/2"));
        assertEquals(33, AviatorEvaluator.execute("100/3"));

        assertEquals(-49, AviatorEvaluator.execute("1-100/2"));
        assertEquals(51, AviatorEvaluator.execute("1+100/2"));
        assertEquals(6 - (4 / 2 - (4 + 5)) * 2 + 100 / (2 + 1) * 20 - 5 * 5 * 5 + (6 + 1) / (2 - 3 / (1 + 1)),
            AviatorEvaluator.execute("6-(4/2-(4+5))*2+100/(2+1)*20-5*5*5+(6+1)/(2-3/(1+1))"));

        assertEquals(62.8, AviatorEvaluator.execute("2*3.14*10"));
        assertEquals(96.3, AviatorEvaluator.execute("100.3-4"));
        assertEquals(-96.3, AviatorEvaluator.execute("4-100.3"));
        assertEquals(100.3 / 4 - (4.0 / 2 + 5), AviatorEvaluator.execute("100.3/4-(4.0/2+5)"));

        assertEquals(1, AviatorEvaluator.execute("100%3"));
        assertEquals(0, AviatorEvaluator.execute("1-100%3"));
        assertEquals(100 % 3 * 4.2 + (37 + 95) / (6 * 3 - 18.0), (Double) AviatorEvaluator
            .execute("100%3*4.2+(37+95)/(6*3-18.0)"), 0.0001);
    }


    @Test
    public void testArithmeticExpressionWithVariable() {
        Map<String, Object> env = new HashMap<String, Object>();
        int i = 100;
        float pi = 3.14f;
        double d = -3.9;
        byte b = (byte) 4;
        env.put("i", i);
        env.put("pi", pi);
        env.put("d", d);
        env.put("b", b);

        assertEquals(-100, AviatorEvaluator.execute("-i", env));
        assertEquals(-103.4, AviatorEvaluator.execute("-i-pi", env));
        assertEquals(2 * 3.14 * 10, (Double) AviatorEvaluator.execute("2*pi*10", env), 0.001);
        assertEquals(3.14 * d * d, (Double) AviatorEvaluator.execute("pi*d*d", env), 0.001);

        assertEquals((i + pi + d + b) / 4, AviatorEvaluator.execute("(i+pi+d+b)/4", env));
        assertEquals(200, AviatorEvaluator.execute("i+100", env));
        assertEquals(0, AviatorEvaluator.execute("i%4", env));
        assertEquals(i * pi + (d * b - 199) / (1 - d * pi) - (2 + 100 - i / pi) % 99, AviatorEvaluator.execute(
            "i * pi + (d * b - 199) / (1 - d * pi) - (2 + 100 - i / pi) % 99", env));
    }


    @Test
    public void testOperatorPrecedence() {
        assertEquals(false, AviatorEvaluator
            .execute("6.7-100>39.6 ? 5==5? 4+5:6-1 : !false ? 5-6>0&& false: 100%3<=5 || 67*40>=100"));
    }


    @Test
    public void testLogicExpression() {
        assertTrue((Boolean) AviatorEvaluator.execute("3+1==4"));
        assertTrue((Boolean) AviatorEvaluator.execute("3+1>=4"));
        assertTrue((Boolean) AviatorEvaluator.execute("3+1<=4"));
        assertFalse((Boolean) AviatorEvaluator.execute("3+1>4"));
        assertFalse((Boolean) AviatorEvaluator.execute("3+1<4"));

        assertTrue((Boolean) AviatorEvaluator.execute("100/2-50==0"));
        assertTrue((Boolean) AviatorEvaluator.execute("3-(1+2)==0"));
        assertTrue((Boolean) AviatorEvaluator.execute("3-4/2==1"));

        assertTrue((Boolean) AviatorEvaluator.execute("3<1 || -3-100<0 && !(100%3>100)"));
        assertTrue((Boolean) AviatorEvaluator.execute("3>1 || -3-100<0 && !(100%3<100)"));
        assertFalse((Boolean) AviatorEvaluator.execute("(3>1 || -3-100<0 )&& !(100%3<100)"));
        assertFalse((Boolean) AviatorEvaluator.execute("3<1 || -3-100<0 && !(100%3<100)"));
    }


    @Test
    public void testLogicExpressionWithVariable() {
        Map<String, Object> env = new HashMap<String, Object>();
        int i = 100;
        float pi = 3.14f;
        double d = -3.9;
        byte b = (byte) 4;
        env.put("i", i);
        env.put("pi", pi);
        env.put("d", d);
        env.put("b", b);
        env.put("bool", false);

        assertEquals(false, AviatorEvaluator.execute("-i>=0", env));
        assertEquals(true, AviatorEvaluator.execute("-i-pi<=-100", env));
        assertEquals(true, AviatorEvaluator.execute("2*pi*10==2 * pi * 10", env));
        assertEquals(true, AviatorEvaluator.execute("pi*d*d == pi* d *d", env));

        assertEquals((i+pi+d+b)/4%2>0, AviatorEvaluator.execute("(i+pi+d+b)/4%2>0", env));
        assertEquals(true, AviatorEvaluator.execute("(i+100)%3!=1", env));
        assertEquals(true, AviatorEvaluator.execute("i%4<=0", env));
        assertEquals(
            true,
            AviatorEvaluator
                .execute(
                    "i * pi + (d * b - 199) / (1 - d * pi) - (2 + 100 - i / pi) % 99 ==i * pi + (d * b - 199) / (1 - d * pi) - (2 + 100 - i / pi) % 99",
                    env));
    }
}
