package com.googlecode.aviator.runtime.type;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import com.googlecode.aviator.exception.ExpressionRuntimeException;


public class AviatorPatternUnitTest {
    @Test
    public void testComparePattern() {
        AviatorPattern p1 = new AviatorPattern("[a-zA-Z]+");
        AviatorPattern p2 = new AviatorPattern("[a-zA-Z]+");
        assertEquals(0, p1.compare(p2));
        assertEquals(0, p2.compare(p1));

        AviatorPattern p3 = new AviatorPattern("[b-cW]+");
        assertTrue(p1.compare(p3) < 0);
    }


    @Test(expected = ExpressionRuntimeException.class)
    public void testCompareBoolean() {
        AviatorPattern p1 = new AviatorPattern("[a-zA-Z]+");
        p1.compare(AviatorBoolean.TRUE);
    }


    @Test(expected = ExpressionRuntimeException.class)
    public void testCompareString() {
        AviatorPattern p1 = new AviatorPattern("[a-zA-Z]+");
        p1.compare(new AviatorString("hello"));
    }


    @Test(expected = ExpressionRuntimeException.class)
    public void testCompareNumber() {
        AviatorPattern p1 = new AviatorPattern("[a-zA-Z]+");
        p1.compare(AviatorNumber.valueOf(400));
    }


    @Test
    public void testAddString() {
        AviatorPattern p1 = new AviatorPattern("[a-zA-Z]+");
        assertEquals("[a-zA-Z]+ is a pattern", p1.add(new AviatorString(" is a pattern")).getValue());
    }


    @Test
    public void testAddJavaString() {
        AviatorPattern p1 = new AviatorPattern("[a-zA-Z]+");
        assertEquals("[a-zA-Z]+ is a pattern", p1.add(new AviatorJavaType(" is a pattern")).getValue());
    }


    @Test(expected = ExpressionRuntimeException.class)
    public void testAddBoolean() {
        AviatorPattern p1 = new AviatorPattern("[a-zA-Z]+");
        p1.add(AviatorBoolean.TRUE);

    }


    @Test(expected = ExpressionRuntimeException.class)
    public void testAddNumber() {
        AviatorPattern p1 = new AviatorPattern("[a-zA-Z]+");
        p1.add(AviatorNumber.valueOf(3L));

    }


    @Test(expected = ExpressionRuntimeException.class)
    public void testAddPattern() {
        AviatorPattern p1 = new AviatorPattern("[a-zA-Z]+");
        p1.add(new AviatorPattern("\\d+"));
    }


    @Test(expected = ExpressionRuntimeException.class)
    public void testAddJavaNumber() {
        AviatorPattern p1 = new AviatorPattern("[a-zA-Z]+");
        p1.add(new AviatorJavaType(400.01f));
    }


    @Test(expected = ExpressionRuntimeException.class)
    public void testAddJavaDate() {
        AviatorPattern p1 = new AviatorPattern("[a-zA-Z]+");
        p1.add(new AviatorJavaType(new Date()));
    }


    @Test
    public void testMatchString() {
        AviatorPattern p1 = new AviatorPattern("[a-zA-Z]+");
        assertTrue((Boolean) p1.match(new AviatorString("hello")).getValue());
        assertFalse((Boolean) p1.match(new AviatorString("hello world")).getValue());
    }


    @Test
    public void testMatchJavaString() {
        AviatorPattern p1 = new AviatorPattern("[a-zA-Z]+");
        assertTrue((Boolean) p1.match(new AviatorJavaType("hello")).getValue());
        assertFalse((Boolean) p1.match(new AviatorJavaType("hello world")).getValue());
    }


    @Test
    public void testMatchJavaChar() {
        AviatorPattern p1 = new AviatorPattern("[a-zA-Z]+");
        assertTrue((Boolean) p1.match(new AviatorJavaType('a')).getValue());
        assertFalse((Boolean) p1.match(new AviatorJavaType(' ')).getValue());
    }


    @Test(expected = ExpressionRuntimeException.class)
    public void testMatchBoolean() {
        AviatorPattern p1 = new AviatorPattern("[a-zA-Z]+");
        p1.match(AviatorBoolean.TRUE);

    }


    @Test(expected = ExpressionRuntimeException.class)
    public void testMatchNumber() {
        AviatorPattern p1 = new AviatorPattern("[a-zA-Z]+");
        p1.match(AviatorNumber.valueOf(3.3));

    }


    @Test(expected = ExpressionRuntimeException.class)
    public void testMatchPattern() {
        AviatorPattern p1 = new AviatorPattern("[a-zA-Z]+");
        p1.match(new AviatorPattern("\\d+"));
    }


    @Test(expected = ExpressionRuntimeException.class)
    public void testMatchJavaNumber() {
        AviatorPattern p1 = new AviatorPattern("[a-zA-Z]+");
        p1.match(new AviatorJavaType(3000L));

    }


    @Test(expected = ExpressionRuntimeException.class)
    public void testNot() {
        new AviatorPattern("\\d+").not();

    }


    @Test(expected = ExpressionRuntimeException.class)
    public void testNeg() {
        new AviatorPattern("\\d+").neg();

    }
}
