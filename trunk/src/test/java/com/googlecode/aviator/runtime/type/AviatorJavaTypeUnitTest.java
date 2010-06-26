package com.googlecode.aviator.runtime.type;

import static org.junit.Assert.*;

import org.junit.Test;


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

        }
    }
}
