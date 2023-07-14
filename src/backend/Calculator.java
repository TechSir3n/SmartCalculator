package backend;
import assistance.Defines;
import backend.Parser.*;
import java.util.Stack;
import assistance.Validator;

public class Calculator extends CStack {
    public static int evaluatePrefixExpression(String _expression) {
        m_stack = new Stack<Integer> ();
        String m_expression = _expression.substring(0, _expression.length() - 1);
        String[] tokens = m_expression.split(" ");

        for(int i =  tokens.length-1;i>=0;i--) {
            String token = tokens[i];
            if(Parser.IsNumberCalc(token)) {  // check numbers 0 9 1 2
                int operand = Integer.parseInt(token);
                m_stack.push(operand);
            } else if(Parser.IsOperatorCalc(token))  {       // check operators / * - +
                int t1_operand = m_stack.pop();
                int t2_operand = m_stack.pop();
                int result  = performExpression(token,t1_operand,t2_operand);
                m_stack.push(result);
            } else {

            }
        }
        return m_stack.pop();
    }
    public static int evaluatePostfixExpression(String _expression) {
        m_stack = new Stack<Integer>();
        String m_expression;
        String[] tokens = new String[]{};
        if(_expression.contains("=")) {
          m_expression  = _expression.substring(0, _expression.length() - 1);
        } else {
            tokens = _expression.split(" ");
        }

        for(String token: tokens) {
            if(Parser.IsNumberCalc(token))  {
                int operand = Integer.parseInt(token);
                m_stack.push(operand);
            } else if(Parser.IsOperatorCalc(token)) {
                int t1_operand = m_stack.pop();
                int t2_operand = m_stack.pop();
                int result = performExpression(token,t1_operand,t2_operand);
                m_stack.push(result);
            }
        }
        return m_stack.pop();
    }

    public static String evaluateInfixExpression(String  _expression) {
        Stack<String> t_stack = new Stack<String>();
        StringBuilder postfix = new StringBuilder();
        String m_expression = _expression.substring(0, _expression.length() - 1);
        String[] tokens = m_expression.split(" ");

        for(String token:tokens) {
            if (Parser.IsNumberCalc(token)) {
                postfix.append(token).append(" ");
            } else if (token.equals("(")) {
                t_stack.push(token);
            } else if (token.equals(")")) {
                while (!t_stack.isEmpty() && !t_stack.peek().equals("(")) {
                    postfix.append(t_stack.pop()).append(" ");
                }
                t_stack.pop();
            } else if (Parser.IsOperatorCalc(token)) {
                while (!t_stack.isEmpty() && operationPriority(t_stack.peek(), token)) {
                    postfix.append(t_stack.pop()).append(" ");
                }
                t_stack.push(token);
            }
        }

        while (!t_stack.isEmpty()) {
            postfix.append(t_stack.pop()).append(" ");
        }

        return postfix.toString().trim();
    }

    private static int performExpression(String _operator,int t1_operand,int t2_operand) {
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
                return t1_operand  * t2_operand;
            case "%":
                return t1_operand % t2_operand;
            case "^":
                return (int) Math.pow(t1_operand,t2_operand);
            default:
                throw new IllegalArgumentException("Doesn't supported type" + _operator);
        }
    }
    private static boolean operationPriority(String t1_operator,String t2_operator) {
        int t1_precedence = Parser.getPrecedence(t1_operator);
        int t2_precedence = Parser.getPrecedence(t2_operator);
        return t1_precedence>=t2_precedence;
    }
    public static boolean IsPostfixExpression(String _expression) {
        String m_expression = _expression.substring(0, _expression.length() - 1);
        String[] tokens  = m_expression.split(" ");
        return Parser.IsNumberCalc(tokens[0]) && Parser.IsNumberCalc(tokens[1])
                && Parser.IsOperatorCalc(tokens[2]);
    }

    public static boolean IsPrefixExpression(String _expression) {
        String m_expression = _expression.substring(0, _expression.length() - 1);
        String[] tokens = m_expression.split(" ");
        return Parser.IsOperatorCalc(tokens[0]) && Parser.IsNumberCalc(tokens[1])
                && Parser.IsNumberCalc(tokens[2]);
    }

    public static boolean IsInfixExpression(String _expression) {
        String m_expression = _expression.substring(0, _expression.length() - 1);
        String[] tokens = m_expression.split(" ");
        return Parser.IsNumberCalc(tokens[0]) && Parser.IsOperatorCalc(tokens[1])
                && Parser.IsNumberCalc(tokens[2]);
    }
 private static Stack<Integer> m_stack;
}

