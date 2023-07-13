package gui;
import javax.swing.*;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View extends JFrame {
   public  View() {
        _panel = new JPanel(new GridLayout(5,5));
       _panel.setBackground(new Color(240, 240, 240));
    }
    public void showCalculator() {
        JTextField textField = new JTextField();
        textField.setBounds(210, 10, 470, 55);
        textField.setToolTipText("Enter expression");
        textField.setName("Expression");

        Font fontTextField = new Font("Arial",Font.PLAIN,24);
        textField.setFont(fontTextField);

        String[] buttonLabels = {
               "cos","log","AC","(",")",  "%",
                "sin","in", "7", "8", "9", "/",
                "tan", "sqrt", "4", "5", "6", "*",
                "acos", "atan",  "1", "2", "3", "-",
                "asin", "^", "0", ",", "=", "+"
        };

        Font fontButton = new Font("Arial",Font.BOLD,16);
        Color colorButton = new Color(255, 255, 255);
        _panel.setLayout(new GridLayout(0, 6, 10, 10));

        for(String label: buttonLabels) {
            JButton button = new JButton(label);
            button.addActionListener(actionEvent -> {
                    JButton clickedButton = (JButton) actionEvent.getSource();
                    String buttonLabel = clickedButton.getText();
                    if(buttonLabel.equals("AC")) {
                        textField.setText("");
                        return;
                    }
                    String previousLabel = textField.getText();
                    textField.setText(previousLabel + buttonLabel);
                });

            button.setFont(fontButton);
            button.setBackground(colorButton);
            addButton(button);
        }

        _panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        textField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        addTextField(textField);
        add(_panel,BorderLayout.CENTER);
        pack();

        setLayout(new BorderLayout(10,10));
        setPreferredSize(new Dimension(500, 600));
        setResizable(false);
        setLocationRelativeTo(null);

        setTitle("Smart Calculator by TechSir3n");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);
    }
    private void addTextField(Component _textField) {
      add(_textField,BorderLayout.NORTH);
    }
    private void addButton(Component _button) {
        _panel.add(_button);
    }
    private final JPanel _panel;
}