import gui.*;
import assistance.Validator;
public class Main {
    public static void main(String[] args) {
        try {
            if(Validator.parenthess("(5 + 2)")) {
                System.out.println("CORRECT");
            } else {
                System.out.println("NOT CORRECT");
            }
            //View view = new View();
            //view.showCalculator();
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        }

    }
}
