package org.lpc;

import java.util.LinkedList;
import java.util.Queue;
import java.util.*;

public class Calculator {
    public double evaluate(String expression) {

        String postfixExpression = infixToPostfix(expression);

        System.out.println("Infix expression: " + expression);

        System.out.println("Postfix expression: " + postfixExpression);

        double result = evaluatePostfix(postfixExpression);

        System.out.println("Result: " + result + "\n");

        return result;
    }
    public String infixToPostfix(String inFixExpression) {
        inFixExpression = format(inFixExpression);

        Queue<String> postFixQueue = new LinkedList<>();

        // Stack to store the operators and parenthesis temporarily
        Stack<String> operatorStack = new Stack<>();

        // StringBuilder to accumulate multi-digit numbers and decimals
        StringBuilder currentNumber = new StringBuilder();

        for (char character : inFixExpression.toCharArray()) {


            // IMPORTANT:
            //  1. if the character is a digit or a decimal, add it to the current number
            //  2. if the character is an operator, add the current number to the postfix queue and process the operator
            //     - Pop operators with higher or equal precedence from operatorStack to postFixQueue
            //     - Push current operator to operatorStack
            //  3. if the character is a left parenthesis, push it to the operatorStack
            //  4. if the character is a right parenthesis, pop operators from operatorStack to postFixQueue until "(" is found
            //     - Remove the "(" from the stack
            //  5. Ignore spaces and new lines

            if (Character.isDigit(character) || character == '.') {
                currentNumber.append(character);
            }
            else {
                // If there is a number accumulated, add it to the postfix queue
                if (currentNumber.length() > 0) {
                    postFixQueue.add(currentNumber.toString());
                    currentNumber.setLength(0);  // Clear the buffer
                }

                String c = String.valueOf(character);

                switch (c) {
                    case "+", "-", "*", "/":
                        // Pop operators with higher or equal precedence from operatorStack to postFixQueue
                        while (!operatorStack.isEmpty() && precedence(operatorStack.peek()) >= precedence(c)) {
                            postFixQueue.add(operatorStack.pop());
                        }

                        // Push current operator to operatorStack
                        operatorStack.push(c);
                        break;

                    case "^":
                        // IMPORTANT: Right-associative operator -> Pop operators with strictly higher precedence
                        while (!operatorStack.isEmpty() && precedence(operatorStack.peek()) > precedence(c)) {
                            postFixQueue.add(operatorStack.pop());
                        }

                        // Push current operator to operatorStack
                        operatorStack.push(c);
                        break;

                    case "(":
                        // Push "(" to operatorStack
                        operatorStack.push(c);
                        break;

                    case ")":
                        // Pop operators from operatorStack to postFixQueue until "(" is found
                        while (!operatorStack.isEmpty() && !operatorStack.peek().equals("(")) {
                            postFixQueue.add(operatorStack.pop());
                        }

                        // Remove the "(" from the stack
                        if (!operatorStack.isEmpty()) {
                            operatorStack.pop();
                        }
                        break;

                    case " ":
                    case "\n":
                        // Ignore spaces and new lines
                        break;

                    default:
                        throw new IllegalArgumentException("Invalid character: " + character);
                }
            }
        }

        // If there is a number left in the buffer, add it to the postfix queue
        if (currentNumber.length() > 0) {
            postFixQueue.add(currentNumber.toString());
        }

        // Pop remaining operators from the stack
        while (!operatorStack.isEmpty()) {
            postFixQueue.add(operatorStack.pop());
        }

        // Convert the queue to a space-separated string for the final postfix expression
        StringBuilder postfixExpression = new StringBuilder();
        for (String token : postFixQueue) {
            postfixExpression.append(token).append(' ');
        }

        return postfixExpression.toString().trim();  // Remove trailing space
    }

    private String format(String expression) {
        // If there's a negative sign at the beginning, add a zero before it
        if (expression.startsWith("-")) {
            expression = "0" + expression;
        }

        // If there's a ( next to a number, add a multiplication sign between them
        expression = expression.replaceAll("(\\d)\\(", "$1*(");

        // If there's multiple spaces, replace them with a single space
        expression = expression.replaceAll("\\s+", " ");

        return expression;
    }
    private int precedence(String c) {
        return switch (c) {
            case "+", "-" -> 1;
            case "*", "/" -> 2;
            case "^" -> 3;
            default -> 0;   // Parentheses or invalid
        };
    }

    public double evaluatePostfix(String postfixExpression) {
        Stack<Double> stack = new Stack<>();
        String[] tokens = postfixExpression.split(" ");
        for (String token : tokens) {
            // If the token is a number, or a decimal push it to the stack
            if (token.matches("\\d+(\\.\\d+)?")) {
                stack.push(Double.parseDouble(token));
            }
            else {
                double operand2 = stack.pop();
                double operand1 = stack.pop();
                double result = switch (token) {
                    case "+" -> operand1 + operand2;
                    case "-" -> operand1 - operand2;
                    case "*" -> operand1 * operand2;
                    case "/" -> {
                        if (operand2 == 0) throw new ArithmeticException("Division by zero");
                        yield operand1 / operand2;
                    }
                    case "^" -> (int) Math.pow(operand1, operand2);
                    default -> throw new IllegalArgumentException("Invalid operator");
                };
                stack.push(result);
            }
        }

        return stack.pop();
    }
}
