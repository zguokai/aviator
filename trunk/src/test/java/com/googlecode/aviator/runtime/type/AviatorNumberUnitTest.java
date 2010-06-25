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


    @Test(expected = ExpressionRuntimeException.class)
    public void testAddWithOtherType1() {
        AviatorNumber a = AviatorNumber.valueOf(1000);
        a.add(new AviatorBoolean(Boolean.TRUE));
    }


    @Test(expected = ExpressionRuntimeException.class)
    public void testAddWithOtherType2() {
        AviatorNumber a = AviatorNumber.valueOf(1000);
        a.add(new AviatorJavaType(Boolean.TRUE));
    }


    @Test(expected = ExpressionRuntimeException.class)
    public void testSubWithOtherType1() {
        AviatorNumber a = AviatorNumber.valueOf(1000);
        a.sub(new AviatorBoolean(Boolean.TRUE));
    }


    @Test(expected = ExpressionRuntimeException.class)
    public void testSubWithOtherType2() {
        AviatorNumber a = AviatorNumber.valueOf(1000);
        a.sub(new AviatorJavaType(Boolean.TRUE));
    }


    @Test(expected = ExpressionRuntimeException.class)
    public void testMultWithOtherType1() {
        AviatorNumber a = AviatorNumber.valueOf(1000);
        a.mult(new AviatorBoolean(Boolean.TRUE));
    }


    @Test(expected = ExpressionRuntimeException.class)
    public void testMultWithOtherType2() {
        AviatorNumber a = AviatorNumber.valueOf(1000);
        a.mult(new AviatorJavaType(Boolean.TRUE));
    }


    @Test(expected = ExpressionRuntimeException.class)
    public void testDivWithOtherType1() {
        AviatorNumber a = AviatorNumber.valueOf(1000);
        a.div(new AviatorBoolean(Boolean.TRUE));
    }


    @Test(expected = ExpressionRuntimeException.class)
    public void testDivWithOtherType2() {
        AviatorNumber a = AviatorNumber.valueOf(1000);
        a.div(new AviatorJavaType(Boolean.TRUE));
    }


    @Test(expected = ExpressionRuntimeException.class)
    public void testModWithOtherType1() {
        AviatorNumber a = AviatorNumber.valueOf(1000);
        a.mod(new AviatorBoolean(Boolean.TRUE));
    }


    @Test(expected = ExpressionRuntimeException.class)
    public void testModWithOtherType2() {
        AviatorNumber a = AviatorNumber.valueOf(1000);
        a.mod(new AviatorJavaType(Boolean.TRUE));
    }


    @Test
    public void testAddWithString() {
        AviatorNumber a = AviatorNumber.valueOf(1000);
        AviatorString s = new AviatorString("hello");
        assertEquals("1000hello", a.add(s).getValue());
        assertEquals("1000hello", a.add(s).getValue());
        assertEquals(AviatorType.String, a.add(s).getAviatorType());
    }
    
    @Test
    public void testAddWithJavaString(){
        AviatorNumber a = AviatorNumber.valueOf(1000);
        AviatorJavaType s = new AviatorJavaType("hello");
        assertEquals("1000hello", a.add(s).getValue());
        assertEquals("1000hello", a.add(s).getValue());
        assertEquals(AviatorType.String, a.add(s).getAviatorType());
    }


    @Test
    public void testAddWithJavaType() {
        doArithOperationWithJavaType(OperatorType.Add);
    }


    @Test
    public void testSubWithJavaType() {

        doArithOperationWithJavaType(OperatorType.Sub);
    }


    @Test
    public void testMultWithJavaType() {

        doArithOperationWithJavaType(OperatorType.Mult);
    }


    @Test
    public void testDivWithJavaType() {

        doArithOperationWithJavaType(OperatorType.Div);
    }


    @Test
    public void testModWithJavaType() {

        doArithOperationWithJavaType(OperatorType.Mod);
    }


    public void doArithOperationWithJavaType(OperatorType operatorType) {
        AviatorNumber a = AviatorNumber.valueOf(1000);
        AviatorJavaType byteType = new AviatorJavaType((byte) 4);
        AviatorJavaType shortType = new AviatorJavaType((short) 4);
        AviatorJavaType intType = new AviatorJavaType(4);
        AviatorJavaType longType = new AviatorJavaType((long) 4);
        AviatorJavaType doubleType = new AviatorJavaType(4.3);
        AviatorJavaType floatType = new AviatorJavaType(4.3f);
        switch (operatorType) {
        case Add:

            assertEquals(1004L, a.add(byteType).getValue());
            assertEquals(1004L, a.add(shortType).getValue());
            assertEquals(1004L, a.add(intType).getValue());
            assertEquals(1004L, a.add(longType).getValue());
            assertEquals(1004.3, a.add(doubleType).getValue());
            assertEquals(1004.3, a.add(floatType).getValue());
            break;
        case Sub:
            assertEquals(996L, a.sub(byteType).getValue());
            assertEquals(996L, a.sub(shortType).getValue());
            assertEquals(996L, a.sub(intType).getValue());
            assertEquals(996L, a.sub(longType).getValue());
            assertEquals(995.7d, a.sub(doubleType).getValue());
            assertEquals(995.7d, a.sub(floatType).getValue());
            break;

        case Mod:
            // 1000 4 4.3
            assertEquals(0L, a.mod(byteType).getValue());
            assertEquals(0L, a.mod(shortType).getValue());
            assertEquals(0L, a.mod(intType).getValue());
            assertEquals(0L, a.mod(longType).getValue());
            assertEquals(2.4000, (Double) a.mod(doubleType).getValue(), 0.001);
            assertEquals(2.4000, (Double) a.mod(floatType).getValue(), 0.001);
            break;
        case Mult:
            assertEquals(4000L, a.mult(byteType).getValue());
            assertEquals(4000L, a.mult(shortType).getValue());
            assertEquals(4000L, a.mult(intType).getValue());
            assertEquals(4000L, a.mult(longType).getValue());
            assertEquals(4300.0, (Double) a.mult(doubleType).getValue(), 0.001);
            assertEquals(4300.0, (Double) a.mult(floatType).getValue(), 0.001);
            break;
        case Div:
            assertEquals(250L, a.div(byteType).getValue());
            assertEquals(250L, a.div(shortType).getValue());
            assertEquals(250L, a.div(intType).getValue());
            assertEquals(250L, a.div(longType).getValue());
            assertEquals(232.558139, (Double) a.div(doubleType).getValue(), 0.001);
            assertEquals(232.558139, (Double) a.div(floatType).getValue(), 0.001);
            break;

        }
    }


    @Test
    public void testAddWithNumber() {
        testArthOperationWithNumber(OperatorType.Add);
    }


    @Test
    public void testSubWithNumber() {
        testArthOperationWithNumber(OperatorType.Sub);
    }


    @Test
    public void testMultWithNumber() {
        testArthOperationWithNumber(OperatorType.Mult);
    }


    @Test
    public void testDivWithNumber() {
        testArthOperationWithNumber(OperatorType.Div);
    }


    @Test
    public void testModWithNumber() {
        testArthOperationWithNumber(OperatorType.Mod);
    }

    private enum OperatorType {
        Add,
        Sub,
        Mod,
        Mult,
        Div
    }


    public void testArthOperationWithNumber(OperatorType operatorType) {
        AviatorNumber a = AviatorNumber.valueOf(3.3f);
        AviatorNumber b = AviatorNumber.valueOf(3);
        AviatorNumber c = AviatorNumber.valueOf(1000);
        AviatorNumber d = AviatorNumber.valueOf(4.3d);
        switch (operatorType) {
        case Add:
            assertEquals(6.3, a.add(b).getValue());
            assertEquals(6.3, b.add(a).getValue());

            assertEquals(7.6, a.add(d).getValue());
            assertEquals(7.6, d.add(a).getValue());

            assertEquals(1003, b.add(c).getValue());
            assertEquals(1003, c.add(b).getValue());

            assertEquals(7.3, b.add(d).getValue());
            assertEquals(7.3, d.add(b).getValue());
            break;
        case Sub:
            assertEquals(0.3, a.sub(b).getValue());
            assertEquals(-0.3, b.sub(a).getValue());

            assertEquals(-1.0, a.sub(d).getValue());
            assertEquals(1.0, d.sub(a).getValue());

            assertEquals(-997, b.sub(c).getValue());
            assertEquals(997, c.sub(b).getValue());

            assertEquals(-1.3, b.sub(d).getValue());
            assertEquals(1.3, d.sub(b).getValue());
            break;
        case Mult:
            assertEquals(9.9, a.mult(b).getValue());
            assertEquals(9.9, b.mult(a).getValue());

            assertEquals(14.19, a.mult(d).getValue());
            assertEquals(14.19, d.mult(a).getValue());

            assertEquals(3000, b.mult(c).getValue());
            assertEquals(3000, c.mult(b).getValue());

            assertEquals(12.9, b.mult(d).getValue());
            assertEquals(12.9, d.mult(b).getValue());
            break;

        case Div:
            // 3.3 3 1000 4.3
            assertEquals(1.1, (Double) a.div(b).getValue(), 0.001);
            assertEquals(0.90909090, (Double) b.div(a).getValue(), 0.001);

            assertEquals(0.76744, (Double) a.div(d).getValue(), 0.001);
            assertEquals(1.30303030, (Double) d.div(a).getValue(), 0.001);

            assertEquals(0, b.div(c).getValue());
            assertEquals(333, c.div(b).getValue());

            assertEquals(0.6976744, (Double) b.div(d).getValue(), 0.001);
            assertEquals(1.433333333, (Double) d.div(b).getValue(), 0.001);
            break;
        case Mod:
            assertEquals(0.3, (Double) a.mod(b).getValue(), 0.001);
            assertEquals(3.0, (Double) b.mod(a).getValue(), 0.001);

            assertEquals(3.3, (Double) a.mod(d).getValue(), 0.001);
            assertEquals(1.0, (Double) d.mod(a).getValue(), 0.001);

            assertEquals(3, b.mod(c).getValue());
            assertEquals(1, c.mod(b).getValue());

            assertEquals(3.0, (Double) b.mod(d).getValue(), 0.001);
            assertEquals(1.3, (Double) d.mod(b).getValue(), 0.001);
            break;

        }

    }

}
