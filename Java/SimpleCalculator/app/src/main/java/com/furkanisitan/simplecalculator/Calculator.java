package com.furkanisitan.simplecalculator;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Stack;
import java.util.StringTokenizer;

public class Calculator {

    private static String operators = "-÷x+";

    static String postfixCalculator(String postfixExpression) {

        String temp;
        StringTokenizer tokenizer = new StringTokenizer(postfixExpression," ", true);	// Boşluklara göre ayırır
        Stack<String> stack = new Stack();

        while(tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            try {
                Double.parseDouble(token);	// operand
                stack.push(token);
            } catch (Exception e) {
                if (!token.equals(" ")) {	// operator
                    temp = stack.pop();
                    if (stack.isEmpty())    // Fazldan verilmiş operatörleri dikkate almayacaktır.
                        return temp;
                    try {
                        stack.push(getResults(stack.pop(), token, temp));
                    } catch (ArithmeticException e2) {
                        return null;
                    }
                }
            }
        }
        return stack.pop();
    }

    private static String getResults(String operand1, String operator, String operand2)	{

        BigDecimal bd1 = new BigDecimal(operand1);
        BigDecimal bd2 = new BigDecimal(operand2);

        switch (operator) {
            case "x":
                return "" + bd1.multiply(bd2, MathContext.DECIMAL64);
            case "÷":
                return "" + bd1.divide(bd2, MathContext.DECIMAL64);
            case "+":
                return "" + bd1.add(bd2, MathContext.DECIMAL64);
            case "-":
                return "" + bd1.subtract(bd2, MathContext.DECIMAL64);
        }
        return null;
    }

    static String infixtoPostfix(String expression) {

        expression = expression.replaceAll(" ", "");
        String postfix = "", temp, prevToken = "";
        StringTokenizer tokenizer = new StringTokenizer(expression, operators + "()", true);
        Stack<String> stack = new Stack();
        boolean negative = false;	// negative sayılarda '-' operator olarak algılanmamalı

        while(tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            try {
                if (negative)
                    token = "-" + token;
                if (token.indexOf('E') >= 0 )	// Son karakter 'E' ise parseDouble metodu exception fırlatır.
                    postfix += token;
                Double.parseDouble(token);  // Exception alınmazsa Token operanddır.
                postfix += token + " ";
                negative = false;
                if (prevToken.equals(")"))	// Örn: (4-8)2 = -8 olmalı
                    stack.push("x");
            } catch (Exception e) {
                if (token.equals("(")) {
                    if (operators.indexOf(prevToken) < 0 && !prevToken.equals("(")) //Bir önceki token, operator değilse
                        stack.push("x");
                    stack.push(token);
                }
                else if (token.equals(")")) {
                    if (prevToken.equals("") || operators.indexOf(prevToken) >= 0)
                        return null;
                    while(!(temp = stack.isEmpty() ? "" : stack.pop()).equals("(")) {
                        if (temp.equals(""))	// stackin sonuna kadar gidip '(' bulunmamışsa
                            return null;
                        postfix += temp + " ";
                    }
                }
                else if (getPriority(token) > 0) {
                    if (prevToken.charAt(prevToken.length()-1) == 'E')	// Bir önceki tokende 'E' varsa, token operatör değildir.
                        postfix += token;
                    else if (token.equals("-") && (prevToken.equals("") || "(÷x)".indexOf(prevToken) >= 0))
                        negative = true;	// token=='-' ve prevToken boşsa veya "÷,x,(" lerden birine eşitse
                    else {
                        negative = false;
                        while(getPriority(stack.isEmpty() ? "" : stack.peek() ) >= getPriority(token))
                            postfix += stack.pop() + " ";
                        stack.push(token);
                    }
                }
            }
            prevToken = token;
        }
        if (negative || prevToken.equals("(")) // Parantezden sonra operand yoksa
            return null;
        if (operators.indexOf(prevToken) >= 0)	// Son token operator ise, bu operator fazlalıktır.
            stack.pop();	// Not: Hesap makinesinde peş peşe iki operand gelemeyecek şekilde ayarlandı.
        // O yüzden en fazla bir tane operator fazlalık olabilir.
        while(!stack.isEmpty()) {
            temp = stack.pop();
            if (!temp.equals("("))	// Parantez kullanılıp kapatılmamışsa varsayılan olarak
                postfix += temp + " ";	//parantez içi işlem ifadenin sonuna kadar yapılır
        }
        return postfix;
    }

    private static int getPriority(String operator) {
        switch (operator) {
            case "x": case "÷":
                return 2;
            case "+": case "-":
                return 1;
        }
        return 0;
    }
}

