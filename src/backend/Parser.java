package backend;
import assistance.Token;

public class Parser {
    public static boolean IsOperatorCalc(String _sym) {
        for (int i = 0; i < _sym.length(); i++) {
            char c = _sym.charAt(i);
            switch (c) {
                case '+', '-', '*', '/', '%', '^' -> {
                    return true;

                }
            }
        }
        return false;
    }
    public static int getPrecedence(Token _token) {
       switch (_token) {
           case O_PLUS:
           case O_MINUS:
               return 1;

           case O_DIV:
           case O_MUL:
               return 2;

           case O_POW:
               return 3;

           case O_OPBRACK:
           case O_CLOBRACK:
               return 4;

           case O_ACOS:
           case O_ASIN:
           case O_ATAN:
           case O_COS:
           case O_LOG:
           case O_SIN:
           case O_SQRT:
               return 6;

           case O_UNKNOWN:
               throw new IllegalArgumentException("Unknown operator got");
       }

       return 0;
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
