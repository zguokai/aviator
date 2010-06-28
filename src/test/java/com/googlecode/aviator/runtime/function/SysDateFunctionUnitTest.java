package com.googlecode.aviator.runtime.function;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import com.googlecode.aviator.runtime.type.AviatorObject;


public class SysDateFunctionUnitTest {
    @Test
    public void testCall() {
        SysDateFunction fun = new SysDateFunction();

        AviatorObject result = fun.call(null);
        assertNotNull(result);
        assertTrue(result.getValue(null) instanceof Date);
    }
}
