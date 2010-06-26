package com.googlecode.aviator.runtime.type;

import static org.junit.Assert.*;

import org.junit.Test;

import com.googlecode.aviator.exception.ExpressionRuntimeException;


public class AviatorStringUnitTest {
    @Test
    public void testAddNumber() {
        AviatorString s = new AviatorString("hello ");
        AviatorNumber n = AviatorNumber.valueOf(30L);
        assertEquals("hello 30", s.add(n).getValue());
    }


    @Test
    public void testAddBoolean() {
        AviatorString s = new AviatorString("hello ");
        AviatorBoolean n = AviatorBoolean.TRUE;
        assertEquals("hello true", s.add(n).getValue());
    }


    @Test
    public void testAddString() {
        AviatorString s = new AviatorString("hello ");
        AviatorString n = new AviatorString("world");
        assertEquals("hello world", s.add(n).getValue());
    }


    @Test
    public void testAddJavaType() {
        AviatorString s = new AviatorString("hello ");
        AviatorJavaType javaType = new AviatorJavaType("world");
        assertEquals("hello world", s.add(javaType).getValue());

        javaType = new AviatorJavaType(Boolean.TRUE);
        assertEquals("hello true", s.add(javaType).getValue());

        javaType = new AviatorJavaType(400);
        assertEquals("hello 400", s.add(javaType).getValue());

        javaType = new AviatorJavaType(3.4f);
        assertEquals("hello 3.4", s.add(javaType).getValue());
    }


    @Test
    public void testAddPattern() {
        AviatorString s = new AviatorString("hello ");
        AviatorPattern n = new AviatorPattern("[\\d\\.]+");
        assertEquals("hello [\\d\\.]+", s.add(n).getValue());
    }


    @Test
    public void testCompareString() {
        AviatorString s = new AviatorString("hello ");
        AviatorString n = new AviatorString("world");
        assertTrue(s.compare(n) < 0);
        assertEquals(0, s.compare(s));

        n = new AviatorString("awt");
        assertFalse(s.compare(n) < 0);
    }


    @Test
    public void testCompareJavaString() {
        AviatorString s = new AviatorString("hello ");
        assertEquals("hello ", s.getLexeme());
        AviatorJavaType n = new AviatorJavaType("world");
        assertTrue(s.compare(n) < 0);
        assertEquals(0, s.compare(s));

        n = new AviatorJavaType("awt");
        assertFalse(s.compare(n) < 0);
    }


    @Test
    public void testCompareJavaChar() {
        AviatorString s = new AviatorString("hello ");
        AviatorJavaType n = new AviatorJavaType('w');
        assertTrue(s.compare(n) < 0);
        assertEquals(0, s.compare(s));

        n = new AviatorJavaType('a');
        assertFalse(s.compare(n) < 0);
    }


    @Test(expected = ExpressionRuntimeException.class)
    public void testCompareBoolean() {
        AviatorString s = new AviatorString("hello ");
        AviatorBoolean n = AviatorBoolean.TRUE;
        s.compare(n);
    }


    @Test(expected = ExpressionRuntimeException.class)
    public void testComparePattern() {
        AviatorString s = new AviatorString("hello ");
        AviatorPattern n = new AviatorPattern("\\d+");
        s.compare(n);
    }


    @Test(expected = ExpressionRuntimeException.class)
    public void testCompareNumber() {
        AviatorString s = new AviatorString("hello ");
        AviatorNumber n = AviatorNumber.valueOf(3.4f);
        s.compare(n);
    }


    @Test(expected = ExpressionRuntimeException.class)
    public void testCompareJavaNumber() {
        AviatorString s = new AviatorString("hello ");
        AviatorJavaType n = new AviatorJavaType(3.4f);
        s.compare(n);
    }

}
