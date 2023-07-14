package gui;
import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;
import java.awt.*;
import javax.swing.text.Document;
import javax.swing.text.AttributeSet;
import javax.swing.text.AbstractDocument;
import java.awt.event.ActionEvent;
import javax.swing.text.BadLocationException;
import java.awt.event.ActionListener;
import backend.Calculator;
import assistance.Defines;

public class View extends JFrame {
   public  View() {
         textField = new JTextField();
         maxInputJTextField();
        _panel = new JPanel(new GridLayout(5,5));
       _panel.setBackground(new Color(240, 240, 240));

    }
   private  void maxInputJTextField() {
        ((AbstractDocument) textField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws  BadLocationException {
                Document doc = fb.getDocument();
                StringBuilder sb = new StringBuilder();
                sb.append(doc.getText(0, doc.getLength()));

                if (length > 0) {
                    sb.delete(offset, offset + length);
                }

                sb.insert(offset, text);
                if (sb.length() <= Defines.MAX_EXPRESSION.getValue()) {
                    super.replace(fb, offset, length, text, attrs);
                } else {
                   JOptionPane.showMessageDialog(null, "Maximum count of number: " + Defines.MAX_EXPRESSION.getValue(), "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    public void showCalculator() {
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
            JButton button = getjButton(label, textField);
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

        setTitle("SmartCalculator by TechSir3n");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);
    }

    private static JButton getjButton(String label, JTextField textField) {
        JButton button = new JButton(label);
        button.addActionListener(actionEvent -> {
                JButton clickedButton = (JButton) actionEvent.getSource();
                String buttonLabel = clickedButton.getText();
                String previousLabel = textField.getText();
                if(buttonLabel.equals("AC")) {
                    textField.setText("");
                    return;
                } else if (buttonLabel.equals("=")) {
                    int result = 0;
                    if(Calculator.IsPostfixExpression(previousLabel + buttonLabel)) {
                        System.out.println("here3");
                        result = Calculator.evaluatePostfixExpression(previousLabel + buttonLabel);
                    } else if(Calculator.IsPrefixExpression(previousLabel + buttonLabel)) {
                        System.out.println("here2");
                        result = Calculator.evaluatePrefixExpression(previousLabel + buttonLabel);
                    } else if(Calculator.IsInfixExpression(previousLabel + buttonLabel)) {
                        System.out.println("here1");
                        String postfixExpression  = Calculator.evaluateInfixExpression(previousLabel + buttonLabel);
                        result  = Calculator.evaluatePostfixExpression(postfixExpression);
                    }
                    textField.setText(String.valueOf(result));
                    return;
                }
                  textField.setText(previousLabel + buttonLabel);
            });
        return button;
    }

    private void addTextField(Component _textField) {
      add(_textField,BorderLayout.NORTH);
    }
    private void addButton(Component _button) {
        _panel.add(_button);
    }
    private final JPanel _panel;
    private final JTextField textField;
}