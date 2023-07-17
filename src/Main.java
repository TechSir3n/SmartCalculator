import gui.*;

public class Main {
    public static void main(String[] args) {
        try {
            View view = new View();
            view.showCalculator();
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        }

    }
}
