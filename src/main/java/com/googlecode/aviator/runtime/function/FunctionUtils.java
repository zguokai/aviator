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
package com.googlecode.aviator.runtime.function;

import java.util.Map;

import com.googlecode.aviator.exception.ExpressionRuntimeException;
import com.googlecode.aviator.runtime.type.AviatorFunction;
import com.googlecode.aviator.runtime.type.AviatorJavaType;
import com.googlecode.aviator.runtime.type.AviatorObject;


/**
 * Function helper
 * 
 * @author dennis
 * 
 */
public class FunctionUtils {

    public static final String getStringValue(int index, AviatorObject[] args, Map<String, Object> env) {
        String result = null;

        final Object value = args[index].getValue(env);
        if (value instanceof Character) {
            result = value.toString();
        }
        else {
            result = (String) value;
        }
        return result;
    }


    public static AviatorFunction getFunction(int index, AviatorObject[] args, Map<String, Object> env) {
        final AviatorObject arg = args[index];
        if (!(arg instanceof AviatorJavaType)) {
            throw new ExpressionRuntimeException(arg.desc(env) + " is not a function");
        }
        return (AviatorFunction) env.get(((AviatorJavaType) arg).getName());
    }


    public static final Number getNumberValue(int index, AviatorObject[] args, Map<String, Object> env) {

        return (Number) args[index].getValue(env);
    }


    public static final Iterable<?> getSeq(int index, AviatorObject[] args, Map<String, Object> env) {
        return (Iterable<?>) args[index].getValue(env);
    }

}
