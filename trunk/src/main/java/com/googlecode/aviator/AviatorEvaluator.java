package com.googlecode.aviator;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.googlecode.aviator.code.CodeGenerator;
import com.googlecode.aviator.code.asm.ASMCodeGenerator;
import com.googlecode.aviator.exception.CompileExpressionErrorException;
import com.googlecode.aviator.lexer.ExpressionLexer;
import com.googlecode.aviator.parser.AviatorClassLoader;
import com.googlecode.aviator.parser.ExpressionParser;


/**
 * Avaitor Evaluator
 * 
 * @author dennis
 * 
 */
public final class AviatorEvaluator {
    // The classloader to define generated class
    private static AviatorClassLoader aviatorClassLoader =
            new AviatorClassLoader(AviatorEvaluator.class.getClassLoader());

    /**
     * Compiled Expression cache
     */
    private final static ConcurrentHashMap<String/* text expression */, Expression/*
                                                                                   * Compiled
                                                                                   * expression
                                                                                   */> cacheExpression =
            new ConcurrentHashMap<String, Expression>();


    private AviatorEvaluator() {

    }


    public static void clearExpressionCache() {
        cacheExpression.clear();
    }


    public static AviatorClassLoader getAviatorClassLoader() {
        return aviatorClassLoader;
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
        if (expression == null || expression.length() == 0) {
            throw new IllegalArgumentException("Blank expression");
        }
        ExpressionLexer lexer = new ExpressionLexer(expression);
        CodeGenerator codeGenerator =
                new ASMCodeGenerator(aviatorClassLoader, Boolean.valueOf(System.getProperty("aviator.asm.trace",
                    "false")));
        ExpressionParser parser = new ExpressionParser(lexer, codeGenerator);

        try {
            Class<?> clazz = parser.parse();
            Expression compiledExpression = new Expression(clazz);
            if (cached) {
                cacheExpression.putIfAbsent(expression, compiledExpression);
            }
            return compiledExpression;
        }
        catch (Throwable t) {
            throw new CompileExpressionErrorException("Compile expression error", t);
        }

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
     * Execute a text expression with enviroment
     * 
     * @param expression
     *            text expression
     * @param env
     *            Binding variable enviroment
     * @param cached
     *            Whether to cache the compiled result,make true to cache it.
     */
    public static Object execute(String expression, Map<String, Object> env, boolean cached) {
        if (cached) {
            Expression compiledExpression = cacheExpression.get(expression);
            if (compiledExpression == null) {
                compiledExpression = compile(expression);
                Expression oldExpression = cacheExpression.putIfAbsent(expression, compiledExpression);
                if (oldExpression != null) {
                    compiledExpression = oldExpression;
                }
            }
            return compiledExpression.execute(env);
        }
        else {

            return compile(expression).execute(env);
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
}
