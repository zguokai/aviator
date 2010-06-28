package com.googlecode.aviator.runtime.function;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.googlecode.aviator.runtime.type.AviatorBoolean;
import com.googlecode.aviator.runtime.type.AviatorFunction;
import com.googlecode.aviator.runtime.type.AviatorJavaType;
import com.googlecode.aviator.runtime.type.AviatorLong;
import com.googlecode.aviator.runtime.type.AviatorNil;
import com.googlecode.aviator.runtime.type.AviatorString;


public class StringStartsWithFunctionUnitTest {
    private AviatorFunction function;


    @Before
    public void setUp() {
        this.function = new StringSubStringFunction();
    }


    @Test
    public void testCall() {
        Map<String, Object> env = new HashMap<String, Object>();
        env.put("s1", "hello");
        env.put("begin", 2L);
        env.put("end", 4);

        assertEquals("llo", this.function.call(null, new AviatorString("hello"), new AviatorLong(2)).getValue(null));
        assertEquals("l", this.function.call(null, new AviatorString("hello"), new AviatorLong(2), new AviatorLong(3))
            .getValue(null));
        assertEquals("ll", this.function.call(env, new AviatorString("hello"), new AviatorJavaType("begin"),
            new AviatorJavaType("end")).getValue(env));
        assertEquals("o", this.function.call(env, new AviatorString("hello"), new AviatorJavaType("end")).getValue(env));
        assertEquals("ll", this.function.call(env, new AviatorJavaType("s1"), new AviatorJavaType("begin"),
            new AviatorJavaType("end")).getValue(env));
        assertEquals("llo", this.function.call(env, new AviatorJavaType("s1"), new AviatorLong(2), new AviatorLong(5))
            .getValue(env));
    }


    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArguments_null() {
        this.function.call(null);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArguments_one() {
        this.function.call(null, new AviatorString("hello"));
    }


    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArguments_Four() {
        this.function.call(null, new AviatorString("hello"), new AviatorString("hello"), new AviatorString("hello"),
            new AviatorString("hello"));
    }


    @Test(expected = ClassCastException.class)
    public void testClassCastError1() {
        this.function.call(null, AviatorBoolean.TRUE, new AviatorString("hello"));
    }


    @Test(expected = ClassCastException.class)
    public void testClassCastError2() {
        this.function.call(null, new AviatorString("hello"), new AviatorLong(3), AviatorBoolean.valueOf(Boolean.TRUE));
    }


    @Test(expected = NullPointerException.class)
    public void testNullPointerException1() {
        this.function.call(null, new AviatorString("hello"), AviatorNil.NIL);
    }


    @Test(expected = NullPointerException.class)
    public void testNullPointerException2() {
        this.function.call(null, AviatorNil.NIL, new AviatorLong(2), new AviatorLong(4));
    }


    @Test(expected = NullPointerException.class)
    public void testNullPointerException3() {
        this.function.call(null, new AviatorString("hello"), new AviatorLong(2), null);
    }

}
