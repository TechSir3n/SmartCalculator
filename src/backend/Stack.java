package backend;
import backend.Parser.*;

class Stack {
    public static int evaluateExpression(String _expression) {
        if(Parser.IsOperatorCalc(_expression.charAt(0))) {
        return 0;
        } else {
            return 1;
        }
    }

}