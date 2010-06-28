package com.googlecode.aviator.runtime.type;

import java.util.Map;


public class AviatorRuntimeJavaType extends AviatorJavaType {
    private final Object object;


    public AviatorRuntimeJavaType(Object object) {
        super("unknow");
        this.object = object;
    }


    @Override
    public Object getValue(Map<String, Object> env) {
        return this.object;
    }

}
