package backend;
import assistance.Defines;
import backend.Parser.*;
import java.util.Stack;
import assistance.Validator;
import assistance.Token;

public class Calculator extends CStack {
    public static int evaluatePrefixExpression(String _expression) {
        m_stack = new Stack<Integer> ();
        String m_expression = _expression.substring(0, _expression.length() - 1);
        String[] tokens = m_expression.split(" ");

        for(int i =  tokens.length-1;i>=0;i--) {
            String token = tokens[i];
            if(Parser.IsNumberCalc(token)) {
                int operand = Integer.parseInt(token);
                m_stack.push(operand);
            } else if(Parser.IsOperatorCalc(token))  {
                if(m_stack.size()<2) {
                    throw new IllegalArgumentException("Invalid expression format");
                }

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
        StringBuilder builder = new StringBuilder();
        String m_expression;
        String[] tokens = new String[]{};
        if(_expression.endsWith("=")) {
          m_expression = _expression.replace("=","");
          tokens = m_expression.split(" ");
        } else {
            tokens = _expression.split(" ");
        }

        for(String token: tokens) {
            if(Parser.IsNumberCalc(token))  {
                int operand = Integer.parseInt(token);
                m_stack.push(operand);
            } else if(Parser.IsOperatorCalc(token)) {
                while (!m_stack.isEmpty() && operationPriority(token,String.valueOf(m_stack.peek()))) {
                    if(m_stack.size()<2) {
                        break;
                    }
                        int t1_operand = m_stack.pop();
                        int t2_operand = m_stack.pop();
                        System.out.println(t1_operand);
                    System.out.println(t2_operand);
                        int result = performExpression(token,t2_operand,t1_operand);
                        m_stack.push(result);
                }
            }
        }

        while(m_stack.size() > 1){
            int t1_operand = m_stack.pop();
            int t2_operand = m_stack.pop();
            int result = performExpression(String.valueOf(m_stack.pop()),t2_operand,t1_operand);
            m_stack.push(result);
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
        int t1_precedence = Parser.getPrecedence(Token.O_PLUS);
        int t2_precedence = Parser.getPrecedence(Token.O_PLUS);
        return t1_precedence<=t2_precedence;
    }

 private static Stack<Integer> m_stack;
}

