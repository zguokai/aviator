package com.googlecode.aviator.runtime.type;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.googlecode.aviator.exception.ExpressionRuntimeException;


public class AviatorPatternUnitTest {
    @Test
    public void testComparePattern() {
        AviatorPattern p1 = new AviatorPattern("[a-zA-Z]+");
        AviatorPattern p2 = new AviatorPattern("[a-zA-Z]+");
        assertEquals(0, p1.compare(p2, null));
        assertEquals(0, p2.compare(p1, null));

        AviatorPattern p3 = new AviatorPattern("[b-cW]+");
        assertTrue(p1.compare(p3, null) < 0);
    }


    @Test(expected = ExpressionRuntimeException.class)
    public void testCompareBoolean() {
        AviatorPattern p1 = new AviatorPattern("[a-zA-Z]+");
        p1.compare(AviatorBoolean.TRUE, null);
    }


    @Test(expected = ExpressionRuntimeException.class)
    public void testCompareString() {
        AviatorPattern p1 = new AviatorPattern("[a-zA-Z]+");
        p1.compare(new AviatorString("hello"), null);
    }


    @Test(expected = ExpressionRuntimeException.class)
    public void testCompareNumber() {
        AviatorPattern p1 = new AviatorPattern("[a-zA-Z]+");
        p1.compare(AviatorNumber.valueOf(400), null);
    }


    @Test
    public void testAddString() {
        AviatorPattern p1 = new AviatorPattern("[a-zA-Z]+");
        assertEquals("[a-zA-Z]+ is a pattern", p1.add(new AviatorString(" is a pattern"),null).getValue(null));
    }


    @Test
    public void testAddJavaString() {
        AviatorPattern p1 = new AviatorPattern("[a-zA-Z]+");
        assertEquals("[a-zA-Z]+ is a pattern", p1.add(new AviatorJavaType("s"), createEnvWith("s", " is a pattern")).getValue(null));
    }


    @Test(expected = ExpressionRuntimeException.class)
    public void testAddBoolean() {
        AviatorPattern p1 = new AviatorPattern("[a-zA-Z]+");
        p1.add(AviatorBoolean.TRUE, null);

    }


    @Test(expected = ExpressionRuntimeException.class)
    public void testAddNumber() {
        AviatorPattern p1 = new AviatorPattern("[a-zA-Z]+");
        p1.add(AviatorNumber.valueOf(3L), null);

    }


    @Test(expected = ExpressionRuntimeException.class)
    public void testAddPattern() {
        AviatorPattern p1 = new AviatorPattern("[a-zA-Z]+");
        p1.add(new AviatorPattern("\\d+"), null);
    }


    @Test(expected = ExpressionRuntimeException.class)
    public void testAddJavaNumber() {
        AviatorPattern p1 = new AviatorPattern("[a-zA-Z]+");
        p1.add(new AviatorJavaType("a"), createEnvWith("a", 400.01f));
    }


    private Map<String, Object> createEnvWith(String name, Object obj) {
        Map<String, Object> env = new HashMap<String, Object>();
        if (name != null) {
            env.put(name, obj);
        }
        env.put("true", Boolean.TRUE);
        env.put("false", Boolean.FALSE);
        return env;
    }


    @Test(expected = ExpressionRuntimeException.class)
    public void testAddJavaDate() {
        AviatorPattern p1 = new AviatorPattern("[a-zA-Z]+");
        p1.add(new AviatorJavaType("date"), createEnvWith("date", new Date()));
    }


    @Test
    public void testMatchString() {
        AviatorPattern p1 = new AviatorPattern("[a-zA-Z]+");
        assertTrue((Boolean) p1.match(new AviatorString("hello"), null).getValue(null));
        assertFalse((Boolean) p1.match(new AviatorString("hello world"), null).getValue(null));
    }


    @Test
    public void testMatchJavaString() {
        AviatorPattern p1 = new AviatorPattern("[a-zA-Z]+");
        assertTrue((Boolean) p1.match(new AviatorJavaType("s"), createEnvWith("s", "hello")).getValue(null));
        assertFalse((Boolean) p1.match(new AviatorJavaType("s"), createEnvWith("s", "hello world")).getValue(null));
    }


    @Test
    public void testPatternGroup() {
        //
        AviatorPattern p1 = new AviatorPattern("-\\d+\\.\\d+");
        Map<String, Object> env = new HashMap<String, Object>();
        p1.match(new AviatorString("-3.4"), env);
        assertEquals(1, env.size());
        assertEquals("-3.4", env.get("$0"));

        p1 = new AviatorPattern("^(-?\\d+)(\\.\\d+)?$");
        env.clear();
        p1.match(new AviatorString("-3.4"), env);
        assertEquals(3, env.size());
        assertEquals("-3.4", env.get("$0"));
        assertEquals("-3", env.get("$1"));
        assertEquals(".4", env.get("$2"));
    }


    @Test
    public void testMatchJavaChar() {
        AviatorPattern p1 = new AviatorPattern("[a-zA-Z]+");
        assertTrue((Boolean) p1.match(new AviatorJavaType("ch"), createEnvWith("ch", 'a')).getValue(null));
        assertFalse((Boolean) p1.match(new AviatorJavaType("ch"), createEnvWith("ch", ' ')).getValue(null));
    }


    @Test(expected = ExpressionRuntimeException.class)
    public void testMatchBoolean() {
        AviatorPattern p1 = new AviatorPattern("[a-zA-Z]+");
        p1.match(AviatorBoolean.TRUE, null);

    }


    @Test(expected = ExpressionRuntimeException.class)
    public void testMatchNumber() {
        AviatorPattern p1 = new AviatorPattern("[a-zA-Z]+");
        p1.match(AviatorNumber.valueOf(3.3), null);

    }


    @Test(expected = ExpressionRuntimeException.class)
    public void testMatchPattern() {
        AviatorPattern p1 = new AviatorPattern("[a-zA-Z]+");
        p1.match(new AviatorPattern("\\d+"), null);
    }


    @Test(expected = ExpressionRuntimeException.class)
    public void testMatchJavaNumber() {
        AviatorPattern p1 = new AviatorPattern("[a-zA-Z]+");
        p1.match(new AviatorJavaType("num"),createEnvWith("num", 3000L));

    }


    @Test(expected = ExpressionRuntimeException.class)
    public void testNot() {
        new AviatorPattern("\\d+").not(null);

    }


    @Test(expected = ExpressionRuntimeException.class)
    public void testNeg() {
        new AviatorPattern("\\d+").neg(null);

    }
}
