package gui;

import polynomial.Polynomial;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Melroy van Nijnatten - 0849740.
 */
public class GUICore extends JFrame {
    private JPanel mainPane;
    private JTextField polynomial1;
    private JTextField polynomial2;
    private JTextPane resultPane;
    private JComboBox arithmetic;
    private JButton solve;
    private JTextField modulus;
    private JPanel parameters;
    private JButton swapButton;
    private JButton eraseButton;
    private JTabbedPane tabbedPane1;
    private JTextField polynomial3;
    private JLabel polynomial1label;
    private JLabel polynomial2label;
    private JLabel polynomial3label;

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
                clearResultPane();
                arithmetic.setSelectedIndex(0);
            }
        });
        solve.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                clearResultPane();
                String p1s = polynomial1.getText();
                String p2s = polynomial2.getText();
                String mod = modulus.getText();
                String operation = arithmetic.getSelectedItem().toString();
                if (p1s.equals("") || p2s.equals("")) {
                    resultPane.setText("Please insert two polynomials.");
                    return;
                }
                if (mod.equals("")) {
                    resultPane.setText("Please insert modulus value.");
                    return;
                }

                addResultText("Simplified given polynomials:");
                Polynomial p1 = new Polynomial(Integer.parseInt(mod), p1s);
                addResultText(p1s + " \u2261 " + p1.toString() + " (mod " + mod + ")");
                Polynomial p2 = new Polynomial(Integer.parseInt(mod), p2s);
                addResultText(p2s + " \u2261 " + p2.toString() + " (mod " + mod + ")");
                addResultText("");

                Polynomial result = null;

                String operator;

                if (operation.equals("Sum")) {
                    result = p1.sum(p2);
                    operator = ") + (";
                } else if (operation.equals("Difference")) {
                    result = p1.difference(p2);
                    operator = ") - (";
                } else if (operation.equals("Product")) {
                    result = p1.difference(p2);
                    operator = ") * (";
                } else if (operation.equals("Difference")) {
                    result = p1.difference(p2);
                    operator = ") - (";
                } else if (operation.equals("Scalar Multiple")) {
                    result = p1.scalarMultiplication(p2.getCoefficient(0));
                    operator = "*  (";
                } else if (operation.equals("Long Division")) {
                    result = p1.scalarMultiplication(p2.getCoefficient(0));
                    operator = ") / (";
                } else if (operation.equals("Extended Euclidean Algorithm")) {
                    result = p1.scalarMultiplication(p2.getCoefficient(0));
                    operator = ") - (";
                } else {
                    result = p1;
                    operator = ") * (";
                }

                addResultText("Result:");
                addResultText("(" + p1.toString() + operator + p2.toString() + ") \u2261 " + result.toString() + " (mod " + mod + ")");
            }
        });
        arithmetic.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String operation = arithmetic.getSelectedItem().toString();
                if (operation.equals("Sum")) {
                    polynomial1.setVisible(true);
                    polynomial2.setVisible(true);
                    polynomial3.setVisible(false);
                    polynomial2label.setText("Polynomial 2:");
                    polynomial3label.setVisible(false);
                } else if (operation.equals("Difference")) {
                    polynomial1.setVisible(true);
                    polynomial2.setVisible(true);
                    polynomial3.setVisible(false);
                    polynomial2label.setText("Polynomial 2:");
                    polynomial3label.setVisible(false);
                } else if (operation.equals("Product")) {
                    polynomial1.setVisible(true);
                    polynomial2.setVisible(true);
                    polynomial3.setVisible(false);
                    polynomial2label.setText("Polynomial 2:");
                    polynomial3label.setVisible(false);
                } else if (operation.equals("Scalar Multiple")) {
                    polynomial1.setVisible(true);
                    polynomial2.setVisible(true);
                    polynomial3.setVisible(false);
                    polynomial2label.setText("Scalar:");
                    polynomial3label.setVisible(false);
                } else if (operation.equals("Long Division")) {
                    polynomial1.setVisible(true);
                    polynomial2.setVisible(true);
                    polynomial3.setVisible(false);
                    polynomial2label.setText("Polynomial 2:");
                    polynomial3label.setVisible(false);
                } else if (operation.equals("Extended Euclidean Algorithm")) {
                    polynomial1.setVisible(true);
                    polynomial2.setVisible(true);
                    polynomial3.setVisible(false);
                    polynomial2label.setText("Polynomial 2:");
                    polynomial3label.setVisible(false);
                } else {
                    polynomial1.setVisible(true);
                    polynomial2.setVisible(true);
                    polynomial3.setVisible(true);
                    polynomial2label.setText("Polynomial 2:");
                    polynomial3label.setVisible(true);
                }
            }
        });

        polynomial3.setVisible(false);
        polynomial3label.setVisible(false);
    }

    public static void main(String[] args) {
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

    public void addResultText(String message) {
        resultPane.setText(resultPane.getText() + message + "\n");
    }

    public void clearResultPane() {
        resultPane.setText("");
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
