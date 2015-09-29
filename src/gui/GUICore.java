package gui;

import polynomial.Arithmetic;
import polynomial.Polynomial;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Melroy van Nijnatten - 0849740.
 */
public class GUICore extends JFrame {
    public JPanel mainPane;
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
    private JRadioButton positiveAnswer;
    private JRadioButton smallestAnswer;
    private JRadioButton negativeAnswer;

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
            @SuppressWarnings("ResultOfMethodCallIgnored")
            @Override
            public void actionPerformed(ActionEvent e) {
                clearResultPane();

                String operation = arithmetic.getSelectedItem().toString();

                String p1s = polynomial1.getText();
                if(p1s.equals("")){
                    addResultText("Please enter polynomial 1.");
                    return;
                }
                String p2s = polynomial2.getText();
                if(p2s.equals("")){
                    if(operation.equals("Scalar Multiple")){
                        addResultText("Please enter a scalar.");
                        return;
                    }
                    addResultText("Please enter polynomial 2.");
                    return;
                } else {
                    if(operation.equals("Scalar Multiple")){
                        Polynomial p = new Polynomial(Integer.MAX_VALUE, p2s);
                        if(p.keySet().size() != 1 || !p.keySet().contains(0)){
                            addResultText("Please enter a valid scalar.");
                            return;
                        }
                    }
                }
                String mod = modulus.getText();
                if(mod.equals("")){
                    addResultText("Please enter the modulus.");
                    return;
                } else {
                    Integer.getInteger(mod);
                    try {
                        if (!Arithmetic.isPrime(Integer.parseInt(mod))) {
                            addResultText("Please add a modulus that is a prime number.");
                            return;
                        }
                    } catch(NumberFormatException exc) {
                        addResultText("Please enter a valid integer modulus.");
                        return;
                    }
                }

                if (operation.equals("Sum")) {
                    solveSum(p1s, p2s, mod);
                } else if (operation.equals("Difference")) {
                    solveDifference(p1s, p2s, mod);
                } else if (operation.equals("Product")) {
                    solveProduct(p1s, p2s, mod);
                } else if (operation.equals("Scalar Multiple")) {
                    solveScalarMultiple(p1s, p2s, mod);
                } else if (operation.equals("Long Division")) {
                    solveLongDivision(p1s, p2s, mod);
                } else if (operation.equals("Extended Euclidean Algorithm")) {
                    solveExtendedEuclideanAlgorithm(p1s, p2s, mod);
                } else {
                    String p3s = polynomial3.getText();
                    if(p3s.equals("")){
                        addResultText("Please enter polynomial 3.");
                        return;
                    }
                    solveEqualModuloPolynomial(p1s, p2s, p3s, mod);
                }
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
                if (operation.equals("Scalar Multiple")) {
                    showScalar();
                } else if (operation.equals("Equal mod p3")) {
                    showThreePolynomials();
                } else {
                    showTwoPolynomials();
                }
            }
        });

        polynomial3.setVisible(false);
        polynomial3label.setVisible(false);

        Font f = new Font(Font.SANS_SERIF, Font.PLAIN, 18);
        resultPane.setFont(f);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Assignment 2WF90 - October 2015 - Stefan Habets & Melroy van Nijnatten");
        frame.setContentPane(new GUICore().mainPane);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        frame.pack();
        frame.setVisible(true);
    }

    private void showThreePolynomials() {
        polynomial1.setVisible(true);
        polynomial2.setVisible(true);
        polynomial3.setVisible(true);
        polynomial2label.setText("Polynomial 2:");
        polynomial3label.setVisible(true);
    }

    private void showScalar() {
        polynomial1.setVisible(true);
        polynomial2.setVisible(true);
        polynomial3.setVisible(false);
        polynomial2label.setText("Scalar:");
        polynomial3label.setVisible(false);
    }

    private void showTwoPolynomials() {
        polynomial1.setVisible(true);
        polynomial2.setVisible(true);
        polynomial3.setVisible(false);
        polynomial2label.setText("Polynomial 2:");
        polynomial3label.setVisible(false);
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

    private String getPolynomialText(Polynomial p){
        String text;
        if(positiveAnswer.isSelected()){
            text = p.makeCompletelyPositive().toString();
        } else if(negativeAnswer.isSelected()){
            text =p.makeCompletelyNegative().toString();
        } else {
            text = p.makeABSMinimal().toString();
        }
        return text;
    }

    public void solveSum(String p1s, String p2s, String mod) {
        addResultText("To Solve:");

        addResultText("(" + p1s + ") + (" + p2s + ") ≡ ? (mod " + mod + ")");
        addResultText("");

        int modulus = Integer.parseInt(mod);
        Polynomial p1 = new Polynomial(modulus, p1s);
        Polynomial p2 = new Polynomial(modulus, p2s);
        addResultText("Parsed Polynomials:");
        addResultText(p1s + " ≡ " + getPolynomialText(p1) + " (mod " + modulus + ")");
        addResultText(p2s + " ≡ " + getPolynomialText(p2) + " (mod " + modulus + ")");
        addResultText("");

        Polynomial result = Arithmetic.sum(p1, p2);
        addResultText("Solution:");
        addResultText("(" + getPolynomialText(p1) + ") + (" + getPolynomialText(p2) + ") ≡ " + result.toString() + " (mod " + modulus + ")");
    }

    public void solveDifference(String p1s, String p2s, String mod) {
        addResultText("To Solve:");
        addResultText("(" + p1s + ") - (" + p2s + ") ≡ ? (mod " + mod + ")");
        addResultText("");

        int modulus = Integer.parseInt(mod);
        Polynomial p1 = new Polynomial(modulus, p1s);
        Polynomial p2 = new Polynomial(modulus, p2s);
        addResultText("Parsed Polynomials:");
        addResultText(p1s + " ≡ " + getPolynomialText(p1) + " (mod " + modulus + ")");
        addResultText(p2s + " ≡ " + getPolynomialText(p2) + " (mod " + modulus + ")");
        addResultText("");

        Polynomial result = Arithmetic.difference(p1, p2);
        addResultText("Solution:");

        addResultText("(" + getPolynomialText(p1) + ") - (" + getPolynomialText(p2) + ") ≡ " + result.toString() + " (mod " + modulus + ")");
    }

    public void solveProduct(String p1s, String p2s, String mod) {
        addResultText("To Solve:");
        addResultText("(" + p1s + ") * (" + p2s + ") ≡ ? (mod " + mod + ")");
        addResultText("");

        int modulus = Integer.parseInt(mod);
        Polynomial p1 = new Polynomial(modulus, p1s);
        Polynomial p2 = new Polynomial(modulus, p2s);
        addResultText("Parsed Polynomials:");
        addResultText(p1s + " ≡ " + getPolynomialText(p1) + " (mod " + modulus + ")");
        addResultText(p2s + " ≡ " + getPolynomialText(p2) + " (mod " + modulus + ")");
        addResultText("");

        Polynomial result = Arithmetic.product(p1, p2);
        addResultText("Solution:");
        addResultText("(" + getPolynomialText(p1) + ") * (" + getPolynomialText(p2) + ") ≡ " + result.toString() + " (mod " + modulus + ")");
    }

    public void solveScalarMultiple(String p1s, String scalars, String mod) {
        addResultText("To Solve:");
        addResultText(scalars + " * (" + p1s + ") ≡ ? (mod " + mod + ")");
        addResultText("");

        int modulus = Integer.parseInt(mod);
        Polynomial p1 = new Polynomial(modulus, p1s);
        int scalar = Integer.parseInt(scalars);
        addResultText("Parsed Polynomials:");
        addResultText(p1s + " ≡ " + getPolynomialText(p1) + " (mod " + modulus + ")");
        addResultText("");

        Polynomial result = Arithmetic.scalar(p1, scalar);
        addResultText("Solution:");
        addResultText(scalar + " * (" + getPolynomialText(p1) + ") ≡ " + result.toString() + " (mod " + modulus + ")");
    }

    public void solveLongDivision(String p1s, String p2s, String mod) {
        addResultText("To Solve:");
        addResultText("((" + p1s + ") / (" + p2s + ")) (mod " + mod + ") ≡ (q * (" + p2s + ") + r) (mod " + mod + "),     q = ?, r = ?");
        addResultText("");

        int modulus = Integer.parseInt(mod);
        Polynomial p1 = new Polynomial(modulus, p1s);
        Polynomial p2 = new Polynomial(modulus, p2s);
        addResultText("Parsed Polynomials:");
        addResultText(p1s + " ≡ " + getPolynomialText(p1) + " (mod " + modulus + ")");
        addResultText(p2s + " ≡ " + getPolynomialText(p2) + " (mod " + modulus + ")");
        addResultText("");

        Polynomial[] result = Arithmetic.longDivision(p1, p2);
        addResultText("Solution:");
        assert result != null;
        assert result.length > 1;
        addResultText("(" + getPolynomialText(p1) + ") / (" + getPolynomialText(p2) + ") ≡ (" + result[0].toString() + ") * (" + getPolynomialText(p2) + ") + " + result[1].toString() + " (mod " + modulus + ")");
        addResultText("q = " + result[0].toString());
        addResultText("r = " + result[1].toString());
    }

    public void solveExtendedEuclideanAlgorithm(String p1s, String p2s, String mod) {
        addResultText("To Solve:");
        addResultText("gcd(" + p1s + "," + p2s + ") ≡ ((" + p1s + ") * x + (" + p2s + ") * y) (mod " + mod + "),     x = ?, y = ?");
        addResultText("");

        int modulus = Integer.parseInt(mod);
        Polynomial p1 = new Polynomial(modulus, p1s);
        Polynomial p2 = new Polynomial(modulus, p2s);
        addResultText("Parsed Polynomials:");
        addResultText(p1s + " ≡ " + getPolynomialText(p1) + " (mod " + modulus + ")");
        addResultText(p2s + " ≡ " + getPolynomialText(p2) + " (mod " + modulus + ")");
        addResultText("");

        Polynomial[] result = Arithmetic.extendedEuclideanAlgorithm(p1, p2);
        addResultText("Solution:");
        assert result != null;
        assert result.length > 2;
        addResultText("gcd(" + getPolynomialText(p1) + "," + getPolynomialText(p2) + ") ≡ ((" + getPolynomialText(p1) + ") * (" + getPolynomialText(result[0]) + ") + (" + getPolynomialText(p2) + ") * (" + getPolynomialText(result[1]) + ")) (mod " + mod + ")");
        addResultText("x = " + result[0].toString());
        addResultText("y = " + result[1].toString());
        addResultText("gcd(" + getPolynomialText(p1) + "," + getPolynomialText(p2) + ") = " + getPolynomialText(result[2]));
    }

    public void solveEqualModuloPolynomial(String p1s, String p2s, String p3s, String mod) {
        addResultText("To Solve:");
        addResultText("((" + p1s + ") (mod " + mod + ")) (mod " + p3s + ") ≡ ((" + p2s + ") (mod " + mod + ")) (mod " + p3s + ")");
        addResultText("");

        int modulus = Integer.parseInt(mod);
        Polynomial p1 = new Polynomial(modulus, p1s);
        Polynomial p2 = new Polynomial(modulus, p2s);
        Polynomial p3 = new Polynomial(Integer.MAX_VALUE, p3s);
        addResultText("Parsed Polynomials:");
        addResultText(p1s + " ≡ " + getPolynomialText(p1) + " (mod " + modulus + ")");
        addResultText(p2s + " ≡ " + getPolynomialText(p2) + " (mod " + modulus + ")");
        addResultText(p3s + " = " + getPolynomialText(p3));
        addResultText("");

        Object[] result = Arithmetic.equalModuloPolynomial(p1, p2, p3);
        assert result != null;
        assert result.length > 2;
        Polynomial resultP1 = (Polynomial) result[0];
        Polynomial resultP2 = (Polynomial) result[1];
        boolean isEqual = (Boolean) result[2];
        addResultText("Solution:");
        addResultText("((" + getPolynomialText(p1) + ") (mod " + mod + ")) ≡ ((" + getPolynomialText(p2) + ") (mod " + mod + ")) (mod " + getPolynomialText(p3) + ")");
        addResultText(getPolynomialText(resultP1) + " ≡ " + getPolynomialText(resultP2) + " (mod " + getPolynomialText(p3) + ")");
        addResultText(isEqual ? "Does hold." : "Does not hold.");
    }
}
