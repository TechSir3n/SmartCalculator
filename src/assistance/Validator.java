package assistance;

import backend.Parser;
import java.util.Stack;

public class Validator {
    public static boolean parenthess(String _expression) {
        if(_expression.length() < 3) {
            throw new RuntimeException("Expression cannot be less than 3 operand");
        }
        
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
        Stack<Integer> stack = new Stack<Integer>();
        for(int i = 0; i < _expression.length(); i++) {
            char c = _expression.charAt(i);
            if (Parser.IsNumberCalc(String.valueOf(c))) {
                stack.push(1);
            } else if (Parser.IsOperatorCalc(String.valueOf(c))) {
                if (stack.size() < 2) {
                    return false;
                }
                stack.pop();
                stack.pop();
                stack.push(1);
            }
        }

        return stack.size() == 1;
    }

    public static boolean IsPrefixExpression(String _expression) {
        Stack<Integer> stack = new Stack<Integer>();
        for(int i = _expression.length() -1;i>=0;i--) {
            char c = _expression.charAt(i);
            if(Parser.IsNumberCalc(String.valueOf(c))) {
                stack.push(1);
            } else if(Parser.IsOperatorCalc(String.valueOf(c))) {
                if(stack.size() < 2) {
                    return false;
                }
                stack.pop();
                stack.pop();
                stack.push(1);
            }
        }

        return stack.size() == 1;
    }

    public static boolean IsInfixExpression(String _expression) {
        Stack<Character> stack = new Stack<Character>();
        for(int i = 0;i<_expression.length();i++) {
            char c =  _expression.charAt(i);
            if(Parser.IsFunctionCalc(String.valueOf(c))) {
                continue;
            }else if(c == '(') {
                stack.push(c);
            } else if(c == ')') {
                if(stack.isEmpty() || stack.pop()!='(') {
                       return false;
                }
            } else if(Parser.IsNumberCalc(String.valueOf(c))) {
                continue;
            } else if(Parser.IsOperatorCalc(String.valueOf(c))) {
                continue;
            }
        }
        return stack.isEmpty();
    }

    public static boolean IsFunctionExpression(String _expression) {
        Stack<Character> stack = new Stack<Character>();
        for(int i = 0;i<_expression.length();i++) {
            char c =  _expression.charAt(i);
           if(Parser.IsFunctionCalc(String.valueOf(c))) {
               stack.push(c);
            } else if(Parser.IsNumberCalc(String.valueOf(c))) {
                continue;
            } else if(Parser.IsOperatorCalc(String.valueOf(c))) {
                continue;
            }
        }
        return !stack.isEmpty();
    }

   public static boolean IsDivisionByZero(String _expression) {
        return _expression.contains("/0");
    }
}

