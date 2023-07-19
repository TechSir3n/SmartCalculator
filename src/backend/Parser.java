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
               return 50;

           case O_DIV:
           case O_MUL:
               return 40;

           case O_POW:
               return 30;

           case O_OPBRACK:
               return 10;
           case O_CLOBRACK:
               return 20;

           case O_ACOS:
           case O_ASIN:
           case O_ATAN:
           case O_COS:
           case O_LOG:
           case O_SIN:
           case O_SQRT:
               return 60;

           case O_UNKNOWN:
               throw new IllegalArgumentException("Unknown operator got");
       }

       return 0;
    }

    public static Token getToken(String _operator) {
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

    public static boolean IsNumberCalc(String _num) {
        try {
            Double.parseDouble(_num);
            return true;
        } catch(NumberFormatException e) {
            return false;
        }
    }
   public  static boolean IsFunctionCalc(final String _func) {
         return switch (_func) {
             case "s",
                  "c",
                  "C",
                  "S",
                  "t",
                  "T",
                  "L",
                  "Q",
                  "l" -> true;

             default -> false;
         };
     }
}
