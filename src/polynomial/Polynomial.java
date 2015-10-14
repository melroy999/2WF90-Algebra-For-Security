package polynomial;

import core.Core;
import parser.Parser;
import parser.tree.TreeNode;

import java.util.*;

/**
 * Created by Melroy van Nijnatten - 0849740.
 */
public class Polynomial {
    private final int modulus;
    protected TreeMap<Integer, Integer> terms = new TreeMap<Integer, Integer>();

    public Polynomial(int modulus) {
        this.modulus = modulus;
    }

    public Polynomial(int modulus, String s) {
        this.modulus = modulus;
        this.terms = nodesToPoly(Parser.parse(s)).getTerms();
    }

    /**
     * Converts all numbers to superscript equivalent.
     *
     * @param str: The number that has to be converted to superscript.
     * @return The string converted to superscript.
     */
    public static String superscript(String str) {
        str = str.replaceAll("0", "⁰");
        str = str.replaceAll("1", "¹");
        str = str.replaceAll("2", "²");
        str = str.replaceAll("3", "³");
        str = str.replaceAll("4", "⁴");
        str = str.replaceAll("5", "⁵");
        str = str.replaceAll("6", "⁶");
        str = str.replaceAll("7", "⁷");
        str = str.replaceAll("8", "⁸");
        str = str.replaceAll("9", "⁹");
        return str;
    }

    /**
     * get all possible polynomials that have degree at most the given degree.
     *
     * @param degree: The maximum degree.
     * @param modulo: The prime modulus.
     * @return All possible polynomials that fit.
     */
    public static ArrayList<Polynomial> getAllDegreePolynomials(int degree, int modulo) {
        ArrayList<Polynomial> polynomials = new ArrayList<Polynomial>();
        //the amount of possible polynomials with at most the degree together with modulo is modulo ^ degree.
        int max_polynomial_rep = (int) Math.pow(modulo, degree);
        //for all numbers up to modulo ^ degree, convert to base modulo, and use each term as the coefficient.
        for (int i = 0; i < max_polynomial_rep; i++) {
            Polynomial result = new Polynomial(modulo);
            Stack<Integer> base = toBaseRepresentation(modulo, i);
            int counter = base.size() - 1;
            //check if something is present in base.
            while (!base.isEmpty()) {
                //add the term to the polynomial.
                result.addTerm(base.pop(), counter);
                counter--;
            }
            //add the result to the list of polynomials.
            polynomials.add(result);
        }
        return polynomials;
    }

    /**
     * Converts i to a representation with base number modulus. (binary, etc)
     *
     * @param modulo: The prime modulus.
     * @param i:      the number to change to base modulus reprensentation.
     * @return [i]_modulus, in the form of a stack.
     */
    private static Stack<Integer> toBaseRepresentation(int modulo, int i) {
        Stack<Integer> baseModulo = new Stack<Integer>();
        int quotient;
        //push the remainder onto the stack until the quotient is 0.
        do {
            quotient = i / modulo;
            int remainder = i % modulo;
            i = quotient;
            baseModulo.push(remainder);
        }
        while (quotient != 0);
        return baseModulo;
    }

    /**
     * Returns the degree of the polynomial.
     *
     * @return 0 if not set, maximum degree if set.
     */
    public int degree() {
        if (!terms.isEmpty()) {
            return terms.lastKey();
        }
        return 0;
    }

    /**
     * Returns the leading coefficient.
     *
     * @return returns the coefficient of the degree of this polynomial.
     */
    public int getLeadingCoefficient() {
        return getCoefficient(degree());
    }

    /**
     * Creates a copy of this polynomial.
     *
     * @return copy of this polynomial, with its own term tree.
     */
    @SuppressWarnings({"CloneDoesntCallSuperClone", "CloneDoesntDeclareCloneNotSupportedException"})
    public Polynomial clone() {
        Polynomial result = new Polynomial(modulus);
        result.terms = this.getTerms();
        return result;
    }

    /**
     * Returns the modulus used to create this polynomial.
     *
     * @return modulus.
     */
    public int getModulus() {
        return modulus;
    }

    /**
     * Returns the amount of terms the polynomial has.
     *
     * @return the size of the tree.
     */
    public int size(){
        return terms.size();
    }

    /**
     * Returns a keyset of the degrees.
     *
     * @return terms.keySet().
     */
    public Set<Integer> keySet(){
        return terms.keySet();
    }

    /**
     * Returns a reversed keyset of the degrees.
     *
     * @return terms.descendingKeySet().
     */
    public Set<Integer> inverseKeySet(){
        return terms.descendingKeySet();
    }

    /**
     * Returns a clone of the terms of the tree.
     *
     * @return terms.clone().
     */
    @SuppressWarnings("unchecked")
    public TreeMap<Integer, Integer> getTerms() {
        return (TreeMap<Integer, Integer>) terms.clone();
    }

