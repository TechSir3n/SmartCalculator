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

import assistance.Validator;
import backend.Calculator;
import assistance.Defines;
import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.data.xy.*;
import org.jfree.data.category.DefaultCategoryDataset;
import java.math.*;
import org.jfree.chart.axis.ValueAxis;

public class View extends JFrame {
   public  View() {
         textField = new JTextField();
         maxInputJTextField();
        mainPanel = new JPanel();
         _graphPanel  = new JPanel();
         _nameFunction =  "Function";
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
        Font fontTextField = new Font("Arial", Font.PLAIN, 24);
        textField.setFont(FONT_TEXT_FIELD);
        _panel.setLayout(new GridLayout(0, 6, 10, 10));

        for (String label : BUTTON_LABELS) {
            JButton button = getjButton(label, textField);
            button.setFont(FONT_BUTTON);
            button.setBackground(COLOR_BUTTON);
            addButtonCaclulator(button);
        }

        _panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        textField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        initGraph();
        initLayout();
    }

   private void initLayout() {
        JButton history = new JButton("History");
        JButton graph = new JButton("Graph");

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
       topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.weightx = 0.5;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 10, 0, 5);
        topPanel.add(history, gbc);

        gbc.weightx = 0.5;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 10, 0, 15);
        topPanel.add(graph, gbc);

        gbc.weightx = 1.0;
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.anchor = GridBagConstraints.LINE_END;
        topPanel.add(textField, gbc);

        graph.setFont(FONT_BUTTON);
        history.setFont(FONT_BUTTON);
       JScrollPane scrollPane = new JScrollPane(_graphPanel);
       scrollPane.setPreferredSize(new java.awt.Dimension(500, 450));

        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(topPanel,BorderLayout.NORTH);
        mainPanel.add(_panel,BorderLayout.CENTER);
        mainPanel.add(scrollPane,BorderLayout.EAST);

        add(mainPanel);
        pack();

       setLayout(new BorderLayout());
       setPreferredSize(new Dimension(1000,700));
        setResizable(false);

        setLocationRelativeTo(null);
        setBackground(Color.WHITE);

        setTitle("SmartCalculatorV3.0 by TechSir3n");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);
    }

    void initGraph() {
       JLabel xMinLabel = new JLabel("Xmin");
       JLabel xMaxLabel = new JLabel("Ymax");

       JLabel yMinLabel = new JLabel("Ymin");
       JLabel yMaxLabel = new JLabel("Xmax");

        JTextField yMinField = new JTextField(7);
        yMinField.setText("-5");

        JTextField yMaxField = new JTextField(7);
        yMaxField.setText("5");

        JTextField xMinField = new JTextField(7);
        xMinField.setText("-10");

        JTextField xMaxField = new JTextField(7);
        xMaxField.setText("10");

       JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(actionEvent -> {


        });

       _graphPanel.setLayout(new GridBagLayout());
       GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 10, 5);

       gbc.gridx = 0;
       gbc.gridy= 0 ;
       _graphPanel.add(xMinLabel,gbc);

       gbc.gridx = 0;
       gbc.gridy = 1;
       _graphPanel.add(xMinField,gbc);

        gbc.gridx = 1;
        gbc.gridy= 0 ;
        _graphPanel.add(yMinLabel,gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        _graphPanel.add(yMinField,gbc);

        gbc.gridx = 2;
        gbc.gridy= 0;
        _graphPanel.add(xMaxLabel,gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        _graphPanel.add(xMaxField,gbc);

        gbc.gridx = 3;
        gbc.gridy= 0 ;
        _graphPanel.add(yMaxLabel,gbc);

        gbc.gridx = 3;
        gbc.gridy = 1;
        _graphPanel.add(yMaxField,gbc);

        gbc.gridx = 4;
        gbc.gridy = 1;
        _graphPanel.add(resetButton,gbc);

       DefaultXYDataset dataSet = new DefaultXYDataset();
       double[][] data = new double[2][100];
        for (int i = 0; i < 100; i++) {
            double x = i * 0.1;
            double y = Math.sin(x);

            data[0][i] = x;
            data[1][i] = y;
        }

        dataSet.addSeries(_nameFunction,data);

        JFreeChart chart = ChartFactory.createXYLineChart("Graph","X","Y",dataSet);
        ChartPanel chartPanel = new ChartPanel(chart);


        ValueAxis xAxis = chart.getXYPlot().getDomainAxis();
        xAxis.setRange(Double.parseDouble(xMinField.getText()),Double.parseDouble(xMaxField.getText()));

        ValueAxis yAxis = chart.getXYPlot().getRangeAxis();
        yAxis.setRange(Double.parseDouble(yMinField.getText()),Double.parseDouble(yMaxField.getText()));

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 5;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        _graphPanel.add(chartPanel,gbc);
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
                    if(Validator.IsPostfixExpression(previousLabel + buttonLabel)) {
                        result = Calculator.evaluatePostfixExpression(previousLabel + buttonLabel);
                    } else if(Validator.IsPrefixExpression(previousLabel + buttonLabel)) {;
                        result = Calculator.evaluatePrefixExpression(previousLabel + buttonLabel);
                    } else if(Validator.IsInfixExpression(previousLabel + buttonLabel)) {
                        String postfixExpression  = Calculator.evaluateInfixExpression(previousLabel + buttonLabel);
                        result  = Calculator.evaluatePostfixExpression(postfixExpression);
                    } else if(Validator.IsFunctionExpression(previousLabel + buttonLabel)) {

                    }
                    textField.setText(String.valueOf(result));
                    return;
                }
                  textField.setText(previousLabel + buttonLabel);
            });
        return button;
    }

    private void setUseNameFunction(String _name) {
        _nameFunction = _name;
    }

    private void addButtonCaclulator(Component _button) {
        _panel.add(_button);
    }

    private final JPanel _panel;

   private final  JPanel mainPanel;

    private final JPanel _graphPanel;

    private final JTextField textField;

    private static String _nameFunction;

    private static final Font FONT_TEXT_FIELD = new Font("Arial",Font.PLAIN,24);

    private static final Font FONT_BUTTON = new Font("Arial",Font.BOLD,16);

    private static final Color COLOR_BUTTON = new Color(255,255,255);

    private static final String[] BUTTON_LABELS = {
            "cos", "log", "AC", "(", ")", "%",
            "sin", "in", "7", "8", "9", "/",
            "tan", "sqrt", "4", "5", "6", "*",
            "acos", "atan", "1", "2", "3", "-",
            "asin", "^", "0", ",", "=", "+"
    };
}