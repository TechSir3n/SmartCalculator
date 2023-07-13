package backend;


public class Parser {
     public static boolean IsOperatorCalc(char _sym) {
         return switch (_sym) {
             case '+',
                  '-',
                  '*',
                  '/',
                  '%',
                  '^',
                  ')',
                  '(' -> true;

             default -> false;
         };
     }
     static boolean IsFunctionCalc(String _func) {
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
