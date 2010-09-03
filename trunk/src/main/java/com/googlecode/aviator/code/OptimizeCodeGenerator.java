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
package com.googlecode.aviator.code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import com.googlecode.aviator.code.asm.ASMCodeGenerator;
import com.googlecode.aviator.lexer.ExpressionLexer;
import com.googlecode.aviator.lexer.token.NumberToken;
import com.googlecode.aviator.lexer.token.OperatorToken;
import com.googlecode.aviator.lexer.token.OperatorType;
import com.googlecode.aviator.lexer.token.StringToken;
import com.googlecode.aviator.lexer.token.Token;
import com.googlecode.aviator.lexer.token.Variable;
import com.googlecode.aviator.lexer.token.Token.TokenType;
import com.googlecode.aviator.parser.ExpressionParser;
import com.googlecode.aviator.runtime.type.AviatorDouble;
import com.googlecode.aviator.runtime.type.AviatorLong;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.googlecode.aviator.runtime.type.AviatorPattern;
import com.googlecode.aviator.runtime.type.AviatorString;


/**
 * Compile optimize
 * 
 * @author dennis
 * 
 */
public class OptimizeCodeGenerator implements CodeGenerator {
    private final ASMCodeGenerator asmCodeGenerator =
            new ASMCodeGenerator(AviatorEvaluator.getAviatorClassLoader(), false);

    private final List<Token<?>> tokenList = new ArrayList<Token<?>>();


    public static void main(String[] args) throws Exception {
        OptimizeCodeGenerator codeGenerator = new OptimizeCodeGenerator();
        // ASMCodeGenerator codeGenerator = new
        // ASMCodeGenerator(AviatorEvaluator.getAviatorClassLoader(), false);
        ExpressionParser parser =
                new ExpressionParser(new ExpressionLexer("1000+100.0*99-(600-3*15)%(((68-9)-3)*2-100)+10000%7*71  "),
                    codeGenerator);
        Expression exp = new Expression(parser.parse());
        Map<String, Object> env = new HashMap<String, Object>();
        env.put("a", 3);
        env.put("b", 4.3);
        System.out.println(exp.execute(env));
    }


    private int execute() {
        int exeCount = 0;
        final int size = tokenList.size();
        for (int i = 0; i < size; i++) {
            Token<?> token = tokenList.get(i);

            if (token.getType() == TokenType.Operator) {
                final OperatorToken op = (OperatorToken) token;
                final OperatorType operatorType = op.getOperatorType();
                final int operandCount = operatorType.getOperandCount();
                boolean canExecute = true;
                for (int j = i - operandCount; j < i; j++) {
                    token = tokenList.get(j);
                    if (token == null) {
                        compactTokenList();
                        return exeCount;
                    }
                    if (token.getType() == TokenType.Operator || token.getType() == TokenType.Variable) {
                        canExecute = false;
                        break;
                    }
                }

                if (canExecute) {
                    AviatorObject[] args = new AviatorObject[operandCount];
                    int index = 0;
                    for (int j = i - operandCount; j < i; j++) {
                        args[index++] = getAviatorObjectFromToken(tokenList.get(j));
                        tokenList.set(j, null);
                    }
                    AviatorObject result = operatorType.eval(args);
                    tokenList.set(i, getTokenFromOperand(result));
                    exeCount++;
                }

            }
        }
        compactTokenList();
        return exeCount;
    }


    private Token<?> getTokenFromOperand(AviatorObject operand) {
        Token<?> token = null;
        switch (operand.getAviatorType()) {
        case Boolean:
            token = operand.booleanValue(null) ? Variable.TRUE : Variable.FALSE;
            break;
        case Nil:
            token = Variable.NIL;
            break;
        case Number:
            final Number value = (Number) operand.getValue(null);
            token = new NumberToken(value, value.toString());
            break;
        case String:
            final String str = (String) operand.getValue(null);
            token = new StringToken(str, -1);
            break;
        }
        return token;
    }


    private void compactTokenList() {
        Iterator<Token<?>> it = this.tokenList.iterator();
        while (it.hasNext()) {
            if (it.next() == null) {
                it.remove();
            }
        }
    }


    private AviatorObject getAviatorObjectFromToken(Token<?> lookhead) {
        AviatorObject result = null;
        switch (lookhead.getType()) {
        case Number:
            // load numbers
            NumberToken numberToken = (NumberToken) lookhead;
            if (numberToken.getNumber() instanceof Double) {
                result = AviatorDouble.valueOf(numberToken.getNumber());
            }
            else {
                result = AviatorLong.valueOf(numberToken.getNumber());
            }
            break;
        case String:
            // load string
            result = new AviatorString((String) lookhead.getValue(null));
            break;
        case Pattern:
            // load pattern
            result = new AviatorPattern((String) lookhead.getValue(null));
            break;
        }
        return result;
    }


    public Class<?> getResult() {
        // execute literal expression
        while (execute() > 0) {
            ;
        }

        // call asm to generate byte codes
        while (callASM() > 0) {
            ;
        }
        // still have token,push them to operand stack
        for (Token<?> token : tokenList) {
            if (token != DUMMY) {
                this.asmCodeGenerator.onConstant(token);
            }
        }
        // get result from asm
        return asmCodeGenerator.getResult();
    }


