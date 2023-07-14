package backend;

public class Parser {
    public static boolean IsOperatorCalc(final String _sym) {
        for (int i = 0; i < _sym.length(); i++) {
            char c = _sym.charAt(i);
            switch (c) {
                case '+', '-', '*', '/', '%', '^', ')', '(' -> {
                    return true;
                }
            }
        }
        return false;
    }
    public static int getPrecedence(String _operator) {
        return switch (_operator) {
            case "+", "-" -> 1;
            case "*", "/" -> 2;
            case "^" -> 3;
            default -> 0;
        };
    }

    public static boolean IsNumberCalc(String _num) {
        try {
            Integer.parseInt(_num);
            return true;
        } catch(NumberFormatException e) {
            return false;
        }
    }
   public  static boolean IsFunctionCalc(final String _func) {
         return switch (_func) {
             case "sin",
                  "sqrt",
                  "log",
                  "cos",
                  "tan",
                  "asin",
                  "acos",
                  "in",
                  "atan" -> true;

             default -> false;
         };
     }
}
