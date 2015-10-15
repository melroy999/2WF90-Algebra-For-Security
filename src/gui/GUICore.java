package gui;

import core.Core;
import polynomial.Arithmetic;
import polynomial.Polynomial;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Melroy van Nijnatten - 0849740.
 */
public class GUICore extends JFrame {
    public JPanel mainPane;
    protected JTextPane resultPaneP;
    protected JTextPane logPane;
    protected JTextPane resultPaneFF;
    protected JLabel polynomialQLabelP;
    protected JRadioButton positiveAnswer;
    protected JRadioButton negativeAnswer;
    protected JTextField modulusFF;
    protected JTextField fieldElementA;
    protected JTextField fieldElementB;
    GUIWorker currentWorker;
    private JTextField polynomialPP;
    private JTextField polynomialPQ;
    private JComboBox operationP;
    private JButton solveP;
    private JButton swapButtonP;
    private JButton eraseButtonP;
    private JTextField polynomialPR;
    private JLabel polynomialPLabelP;
    private JLabel polynomialRLabelP;
    private JButton solveFF;
    private JComboBox operationFF;
    private JTextField polynomialQFF;
    private JButton eraseButtonFF;
    private JLabel polynomialQLabelFF;
    private JLabel fieldElementALabel;
    private JLabel fieldElementBLabel;
    private JLabel primeLabelP;
    private JTextField modulusP;
    private JLabel primeLabelFF;
    private JButton eraseButton;
    private JButton terminateCalculationButton;

