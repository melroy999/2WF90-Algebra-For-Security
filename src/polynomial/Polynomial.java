package polynomial;

import java.util.Iterator;
import java.util.TreeMap;

/**
 * Created by Melroy van Nijnatten - 0849740.
 */
public class Polynomial {
    /**
     * TreeMap representation of the polynomial.
     */
    private TreeMap<Integer, Integer> polynomial = new TreeMap<Integer, Integer>();

    /**
     *
     * #-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#
     *                                  Constructors
     * #-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#
     *
     */

    /**
     * Creates a Polynomial given the TreeMap. This creates a copy of the polynomial.
     *
     * @param polynomial: A TreeMap representing the polynomial.
     */
    public Polynomial(TreeMap<Integer, Integer> polynomial) {
        this.polynomial = new TreeMap<Integer, Integer>(polynomial);
    }

    /**
     * Creates a Polynomial given a string representation of this Polynomial.
     *
     * @param polynomial: String representation of the Polynomial.
     */
    public Polynomial(String polynomial) {
        toPolynomial(polynomial);
    }

    /**
     * Empty Polynomial.
     */
    private Polynomial() {

    }

    /**
     *
     * #-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#
     *                              Class support methods
     * #-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#
     *
     */

    /**
     * Returns a string representation of the polynomial.
     *
     * @return   The polynomial in string format.
     */
    public String toString() {
        String standardForm = "";
        Iterator<Integer> iterator = inverseIterator();
        while (iterator.hasNext()) {
            int degree = iterator.next();
            int coefficient = polynomial.get(degree);
            standardForm += (coefficient == 1 && degree != 0 ? "" : coefficient);
            standardForm += (degree == 0 ? "" : "X");
            standardForm += (degree > 1 ? "^" + degree : "");
            standardForm += (iterator.hasNext() ? " + " : "");
        }
        standardForm = standardForm.replace("+ -", "-");
        return standardForm;
    }

    /**
     * Returns the coefficient of the term with the given degree.
     *
     * @param degree: Degree of the term in the polynomial.
     * @return   0 if not present, the coefficient connected to the degree if present.
     */
    public int getCoefficient(int degree) {
        int coefficient = 0;
        if (polynomial.containsKey(degree)) {
            coefficient = polynomial.get(degree);
        }
        return coefficient;
    }

    /**
     * Adds a term to this coefficient, in the form coefficient * X^degree.
     * If the coefficient becomes 0, it removes the term from the TreeMap.
     *
     * @param coefficient: The coefficient of this term.
     * @param degree: The degree of this term.
     */
    public void addTerm(int coefficient, int degree) {
        if (polynomial.containsKey(degree)) {
            coefficient += polynomial.get(degree);
        }

        if(coefficient == 0){
            if(polynomial.containsKey(degree)) {
                polynomial.remove(degree);
            }
        } else {
            polynomial.put(degree, coefficient);
        }
    }

    /**
     * Converts a given string to a polynomial object.
     *
     * @param polynomial: String representation of the polynomial.
     */
    private void toPolynomial(String polynomial) {
        polynomial = polynomial.replace(" ", "");
        polynomial = polynomial.replaceAll("[^0-9\\^\\+\\-]", "x");
        polynomial = polynomial.replace("-", "+-");
        polynomial = polynomial.replace("-x", "-1x");
        polynomial = polynomial.replace("+x", "+1x");
        if(polynomial.charAt(0) == 'x'){
            polynomial = "1" + polynomial;
        }
        String[] terms = polynomial.split("\\+");
        for (String term : terms) {
            if(term.equals("")){
                continue;
            }
            int degree;
            int coefficient;
            if (term.contains("^")) {
                term = term.replace("x", "");
                String[] components = term.split("\\^");
                coefficient = Integer.parseInt(components[0]);
                degree = Integer.parseInt(components[1]);
            } else {
                if (term.contains("x")) {
                    term = term.replace("x", "");
                    degree = 1;
                } else {
                    degree = 0;
                }
                coefficient = Integer.parseInt(term);
            }
            addTerm(coefficient, degree);
        }
    }

    /**
     * Returns an iterator for the terms of the polynomial.
     *
     * @return  iterator of the keyset of the TreeMap.
     */
    public Iterator<Integer> iterator(){
        return polynomial.keySet().iterator();
    }

    /**
     * Returns an iterator for the terms of the polynomial, from largest to smallest terms.
     *
     * @return  iterator of the descendingKeySet of the TreeMap.
     */
    public Iterator<Integer> inverseIterator(){
        return polynomial.descendingKeySet().iterator();
    }

    /**
     *
     * #-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#
     *                              Calculation methods
     * #-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#
     *
     */

    /*
    TODO
    - Addition
    - Subtraction
    - Long Division
    - GCD
    - Factorization
     */

    /**
     * Multiplies the polynomial by a constant value.
     *
     * @param multiplier: An integer;
     * @return  this polynomial, to allow chaining.
     */
    public Polynomial multiply(int multiplier){
        Iterator<Integer> iterator = iterator();
        while(iterator.hasNext()){
            int degree = iterator.next();
            int coefficient = polynomial.get(degree);
            polynomial.put(degree, coefficient * multiplier);
        }
        return this;
    }

    /**
     * Gives the degree of the polynomial.
     *
     * @return   The largest degree that is paired with a coefficient that is not zero.
     */
    public int getDegree() {
        return polynomial.lastKey();
    }

    /**
     * Finds the leading coefficient of the polynomial.
     *
     * @return   The coefficient of the term with the highest degree, that is not zero.
     */
    public int getLC(){
        return getCoefficient(this.getDegree());
    }
}
