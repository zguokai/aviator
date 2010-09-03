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
package com.googlecode.aviator;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashMap;
import java.util.Map;

import com.googlecode.aviator.code.CodeGenerator;
import com.googlecode.aviator.code.asm.ASMCodeGenerator;
import com.googlecode.aviator.exception.CompileExpressionErrorException;
import com.googlecode.aviator.exception.ExpressionRuntimeException;
import com.googlecode.aviator.lexer.ExpressionLexer;
import com.googlecode.aviator.lexer.token.OperatorType;
import com.googlecode.aviator.parser.AviatorClassLoader;
import com.googlecode.aviator.parser.ExpressionParser;
import com.googlecode.aviator.runtime.function.math.MathAbsFunction;
import com.googlecode.aviator.runtime.function.math.MathCosFunction;
import com.googlecode.aviator.runtime.function.math.MathLog10Function;
import com.googlecode.aviator.runtime.function.math.MathLogFunction;
import com.googlecode.aviator.runtime.function.math.MathPowFunction;
import com.googlecode.aviator.runtime.function.math.MathSinFunction;
import com.googlecode.aviator.runtime.function.math.MathSqrtFunction;
import com.googlecode.aviator.runtime.function.math.MathTanFunction;
import com.googlecode.aviator.runtime.function.seq.SeqMapFunction;
import com.googlecode.aviator.runtime.function.seq.SeqReduceFunction;
import com.googlecode.aviator.runtime.function.string.StringContainsFunction;
import com.googlecode.aviator.runtime.function.string.StringEndsWithFunction;
import com.googlecode.aviator.runtime.function.string.StringLengthFunction;
import com.googlecode.aviator.runtime.function.string.StringStartsWithFunction;
import com.googlecode.aviator.runtime.function.string.StringSubStringFunction;
import com.googlecode.aviator.runtime.function.system.BinaryFunction;
import com.googlecode.aviator.runtime.function.system.PrintFunction;
import com.googlecode.aviator.runtime.function.system.PrintlnFunction;
import com.googlecode.aviator.runtime.function.system.SysDateFunction;
import com.googlecode.aviator.runtime.type.AviatorFunction;


/**
 * Avaitor Expression evaluator
 * 
 * @author dennis
 * 
 */
public final class AviatorEvaluator {
    // The classloader to define generated class
    private static AviatorClassLoader aviatorClassLoader;

    static {
        aviatorClassLoader = AccessController.doPrivileged(new PrivilegedAction<AviatorClassLoader>() {

            public AviatorClassLoader run() {
                return new AviatorClassLoader(AviatorEvaluator.class.getClassLoader());
            }

        });
    }

    public final static Map<String, Object> FUNC_MAP = new HashMap<String, Object>();

    static {
        // Load internal functions
        // load sys lib
        addFunction(new SysDateFunction());
        addFunction(new PrintlnFunction());
        addFunction(new PrintFunction());
        addFunction(new BinaryFunction(OperatorType.ADD));
        addFunction(new BinaryFunction(OperatorType.SUB));
        addFunction(new BinaryFunction(OperatorType.MULT));
        addFunction(new BinaryFunction(OperatorType.DIV));
        addFunction(new BinaryFunction(OperatorType.MOD));
        addFunction(new BinaryFunction(OperatorType.NEG));
        addFunction(new BinaryFunction(OperatorType.NOT));

        // load string lib

        addFunction(new StringContainsFunction());
        addFunction(new StringStartsWithFunction());
        addFunction(new StringEndsWithFunction());
        addFunction(new StringSubStringFunction());
        addFunction(new StringLengthFunction());
        // load math lib
        addFunction(new MathAbsFunction());
        addFunction(new MathPowFunction());
        addFunction(new MathSqrtFunction());
        addFunction(new MathLog10Function());
        addFunction(new MathLogFunction());
        addFunction(new MathSinFunction());
        addFunction(new MathCosFunction());
        addFunction(new MathTanFunction());

        // seq lib
        addFunction(new SeqMapFunction());
        addFunction(new SeqReduceFunction());
    }

    /**
     * Compiled Expression cache
     */
    private final static Map<String/* text expression */, Expression/*
                                                                     * Compiled
                                                                     * expression
                                                                     */> cacheExpressions =
            new HashMap<String, Expression>();


