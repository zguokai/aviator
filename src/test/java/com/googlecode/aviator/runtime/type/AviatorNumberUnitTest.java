package com.googlecode.aviator.runtime.type;

import static org.junit.Assert.*;

import org.junit.Test;

import com.googlecode.aviator.exception.ExpressionRuntimeException;


public class AviatorNumberUnitTest {

    @Test
    public void testCompareWithNumber() {
        AviatorNumber a = AviatorNumber.valueOf(1000);
        AviatorNumber b = AviatorNumber.valueOf(2000);
        assertTrue(a.compare(b) < 0);
        assertTrue(b.compare(a) > 0);

        AviatorNumber c = AviatorNumber.valueOf(3.2f);
        AviatorNumber d = AviatorNumber.valueOf(-0.3d);
        assertTrue(c.compare(d) > 0);
        assertTrue(d.compare(c) < 0);

        a = AviatorNumber.valueOf(1000);
        b = AviatorNumber.valueOf(1000);

        assertEquals(0, a.compare(b));
        assertEquals(0, b.compare(a));
        assertTrue(a.compare(c) > 0);
        assertTrue(b.compare(d) > 0);
        assertTrue(d.compare(c) < 0);
        assertTrue(d.compare(a) < 0);
        assertTrue(d.compare(b) < 0);
    }


    @Test
    public void testCompareWithJavaType() {
        AviatorNumber a = AviatorNumber.valueOf(1000);
        AviatorJavaType longType = new AviatorJavaType(2000L);
        AviatorJavaType byteType = new AviatorJavaType((byte) 3);
        AviatorJavaType shortType = new AviatorJavaType((short) 1000);
        AviatorJavaType intType = new AviatorJavaType(500);

        assertTrue(a.compare(longType) < 0);
        assertTrue(a.compare(byteType) > 0);
        assertEquals(0, a.compare(shortType));
        assertTrue(a.compare(intType) > 0);

        AviatorJavaType floatType = new AviatorJavaType(1000.1f);
        AviatorJavaType doubleType = new AviatorJavaType(999.9d);
        assertTrue(a.compare(floatType) < 0);
        assertTrue(a.compare(doubleType) > 0);
    }


    @Test(expected = ExpressionRuntimeException.class)
    public void testCompareWithJavaString() {
        AviatorNumber a = AviatorNumber.valueOf(1000);
        AviatorJavaType s = new AviatorJavaType("hello");
        a.compare(s);

    }


    @Test(expected = ExpressionRuntimeException.class)
    public void testCompareWithString() {
        AviatorNumber a = AviatorNumber.valueOf(1000);
        AviatorString s = new AviatorString("hello");
        a.compare(s);

    }


    @Test
    public void testAddWithNumber() {
        AviatorNumber a = AviatorNumber.valueOf(1000);
        AviatorNumber b = AviatorNumber.valueOf(2000);
        assertEquals(3000, a.add(b).numberValue());
        assertEquals(3000, b.add(a).numberValue());

        AviatorNumber c = AviatorNumber.valueOf((byte) 9);
        assertEquals(1009, a.add(c).numberValue());
        assertEquals(1009, c.add(a).numberValue());

        assertEquals(2009, b.add(c).numberValue());
        assertEquals(2009, c.add(b).numberValue());

        AviatorNumber d = AviatorNumber.valueOf(3.14159);
        assertEquals(2003.14159, b.add(d).numberValue());
        assertEquals(2003.14159, d.add(b).numberValue());

        AviatorNumber e = AviatorNumber.valueOf(5.9d);
        assertEquals(9.04159, d.add(e).numberValue());
        assertEquals(9.04159, e.add(d).numberValue());
    }
}
