package com.googlecode.aviator.parser;

public class AviatorClassLoader extends ClassLoader {

    public AviatorClassLoader(ClassLoader parent) {
        super(parent);
    }


    public Class<?> defineClass(String name, byte[] b) {
        return defineClass(name, b, 0, b.length);
    }
}
