package backend;

import assistance.Defines;
import backend.Parser.*;

import java.util.Stack;

import assistance.Validator;
import assistance.Token;

public class Calculator extends CStack {
    public static double evaluatePrefixExpression(String _expression) {
        m_stack = new Stack<Double>();
        Stack<String> m_operatorStack = new Stack<String>();
        String m_expression = _expression.substring(0, _expression.length() - 1);
        String[] tokens = m_expression.split(" ");

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
                    double result = performExpression(m_stackOperator.pop(),m_stack.pop(),m_stack.pop());
                    m_stack.push(result);
                }
                } else if(Parser.IsNumberCalc(token)) {
                    double operand = Double.parseDouble(token);
                    m_stack.push(operand);
                } else if(Parser.IsOperatorCalc(token)) {
                    while(!m_stack.isEmpty() && !m_stackOperator.isEmpty()
                            && operationPriority(token, m_stackOperator.peek())) {
                        double result = performExpression(m_stackOperator.pop(), m_stack.pop(),m_stack.pop());
                        m_stack.push(result);
                    }
                    m_stackOperator.push(token);
                }
            }

            while(!m_stackOperator.isEmpty()) {
                double result = performExpression(m_stackOperator.pop(),m_stack.pop(), m_stack.pop());
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

    public static double evaluateFuncExpression(String _expression) {
        _expression = _expression.substring(0,_expression.length()-1);
        _expression = _expression.replaceAll("\\s+","");
        m_stack = new Stack<Double>();
        Stack<Character> operatorStack = new Stack<Character>();

        for(int i  = 0; i < _expression.length();i++) {
            char c = _expression.charAt(i);
            System.out.println(c);
            if(Parser.IsNumberCalc(String.valueOf(c))) {
                StringBuilder sb = new StringBuilder();
                while(i < _expression.length() && Parser.IsNumberCalc(String.valueOf(_expression.charAt(i)))
                        || _expression.charAt(i) == '.' ) {
                    sb.append(_expression.charAt(i));
                    i++;
                }
                i--;
                m_stack.push(Double.parseDouble(sb.toString()));
            } else if(Parser.IsOperatorCalc(String.valueOf(c))) {
                while(!operatorStack.isEmpty() && Parser.IsOperatorCalc(String.valueOf(operatorStack.peek()))
                        && operationPriority(String.valueOf(operatorStack.peek()), String.valueOf(c))) {
                    double result = performExpression(String.valueOf(operatorStack.pop()),m_stack.pop(),m_stack.pop());
                    m_stack.push(result);
                }
                operatorStack.push(c);
            } else if(c == '(') {
                operatorStack.push(c);
            } else if(c== ')') {
                while(!operatorStack.isEmpty() && operatorStack.peek()!='(') {
                    double result = performExpression(String.valueOf(operatorStack.pop()),m_stack.pop(),m_stack.pop());
                    m_stack.push(result);
                }
                if(!operatorStack.isEmpty() && operatorStack.peek() == '(') {
                    operatorStack.pop();
                }
            }
        }

        while(!operatorStack.isEmpty()) {
            double result = performExpression(String.valueOf(operatorStack.pop()),m_stack.pop(),m_stack.pop());
            m_stack.push(result);
        }

        return m_stack.pop();
    }

    private static double performExpression(String _operator, double t1_operand, double t2_operand) {
        if(Validator.IsDivisionByZero(_operator + String.valueOf(t1_operand)) ||
                Validator.IsDivisionByZero(_operator + String.valueOf(t2_operand))) {
            throw new ArithmeticException(Defines.BAD_ATTEMPT.getDescription());
        }
        System.out.println(_operator);
       // System.out.println(t1_operand);
       // System.out.println(t2_operand);

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
        int t1_precedence = Parser.getPrecedence(getToken(t1_operator));
        int t2_precedence = Parser.getPrecedence(getToken(t2_operator));
        return t1_precedence >= t2_precedence;
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
            case "t":
                return Token.O_TAN;
            case "Q":
                return Token.O_SQRT;
            case "L":
                return Token.O_LOG;
            default:
                return Token.O_UNKNOWN;
        }
    }

    private static Stack<Double> m_stack;
}

