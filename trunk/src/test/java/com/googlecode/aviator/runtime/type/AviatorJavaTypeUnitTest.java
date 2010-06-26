package com.googlecode.aviator.runtime.type;

import static org.junit.Assert.*;

import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;

import com.googlecode.aviator.exception.ExpressionRuntimeException;


public class AviatorJavaTypeUnitTest {
    private enum OperatorType {
        Add,
        Sub,
        Mult,
        Div,
        Mod
    }


    @Test
    public void testAddWithNumber() {
        doArthOpJavaTypeNumber(OperatorType.Add);
    }


    @Test
    public void testSubWithNumber() {
        doArthOpJavaTypeNumber(OperatorType.Sub);
    }


    @Test
    public void testMultWithNumber() {
        doArthOpJavaTypeNumber(OperatorType.Mult);
    }


    @Test
    public void testDivWithNumber() {
        doArthOpJavaTypeNumber(OperatorType.Div);
    }


    @Test
    public void testModWithNumber() {
        doArthOpJavaTypeNumber(OperatorType.Mod);
    }


    public void doArthOpJavaTypeNumber(OperatorType operatorType) {
        AviatorJavaType byteType = new AviatorJavaType((byte) 4);
        AviatorJavaType shortType = new AviatorJavaType((short) 4);
        AviatorJavaType intType = new AviatorJavaType(4);
        AviatorJavaType longType = new AviatorJavaType(4L);
        AviatorJavaType floatType = new AviatorJavaType(4.4f);
        AviatorJavaType doubleType = new AviatorJavaType(4.4d);

        AviatorNumber n1 = AviatorNumber.valueOf(3);
        AviatorNumber n2 = AviatorNumber.valueOf(3.3f);

        switch (operatorType) {
        case Add:
            assertEquals(7, byteType.add(n1).getValue());
            assertEquals(7, shortType.add(n1).getValue());
            assertEquals(7, intType.add(n1).getValue());
            assertEquals(7, longType.add(n1).getValue());
            assertEquals(7.4, (Double) floatType.add(n1).getValue(), 0.001);
            assertEquals(7.4, (Double) doubleType.add(n1).getValue(), 0.001);
            assertEquals(8L, byteType.add(intType).getValue());
            assertEquals(8L, shortType.add(longType).getValue());
            assertEquals(8.4, (Double) floatType.add(byteType).getValue(), 0.001);
            assertEquals(8.8, (Double) doubleType.add(floatType).getValue(), 0.001);
            assertEquals(7.7, (Double) n2.add(doubleType).getValue(), 0.001);
            break;
        case Sub:
            assertEquals(1, byteType.sub(n1).getValue());
            assertEquals(1, shortType.sub(n1).getValue());
            assertEquals(1, intType.sub(n1).getValue());
            assertEquals(1, longType.sub(n1).getValue());
            assertEquals(1.4, (Double) floatType.sub(n1).getValue(), 0.001);
            assertEquals(1.4, (Double) doubleType.sub(n1).getValue(), 0.001);
            assertEquals(0L, byteType.sub(intType).getValue());
            assertEquals(0L, shortType.sub(longType).getValue());
            assertEquals(0.4, (Double) floatType.sub(byteType).getValue(), 0.001);
            assertEquals(0.0, (Double) doubleType.sub(floatType).getValue(), 0.001);
            assertEquals(-1.1, (Double) n2.sub(doubleType).getValue(), 0.001);
            break;

        case Mult:
            // 4 4.4 3 3.3
            assertEquals(12, byteType.mult(n1).getValue());
            assertEquals(12, shortType.mult(n1).getValue());
            assertEquals(12, intType.mult(n1).getValue());
            assertEquals(12, longType.mult(n1).getValue());
            assertEquals(13.2, (Double) floatType.mult(n1).getValue(), 0.001);
            assertEquals(13.2, (Double) doubleType.mult(n1).getValue(), 0.001);
            assertEquals(16L, byteType.mult(intType).getValue());
            assertEquals(16L, shortType.mult(longType).getValue());
            assertEquals(17.6, (Double) floatType.mult(byteType).getValue(), 0.001);
            assertEquals(19.36, (Double) doubleType.mult(floatType).getValue(), 0.001);
            assertEquals(14.52, (Double) n2.mult(doubleType).getValue(), 0.001);
            break;
        case Div:
            assertEquals(1, byteType.div(n1).getValue());
            assertEquals(1, shortType.div(n1).getValue());
            assertEquals(1, intType.div(n1).getValue());
            assertEquals(1, longType.div(n1).getValue());
            assertEquals(1.466667, (Double) floatType.div(n1).getValue(), 0.001);
            assertEquals(1.466667, (Double) doubleType.div(n1).getValue(), 0.001);
            assertEquals(1L, byteType.div(intType).getValue());
            assertEquals(1L, shortType.div(longType).getValue());
            assertEquals(1.1, (Double) floatType.div(byteType).getValue(), 0.001);
            assertEquals(1.0, (Double) doubleType.div(floatType).getValue(), 0.001);
            assertEquals(0.75, (Double) n2.div(doubleType).getValue(), 0.001);
            break;
        case Mod:
            assertEquals(1, byteType.mod(n1).getValue());
            assertEquals(1, shortType.mod(n1).getValue());
            assertEquals(1, intType.mod(n1).getValue());
            assertEquals(1, longType.mod(n1).getValue());
            assertEquals(1.4, (Double) floatType.mod(n1).getValue(), 0.001);
            assertEquals(1.4, (Double) doubleType.mod(n1).getValue(), 0.001);
            assertEquals(0L, byteType.mod(intType).getValue());
            assertEquals(0L, shortType.mod(longType).getValue());
            assertEquals(0.4, (Double) floatType.mod(byteType).getValue(), 0.001);
            System.out.println(4.4 % 4.40000001);
            // assertEquals(0.0, (Double) doubleType.mod(floatType).getValue(),
            // 0.001);
            assertEquals(3.3, (Double) n2.mod(doubleType).getValue(), 0.001);
            break;

        }
    }


