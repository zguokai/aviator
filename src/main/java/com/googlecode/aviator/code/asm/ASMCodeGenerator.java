/**
 *  Copyright (C) 2010 dennis zhuang (killme2008@gmail.com)
 *
 *  This library is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published
 *  by the Free Software Foundation; either version 2.1 of the License, or
 *  (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 *
 **/
package com.googlecode.aviator.code.asm;

import static org.objectweb.asm.Opcodes.*;

import java.io.PrintWriter;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicLong;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.util.CheckClassAdapter;
import org.objectweb.asm.util.TraceClassVisitor;

import com.googlecode.aviator.code.CodeGenerator;
import com.googlecode.aviator.lexer.token.NumberToken;
import com.googlecode.aviator.lexer.token.Token;
import com.googlecode.aviator.lexer.token.Variable;
import com.googlecode.aviator.parser.AviatorClassLoader;


/**
 * code generator using asm
 * 
 * @author dennis
 * 
 */
public class ASMCodeGenerator implements CodeGenerator {
    // Class Writer to generate class
    private final ClassWriter classWriter;
    // Trace visitor
    private ClassVisitor traceClassVisitor;
    // Check visitor
    private ClassVisitor checkClassAdapter;
    // Method visitor
    private MethodVisitor mv;
    // Class name
    private final String className;
    // Class loader to define generated class
    private final AviatorClassLoader classLoader;

    private static final AtomicLong CLASS_COUNTER = new AtomicLong();

    /**
     * Operands stack for checking
     */
    private final Stack<Token<?>> operandStack = new Stack<Token<?>>();

    private int maxStacks = 0;


    private void setMaxStacks(int newMaxStacks) {
        if (newMaxStacks > this.maxStacks) {
            this.maxStacks = newMaxStacks;
        }
    }


    /**
     * Calc current stack depth
     * 
     * @return
     */
    private int calcStacks() {
        int newMaxStacks = 0;
        for (Token<?> t : this.operandStack) {
            switch (t.getType()) {
            case Number:
                newMaxStacks += 2;
                break;
            case Char:
                // ignore;
                break;
            case String:
            case Pattern:
            case Variable:
                newMaxStacks += 1;
                break;
            }
        }
        return newMaxStacks;
    }


    public ASMCodeGenerator(AviatorClassLoader classLoader, boolean trace) {
        this.classLoader = classLoader;
        // Generate inner class name
        className = "Script_" + System.currentTimeMillis() + "_" + CLASS_COUNTER.getAndIncrement();
        // Auto compute frames
        classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        if (trace) {
            traceClassVisitor = new TraceClassVisitor(classWriter, new PrintWriter(System.out));
            checkClassAdapter = new CheckClassAdapter(traceClassVisitor);
        }
        else {
            checkClassAdapter = new CheckClassAdapter(classWriter);
        }
        makeConstructor();
        startVisitMethodCode();
    }


    private void startVisitMethodCode() {
        mv =
                checkClassAdapter.visitMethod(ACC_PUBLIC + ACC_STATIC, "run", "(Ljava/util/Map;)Ljava/lang/Object;",
                    "(Ljava/util/Map<Ljava/lang/String;Ljava/lang/Object;>;)Ljava/lang/Object;", null);
        mv.visitCode();
    }