    /**
     * Returns a string representation of the p.
     *
     * @return The p in string format.
     */
    public String toString() {
        String standardForm = "";
        Iterator<Integer> iterator = inverseKeySet().iterator();
        //for each term in the polynomial.
        while (iterator.hasNext()) {
            int degree = iterator.next();
            int coefficient = terms.get(degree);
            //do some fancy formatting...
            standardForm += (coefficient > 0 ? (coefficient == 1 && degree != 0 ? "" : coefficient) : coefficient == -1 && degree != 0 ? "-" : coefficient);
            standardForm += (degree == 0 ? "" : "X");
            standardForm += (degree > 1 ? superscript(String.valueOf(degree)) : "");
            standardForm += (iterator.hasNext() ? " + " : "");
        }
        //if it is empty, return 0 as value.
        if (standardForm.equals("")) {
            standardForm = "0";
        }
        //replace all + - with -.
        standardForm = standardForm.replace("+ -", "- ");
        return standardForm;
    }

    /**
     * Returns the coefficient connected to the degree.
     *
     * @param degree: The degree of the desired term.
     * @return 0 if not existing, coefficient if existing.
     */
    public int getCoefficient(int degree){
        if(terms.containsKey(degree)){
            return terms.get(degree);
        } else {
            return 0;
        }

    }

    /**
     * Converts a tree to the polynomial.
     *
     * @param current: The leading node.
     * @return A polynomial satisfying the calculational rules given.
     */
    private Polynomial nodesToPoly(TreeNode current){
        Polynomial leftChildP = null;
        Polynomial rightChildP = null;
        String value = current.getValue();

        //first solve the trees that have this as node as root.
        TreeNode leftChild = current.getLeftNode();
        if(leftChild!=null){
            leftChildP = nodesToPoly(current.getLeftNode());
        }

        //first solve the trees that have this as node as root.
        TreeNode rightChild = current.getRightNode();
        if(rightChild!=null){
            rightChildP = nodesToPoly(current.getRightNode());
        }

        Polynomial result;

        //check which operation this node represents, and execute it. Also check if we don't accidently get nulls.
        if(current.getValue().equals("-")){
            assert leftChildP != null;
            result = leftChildP.scalar(-1);
        } else if(current.getValue().equals("+")){
            assert leftChildP != null;
            assert rightChildP != null;
            result = leftChildP.sum(rightChildP);
        } else if(current.getValue().equals("*")) {
            assert leftChildP != null;
            assert rightChildP != null;
            result = leftChildP.product(rightChildP);
        } else {//no children! so must be term
            int d = 0;
            int c;
            //if it is a term, split on ^. This gives the coefficient and degree.
            if(value.contains("^")){
                String[] split = value.split("\\^");
                d = Integer.parseInt(split[1]);
                c = Integer.parseInt(split[0]);
            } else { //if no ^ is present, it must be a constant, so add it as a constant.
                c = Integer.parseInt(value);
            }
            //add this term to the resulting polynomial.
            result = new Polynomial(modulus).addTerm(c, d);
        }
        return result;
    }

    /**
     * Add a term to the polynomial.
     *
     * @param c: the coefficient.
     * @param d: the degree.
     * @return this polynomial.
     */
    protected Polynomial addTerm(int c, int d) {
        //add the coefficient.
        c += this.getCoefficient(d);

        //take the modulus
        c %= modulus;

        //if it has become zero, remove it from the tree.
        if (c == 0) {
            terms.remove(d);
            return this;
        }

        //else put it in the tree.
        terms.put(d, c);
        return this;
    }

    /**
     * Calculate this polynomial multiplied by a constant.
     *
     * @param scalar: The constant to be multiplied by.
     * @return this polynomial multiplied by the scalar.
     */
    public Polynomial scalar(int scalar) {
        return Arithmetic.scalar(this, scalar);
    }

    /**
     * Return a list containing all coefficients of the polynomial.
     *
     * @return A list containing all coefficients of the polynomial.
     */
    public ArrayList<Integer> getAllCoefficients() {
        ArrayList<Integer> result = new ArrayList<Integer>();
        for (int degree : terms.keySet()) {
            result.add(getCoefficient(degree));
        }
        return result;
    }

    /**
     * Returns the difference between this and another polynomial.
     *
     * @param q: The other polynomial.
     * @return this - q.
     */
    public Polynomial difference(Polynomial q) {
        return Arithmetic.difference(this, q);
    }

    /**
     * Returns the sum of this and another polynomial.
     *
     * @param q: The other polynomial.
     * @return this + q.
     */
    public Polynomial sum(Polynomial q) {
        return Arithmetic.sum(this, q);
    }

    /**
     * Returns the product of this and another polynomial.
     *
     * @param q: The other polynomial.
     * @return this * q.
     */
    public Polynomial product(Polynomial q) {
        return Arithmetic.product(this, q);
    }

    /**
     * Returns the quotient and remainder of a polynomial long division.
     *
     * @param q: The polynomial to divide by.
     * @return Quotient and remainder such that Quotient * q + r is this.
     */
    public Polynomial[] longDivision(Polynomial q) {
        return Arithmetic.longDivision(this, q);
    }

