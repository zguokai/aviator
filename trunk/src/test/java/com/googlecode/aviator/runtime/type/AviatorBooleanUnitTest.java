package com.googlecode.aviator.runtime.type;

import static org.junit.Assert.*;

import org.junit.Test;

import com.googlecode.aviator.exception.ExpressionRuntimeException;


public class AviatorBooleanUnitTest {

    @Test
    public void testAddString() {
        AviatorBoolean aviatorBoolean = new AviatorBoolean(Boolean.TRUE);
        AviatorString aviatorString = new AviatorString(" is true");
        assertEquals("true is true", aviatorBoolean.add(aviatorString).getValue());

    }


    @Test
    public void testAddJavaString() {
        AviatorBoolean aviatorBoolean = new AviatorBoolean(Boolean.TRUE);
        AviatorJavaType aviatorString = new AviatorJavaType(" is true");
        assertEquals("true is true", aviatorBoolean.add(aviatorString).getValue());

    }


    @Test
    public void testAddJavaChar() {
        AviatorBoolean aviatorBoolean = new AviatorBoolean(Boolean.TRUE);
        AviatorJavaType aviatorString = new AviatorJavaType('g');
        assertEquals("trueg", aviatorBoolean.add(aviatorString).getValue());

    }


    @Test(expected = ExpressionRuntimeException.class)
    public void testAddNumber() {
        AviatorBoolean aviatorBoolean = new AviatorBoolean(Boolean.TRUE);
        aviatorBoolean.add(AviatorNumber.valueOf(1));
    }


    @Test(expected = ExpressionRuntimeException.class)
    public void testAddJavaType() {
        AviatorBoolean aviatorBoolean = new AviatorBoolean(Boolean.TRUE);
        aviatorBoolean.add(new AviatorJavaType(4.6));
    }


    @Test
    public void testCompareBoolean() {
        AviatorBoolean aviatorBoolean1 = new AviatorBoolean(Boolean.TRUE);
        AviatorBoolean aviatorBoolean2 = new AviatorBoolean(Boolean.TRUE);
        AviatorBoolean aviatorBoolean3 = new AviatorBoolean(Boolean.FALSE);
        AviatorBoolean aviatorBoolean4 = new AviatorBoolean(Boolean.FALSE);

        assertEquals(0, aviatorBoolean1.compare(aviatorBoolean2));
        assertEquals(1, aviatorBoolean1.compare(aviatorBoolean3));
        assertEquals(-1, aviatorBoolean3.compare(aviatorBoolean2));
        assertEquals(0, aviatorBoolean3.compare(aviatorBoolean4));

    }


    @Test
    public void testNot() {
        assertEquals(Boolean.TRUE, AviatorBoolean.FALSE.not().getValue());
        assertEquals(Boolean.FALSE, AviatorBoolean.TRUE.not().getValue());
    }


    @Test(expected = ExpressionRuntimeException.class)
    public void testNeg() {
        AviatorBoolean.TRUE.neg();
    }


    @Test
    public void testCompareJavaBoolean() {
        AviatorBoolean t = new AviatorBoolean(Boolean.TRUE);
        AviatorBoolean f = new AviatorBoolean(Boolean.FALSE);

        AviatorJavaType javaType = new AviatorJavaType(Boolean.TRUE);
        assertEquals(0, t.compare(javaType));
        assertEquals(-1, f.compare(javaType));
    }


    @Test(expected = ExpressionRuntimeException.class)
    public void testCompareNumber() {
        AviatorBoolean aviatorBoolean = new AviatorBoolean(Boolean.TRUE);
        aviatorBoolean.compare(AviatorNumber.valueOf(1));
    }


    @Test(expected = ExpressionRuntimeException.class)
    public void testCompareJavaType() {
        AviatorBoolean aviatorBoolean = new AviatorBoolean(Boolean.TRUE);
        aviatorBoolean.compare(new AviatorJavaType(4.6));
    }

}