    public GUICore() {
        swapButtonP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                swapP();
            }
        });
        eraseButtonP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eraseP();
            }
        });
        solveP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentWorker == null || (currentWorker.isDone() || currentWorker.isCancelled())) {
                    solveP();
                } else {
                    System.out.println("Worker is still busy!");
                }
            }
        });
        operationP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchModeP();
            }
        });
        eraseButtonFF.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eraseFF();
            }
        });
        operationFF.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchModeFF();
            }
        });
        solveFF.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentWorker == null || (currentWorker.isDone() || currentWorker.isCancelled())) {
                    solveFF();
                } else {
                    System.out.println("Worker is still busy!");
                }
            }
        });
        eraseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Core.printHandler.clearLogPane();
            }
        });

        showTwoPolynomials();
        showPoly();
        terminateCalculationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentWorker != null && !currentWorker.isDone()) {
                    currentWorker.cancel(true);
                    System.out.println("Interrupted thread!");
                    Core.printHandler.appendLog("CANCELLED CALCULATION!", true);
                } else {
                    System.out.println("No worker to interrupt!");
                }
            }
        });
    }

    /**
     * Main solve call for Polynomials.
     */
    private void solveP() {
        Core.printHandler.clearResultPaneP();

        String operation = operationP.getSelectedItem().toString();
        printInputP(operation);

        String mod = validatePrimeP();
        if (mod == null) return;

        String p1s = validatePolynomialPP();
        if (p1s == null) return;

        String p2s = validatePolynomialQP(operation);
        if (p2s == null) return;

        currentWorker = new GUIWorker(true, operation, p1s, p2s, mod);
        currentWorker.execute();
        //chooseOperationP(operation, p1s, p2s, mod);
    }

    /***
     * Main solve call for Finite Fields.
     */
    private void solveFF() {
        Core.printHandler.clearResultPaneFF();

        String operation = operationFF.getSelectedItem().toString();
        printInputFF(operation);

        String mod = validatePrimeFF();
        if (mod == null) return;

        String qs = validatePolynomialQFF(operation);
        if (qs == null) return;

        currentWorker = new GUIWorker(false, operation, qs, mod);
        currentWorker.execute();
        //chooseOperationFF(operation, qs, mod);
    }


    /***
     * ############################################
     * Validate inputs (Polynomials).
     * ############################################
     */

    /**
     * Validates polynomial P.
     *
     * @return null if polynomial is invalid, string representation otherwise.
     */
    private String validatePolynomialPP() {
        String p1s = polynomialPP.getText();
        if (p1s.equals("")) {
            Core.printHandler.appendResultP("Please enter polynomial 1.");
            Core.printHandler.appendLog(polynomialPLabelP.getText() + "\"" + p1s + "\" is invalid.", true);
            return null;
        }
        return p1s;
    }

    /**
     * Validates polynomial Q.
     *
     * @param operation: The operation specified by the user.
     * @return null if polynomial is invalid, string representation otherwise.
     */
    private String validatePolynomialQP(String operation) {
        String p2s = polynomialPQ.getText();
        if (p2s.equals("")) {
            Core.printHandler.appendLog(polynomialQLabelP.getText() + "\"" + p2s + "\" is invalid.", true);
            if (operation.equals("Scalar Multiple")) {
                Core.printHandler.appendResultP("Please enter a scalar.");
                return null;
            }
            Core.printHandler.appendResultP("Please enter polynomial 2.");
            return null;
        } else {
            if (operation.equals("Scalar Multiple")) {
                Polynomial p = new Polynomial(Integer.MAX_VALUE, p2s);
                if (p.keySet().size() != 1 || !p.keySet().contains(0)) {
                    Core.printHandler.appendResultP("Please enter a valid scalar.");
                    Core.printHandler.appendLog(polynomialQLabelP.getText() + "\"" + p2s + "\" is not a constant.", true);
                    return null;
                }
            }
        }
        return p2s;
    }

    /**
     * Validates the prime modulo.
     *
     * @return null if prime is invalid, string representation otherwise.
     */
    private String validatePrimeP() {
        String mod = modulusP.getText();
        Polynomial p_mod = new Polynomial(Integer.MAX_VALUE, mod);
        mod = p_mod.toString();
        if (mod.equals("")) {
            Core.printHandler.appendResultP("Please enter the prime.");
            Core.printHandler.appendLog("Prime:\"" + mod + "\" is invalid.", true);
            return null;
        } else {
            try {
                if (!Arithmetic.isPrime(Integer.parseInt(mod))) {
                    Core.printHandler.appendResultP("Please add a modulus that is a prime number.");
                    Core.printHandler.appendLog(primeLabelP.getText() + "\"" + mod + "\" is not prime.", true);
                    return null;
                }
            } catch (NumberFormatException exc) {
                Core.printHandler.appendResultP("Please enter a valid prime.");
                Core.printHandler.appendLog(primeLabelP.getText() + "\"" + mod + "\" is not a constant.", true);
                return null;
            }
        }
        return mod;
    }

    /**
     * Validates polynomial R.
     *
     * @return null if polynomial is invalid, string representation otherwise.
     */
    protected String validatePolynomialR() {
        String p3s = polynomialPR.getText();
        if (p3s.equals("")) {
            Core.printHandler.appendResultP("Please enter polynomial 3.");
            Core.printHandler.appendLog(polynomialRLabelP.getText() + "\"" + p3s + "\" is invalid.", true);
            return null;
        }
        return p3s;
    }


    /**
     * ############################################
     * Validate inputs (Finite Fields).
     * ############################################
     */

    /**
     * Checks if the given prime is valid.
     *
     * @return null if prime is invalid, string representation otherwise.
     */
    private String validatePrimeFF() {
        String mod = modulusFF.getText();
        Polynomial p_mod = new Polynomial(Integer.MAX_VALUE, mod);
        mod = p_mod.toString();
        if (mod.equals("")) {
            Core.printHandler.appendResultFF("Please enter the prime.");
            Core.printHandler.appendLog("Prime:\"" + mod + "\" is invalid.", true);
            return null;
        } else {
            try {
                if (!Arithmetic.isPrime(Integer.parseInt(mod))) {
                    Core.printHandler.appendResultFF("Please add a modulus that is a prime number.");
                    Core.printHandler.appendLog(primeLabelFF.getText() + "\"" + mod + "\" is not prime.", true);
                    return null;
                }
            } catch (NumberFormatException exc) {
                Core.printHandler.appendResultFF("Please enter a valid prime.");
                Core.printHandler.appendLog(primeLabelFF.getText() + "\"" + mod + "\" is not a constant.", true);
                return null;
            }
        }
        return mod;
    }

    /**
     * Validates the irreducible polynomial.
     *
     * @param operation: The operation specified by the user.
     * @return null if polynomial is invalid, string representation otherwise.
     */
    private String validatePolynomialQFF(String operation) {
        String qs = polynomialQFF.getText();
        if (qs.equals("")) {
            Core.printHandler.appendResultFF("Please enter polynomial.");
            Core.printHandler.appendLog(polynomialQLabelFF.getText() + "\"" + qs + "\" is invalid.", true);
            return null;
        } else {
            String mod = modulusFF.getText();
            Polynomial q = new Polynomial(Integer.parseInt(mod), qs);
            if (!operation.equals("Get irreducible") && !q.isIrreducible()) {
                Core.printHandler.appendResultFF("Please enter an irreducible polynomial q.");
                Core.printHandler.appendLog(polynomialQLabelFF.getText() + "\"" + qs + "\" is reducible.", true);
                return null;
            }
        }
        return qs;
    }

    /***
     * print the input given by user (Polynomial).
     *
     * @param operation: The operation specified by the user.
     */
    private void printInputP(String operation) {
        String input = "INPUT:&#9;" + primeLabelP.getText() + "\"" + modulusP.getText() + "\", " + polynomialPLabelP.getText() + "\"" + polynomialPP.getText() + "\", " + polynomialQLabelP.getText() + "\"" + polynomialPQ.getText() + "\"";
        if (operation.equals("Equal mod p3")) {
            input = input + ", " + polynomialRLabelP.getText() + "\"" + polynomialPR.getText() + "\"";
        }
        Core.printHandler.appendLog(input);
    }

    /**
     * print the input given by user (Finite Field).
     *
     * @param operation: The operation specified by the user.
     */
    private void printInputFF(String operation) {
        String input = "INPUT:&#9;" + primeLabelFF.getText() + "\"" + modulusFF.getText() + "\"";
        input += ", " + polynomialQLabelFF.getText() + "\"" + polynomialQFF.getText() + "\"";
        if(operation.equals("Field element arithmetic") || operation.equals("Is primitive element")){
            input += ", " + fieldElementALabel.getText() + "\"" + fieldElementA.getText() + "\"";
        }
        if(operation.equals("Field element arithmetic")){
            input += ", " + fieldElementBLabel.getText() + "\"" + fieldElementB.getText() + "\"";
        }

        Core.printHandler.appendLog(input);
    }

    /***
     * Erase the input on the Polynomials tab.
     */
    private void eraseP() {
        polynomialPP.setText("");
        polynomialPQ.setText("");
        modulusP.setText("");
        operationP.setSelectedIndex(0);
        Core.printHandler.clearResultPaneP();
    }

    /**
     * Erase the input on the Finite Fields tab.
     */
    private void eraseFF() {
        polynomialQFF.setText("");
        fieldElementA.setText("");
        fieldElementB.setText("");
        modulusFF.setText("");
        operationFF.setSelectedIndex(0);
        Core.printHandler.clearResultPaneFF();
    }

    /***
     * Swap contents of Polynomial p and Polynomial q.
     */
    private void swapP() {
        String s = polynomialPP.getText();
        polynomialPP.setText(polynomialPQ.getText());
        polynomialPQ.setText(s);
    }


    /***
     * ############################################
     * Display correct input objects (Polynomials).
     * ############################################
     */

    /**
     * Switch to the correct input fields needed for polynomials.
     */
    private void switchModeP() {
        String operation = operationP.getSelectedItem().toString();
        if (operation.equals("Scalar Multiple")) {
            showScalar();
        } else if (operation.equals("Equal mod p3")) {
            showThreePolynomials();
        } else {
            showTwoPolynomials();
        }
    }

    /**
     * Show three polynomial fields.
     */
    private void showThreePolynomials() {
        polynomialPP.setVisible(true);
        polynomialPQ.setVisible(true);
        polynomialPR.setVisible(true);
        polynomialQLabelP.setText("Polynomial 2:");
        polynomialRLabelP.setVisible(true);
    }

    /**
     * Show the scalar field.
     */
    private void showScalar() {
        polynomialPP.setVisible(true);
        polynomialPQ.setVisible(true);
        polynomialPR.setVisible(false);
        polynomialQLabelP.setText("Scalar:");
        polynomialRLabelP.setVisible(false);
    }

    /**
     * Show two polynomial fields.
     */
    private void showTwoPolynomials() {
        polynomialPP.setVisible(true);
        polynomialPQ.setVisible(true);
        polynomialPR.setVisible(false);
        polynomialQLabelP.setText("Polynomial 2:");
        polynomialRLabelP.setVisible(false);
    }


    /***
     * ############################################
     * Display correct input objects (Finite Fields).
     * ############################################
     */

    /**
     * Switch to the correct fields to process the input.
     */
    private void switchModeFF() {
        String operation = operationFF.getSelectedItem().toString();
        if (operation.equals("Get irreducible")) {
            showDegree();
        } else if (operation.equals("Field element arithmetic")) {
            showFieldElements();
        } else if (operation.equals("Is primitive element")) {
            showFieldElement();
        } else {//get primitive elements
            showPoly();
        }
    }

    /**
     * Show only the polynomial field.
     */
    private void showPoly() {
        polynomialQLabelFF.setText("Polynomial q:");
        fieldElementA.setVisible(false);
        fieldElementALabel.setVisible(false);
        fieldElementB.setVisible(false);
        fieldElementBLabel.setVisible(false);
    }

    /**
     * Show only field element a field.
     */
    private void showFieldElement() {
        polynomialQLabelFF.setText("Polynomial q:");
        fieldElementA.setVisible(true);
        fieldElementALabel.setVisible(true);
        fieldElementB.setVisible(false);
        fieldElementBLabel.setVisible(false);
    }

    /**
     * Show the field elements fields.
     */
    private void showFieldElements() {
        polynomialQLabelFF.setText("Polynomial q:");
        fieldElementA.setVisible(true);
        fieldElementALabel.setVisible(true);
        fieldElementB.setVisible(true);
        fieldElementBLabel.setVisible(true);
    }

    /**
     * Show the fields required for degrees.
     */
    private void showDegree() {
        polynomialQLabelFF.setText("Degree:");
        fieldElementA.setVisible(false);
        fieldElementALabel.setVisible(false);
        fieldElementB.setVisible(false);
        fieldElementBLabel.setVisible(false);
    }
}
