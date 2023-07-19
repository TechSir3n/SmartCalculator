package backend;

import assistance.Defines;
import backend.Parser.*;

import java.util.Stack;

import assistance.Validator;
import assistance.Token;

public final class Calculator extends CStack {
    public static double evaluatePrefixExpression(String _expression) {
        m_stack = new Stack<Double>();
        Stack<String> m_operatorStack = new Stack<String>();
        String[] tokens = _expression.split(" ");

        for(int i = tokens.length - 1; i >= 0; i--) {
            String token = tokens[i];
            if(Parser.IsNumberCalc(token)) {
                double operand = Integer.parseInt(token);
                m_stack.push(operand);
            } else if(Parser.IsOperatorCalc(token)) {
                while(!m_stack.isEmpty() && !m_operatorStack.isEmpty()
                        &&  operationPriority(token, m_operatorStack.peek())) {
                    double result = performExpression(m_operatorStack.pop(), m_stack.pop(),m_stack.pop());
                    m_stack.push(result);
                }
                m_operatorStack.push(token);
            }
        }

        while(!m_operatorStack.isEmpty()) {
            double result = performExpression(m_operatorStack.pop(),m_stack.pop(),m_stack.pop());
            m_stack.push(result);
        }
        return m_stack.pop();
    }

    public static double evaluatePostfixExpression(String _expression) {
        m_stack = new Stack<Double>();
        Stack<String> m_stackOperator = new Stack<String>();

        String[] tokens = tokens = _expression.split(" ");

        for(String token : tokens) {
         if(Parser.IsNumberCalc(token)) {
                    double operand = Double.parseDouble(token);
                    m_stack.push(operand);
                } else if(Parser.IsOperatorCalc(token)) {
                    while(!m_stack.isEmpty() && !m_stackOperator.isEmpty()
                            && operationPriority(token, m_stackOperator.peek())) {
                        double operand2 = m_stack.pop();
                        double operand1 = m_stack.pop();
                        double result = performExpression(m_stackOperator.pop(),operand1,operand2);
                        m_stack.push(result);
                    }
                    m_stackOperator.push(token);
                }
            }

            while(!m_stackOperator.isEmpty()) {
                double operand2 = m_stack.pop();
                double operand1 = m_stack.pop();
                double result = performExpression(m_stackOperator.pop(),operand1,operand2);
                m_stack.push(result);
            }

            return m_stack.pop();
        }

    public static String evaluateInfixExpression(String _expression) {
        Stack<String> m_operatorStack = new Stack<String>();
        StringBuilder postfix = new StringBuilder();
        String[] tokens = _expression.split(" ");

        for(String token : tokens) {
            if(Parser.IsNumberCalc(token)) {
                postfix.append(token).append(" ");
            } else if(Parser.IsOperatorCalc(token)) {
                while(!m_operatorStack.isEmpty()) {
                    if(operationPriority(token,m_operatorStack.peek())) {
                        break;
                    }
                    postfix.append(m_operatorStack.pop()).append(" ");
                }
                m_operatorStack.push(token);
            }
        }

        while(!m_operatorStack.isEmpty()) {
            postfix.append(m_operatorStack.pop()).append(" ");
        }

        return postfix.toString().trim();
    }

    public static double evaluateFuncExpression(String _expression) {
        m_stack = new Stack<Double>();
        Stack<Character> operatorStack = new Stack<Character>();

        for(int i = 0; i < _expression.length();i++) {
            char c = _expression.charAt(i);
            if(Parser.IsFunctionCalc(String.valueOf(c))) {
                operatorStack.push(c);
            } else if(Parser.IsNumberCalc(String.valueOf(c))) {
                double result = performExpression(String.valueOf(operatorStack.pop()),
                        Double.parseDouble(String.valueOf(c)),0.0);
                m_stack.push(result);
            }
        }
        return m_stack.pop();
    }

    private static double performExpression(String _operator, double t1_operand, double t2_operand) {
        if(Validator.IsDivisionByZero(_operator + String.valueOf(t1_operand)) ||
                Validator.IsDivisionByZero(_operator + String.valueOf(t2_operand))) {
            throw new ArithmeticException(Defines.BAD_ATTEMPT.getDescription());
        }

        switch(_operator) {
            case "+":
                return t1_operand + t2_operand;
            case "-":
                return t1_operand - t2_operand;
            case "/":
                return t2_operand / t1_operand;
            case "*":
                return t1_operand * t2_operand;
            case "%":
                return t1_operand % t2_operand;
            case "^":
                return Math.pow(t1_operand, t2_operand);
            case "c":
                return Math.cos(t1_operand);
            case "s":
                return Math.sin(t1_operand);
            case "S":
                return Math.asin(t1_operand);
            case "C":
                return Math.acos(t1_operand);
            case "T":
                return Math.atan(t1_operand);
            case "Q":
                return Math.sqrt(t1_operand);
            case "L":
                return Math.log(t1_operand);
            case "t":
                return Math.tan(t1_operand);
            default:
                throw new IllegalArgumentException("Doesn't supported type" + _operator);
        }
    }

    private static boolean operationPriority(String t1_operator, String t2_operator) {
        int t1_precedence = Parser.getPrecedence(Parser.getToken(t1_operator));
        int t2_precedence = Parser.getPrecedence(Parser.getToken(t2_operator));
        return t1_precedence >= t2_precedence;
    }


    private static Stack<Double> m_stack;
}