    @Test
    public void testAddJavaString() {
        AviatorJavaType a = new AviatorJavaType(300);

        assertEquals("300+3=303", a.add(new AviatorJavaType("+3=303")).getValue());
        assertEquals(301.001, a.add(new AviatorJavaType(1.001)).getValue());

        AviatorJavaType s = new AviatorJavaType("hello ");
        assertEquals("hello world", s.add(new AviatorJavaType("world")).getValue());

        AviatorJavaType ch = new AviatorJavaType('h');
        assertEquals("hello world", ch.add(new AviatorJavaType("ello world")).getValue());

        assertEquals("hello 3", s.add(new AviatorJavaType(3)).getValue());
    }


    @Test(expected = ExpressionRuntimeException.class)
    public void addJavaBooleanWithNumber() {
        AviatorJavaType a = new AviatorJavaType(Boolean.TRUE);
        a.add(AviatorNumber.valueOf(3));
    }


    @Test(expected = ExpressionRuntimeException.class)
    public void addJavaNumberWithJavaBoolean() {
        AviatorJavaType a = new AviatorJavaType(4);
        a.add(new AviatorJavaType(Boolean.TRUE));
    }


    @Test(expected = ExpressionRuntimeException.class)
    public void subJavaBooleanWithNumber() {
        AviatorJavaType a = new AviatorJavaType(Boolean.TRUE);
        a.sub(AviatorNumber.valueOf(3));
    }


    @Test(expected = ExpressionRuntimeException.class)
    public void subJavaNumberWithJavaBoolean() {
        AviatorJavaType a = new AviatorJavaType(4);
        a.sub(new AviatorJavaType(Boolean.TRUE));
    }


    @Test(expected = ExpressionRuntimeException.class)
    public void multJavaBooleanWithNumber() {
        AviatorJavaType a = new AviatorJavaType(Boolean.TRUE);
        a.mult(AviatorNumber.valueOf(3));
    }


    @Test(expected = ExpressionRuntimeException.class)
    public void multJavaNumberWithJavaBoolean() {
        AviatorJavaType a = new AviatorJavaType(4);
        a.mult(new AviatorJavaType(Boolean.TRUE));
    }