    private int callASM() {
        int callCount = 0;
        if (tokenList.size() == 0) {
            return 0;
        }
        OperatorToken op = null;
        int operandCount = 0;
        int size = tokenList.size();
        for (int i = 0; i < size; i++) {
            Token<?> token = tokenList.get(i);
            if (token.getType() == TokenType.Operator) {
                op = (OperatorToken) token;
                if (op.getOperatorType().getOperandCount() == operandCount) {
                    switch (op.getOperatorType()) {
                    case ADD:
                        onOperand(operandCount, i);
                        this.asmCodeGenerator.onAdd(null);
                        break;
                    case SUB:
                        onOperand(operandCount, i);
                        this.asmCodeGenerator.onSub(null);
                        break;
                    case MULT:
                        onOperand(operandCount, i);
                        this.asmCodeGenerator.onMult(null);
                        break;
                    case DIV:
                        onOperand(operandCount, i);
                        this.asmCodeGenerator.onDiv(null);
                        break;
                    case MOD:
                        onOperand(operandCount, i);
                        this.asmCodeGenerator.onMod(null);
                        break;
                    case EQ:
                        onOperand(operandCount, i);
                        this.asmCodeGenerator.onEq(null);
                        break;
                    case NEQ:
                        onOperand(operandCount, i);
                        this.asmCodeGenerator.onNeq(null);
                        break;
                    case LT:
                        onOperand(operandCount, i);
                        this.asmCodeGenerator.onLt(null);
                        break;
                    case LE:
                        onOperand(operandCount, i);
                        this.asmCodeGenerator.onLe(null);
                        break;
                    case GT:
                        onOperand(operandCount, i);
                        this.asmCodeGenerator.onGt(null);
                        break;
                    case GE:
                        onOperand(operandCount, i);
                        this.asmCodeGenerator.onGe(null);
                        break;
                    }
                    this.tokenList.set(i, DUMMY);
                    callCount++;
                }
                operandCount = 0;
            }
            else {
                operandCount++;
            }

        }
        compactTokenList();
        return callCount;
    }

    private final Token<?> DUMMY = new Variable("dummy", -1);


    private void onOperand(int operandCount, int i) {
        for (int j = i - operandCount; j < i; j++) {
            final Token<?> lookhead = tokenList.get(j);
            if (lookhead != DUMMY) {
                asmCodeGenerator.onConstant(lookhead);
            }
            tokenList.set(j, null);
        }
    }


    public void onAdd(Token<?> lookhead) {
        tokenList.add(new OperatorToken(lookhead == null ? -1 : lookhead.getStartIndex(), OperatorType.ADD));

    }


    public void onAndLeft(Token<?> lookhead) {
        // TODO Auto-generated method stub

    }


    public void onAndRight(Token<?> lookhead) {
        // TODO Auto-generated method stub

    }


    public void onConstant(Token<?> lookhead) {
        tokenList.add(lookhead);
    }


    public void onDiv(Token<?> lookhead) {
        tokenList.add(new OperatorToken(lookhead == null ? -1 : lookhead.getStartIndex(), OperatorType.DIV));

    }


    public void onElementEnd(Token<?> lookhead) {
        // TODO Auto-generated method stub

    }


    public void onElementStart(Token<?> lookhead) {
        // TODO Auto-generated method stub

    }


    public void onEq(Token<?> lookhead) {
        tokenList.add(new OperatorToken(lookhead == null ? -1 : lookhead.getStartIndex(), OperatorType.EQ));

    }


    public void onGe(Token<?> lookhead) {
        tokenList.add(new OperatorToken(lookhead == null ? -1 : lookhead.getStartIndex(), OperatorType.GE));

    }


    public void onGt(Token<?> lookhead) {
        tokenList.add(new OperatorToken(lookhead == null ? -1 : lookhead.getStartIndex(), OperatorType.GT));

    }


    public void onJoinLeft(Token<?> lookhead) {
        // TODO Auto-generated method stub

    }


    public void onJoinRight(Token<?> lookhead) {
        // TODO Auto-generated method stub

    }


    public void onLe(Token<?> lookhead) {
        tokenList.add(new OperatorToken(lookhead == null ? -1 : lookhead.getStartIndex(), OperatorType.LE));

    }


    public void onLt(Token<?> lookhead) {
        tokenList.add(new OperatorToken(lookhead == null ? -1 : lookhead.getStartIndex(), OperatorType.LT));

    }


    public void onMatch(Token<?> lookhead) {
        tokenList.add(new OperatorToken(lookhead == null ? -1 : lookhead.getStartIndex(), OperatorType.MATCH));

    }


    public void onMethodInvoke(Token<?> lookhead) {
        // TODO Auto-generated method stub

    }


    public void onMethodName(Token<?> lookhead) {
        // TODO Auto-generated method stub

    }


    public void onMethodParameter(Token<?> lookhead) {
        // TODO Auto-generated method stub

    }


    public void onMod(Token<?> lookhead) {
        tokenList.add(new OperatorToken(lookhead == null ? -1 : lookhead.getStartIndex(), OperatorType.MOD));

    }


    public void onMult(Token<?> lookhead) {
        tokenList.add(new OperatorToken(lookhead == null ? -1 : lookhead.getStartIndex(), OperatorType.MULT));

    }


    public void onNeg(Token<?> lookhead) {
        // TODO Auto-generated method stub

    }


    public void onNeq(Token<?> lookhead) {
        tokenList.add(new OperatorToken(lookhead == null ? -1 : lookhead.getStartIndex(), OperatorType.NEQ));

    }


    public void onNot(Token<?> lookhead) {
        // TODO Auto-generated method stub

    }


    public void onSub(Token<?> lookhead) {
        tokenList.add(new OperatorToken(lookhead == null ? -1 : lookhead.getStartIndex(), OperatorType.SUB));

    }


    public void onTernaryBoolean(Token<?> lookhead) {
        // TODO Auto-generated method stub

    }


    public void onTernaryLeft(Token<?> lookhead) {
        // TODO Auto-generated method stub

    }


    public void onTernaryRight(Token<?> lookhead) {
        // TODO Auto-generated method stub

    }

}
