package gui;

import core.Core;
import finiteField.FiniteField;
import polynomial.Arithmetic;
import polynomial.Polynomial;

import javax.swing.*;

/**
 * Created by Melroy van Nijnatten - 0849740.
 */
public class GUIWorker extends SwingWorker<Void, Integer> {
    private boolean isPolyTab;
    private String[] input;

    public GUIWorker(boolean isPolyTab, String... input) {
        this.isPolyTab = isPolyTab;
        this.input = input;
    }

    /**
     * Switch operation mode (Polynomials).
     *
     * @param operation: The operation specified by the user.
     * @param p1s:       The string representation of the first polynomial.
     * @param p2s:       The string representation of the second polynomial.
     * @param mod:       The string representation of the prime modulo.
     */
    private void chooseOperationP(String operation, String p1s, String p2s, String mod) {
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
            String p3s = Core.guiCore.validatePolynomialR();
            if (p3s == null) return;
            solveEqualModuloPolynomial(p1s, p2s, p3s, mod);
        }
    }

    /**
     * Switch operation mode (Finite Fields).
     *
     * @param operation: The operation specified by the user.
     * @param qs:        The string representation of the irreducible polynomial.
     * @param mod:       The string representation of the prime modulo.
     */
    private void chooseOperationFF(String operation, String qs, String mod) {
        System.out.println(operation);
        if (operation.equals("Addition table")) {
            solveAdditionTable(qs, mod);
        } else if (operation.equals("Multiplication table")) {
            solveMultiplicationTable(qs, mod);
        } else if (operation.equals("Inverse table")) {
            solveInverseTable(qs, mod);
        } else if (operation.equals("Quotient table")) {
            solveQuotientTable(qs, mod);
        } else if (operation.equals("Is irreducible")) {
            solveIsIrreducible(qs);
        } else if (operation.equals("Get irreducible")) {
            Polynomial q = new Polynomial(Integer.MAX_VALUE, qs);
            if (q.keySet().size() != 1 || !q.keySet().contains(0)) {
                Core.printHandler.appendResultP("Please enter a valid integer degree.");
                Core.printHandler.appendLog(Core.guiCore.polynomialQLabelP.getText() + "\"" + qs + "\" is not a constant.", true);
                return;
            }
            solveGetIrreducible(qs, mod);
        } else if (operation.equals("Field element arithmetic")) {
            String as = Core.guiCore.fieldElementA.getText();
            String bs = Core.guiCore.fieldElementB.getText();
            solveFieldElementOperations(qs, as, bs, mod);
        } else if (operation.equals("Is primitive element")) {
            String as = Core.guiCore.fieldElementA.getText();
            solveIsPrimitiveElement(as, qs);
        } else {//get primitive elements
            solveGetPrimitiveElement(qs);
        }
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     * <p/>
     * <p/>
     * Note that this method is executed only once.
     * <p/>
     * <p/>
     * Note: this method is executed in a background thread.
     *
     * @return the computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    protected Void doInBackground() throws Exception {
        if (isPolyTab) {
            if (input.length == 4) {
                chooseOperationP(input[0], input[1], input[2], input[3]);
                return null;
            }
        } else {
            if (input.length == 3) {
                chooseOperationFF(input[0], input[1], input[2]);
                return null;
            }
        }
        System.out.println("Swingworker call is invalid.");
        return null;
    }

    @Override
    protected void done() {
        super.done();
    }

    /***
     * ############################################
     * Solve print/control methods (Polynomials).
     * ############################################
     */

    /**
     * Gets the sum of two polynomials.
     *
     * @param p1s: String representation of the first polynomial.
     * @param p2s: String representation of the second polynomial.
     * @param mod: String representation of the prime modulus that should be used.
     */
    public void solveSum(String p1s, String p2s, String mod) {
        Core.printHandler.appendResultP("To Solve:");

        Core.printHandler.appendResultP("(" + p1s + ") + (" + p2s + ") ≡ ? (mod " + mod + ")");
        Core.printHandler.appendResultP("");
        Core.printHandler.appendLog("TO SOLVE:&#9;(" + p1s + ") + (" + p2s + ") ≡ ? (mod " + mod + ")");

        int modulus = Integer.parseInt(mod);
        Polynomial p1 = new Polynomial(modulus, p1s);
        Polynomial p2 = new Polynomial(modulus, p2s);
        Core.printHandler.appendResultP("Parsed Polynomials:");
        Core.printHandler.appendResultP(p1s + " ≡ " + getPolynomialText(p1) + " (mod " + modulus + ")");
        Core.printHandler.appendResultP(p2s + " ≡ " + getPolynomialText(p2) + " (mod " + modulus + ")");
        Core.printHandler.appendResultP("");

        Polynomial result = Arithmetic.sum(p1, p2);
        Core.printHandler.appendResultP("Solution:");
        Core.printHandler.appendResultP("(" + getPolynomialText(p1) + ") + (" + getPolynomialText(p2) + ") ≡ " + getPolynomialText(result) + " (mod " + modulus + ")");
        Core.printHandler.appendLog("SOLUTION:&#9;(" + getPolynomialText(p1) + ") + (" + getPolynomialText(p2) + ") ≡ " + getPolynomialText(result) + " (mod " + modulus + ")");
    }

    /**
     * Gets the difference of two polynomials.
     *
     * @param p1s: String representation of the first polynomial.
     * @param p2s: String representation of the second polynomial.
     * @param mod: String representation of the prime modulus that should be used.
     */
    public void solveDifference(String p1s, String p2s, String mod) {
        Core.printHandler.appendResultP("To Solve:");
        Core.printHandler.appendResultP("(" + p1s + ") - (" + p2s + ") ≡ ? (mod " + mod + ")");
        Core.printHandler.appendResultP("");
        Core.printHandler.appendLog("TO SOLVE:&#9;(" + p1s + ") - (" + p2s + ") ≡ ? (mod " + mod + ")");

        int modulus = Integer.parseInt(mod);
        Polynomial p1 = new Polynomial(modulus, p1s);
        Polynomial p2 = new Polynomial(modulus, p2s);
        Core.printHandler.appendResultP("Parsed Polynomials:");
        Core.printHandler.appendResultP(p1s + " ≡ " + getPolynomialText(p1) + " (mod " + modulus + ")");
        Core.printHandler.appendResultP(p2s + " ≡ " + getPolynomialText(p2) + " (mod " + modulus + ")");
        Core.printHandler.appendResultP("");

        Polynomial result = Arithmetic.difference(p1, p2);
        Core.printHandler.appendResultP("Solution:");

        Core.printHandler.appendResultP("(" + getPolynomialText(p1) + ") - (" + getPolynomialText(p2) + ") ≡ " + getPolynomialText(result) + " (mod " + modulus + ")");
        Core.printHandler.appendLog("SOLUTION:&#9;(" + getPolynomialText(p1) + ") - (" + getPolynomialText(p2) + ") ≡ " + getPolynomialText(result) + " (mod " + modulus + ")");
    }

    /**
     * Gets the product of two polynomials.
     *
     * @param p1s: String representation of the first polynomial.
     * @param p2s: String representation of the second polynomial.
     * @param mod: String representation of the prime modulus that should be used.
     */
    public void solveProduct(String p1s, String p2s, String mod) {
        Core.printHandler.appendResultP("To Solve:");
        Core.printHandler.appendResultP("(" + p1s + ") * (" + p2s + ") ≡ ? (mod " + mod + ")");
        Core.printHandler.appendResultP("");
        Core.printHandler.appendLog("TO SOLVE:&#9;(" + p1s + ") * (" + p2s + ") ≡ ? (mod " + mod + ")");

        int modulus = Integer.parseInt(mod);
        Polynomial p1 = new Polynomial(modulus, p1s);
        Polynomial p2 = new Polynomial(modulus, p2s);
        Core.printHandler.appendResultP("Parsed Polynomials:");
        Core.printHandler.appendResultP(p1s + " ≡ " + getPolynomialText(p1) + " (mod " + modulus + ")");
        Core.printHandler.appendResultP(p2s + " ≡ " + getPolynomialText(p2) + " (mod " + modulus + ")");
        Core.printHandler.appendResultP("");

        Polynomial result = Arithmetic.product(p1, p2);
        Core.printHandler.appendResultP("Solution:");
        Core.printHandler.appendResultP("(" + getPolynomialText(p1) + ") * (" + getPolynomialText(p2) + ") ≡ " + getPolynomialText(result) + " (mod " + modulus + ")");
        Core.printHandler.appendLog("SOLUTION:&#9;(" + getPolynomialText(p1) + ") * (" + getPolynomialText(p2) + ") ≡ " + getPolynomialText(result) + " (mod " + modulus + ")");
    }

    /**
     * Multiplies the polynomial by a constant.
     *
     * @param p1s:     String representation of the first polynomial.
     * @param scalars: The constant that we multiply by.
     * @param mod:     String representation of the prime modulus that should be used.
     */
    public void solveScalarMultiple(String p1s, String scalars, String mod) {
        Core.printHandler.appendResultP("To Solve:");
        Core.printHandler.appendResultP(scalars + " * (" + p1s + ") ≡ ? (mod " + mod + ")");
        Core.printHandler.appendResultP("");
        Core.printHandler.appendLog("TO SOLVE:&#9;" + scalars + " * (" + p1s + ") ≡ ? (mod " + mod + ")");

        int modulus = Integer.parseInt(mod);
        Polynomial p1 = new Polynomial(modulus, p1s);
        int scalar = Integer.parseInt(scalars);
        Core.printHandler.appendResultP("Parsed Polynomials:");
        Core.printHandler.appendResultP(p1s + " ≡ " + getPolynomialText(p1) + " (mod " + modulus + ")");
        Core.printHandler.appendResultP("");

        Polynomial result = Arithmetic.scalar(p1, scalar);
        Core.printHandler.appendResultP("Solution:");
        Core.printHandler.appendResultP(scalar + " * (" + getPolynomialText(p1) + ") ≡ " + getPolynomialText(result) + " (mod " + modulus + ")");
        Core.printHandler.appendLog("SOLUTION:&#9;" + scalar + " * (" + getPolynomialText(p1) + ") ≡ " + getPolynomialText(result) + " (mod " + modulus + ")");
    }

    /**
     * Gets the quotient and remainder by long division.
     *
     * @param p1s: String representation of the first polynomial.
     * @param p2s: String representation of the second polynomial.
     * @param mod: String representation of the prime modulus that should be used.
     */
    public void solveLongDivision(String p1s, String p2s, String mod) {
        Core.printHandler.appendResultP("To Solve:");
        Core.printHandler.appendResultP("((" + p1s + ") / (" + p2s + ")) (mod " + mod + ") ≡ (q * (" + p2s + ") + r) (mod " + mod + "),     q = ?, r = ?");
        Core.printHandler.appendResultP("");
        Core.printHandler.appendLog("TO SOLVE:&#9;((" + p1s + ") / (" + p2s + ")) (mod " + mod + ") ≡ (q * (" + p2s + ") + r) (mod " + mod + "),     q = ?, r = ?");

        int modulus = Integer.parseInt(mod);
        Polynomial p1 = new Polynomial(modulus, p1s);
        Polynomial p2 = new Polynomial(modulus, p2s);
        Core.printHandler.appendResultP("Parsed Polynomials:");
        Core.printHandler.appendResultP(p1s + " ≡ " + getPolynomialText(p1) + " (mod " + modulus + ")");
        Core.printHandler.appendResultP(p2s + " ≡ " + getPolynomialText(p2) + " (mod " + modulus + ")");
        Core.printHandler.appendResultP("");

        Polynomial[] result = Arithmetic.longDivision(p1, p2);
        Core.printHandler.appendResultP("Solution:");
        assert result != null;
        assert result.length > 1;
        Core.printHandler.appendResultP("(" + getPolynomialText(p1) + ") / (" + getPolynomialText(p2) + ") ≡ (" + getPolynomialText(result[0]) + ") * (" + getPolynomialText(p2) + ") + " + getPolynomialText(result[1]) + " (mod " + modulus + ")");
        Core.printHandler.appendResultP("q = " + getPolynomialText(result[0]));
        Core.printHandler.appendResultP("r = " + getPolynomialText(result[1]));
        Core.printHandler.appendLog("SOLUTION:&#9;(" + getPolynomialText(p1) + ") / (" + getPolynomialText(p2) + ") ≡ (" + getPolynomialText(result[0]) + ") * (" + getPolynomialText(p2) + ") + " + getPolynomialText(result[1]) + " (mod " + modulus + ")");
    }

    /**
     * Get the gcd of two polynomials, together with factors polynomials x and y such that ax + by = gcd.
     *
     * @param p1s: String representation of the first polynomial.
     * @param p2s: String representation of the second polynomial.
     * @param mod: String representation of the prime modulus that should be used.
     */
    public void solveExtendedEuclideanAlgorithm(String p1s, String p2s, String mod) {
        Core.printHandler.appendResultP("To Solve:");
        Core.printHandler.appendResultP("gcd(" + p1s + "," + p2s + ") ≡ ((" + p1s + ") * x + (" + p2s + ") * y) (mod " + mod + "),     x = ?, y = ?");
        Core.printHandler.appendResultP("");
        Core.printHandler.appendLog("TO SOLVE:&#9;gcd(" + p1s + "," + p2s + ") ≡ ((" + p1s + ") * x + (" + p2s + ") * y) (mod " + mod + "),     x = ?, y = ?");

        int modulus = Integer.parseInt(mod);
        Polynomial p1 = new Polynomial(modulus, p1s);
        Polynomial p2 = new Polynomial(modulus, p2s);
        Core.printHandler.appendResultP("Parsed Polynomials:");
        Core.printHandler.appendResultP(p1s + " ≡ " + getPolynomialText(p1) + " (mod " + modulus + ")");
        Core.printHandler.appendResultP(p2s + " ≡ " + getPolynomialText(p2) + " (mod " + modulus + ")");
        Core.printHandler.appendResultP("");

        Polynomial[] result = Arithmetic.extendedEuclideanAlgorithm(p1, p2);
        Core.printHandler.appendResultP("Solution:");
        assert result != null;
        assert result.length > 2;
        Core.printHandler.appendResultP("gcd(" + getPolynomialText(p1) + ", " + getPolynomialText(p2) + ") ≡ ((" + getPolynomialText(p1) + ") * (" + getPolynomialText(result[0]) + ") + (" + getPolynomialText(p2) + ") * (" + getPolynomialText(result[1]) + ")) = " + getPolynomialText(result[2]) + " (mod " + mod + ")");
        Core.printHandler.appendResultP("x = " + getPolynomialText(result[0]));
        Core.printHandler.appendResultP("y = " + getPolynomialText(result[1]));
        Core.printHandler.appendResultP("gcd(" + getPolynomialText(p1) + ", " + getPolynomialText(p2) + ") = " + getPolynomialText(result[2]));
        Core.printHandler.appendLog("SOLUTION:&#9;gcd(" + getPolynomialText(p1) + ", " + getPolynomialText(p2) + ") ≡ ((" + getPolynomialText(p1) + ") * (" + getPolynomialText(result[0]) + ") + (" + getPolynomialText(p2) + ") * (" + getPolynomialText(result[1]) + ")) = " + getPolynomialText(result[2]) + " (mod " + mod + ")");
    }

    /**
     * Check if two polynomials are equal modulo a third polynomial.
     *
     * @param p1s: String representation of the first polynomial.
     * @param p2s: String representation of the second polynomial.
     * @param p3s: String representation of the third polynomial.
     * @param mod: String representation of the prime modulus that should be used.
     */
    public void solveEqualModuloPolynomial(String p1s, String p2s, String p3s, String mod) {
        Core.printHandler.appendResultP("To Solve:");
        Core.printHandler.appendResultP("((" + p1s + ") (mod " + mod + ")) (mod " + p3s + ") ≡ ((" + p2s + ") (mod " + mod + ")) (mod " + p3s + ")");
        Core.printHandler.appendResultP("");
        Core.printHandler.appendLog("TO SOLVE:&#9;((" + p1s + ") (mod " + mod + ")) (mod " + p3s + ") ≡ ((" + p2s + ") (mod " + mod + ")) (mod " + p3s + ")");

        int modulus = Integer.parseInt(mod);
        Polynomial p1 = new Polynomial(modulus, p1s);
        Polynomial p2 = new Polynomial(modulus, p2s);
        Polynomial p3 = new Polynomial(Integer.MAX_VALUE, p3s);
        Core.printHandler.appendResultP("Parsed Polynomials:");
        Core.printHandler.appendResultP(p1s + " ≡ " + getPolynomialText(p1) + " (mod " + modulus + ")");
        Core.printHandler.appendResultP(p2s + " ≡ " + getPolynomialText(p2) + " (mod " + modulus + ")");
        Core.printHandler.appendResultP(p3s + " = " + getPolynomialText(p3));
        Core.printHandler.appendResultP("");

        Object[] result = Arithmetic.equalModuloPolynomial(p1, p2, p3);
        assert result != null;
        assert result.length > 2;
        Polynomial resultP1 = (Polynomial) result[0];
        Polynomial resultP2 = (Polynomial) result[1];
        boolean isEqual = (Boolean) result[2];
        Core.printHandler.appendResultP("Solution:");
        Core.printHandler.appendResultP("((" + getPolynomialText(p1) + ") (mod " + mod + ")) ≡ ((" + getPolynomialText(p2) + ") (mod " + mod + ")) (mod " + getPolynomialText(p3) + ")");
        Core.printHandler.appendResultP(getPolynomialText(resultP1) + " ≡ " + getPolynomialText(resultP2) + " (mod " + getPolynomialText(p3) + ")");
        Core.printHandler.appendResultP(isEqual ? "Does hold." : "Does not hold.");
        Core.printHandler.appendLog("SOLUTION:&#9;(" + getPolynomialText(resultP1) + " ≡ " + getPolynomialText(resultP2) + " (mod " + getPolynomialText(p3) + ") " + (isEqual ? "does hold." : "does not hold."));
    }


    /***
     * ############################################
     * Solve print/control methods (Finite Fields).
     * ############################################
     */

    /**
     * Gets a primitive element of the given polynomial.
     *
     * @param qs: String representation of irreducible polynomial.
     */
    private void solveGetPrimitiveElement(String qs) {
        Core.printHandler.appendLog("TO SOLVE:&#9;Get a primitive element of polynomial " + qs + ".");
        String mod = Core.guiCore.modulusFF.getText();
        Polynomial q = new Polynomial(Integer.parseInt(mod), qs);
        Polynomial primitive = FiniteField.getPrimitiveElement(q);
        Core.printHandler.appendResultFF("The polynomial " + primitive.toString() + " a primitive element of " + q.toString() + ".");
        Core.printHandler.appendLog("SOLUTION:&#9;The polynomial " + primitive.toString() + " a primitive element of " + q.toString() + ".");
    }

    /**
     * Checks if the given field element is a primitive element.
     *
     * @param qs: String representation of irreducible polynomial.
     * @param as: Field element a.
     */
    private void solveIsPrimitiveElement(String as, String qs) {
        Core.printHandler.appendLog("TO SOLVE:&#9;Is " + qs + " a primitive element?");
        String mod = Core.guiCore.modulusFF.getText();
        Polynomial q = new Polynomial(Integer.parseInt(mod), qs);
        Polynomial a = new Polynomial(Integer.parseInt(mod), as);
        Core.printHandler.appendResultFF("The field element " + a.toString() + " is " + (FiniteField.isPrimitiveElement(a, q) ? "" : "not ") + "a primitive element of " + q.toString() + ".");
        Core.printHandler.appendLog("SOLUTION:&#9;The field element " + a.toString() + " is " + (FiniteField.isPrimitiveElement(a, q) ? "" : "not ") + "a primitive element of " + q.toString() + ".");
    }

    /**
     * Returns the addition, multiplication and quotient of two field elements.
     *
     * @param qs:  String representation of irreducible polynomial.
     * @param as:  Field element a.
     * @param bs:  Field element b.
     * @param mod: String representation of the prime modulus that should be used.
     */
    private void solveFieldElementOperations(String qs, String as, String bs, String mod) {
        Core.printHandler.appendLog("TO SOLVE:&#9;Get (" + as + ")+(" + bs + "), (" + as + ")*(" + bs + ") and (" + as + ")*(" + bs + ")^-1.");
        int modulus = Integer.parseInt(mod);
        Polynomial q = new Polynomial(modulus, qs);
        Polynomial a = new Polynomial(modulus, as);
        Polynomial b = new Polynomial(modulus, bs);

        Polynomial addition = FiniteField.sumFieldElements(a, b, q);
        Polynomial multiplication = FiniteField.productFieldElements(a, b, q);
        Polynomial quotient = FiniteField.quotientFieldElements(a, b, q);

        assert quotient != null;
        Core.printHandler.appendResultFF("Solution: ");
        Core.printHandler.appendResultFF("(" + a.toString() + ")+(" + b.toString() + ")≡" + addition.toString() + " (mod + " + q.toString() + ")");
        Core.printHandler.appendResultFF("(" + a.toString() + ")*(" + b.toString() + ")≡" + multiplication.toString() + " (mod + " + q.toString() + ")");
        Core.printHandler.appendResultFF("(" + a.toString() + ")*(" + b.toString() + ")^-1≡" + quotient.toString() + " (mod + " + q.toString() + ")");
        Core.printHandler.appendLog("SOLUTION:&#9;a+b=" + addition.toString() + ", a*b=" + multiplication.toString() + ", a*b^-1=" + quotient.toString());
    }

    /**
     * Generates a random irreducible polynomial of the given degree.
     *
     * @param degree: The degree of the desired irreducible polynomial.
     * @param mod:    String representation of the prime modulus that should be used.
     */
    private void solveGetIrreducible(String degree, String mod) {
        Core.printHandler.appendLog("TO SOLVE:&#9;Get an irreducible polynomial?");
        Polynomial irreducible = FiniteField.getIrreducible(Integer.parseInt(degree), Integer.parseInt(mod));
        Core.printHandler.appendResultFF("The polynomial " + irreducible.toString() + " is irreducible with degree " + degree + ".");
        Core.printHandler.appendLog("SOLUTION:&#9;The polynomial " + irreducible.toString() + " is irreducible with degree " + degree + ".");
    }

    /**
     * Check if the given polynomial is irreducible.
     *
     * @param qs: String representation of irreducible polynomial.
     */
    private void solveIsIrreducible(String qs) {
        Core.printHandler.appendLog("TO SOLVE:&#9;Is " + qs + " irreducible?");
        String mod = Core.guiCore.modulusFF.getText();
        Polynomial q = new Polynomial(Integer.parseInt(mod), qs);
        Core.printHandler.appendResultFF("The polynomial " + q.toString() + " is " + (FiniteField.isIrreducible(q) ? "" : "not ") + "irreducible.");
        Core.printHandler.appendLog("SOLUTION:&#9;The polynomial " + q.toString() + " is " + (FiniteField.isIrreducible(q) ? "" : "not ") + "irreducible.");
    }

    /**
     * Draws a multiplication table for the given irreducible polynomial.
     *
     * @param qs:  String representation of irreducible polynomial.
     * @param mod: String representation of the prime modulus that should be used.
     */
    private void solveMultiplicationTable(String qs, String mod) {
        Core.printHandler.appendLog("TO SOLVE:&#9;Get multiplication table.");
        int modulus = Integer.parseInt(mod);
        Polynomial q = new Polynomial(modulus, qs);
        Core.printHandler.appendResultFF("Multiplication table:");
        //draw the table.
        FiniteField.drawMultiplicationTable(q);
        Core.printHandler.appendLog("Multiplication table has been drawn.");
    }

    /**
     * Draws a addition table for the given irreducible polynomial.
     *
     * @param qs:  String representation of irreducible polynomial.
     * @param mod: String representation of the prime modulus that should be used.
     */
    private void solveAdditionTable(String qs, String mod) {
        Core.printHandler.appendLog("TO SOLVE:&#9;Get addition table.");
        int modulus = Integer.parseInt(mod);
        Polynomial q = new Polynomial(modulus, qs);
        Core.printHandler.appendResultFF("Addition table:");
        //draw the table.
        FiniteField.drawAdditionTable(q);
        Core.printHandler.appendLog("Addition table has been drawn.");
    }

    /**
     * Draws a inverse table for the given irreducible polynomial.
     *
     * @param qs:  String representation of irreducible polynomial.
     * @param mod: String representation of the prime modulus that should be used.
     */
    private void solveInverseTable(String qs, String mod) {
        Core.printHandler.appendLog("TO SOLVE:&#9;Get inverse table.");
        int modulus = Integer.parseInt(mod);
        Polynomial q = new Polynomial(modulus, qs);
        Core.printHandler.appendResultFF("Inverse table:");
        //draw the table.
        FiniteField.drawInverseTable(q);
        Core.printHandler.appendLog("Inverse table has been drawn.");
    }

    /**
     * Draws a quotient table for the given irreducible polynomial.
     *
     * @param qs:  String representation of irreducible polynomial.
     * @param mod: String representation of the prime modulus that should be used.
     */
    private void solveQuotientTable(String qs, String mod) {
        Core.printHandler.appendLog("TO SOLVE:&#9;Get quotient table.");
        int modulus = Integer.parseInt(mod);
        Polynomial q = new Polynomial(modulus, qs);
        Core.printHandler.appendResultFF("Quotient table:");
        //draw the table.
        FiniteField.drawQuotientTable(q);
        Core.printHandler.appendLog("Quotient table has been drawn.");
    }

    /**
     * Convert polynomial to one of the three required modes.
     *
     * @param p: The polynomial that has to be converted.
     * @return The polynomial that has the correct properties.
     */
    private String getPolynomialText(Polynomial p) {
        String text;
        if (Core.guiCore.positiveAnswer.isSelected()) {
            text = p.makeCompletelyPositive().toString();
        } else if (Core.guiCore.negativeAnswer.isSelected()) {
            text = p.makeCompletelyNegative().toString();
        } else {
            text = p.makeABSMinimal().toString();
        }
        return text;
    }
}
