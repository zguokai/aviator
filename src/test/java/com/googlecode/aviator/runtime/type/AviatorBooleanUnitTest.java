package com.googlecode.aviator.runtime.type;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.googlecode.aviator.exception.ExpressionRuntimeException;


public class AviatorBooleanUnitTest {

    @Test
    public void testAddString() {
        AviatorBoolean aviatorBoolean = new AviatorBoolean(Boolean.TRUE);
        AviatorString aviatorString = new AviatorString(" is true");
        assertEquals("true is true", aviatorBoolean.add(aviatorString, null).getValue(null));

    }


    @Test
    public void testAddJavaString() {
        AviatorBoolean aviatorBoolean = new AviatorBoolean(Boolean.TRUE);
        AviatorJavaType aviatorString = new AviatorJavaType("s");
        assertEquals("true is true", aviatorBoolean.add(aviatorString, createEnvWith("s", " is true")).getValue(null));

    }


    @Test
    public void testAddJavaChar() {
        AviatorBoolean aviatorBoolean = new AviatorBoolean(Boolean.TRUE);
        AviatorJavaType aviatorString = new AviatorJavaType("c");
        assertEquals("trueg", aviatorBoolean.add(aviatorString, createEnvWith("c", 'g')).getValue(null));

    }


    private Map<String, Object> createEnvWith(String name, Object obj) {
        Map<String, Object> env = new HashMap<String, Object>();
        env.put(name, obj);
        return env;
    }


    @Test(expected = ExpressionRuntimeException.class)
    public void testAddNumber() {
        AviatorBoolean aviatorBoolean = new AviatorBoolean(Boolean.TRUE);
        aviatorBoolean.add(AviatorNumber.valueOf(1), null);
    }


    @Test(expected = ExpressionRuntimeException.class)
    public void testAddJavaType() {
        AviatorBoolean aviatorBoolean = new AviatorBoolean(Boolean.TRUE);
        aviatorBoolean.add(new AviatorJavaType("a"), null);
    }


    @Test
    public void testCompareBoolean() {
        AviatorBoolean aviatorBoolean1 = new AviatorBoolean(Boolean.TRUE);
        AviatorBoolean aviatorBoolean2 = new AviatorBoolean(Boolean.TRUE);
        AviatorBoolean aviatorBoolean3 = new AviatorBoolean(Boolean.FALSE);
        AviatorBoolean aviatorBoolean4 = new AviatorBoolean(Boolean.FALSE);

        assertEquals(0, aviatorBoolean1.compare(aviatorBoolean2, null));
        assertEquals(1, aviatorBoolean1.compare(aviatorBoolean3, null));
        assertEquals(-1, aviatorBoolean3.compare(aviatorBoolean2, null));
        assertEquals(0, aviatorBoolean3.compare(aviatorBoolean4, null));

    }


    @Test
    public void testNot() {
        assertEquals(Boolean.TRUE, AviatorBoolean.FALSE.not(null).getValue(null));
        assertEquals(Boolean.FALSE, AviatorBoolean.TRUE.not(null).getValue(null));
    }


    @Test(expected = ExpressionRuntimeException.class)
    public void testNeg() {
        AviatorBoolean.TRUE.neg(null);
    }


    @Test
    public void testCompareJavaBoolean() {
        AviatorBoolean t = new AviatorBoolean(Boolean.TRUE);
        AviatorBoolean f = new AviatorBoolean(Boolean.FALSE);

        AviatorJavaType javaType = new AviatorJavaType("true");
        assertEquals(0, t.compare(javaType, createEnvWith("true", Boolean.TRUE)));
        assertEquals(-1, f.compare(javaType, createEnvWith("true", Boolean.TRUE)));
        // compre to null value
        assertEquals(1, t.compare(new AviatorJavaType("a"), null));
        assertEquals(1, f.compare(new AviatorJavaType("a"), null));
    }


    @Test(expected = ExpressionRuntimeException.class)
    public void testCompareNumber() {
        AviatorBoolean aviatorBoolean = new AviatorBoolean(Boolean.TRUE);
        aviatorBoolean.compare(AviatorNumber.valueOf(1), null);
    }


    @Test(expected = ExpressionRuntimeException.class)
    public void testCompareJavaType() {
        AviatorBoolean aviatorBoolean = new AviatorBoolean(Boolean.TRUE);
        aviatorBoolean.compare(new AviatorJavaType("a"), createEnvWith("a", 4.6));
    }
}