    private AviatorEvaluator() {

    }


    public static void clearExpressionCache() {
        cacheExpressions.clear();
    }


    public static AviatorClassLoader getAviatorClassLoader() {
        return aviatorClassLoader;
    }


    /**
     * Add a aviator function
     * 
     * @param function
     */
    public static void addFunction(AviatorFunction function) {
        FUNC_MAP.put(function.getName(), function);
    }


    /**
     * Remove a aviator function by name
     * 
     * @param name
     * @return
     */
    public static AviatorFunction removeFunction(String name) {
        return (AviatorFunction) FUNC_MAP.remove(name);
    }


    /**
     * Remove a aviator function
     * 
     * @param function
     * @return
     */
    public static AviatorFunction removeFunction(AviatorFunction function) {
        return removeFunction(function.getName());
    }


    public static void setAviatorClassLoader(AviatorClassLoader aviatorClassLoader) {
        AviatorEvaluator.aviatorClassLoader = aviatorClassLoader;
    }


    /**
     * Compile a text expression to Expression object
     * 
     * @param expression
     *            text expression
     * @param cached
     *            Whether to cache the compiled result,make true to cache it.
     * @return
     */
    public static Expression compile(String expression, boolean cached) {
        if (expression == null || expression.trim().length() == 0) {
            throw new CompileExpressionErrorException("Blank expression");
        }
        if (cached) {
            synchronized (cacheExpressions) {
                Expression result = cacheExpressions.get(expression);
                if (result != null) {
                    return result;
                }
            }
        }
        ExpressionLexer lexer = new ExpressionLexer(expression);
        CodeGenerator codeGenerator =
                new ASMCodeGenerator(aviatorClassLoader, Boolean.valueOf(System.getProperty("aviator.asm.trace",
                    "false")));
        ExpressionParser parser = new ExpressionParser(lexer, codeGenerator);

        try {
            if (cached) {
                synchronized (cacheExpressions) {
                    // double check
                    Expression result = cacheExpressions.get(expression);
                    if (result != null) {
                        return result;
                    }
                    else {
                        result = innerCompile(parser);
                        // store result to cache
                        cacheExpressions.put(expression, result);
                        return result;
                    }
                }
            }
            else {
                return innerCompile(parser);
            }
        }
        catch (Throwable t) {
            throw new CompileExpressionErrorException("Compile expression error", t);
        }

    }


    private static Expression innerCompile(ExpressionParser parser) throws NoSuchMethodException {
        Expression result;
        Class<?> clazz = parser.parse();
        result = new Expression(clazz);
        return result;
    }


    /**
     * Compile a text expression to Expression Object without caching
     * 
     * @param expression
     * @return
     */
    public static Expression compile(String expression) {
        return compile(expression, false);
    }


    /**
     * Execute a text expression with environment
     * 
     * @param expression
     *            text expression
     * @param env
     *            Binding variable environment
     * @param cached
     *            Whether to cache the compiled result,make true to cache it.
     */
    public static Object execute(String expression, Map<String, Object> env, boolean cached) {
        Expression compiledExpression = null;
        if (cached) {
            synchronized (cacheExpressions) {
                compiledExpression = cacheExpressions.get(expression);
                if (compiledExpression == null) {
                    compiledExpression = compile(expression);
                }
            }
        }
        else {
            compiledExpression = compile(expression);
        }
        if (compiledExpression != null) {
            return compiledExpression.execute(env);
        }
        else {
            throw new ExpressionRuntimeException("Null compiled expression for " + expression);
        }
    }


    /**
     * Execute a text expression without caching
     * 
     * @param expression
     * @param env
     * @return
     */
    public static Object execute(String expression, Map<String, Object> env) {
        return execute(expression, env, false);
    }


    /**
     * Invalidate expression cache
     * 
     * @param expression
     */
    public static void invalidateCache(String expression) {
        synchronized (cacheExpressions) {
            cacheExpressions.remove(expression);
        }
    }


    /**
     * Execute a text expression without caching
     * 
     * @param expression
     * @return
     */
    public static Object execute(String expression) {
        return execute(expression, null);
    }
}
