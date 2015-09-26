package gui;

import polynomial.Polynomial;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Melroy van Nijnatten - 0849740.
 */
public class GUICore extends JFrame {
    private static GUICore core;
    private JPanel mainPane;
    private JTextField polynomial1;
    private JTextField polynomial2;
    private JTextPane resultPane;
    private JComboBox operation;
    private JButton solve;
    private JTextField modulus;
    private JPanel parameters;
    private JButton swapButton;
    private JButton eraseButton;

    public GUICore() {
        swapButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = polynomial1.getText();
                polynomial1.setText(polynomial2.getText());
                polynomial2.setText(s);
            }
        });

        eraseButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                polynomial1.setText("");
                polynomial2.setText("");
                modulus.setText("");
                resultPane.setText("");
                operation.setSelectedIndex(0);
            }
        });
        resultPane.setText("Solution should appear here... I hope...");
        solve.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String p1s = polynomial1.getText();
                String p2s = polynomial2.getText();
                String mod = modulus.getText();
                String op = operation.getSelectedItem().toString();
                if (p1s.equals("") || p2s.equals("")) {
                    resultPane.setText("Please insert two polynomials.");
                    return;
                }
                if (mod.equals("")) {
                    resultPane.setText("Please insert modulus value.");
                    return;
                }
                Polynomial p1 = new Polynomial(Integer.parseInt(mod), p1s);
                Polynomial p2 = new Polynomial(Integer.parseInt(mod), p2s);
                Polynomial result;

                String operator;

                if (op.equals("Addition")) {
                    result = p1.add(p2);
                    operator = ") + (";
                } else if (op.equals("Subtraction")) {
                    result = p1.subtract(p2);
                    operator = ") - (";
                } else {
                    result = p1.multiply(p2);
                    operator = ") * (";
                }

                resultPane.setText("(" + p1.toString() + operator + p2.toString() + ") \u2261 " + result.toString() + " (mod " + mod + ")");
            }
        });
    }

    public static void main(String[] args) {
        core = new GUICore();
        JFrame frame = new JFrame("Assignment 2WF90 - October 2015 - Stefan Habets & Melroy van Nijnatten");
        init();
        frame.setContentPane(new GUICore().mainPane);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        frame.pack();
        frame.setVisible(true);
    }

    public static void init() {

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        String[] options = new String[]{"", "Addition", "Subtraction", "Multiplication"};
        core.operation = new JComboBox(options);
    }
}
