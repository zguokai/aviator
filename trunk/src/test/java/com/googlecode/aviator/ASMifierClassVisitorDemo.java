package com.googlecode.aviator;

import org.objectweb.asm.util.ASMifierClassVisitor;


/*
 * java -cl\sspath asm.jar:asm-util.jar \
 org.objectweb.asm.util.ASMifierClassVisitor \
 java.lang.Runnable

 * 
 */
public class ASMifierClassVisitorDemo {

    public static void main(String[] args) throws Exception {
        ASMifierClassVisitor.main(new String[] { "com.googlecode.aviator.Test" });
    }

}