    private void endVisitCode() {
        if (!this.operandStack.isEmpty()) {
            pushMarkToken();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKEVIRTUAL, "com/googlecode/aviator/runtime/type/AviatorObject", "getValue",
                "(Ljava/util/Map;)Ljava/lang/Object;");
            mv.visitInsn(ARETURN);
            popOperand();
            popOperand();
        }
        else {
            mv.visitInsn(ACONST_NULL);
            mv.visitInsn(ARETURN);
        }
        mv.visitMaxs(maxStacks, 1);
        mv.visitEnd();
        checkClassAdapter.visitEnd();
    }


    private void pushMarkToken() {
        pushOperand(MARK_TOKEN, 0);
    }


    /**
     * Make a default constructor
     */
    private void makeConstructor() {
        checkClassAdapter.visit(V1_5, ACC_PUBLIC + ACC_SUPER, className, null, "java/lang/Object", null);

        {
            mv = checkClassAdapter.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V");
            mv.visitInsn(RETURN);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }
    }

    // 标记Token，用于中间结果表示
    private static final Token<?> MARK_TOKEN = new Variable("mark", -1);


    /**
     * Make a label
     * 
     * @return
     */
    private Label makeLabel() {
        return new Label();
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.googlecode.aviator.code.CodeGenerator#onAdd(com.googlecode.aviator
     * .lexer.token.Token)
     */
    public void onAdd(Token<?> lookhead) {
        doArthOperation("add");
    }


    /**
     * Do arithmetic operation
     * 
     * @param methodName
     */
    private void doArthOperation(String methodName) {
        pushMarkToken();
        mv.visitVarInsn(ALOAD, 0);
        mv
            .visitMethodInsn(
                INVOKEVIRTUAL,
                "com/googlecode/aviator/runtime/type/AviatorObject",
                methodName,
                "(Lcom/googlecode/aviator/runtime/type/AviatorObject;Ljava/util/Map;)Lcom/googlecode/aviator/runtime/type/AviatorObject;");
        popOperand();
        popOperand();
        popOperand();
        pushMarkToken();
    }


    /**
     * Pop a operand from stack
     */
    private void popOperand() {
        this.operandStack.pop();
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.googlecode.aviator.code.CodeGenerator#onSub(com.googlecode.aviator
     * .lexer.token.Token)
     */
    public void onSub(Token<?> lookhead) {
        doArthOperation("sub");
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.googlecode.aviator.code.CodeGenerator#onMult(com.googlecode.aviator
     * .lexer.token.Token)
     */
    public void onMult(Token<?> lookhead) {
        doArthOperation("mult");
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.googlecode.aviator.code.CodeGenerator#onDiv(com.googlecode.aviator
     * .lexer.token.Token)
     */
    public void onDiv(Token<?> lookhead) {
        doArthOperation("div");
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.googlecode.aviator.code.CodeGenerator#onMod(com.googlecode.aviator
     * .lexer.token.Token)
     */
    public void onMod(Token<?> lookhead) {
        doArthOperation("mod");
    }


    /**
     * Do logic operation "&&" left operand
     */
    public void onAndLeft(Token<?> lookhead) {
        pushMarkToken();

        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKEVIRTUAL, "com/googlecode/aviator/runtime/type/AviatorObject", "booleanValue",
            "(Ljava/util/Map;)Z");
        Label l0 = new Label();
        l0stack.push(l0);
        mv.visitJumpInsn(IFEQ, l0);

        popOperand();
        popOperand();
        pushMarkToken();
    }


    /**
     * Do logic operation "&&" right operand
     */
    public void onAndRight(Token<?> lookhead) {
        pushMarkToken();

        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKEVIRTUAL, "com/googlecode/aviator/runtime/type/AviatorObject", "booleanValue",
            "(Ljava/util/Map;)Z");
        mv.visitJumpInsn(IFEQ, l0stack.peek());
        // Result is true
        mv.visitFieldInsn(GETSTATIC, "com/googlecode/aviator/runtime/type/AviatorBoolean", "TRUE",
            "Lcom/googlecode/aviator/runtime/type/AviatorBoolean;");
        Label l1 = new Label();
        mv.visitJumpInsn(GOTO, l1);
        mv.visitLabel(l0stack.pop());
        // Result is false
        mv.visitFieldInsn(GETSTATIC, "com/googlecode/aviator/runtime/type/AviatorBoolean", "FALSE",
            "Lcom/googlecode/aviator/runtime/type/AviatorBoolean;");
        mv.visitLabel(l1);

        popOperand();
        popOperand();
        pushMarkToken();
    }

    /**
     * Label stack for ternary operator
     */
    private final Stack<Label> l0stack = new Stack<Label>();
    private final Stack<Label> l1stack = new Stack<Label>();


    public void onTernaryBoolean(Token<?> lookhead) {
        pushMarkToken();
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKEVIRTUAL, "com/googlecode/aviator/runtime/type/AviatorObject", "booleanValue",
            "(Ljava/util/Map;)Z");
        Label l0 = new Label();
        Label l1 = new Label();
        l0stack.push(l0);
        l1stack.push(l1);
        mv.visitJumpInsn(IFEQ, l0);
        popOperand();
        popOperand();
        pushMarkToken();
    }


    public void onTernaryLeft(Token<?> lookhead) {
        mv.visitJumpInsn(GOTO, l1stack.peek());
        mv.visitLabel(l0stack.pop());
    }


    public void onTernaryRight(Token<?> lookhead) {
        mv.visitLabel(l1stack.pop());
    }


    /**
     * Do logic operation "||" right operand
     */
    public void onJoinRight(Token<?> lookhead) {
        pushMarkToken();

        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKEVIRTUAL, "com/googlecode/aviator/runtime/type/AviatorObject", "booleanValue",
            "(Ljava/util/Map;)Z");
        Label l1 = new Label();
        mv.visitJumpInsn(IFNE, l0stack.peek());
        // Result is False
        mv.visitFieldInsn(GETSTATIC, "com/googlecode/aviator/runtime/type/AviatorBoolean", "FALSE",
            "Lcom/googlecode/aviator/runtime/type/AviatorBoolean;");
        mv.visitJumpInsn(GOTO, l1);
        mv.visitLabel(l0stack.pop());
        // Result is True
        mv.visitFieldInsn(GETSTATIC, "com/googlecode/aviator/runtime/type/AviatorBoolean", "TRUE",
            "Lcom/googlecode/aviator/runtime/type/AviatorBoolean;");
        mv.visitLabel(l1);
        popOperand();
        popOperand();
        pushMarkToken();

    }


    /**
     * Do logic operation "||" left operand
     */
    public void onJoinLeft(Token<?> lookhead) {
        pushMarkToken();

        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKEVIRTUAL, "com/googlecode/aviator/runtime/type/AviatorObject", "booleanValue",
            "(Ljava/util/Map;)Z");
        Label l0 = new Label();
        l0stack.push(l0);
        mv.visitJumpInsn(IFNE, l0);

        popOperand();
        popOperand();
        pushMarkToken();

    }


    public void onEq(Token<?> lookhead) {
        doCompareAndJump(IFNE);
    }


    public void onMatch(Token<?> lookhead) {
        pushMarkToken();
        this.mv.visitInsn(SWAP);
        mv.visitVarInsn(ALOAD, 0);
        mv
            .visitMethodInsn(
                INVOKEVIRTUAL,
                "com/googlecode/aviator/runtime/type/AviatorObject",
                "match",
                "(Lcom/googlecode/aviator/runtime/type/AviatorObject;Ljava/util/Map;)Lcom/googlecode/aviator/runtime/type/AviatorObject;");

        popOperand();
        popOperand();
        popOperand();
        pushMarkToken();
    }


    public void onNeq(Token<?> lookhead) {
        doCompareAndJump(IFEQ);
    }


    private void doCompareAndJump(int ints) {
        pushMarkToken();

        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKEVIRTUAL, "com/googlecode/aviator/runtime/type/AviatorObject", "compare",
            "(Lcom/googlecode/aviator/runtime/type/AviatorObject;Ljava/util/Map;)I");
        Label l0 = makeLabel();
        Label l1 = makeLabel();
        mv.visitJumpInsn(ints, l0);
        mv.visitFieldInsn(GETSTATIC, "com/googlecode/aviator/runtime/type/AviatorBoolean", "TRUE",
            "Lcom/googlecode/aviator/runtime/type/AviatorBoolean;");
        mv.visitJumpInsn(GOTO, l1);
        mv.visitLabel(l0);
        mv.visitFieldInsn(GETSTATIC, "com/googlecode/aviator/runtime/type/AviatorBoolean", "FALSE",
            "Lcom/googlecode/aviator/runtime/type/AviatorBoolean;");
        mv.visitLabel(l1);
        popOperand();
        popOperand();
        popOperand();
        pushMarkToken();
    }


    public void onGe(Token<?> lookhead) {
        doCompareAndJump(IFLT);
    }


    public void onGt(Token<?> lookhead) {
        doCompareAndJump(IFLE);
    }


    public void onLe(Token<?> lookhead) {
        doCompareAndJump(IFGT);

    }


    public void onLt(Token<?> lookhead) {
        doCompareAndJump(IFGE);
    }


    /**
     * 
     * @param token
     *            token
     * @param stacks
     *            额外的栈空间大小
     */
    public void pushOperand(Token<?> token, int stacks) {
        this.operandStack.add(token);
        int newMaxStacks = calcStacks();
        setMaxStacks(newMaxStacks + stacks);
    }


    /**
     * Logic operation '!'
     */
    public void onNot(Token<?> lookhead) {
        pushMarkToken();

        mv.visitTypeInsn(CHECKCAST, "com/googlecode/aviator/runtime/type/AviatorObject");
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKEVIRTUAL, "com/googlecode/aviator/runtime/type/AviatorObject", "not",
            "(Ljava/util/Map;)Lcom/googlecode/aviator/runtime/type/AviatorObject;");

        popOperand();
        popOperand();
        pushMarkToken();
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.googlecode.aviator.code.CodeGenerator#onNeg(com.googlecode.aviator
     * .lexer.token.Token, int)
     */
    public void onNeg(Token<?> lookhead) {
        pushMarkToken();

        mv.visitTypeInsn(CHECKCAST, "com/googlecode/aviator/runtime/type/AviatorObject");
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKEVIRTUAL, "com/googlecode/aviator/runtime/type/AviatorObject", "neg",
            "(Ljava/util/Map;)Lcom/googlecode/aviator/runtime/type/AviatorObject;");
        popOperand();
        popOperand();
        pushMarkToken();
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.googlecode.aviator.code.CodeGenerator#getResult()
     */
    public Class<?> getResult() {
        endVisitCode();
        byte[] bytes = this.classWriter.toByteArray();
        return this.classLoader.defineClass(className, bytes);
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.googlecode.aviator.code.CodeGenerator#onConstant(com.googlecode.aviator
     * .lexer.token.Token)
     */
    public void onConstant(Token<?> lookhead) {
        // load token to stack
        switch (lookhead.getType()) {
        case Number:
            // load numbers
            NumberToken numberToken = (NumberToken) lookhead;
            if (numberToken.getNumber() instanceof Double) {
                mv.visitTypeInsn(NEW, "com/googlecode/aviator/runtime/type/AviatorDouble");
                mv.visitInsn(DUP);
                mv.visitLdcInsn(numberToken.getNumber());
                mv.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;");
                mv.visitMethodInsn(INVOKESPECIAL, "com/googlecode/aviator/runtime/type/AviatorDouble", "<init>",
                    "(Ljava/lang/Number;)V");
            }
            else {
                mv.visitTypeInsn(NEW, "com/googlecode/aviator/runtime/type/AviatorLong");
                mv.visitInsn(DUP);
                mv.visitLdcInsn(numberToken.getNumber());
                mv.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;");
                mv.visitMethodInsn(INVOKESPECIAL, "com/googlecode/aviator/runtime/type/AviatorLong", "<init>",
                    "(Ljava/lang/Number;)V");
            }
            pushOperand(lookhead, 2);
            break;
        case String:
            // load string
            mv.visitTypeInsn(NEW, "com/googlecode/aviator/runtime/type/AviatorString");
            mv.visitInsn(DUP);
            mv.visitLdcInsn(lookhead.getValue(null));
            mv.visitMethodInsn(INVOKESPECIAL, "com/googlecode/aviator/runtime/type/AviatorString", "<init>",
                "(Ljava/lang/String;)V");
            pushOperand(lookhead, 2);
            break;
        case Pattern:
            // load pattern
            mv.visitTypeInsn(NEW, "com/googlecode/aviator/runtime/type/AviatorPattern");
            mv.visitInsn(DUP);
            mv.visitLdcInsn(lookhead.getValue(null));
            mv.visitMethodInsn(INVOKESPECIAL, "com/googlecode/aviator/runtime/type/AviatorPattern", "<init>",
                "(Ljava/lang/String;)V");
            pushOperand(lookhead, 2);
            break;
        case Variable:
            // load variable
            Variable variable = (Variable) lookhead;
            if (variable.equals(Variable.TRUE)) {
                mv.visitFieldInsn(GETSTATIC, "com/googlecode/aviator/runtime/type/AviatorBoolean", "TRUE",
                    "Lcom/googlecode/aviator/runtime/type/AviatorBoolean;");
                pushOperand(lookhead, 0);
            }
            else if (variable.equals(Variable.FALSE)) {
                mv.visitFieldInsn(GETSTATIC, "com/googlecode/aviator/runtime/type/AviatorBoolean", "FALSE",
                    "Lcom/googlecode/aviator/runtime/type/AviatorBoolean;");
                pushOperand(lookhead, 0);
            }
            else if (variable.equals(Variable.NIL)) {
                mv.visitFieldInsn(GETSTATIC, "com/googlecode/aviator/runtime/type/AviatorNil", "NIL",
                    "Lcom/googlecode/aviator/runtime/type/AviatorNil;");
                pushOperand(lookhead, 0);
            }
            else {
                mv.visitTypeInsn(NEW, "com/googlecode/aviator/runtime/type/AviatorJavaType");
                mv.visitInsn(DUP);
                mv.visitLdcInsn(variable.getLexeme());
                mv.visitMethodInsn(INVOKESPECIAL, "com/googlecode/aviator/runtime/type/AviatorJavaType", "<init>",
                    "(Ljava/lang/String;)V");
                pushOperand(lookhead, 2);
            }
            break;
        }

    }

}