    @Test(expected = ExpressionRuntimeException.class)
    public void divJavaBooleanWithNumber() {
        AviatorJavaType a = new AviatorJavaType(Boolean.TRUE);
        a.div(AviatorNumber.valueOf(3));
    }


    @Test(expected = ExpressionRuntimeException.class)
    public void divJavaNumberWithJavaBoolean() {
        AviatorJavaType a = new AviatorJavaType(4);
        a.div(new AviatorJavaType(Boolean.TRUE));
    }


    @Test(expected = ExpressionRuntimeException.class)
    public void modJavaBooleanWithNumber() {
        AviatorJavaType a = new AviatorJavaType(Boolean.TRUE);
        a.mod(AviatorNumber.valueOf(3));
    }


    @Test(expected = ExpressionRuntimeException.class)
    public void modJavaNumberWithJavaBoolean() {
        AviatorJavaType a = new AviatorJavaType(4);
        a.mod(new AviatorJavaType(Boolean.TRUE));
    }


    @Test(expected = ExpressionRuntimeException.class)
    public void modJavaBooleanWithJavaNumber() {
        AviatorJavaType a = new AviatorJavaType(Boolean.TRUE);
        a.mod(new AviatorJavaType(3));
    }


    @Test(expected = ExpressionRuntimeException.class)
    public void addJavaBooleanWithJavaNumber() {
        AviatorJavaType a = new AviatorJavaType(Boolean.TRUE);
        a.add(new AviatorJavaType(3));
    }


    @Test(expected = ExpressionRuntimeException.class)
    public void subJavaBooleanWithJavaNumber() {
        AviatorJavaType a = new AviatorJavaType(Boolean.TRUE);
        a.sub(new AviatorJavaType(3));
    }


    @Test(expected = ExpressionRuntimeException.class)
    public void multJavaBooleanWithJavaNumber() {
        AviatorJavaType a = new AviatorJavaType(Boolean.TRUE);
        a.mult(new AviatorJavaType(3));
    }


    @Test(expected = ExpressionRuntimeException.class)
    public void divJavaBooleanWithJavaNumber() {
        AviatorJavaType a = new AviatorJavaType(Boolean.TRUE);
        a.div(new AviatorJavaType(3));
    }


    @Test
    public void compareWithAviatorType() {

        AviatorJavaType intType = new AviatorJavaType(3);
        AviatorJavaType doubleType = new AviatorJavaType(3.4);
        AviatorJavaType boolType = new AviatorJavaType(Boolean.FALSE);
        AviatorJavaType stringType = new AviatorJavaType("hello");
        AviatorJavaType charType = new AviatorJavaType('c');
        AviatorJavaType dateType = new AviatorJavaType(new Date());

        AviatorNumber number = AviatorNumber.valueOf(3.4);
        AviatorBoolean bool = AviatorBoolean.TRUE;
        AviatorPattern pattern = new AviatorPattern("\\d+");
        AviatorString string = new AviatorString("hello");

        assertTrue(intType.compare(number) < 0);
        assertEquals(0, doubleType.compare(number));
        try {
            intType.compare(bool);
            Assert.fail();
        }
        catch (ExpressionRuntimeException e) {

        }
        try {
            intType.compare(pattern);
            Assert.fail();
        }
        catch (ExpressionRuntimeException e) {

        }
        try {
            intType.compare(string);
            Assert.fail();
        }
        catch (ExpressionRuntimeException e) {

        }

        try {
            stringType.compare(number);
            Assert.fail();
        }
        catch (ExpressionRuntimeException e) {

        }
        try {
            stringType.compare(bool);
            Assert.fail();
        }
        catch (ExpressionRuntimeException e) {

        }
        try {
            stringType.compare(pattern);
            Assert.fail();
        }
        catch (ExpressionRuntimeException e) {

        }
        assertEquals(0, stringType.compare(string));

        try {
            charType.compare(number);
            Assert.fail();
        }
        catch (ExpressionRuntimeException e) {

        }

        try {
            charType.compare(bool);
            Assert.fail();
        }
        catch (ExpressionRuntimeException e) {

        }

        try {
            charType.compare(pattern);
            Assert.fail();
        }
        catch (ExpressionRuntimeException e) {

        }
        assertTrue(charType.compare(string) < 0);

        try {
            dateType.compare(number);
            Assert.fail();
        }
        catch (ExpressionRuntimeException e) {

        }
        try {
            dateType.compare(bool);
            Assert.fail();
        }
        catch (ExpressionRuntimeException e) {

        }
        try {
            dateType.compare(string);
            Assert.fail();
        }
        catch (ExpressionRuntimeException e) {

        }
        try {
            dateType.compare(pattern);
            Assert.fail();
        }
        catch (ExpressionRuntimeException e) {

        }

        try {
            boolType.compare(number);
            Assert.fail();
        }
        catch (ExpressionRuntimeException e) {

        }
        try {
            boolType.compare(string);
            Assert.fail();
        }
        catch (ExpressionRuntimeException e) {

        }
        try {
            boolType.compare(pattern);
            Assert.fail();
        }
        catch (ExpressionRuntimeException e) {

        }

        assertTrue(boolType.compare(bool) < 0);
    }