    /**
     * Returns x, y and gcd(this, q).
     *
     * @param q: The other polynomial.
     * @return x, y and gcd such that x*this + y*q = gcd.
     */
    public Polynomial[] extendedEuclideanAlgorithm(Polynomial q) {
        return Arithmetic.extendedEuclideanAlgorithm(this, q);
    }

    /**
     * Checks if two polynomials are equal mod r.
     *
     * @param q: Second polynomial.
     * @param r: The polynomial that should be used as modulo.
     * @return if this == q (mod r).
     */
    public Object[] equalityModPolynomial(Polynomial q, Polynomial r) {
        return Arithmetic.equalModuloPolynomial(this, q, r);
    }

    /**
     * Checks if this and q are equal.
     *
     * @param q: The other polynomial.
     * @return this==q.
     */
    public boolean isEqual(Polynomial q) {
        //convert to same form
        Polynomial p1 = this.makeABSMinimal();
        Polynomial p2 = q.makeABSMinimal();

        //check if the list of coefficients and degrees is equal.
        return p1.getAllCoefficients().equals(p2.getAllCoefficients()) && p1.keySet().equals(p2.keySet());
    }

    /**
     * Change this polynomial to one only containing positive coefficients.
     *
     * @return this polynomial converted to positive coefficients only.
     */
    public Polynomial makeCompletelyPositive() {
        Polynomial result = new Polynomial(modulus);
        for (int degree : keySet()) {
            int coefficient = getCoefficient(degree);
            if (coefficient < 0) {
                coefficient += modulus;
            }
            result.terms.put(degree, coefficient);
        }
        return result;
    }

    /**
     * Change this polynomial to one only containing negative coefficients.
     *
     * @return this polynomial converted to negative coefficients only.
     */
    public Polynomial makeCompletelyNegative() {
        Polynomial result = new Polynomial(modulus);
        for (int degree : keySet()) {
            int coefficient = getCoefficient(degree);
            if (coefficient > 0) {
                coefficient -= modulus;
            }
            result.terms.put(degree, coefficient);
        }
        return result;
    }

    /**
     * Change this polynomial such that the coefficients are the smallest possible in absolute value.
     *
     * @return this with smallest possible absolute coefficients value wise.
     */
    public Polynomial makeABSMinimal() {
        Polynomial temp = makeCompletelyPositive();
        Polynomial result = new Polynomial(modulus);
        for (int degree : temp.keySet()) {
            int coefficient = temp.getCoefficient(degree);
            if (coefficient > Math.abs(coefficient - modulus)) {
                result.terms.put(degree, coefficient - modulus);
            } else {
                result.terms.put(degree, coefficient);
            }
        }
        return result;
    }

    /**
     * Check if this polynomial is irreducible.
     *
     * @return if this is irreducible.
     * Source: Algorithm 4.1.4 in handout.
     */
    public boolean isIrreducible() {
        //0 and 1 are always irreducible, so handle it as an exception.
        if (this.toString().equals("0") || this.toString().equals("1")) {
            return true;
        }

        //Generate the polynomial X^modulus^t - X
        int t = 1;
        Polynomial q = new Polynomial(modulus);
        q.addTerm(-1, 1);
        q.addTerm(1, (int) Math.pow(modulus, t));
        //check if the gcd is equal to 1.

        Core.printHandler.appendLog(t + ":" + q.toString() + ", " + this.extendedEuclideanAlgorithm(q)[2].toString());
        while (this.extendedEuclideanAlgorithm(q)[2].toString().equals("1")) {
            t++;
            //Generate the polynomial X^modulus^t - X
            q = new Polynomial(modulus);
            q.addTerm(-1, 1);
            q.addTerm(1, (int) Math.pow(modulus, t));
            Core.printHandler.appendLog(t + ":" + q.toString() + ", " + this.extendedEuclideanAlgorithm(q)[2].toString());
        }
        //if all are equal to 1, this polynomial is irreducible.
        return t == this.degree();
    }

    /**
     * Create a random polynomial of given degree.
     *
     * @param degree: The desired degree.
     * @param ofDegree: If the degree may be lower or not.
     */
    public void randomize(int degree, boolean ofDegree) {
        Random rand = new Random();
        terms = new TreeMap<Integer, Integer>();
        //for each term, 0 to degree, generate random value 0 to modulus - 1.
        //leave out last one if we want a polynomial of exactly the degree.
        for (int i = 0; i < (ofDegree ? degree : degree + 1); i++) {
            int randomNum = rand.nextInt(modulus);
            addTerm(randomNum, i);
        }

        //add the term with the given degree, with a coefficient between 1 and modulus -1.
        if (ofDegree) {
            int randomNum = 1 + rand.nextInt(modulus - 1);
            addTerm(randomNum, degree);
        }
    }

    /**
     * Returns this to the power n.
     *
     * @param n: The power.
     * @param q: The polynomial that is used as modulus.
     * @return this ^ n.
     *
     * This could have been done recursively to spare speed, but it currently isn't used.
     */
    public Polynomial pow(int n, Polynomial q) {
        Polynomial result = new Polynomial(modulus, "1");
        for (int i = 0; i < n; i++) {
            result = result.product(this);
            result = result.longDivision(q)[1];
        }
        return result;
    }
}
