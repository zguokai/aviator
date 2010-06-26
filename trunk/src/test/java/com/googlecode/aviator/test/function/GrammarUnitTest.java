package com.googlecode.aviator.test.function;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.googlecode.aviator.AviatorEvaluator;


/**
 * Aviator grammar test
 * 
 * @author dennis
 * 
 */
public class GrammarUnitTest {

    /**
     * 类型测试
     */
    @Test
    public void testType() {
        assertTrue(AviatorEvaluator.execute("1") instanceof Long);
        assertTrue(AviatorEvaluator.execute("3.2") instanceof Double);
        assertTrue(AviatorEvaluator.execute(Long.MAX_VALUE + "") instanceof Long);
        assertTrue(AviatorEvaluator.execute("3.14159265") instanceof Double);

        assertEquals("hello world", AviatorEvaluator.execute("'hello world'"));
        assertEquals("hello world", AviatorEvaluator.execute("\"hello world\""));
        assertEquals("hello \" world", AviatorEvaluator.execute("'hello \" world'"));
        assertEquals("hello 'world'", AviatorEvaluator.execute("\"hello 'world'\""));
        assertEquals("hello 'world' 'dennis'", AviatorEvaluator.execute("\"hello 'world' 'dennis'\""));

        assertTrue((Boolean) AviatorEvaluator.execute("true"));
        assertFalse((Boolean) AviatorEvaluator.execute("false"));

        assertEquals("\\w+\\d?\\..*", AviatorEvaluator.execute("/\\w+\\d?\\..*/"));
        Map<String, Object> env = new HashMap<String, Object>();
        env.put("_a", 3);
        assertEquals(3, AviatorEvaluator.execute("_a", env));
        long now = System.currentTimeMillis();
        env.put("currentTime", now);
        assertEquals(now, AviatorEvaluator.execute("currentTime", env));

    }

    public class Foo {
        int a;


        public Foo() {

        }


        public Foo(int a) {
            super();
            this.a = a;
        }


        public int getA() {
            return a;
        }


        public void setA(int a) {
            this.a = a;
        }

    }

    public class Bar extends Foo {
        int b;


        public Bar() {

        }


        public Bar(int a, int b) {
            super(a);
            this.b = b;
        }


        public int getB() {
            return b;
        }


        public void setB(int b) {
            this.b = b;
        }

    }


    /**
     * 类型转换
     */
    @Test
    public void testTypeConversation() {
        Map<String, Object> env = new HashMap<String, Object>();
        env.put("foo", new Foo(100));
        env.put("bar", new Bar(99, 999));
        env.put("date", new Date());

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("key", "aviator");
        env.put("map", map);
        env.put("bool", Boolean.FALSE);

        // long op long=long
        assertTrue(AviatorEvaluator.execute("3+3") instanceof Long);
        assertTrue(AviatorEvaluator.execute("3+3/2") instanceof Long);
        assertTrue(AviatorEvaluator.execute("foo.a+bar.a", env) instanceof Long);
        assertEquals(1098L, AviatorEvaluator.execute("bar.a+bar.b", env));

        // double op double=double
        assertTrue(AviatorEvaluator.execute("3.2+3.3") instanceof Double);
        assertTrue(AviatorEvaluator.execute("3.01+3.1/2.1") instanceof Double);
        assertTrue(AviatorEvaluator.execute("3.19+3.1/2.9-1.0/(6.0002*7.7+8.9)") instanceof Double);

        // double + long=double
        assertTrue(AviatorEvaluator.execute("3+0.02") instanceof Double);
        assertTrue(AviatorEvaluator.execute("3+0.02-100") instanceof Double);
        assertTrue(AviatorEvaluator.execute("3+3/2-1/(6*7+8.0)") instanceof Double);
        assertTrue(AviatorEvaluator.execute("foo.a+3.2-1000", env) instanceof Double);

        // object + string =string
        assertEquals("hello world", AviatorEvaluator.execute("'hello '+ 'world'"));
        assertEquals("hello aviator", AviatorEvaluator.execute("'hello '+map.key", env));
        assertEquals("true aviator", AviatorEvaluator.execute("true+' '+map.key", env));
        assertEquals("100aviator", AviatorEvaluator.execute("foo.a+map.key", env));
        assertEquals("\\d+hello", AviatorEvaluator.execute("/\\d+/+'hello'"));
        assertEquals("3.2aviator", AviatorEvaluator.execute("3.2+map.key", env));
        assertEquals("false is false", AviatorEvaluator.execute("bool+' is false'", env));

    }


    /**
     * 操作符的操作数限制
     */
    @Test
    public void testOperatorLimit() {

    }
}
