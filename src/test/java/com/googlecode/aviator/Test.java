package com.googlecode.aviator;

import com.googlecode.aviator.runtime.type.AviatorNumber;
import com.googlecode.aviator.runtime.type.AviatorObject;



public class Test {
    public static AviatorObject test(AviatorObject a) {
       return ((AviatorNumber)a).neg();
    }
}
