package backend;

import assistance.Defines;
import backend.Parser.*;

import java.util.Stack;

import assistance.Validator;
import assistance.Token;

public class Calculator extends CStack {
    public static int evaluatePrefixExpression(String _expression) {
        m_stack = new Stack<Integer>();
        Stack<String> m_operatorStack = new Stack<String>();
        String m_expression = _expression.substring(0, _expression.length() - 1);
        String[] tokens = m_expression.split(" ");

        for(int i = tokens.length - 1; i >= 0; i--) {
            String token = tokens[i];
            if(Parser.IsNumberCalc(token)) {
                int operand = Integer.parseInt(token);
                m_stack.push(operand);
            } else if(Parser.IsOperatorCalc(token)) {
                while(!m_stack.isEmpty() && !m_operatorStack.isEmpty()
                        &&  operationPriority(token, m_operatorStack.peek())) {
                    int result = performExpression(m_operatorStack.pop(), m_stack.pop(),m_stack.pop());
                    m_stack.push(result);
                }
                m_operatorStack.push(token);
            }
        }

        while(!m_operatorStack.isEmpty()) {
            int result =performExpression(m_operatorStack.pop(),m_stack.pop(),m_stack.pop());
            m_stack.push(result);
        }
        return m_stack.pop();
    }

    public static int evaluatePostfixExpression(String _expression) {
        m_stack = new Stack<Integer>();
        Stack<String> m_stackOperator = new Stack<String>();
        StringBuilder builder = new StringBuilder();
        String m_expression;
        String[] tokens = new String[]{};
        if(_expression.endsWith("=")) {
            m_expression = _expression.replace("=", "");
            tokens = m_expression.split(" ");
        } else {
            tokens = _expression.split(" ");
        }

        for(String token : tokens) {
            if(token.equals("(")) {
                m_stackOperator.push(token);
            } else if(token.equals(")")) {
                while(!m_stackOperator.isEmpty() && !m_stackOperator.peek().equals("(")) {

                    int result = performExpression(m_stackOperator.pop(),m_stack.pop(),m_stack.pop());
                    m_stack.push(result);
                }
            } else if(Parser.IsNumberCalc(token)) {
                int operand = Integer.parseInt(token);
                m_stack.push(operand);
            } else if(Parser.IsOperatorCalc(token)) {
                while(!m_stack.isEmpty() && !m_stackOperator.isEmpty()
                        && operationPriority(token, m_stackOperator.peek())) {
                    int result = performExpression(m_stackOperator.pop(), m_stack.pop(), m_stack.pop());
                    m_stack.push(result);
                }
                m_stackOperator.push(token);
            }
        }

        while(!m_stackOperator.isEmpty()) {
            int result = performExpression(m_stackOperator.pop(), m_stack.pop(), m_stack.pop());
            m_stack.push(result);
        }

        return m_stack.pop();
    }

    public static String evaluateInfixExpression(String _expression) {
        Stack<String> m_operatorStack = new Stack<String>();
        StringBuilder postfix = new StringBuilder();
        String m_expression = _expression.substring(0, _expression.length() - 1);
        String[] tokens = m_expression.split(" ");

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

    private static int performExpression(String _operator, int t1_operand, int t2_operand) {
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
                return t1_operand / t2_operand;
            case "*":
                return t1_operand * t2_operand;
            case "%":
                return t1_operand % t2_operand;
            case "^":
                return (int) Math.pow(t1_operand, t2_operand);
            default:
                throw new IllegalArgumentException("Doesn't supported type" + _operator);
        }
    }

    private static boolean operationPriority(String t1_operator, String t2_operator) {
        int t1_precedence = Parser.getPrecedence(getToken(t1_operator));
        int t2_precedence = Parser.getPrecedence(getToken(t2_operator));
        return t1_precedence <= t2_precedence;
    }

    private static Token getToken(String _operator) {
        switch(_operator) {
            case "+":
                return Token.O_PLUS;
            case "-":
                return Token.O_MINUS;
            case "*":
                return Token.O_MUL;
            case "/":
                return Token.O_DIV;
            case "^":
                return Token.O_POW;
            case "(":
                return Token.O_OPBRACK;
            case ")":
                return Token.O_CLOBRACK;
            case "c":
                return Token.O_COS;
            case "C":
                return Token.O_ACOS;
            case "s":
                return Token.O_SIN;
            case "S":
                return Token.O_ASIN;
            case "T":
                return Token.O_ATAN;
            case "Q":
                return Token.O_SQRT;
            case "L":
                return Token.O_LOG;

            default:
                return Token.O_UNKNOWN;
        }
    }

    private static Stack<Integer> m_stack;
}