    @Test
    public void compareWithJavaType() {

        AviatorJavaType intType = new AviatorJavaType(3);
        AviatorJavaType boolType = new AviatorJavaType(Boolean.FALSE);
        AviatorJavaType stringType = new AviatorJavaType("hello");
        AviatorJavaType charType = new AviatorJavaType('c');
        AviatorJavaType dateType = new AviatorJavaType(new Date());

        assertEquals(0, intType.compare(intType));
        try {
            intType.compare(boolType);
            Assert.fail();
        }
        catch (ExpressionRuntimeException e) {
        }
        try {
            intType.compare(stringType);
            Assert.fail();
        }
        catch (ExpressionRuntimeException e) {
        }
        try {
            intType.compare(charType);
            Assert.fail();
        }
        catch (ExpressionRuntimeException e) {
        }
        try {
            intType.compare(dateType);
            Assert.fail();
        }
        catch (ExpressionRuntimeException e) {
        }

        try {
            boolType.compare(intType);
            Assert.fail();
        }
        catch (ExpressionRuntimeException e) {
        }
        assertEquals(0, boolType.compare(boolType));
        try {
            boolType.compare(stringType);
            Assert.fail();
        }
        catch (ExpressionRuntimeException e) {
        }
        try {
            boolType.compare(charType);
            Assert.fail();
        }
        catch (ExpressionRuntimeException e) {
        }
        try {
            boolType.compare(dateType);
            Assert.fail();
        }
        catch (ExpressionRuntimeException e) {
        }

        try {
            stringType.compare(intType);
            Assert.fail();
        }
        catch (ExpressionRuntimeException e) {
        }
        try {
            stringType.compare(boolType);
            Assert.fail();
        }
        catch (ExpressionRuntimeException e) {
        }
        assertEquals(0, stringType.compare(stringType));
        try {
            stringType.compare(dateType);
            Assert.fail();
        }
        catch (ExpressionRuntimeException e) {
        }
        assertTrue(stringType.compare(charType) > 0);

        // char

        try {
            charType.compare(intType);
            Assert.fail();
        }
        catch (ExpressionRuntimeException e) {
        }

        try {
            charType.compare(boolType);
            Assert.fail();
        }
        catch (ExpressionRuntimeException e) {
        }
        assertTrue(charType.compare(stringType) < 0);
        assertEquals(0, charType.compare(charType));
        try {
            charType.compare(dateType);
            Assert.fail();
        }
        catch (ExpressionRuntimeException e) {
        }

        try {
            dateType.compare(intType);
            Assert.fail();
        }
        catch (ExpressionRuntimeException e) {
        }
        try {
            dateType.compare(boolType);
            Assert.fail();
        }
        catch (ExpressionRuntimeException e) {
        }
        try {
            dateType.compare(stringType);
            Assert.fail();
        }
        catch (ExpressionRuntimeException e) {
        }
        try {
            dateType.compare(charType);
            Assert.fail();
        }
        catch (ExpressionRuntimeException e) {
        }
        assertEquals(0, dateType.compare(dateType));

    }
}
