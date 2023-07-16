package assistance;

import backend.Parser;
import java.util.Stack;

public class Validator {
    public static boolean parenthess(String _expression) {
        Stack<Character> _stack = new Stack<Character>();

        for(char c : _expression.toCharArray()) {
            if(c == '(') {
                _stack.push(c);
            } else if(c == ')') {
                if(!_stack.isEmpty() && _stack.peek() == '(') {
                    _stack.pop();
                } else {
                    return false;
                }
            }
        }

        return _stack.isEmpty();
    }

    public static boolean IsPostfixExpression(String _expression) {
        String m_expression = _expression.substring(0, _expression.length() - 1);
        String[] tokens  = m_expression.split(" ");

        if(tokens.length < 3) {
            return false;
        }

        int operandCount = 0;
        int operatorCount = 0;

        for(int i = 0; i < tokens.length; i++) {
            if(Parser.IsNumberCalc(tokens[i])) {
                operandCount++;
            } else if(Parser.IsOperatorCalc(tokens[i])) {
                operatorCount++;
            } else {
                return false;
            }
        }

        return operandCount == operatorCount + 1;
    }

    public static boolean IsPrefixExpression(String _expression) {
        String m_expression = _expression.substring(0, _expression.length() - 1);
        String[] tokens = m_expression.split(" ");

        if(tokens.length < 3) {
            return false;
        }
        if(!Parser.IsOperatorCalc(tokens[0])) {
            return false;
        }

        int operandCount = 0;
        int operatorCount = 1;
        for(int i = 1; i < tokens.length; i++) {
            if(Parser.IsNumberCalc(tokens[i])) {
                operandCount++;
            } else if(Parser.IsOperatorCalc(tokens[i])) {
                operatorCount++;
            } else {
                return false;
            }
        }

        return operandCount == operatorCount;
    }

    public static boolean IsInfixExpression(String _expression) {
        String m_expression = _expression.substring(0, _expression.length() - 1);
        String[] tokens = m_expression.split(" ");

        if(tokens.length < 3) {
            return false;
        }

        int operandCount = 0;
        int operatorCount = 0;
        for(int i = 0; i < tokens.length; i++) {
            if(Parser.IsNumberCalc(tokens[i])) {
                operandCount++;
            } else if(Parser.IsOperatorCalc(tokens[i])) {
                operatorCount++;
            } else {
                return false;
            }
        }

        return operandCount == operatorCount + 1;
    }

    public static boolean IsFunctionExpression(String _expression) {
        return Parser.IsFunctionCalc(_expression.substring(0,_expression.length() - 1));
    }

   public static boolean IsDivisionByZero(String _expression) {
        return _expression.contains("/0");
    }
}

