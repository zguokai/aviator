package com.googlecode.aviator.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.googlecode.aviator.AviatorEvaluator;


/**
 * A aviator shell for test
 * 
 * @author dennis
 * 
 */
public class AviatorShell {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String line = null;
        while ((line = reader.readLine()) != null) {
            if (line.equals("quit") || line.equals("exit")) {
                break;
            }

            try {
                Object result = AviatorEvaluator.execute(line);
                System.out.println(result);
            }
            catch (Throwable t) {
                System.err.println("evaluate error:" + t.getMessage());
            }
        }
    }
}
