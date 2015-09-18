package polynomial;

import java.util.Iterator;
import java.util.TreeMap;

/**
 * Created by Melroy van Nijnatten - 0849740.
 */
public class Polynomial {
    /**
     * TreeMap representation of the p.
     */
    private TreeMap<Integer, Integer> p = new TreeMap<Integer, Integer>();

    /**
     *
     * #-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#
     *                                  Constructors
     * #-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#
     *
     */

    /**
     * Creates a Polynomial given the TreeMap. This creates a copy of the p.
     *
     * @param p: A TreeMap representing the p.
     */
    public Polynomial(TreeMap<Integer, Integer> p) {
        this.p = new TreeMap<Integer, Integer>(p);
    }

    /**
     * Creates a Polynomial given a string representation of this Polynomial.
     *
     * @param p: String representation of the Polynomial.
     */
    public Polynomial(String p) {
        toPolynomial(p);
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
     * Returns a string representation of the p.
     *
     * @return   The p in string format.
     */
    public String toString() {
        String standardForm = "";
        Iterator<Integer> iterator = inverseIterator();
        while (iterator.hasNext()) {
            int degree = iterator.next();
            int coefficient = p.get(degree);
            standardForm += (coefficient == 1 && degree != 0 ? "" : coefficient);
            standardForm += (degree == 0 ? "" : "X");
            standardForm += (degree > 1 ? "^" + degree : "");
            standardForm += (iterator.hasNext() ? " + " : "");
        }
        standardForm = standardForm.replace("+ -", "- ");
        return standardForm;
    }

    /**
     * Returns the coefficient of the term with the given degree.
     *
     * @param degree: Degree of the term in the p.
     * @return   0 if not present, the coefficient connected to the degree if present.
     */
    public int getCoefficient(int degree) {
        int coefficient = 0;
        if (p.containsKey(degree)) {
            coefficient = p.get(degree);
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
        if (p.containsKey(degree)) {
            coefficient += p.get(degree);
        }

        if(coefficient == 0 && degree != 0){
            if(p.containsKey(degree)) {
                p.remove(degree);
                if(p.isEmpty()){
                    p.put(0, 0);
                }
            }
        } else {
            p.put(degree, coefficient);
        }
    }

    /**
     * Converts a given string to a p object.
     *
     * @param polynomial: String representation of the p.
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
     * Returns an iterator for the terms of the p.
     *
     * @return  iterator of the keyset of the TreeMap.
     */
    public Iterator<Integer> iterator(){
        return p.keySet().iterator();
    }

    /**
     * Returns an iterator for the terms of the p, from largest to smallest terms.
     *
     * @return  iterator of the descendingKeySet of the TreeMap.
     */
    public Iterator<Integer> inverseIterator(){
        return p.descendingKeySet().iterator();
    }

    /**
     * Returns the amount of terms in p.
     * @return  p.size();
     */
    public int size(){
        return p.size();
    }

    public Polynomial clone(){
        return new Polynomial(this.p);
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
     * Multiplies the p by a constant value.
     *
     * @param multiplier: An integer;
     * @return  this p, to allow chaining.
     */
    public Polynomial multiply(int multiplier){
        Iterator<Integer> iterator = iterator();
        while(iterator.hasNext()){
            int degree = iterator.next();
            int coefficient = p.get(degree);
            p.put(degree, coefficient * multiplier);
        }
        return this;
    }

    /**
     * Gives the degree of the p.
     *
     * @return   The largest degree that is paired with a coefficient that is not zero.
     */
    public int getDegree() {
        return p.lastKey();
    }

    /**
     * Finds the leading coefficient of the p.
     *
     * @return   The coefficient of the term with the highest degree, that is not zero.
     */
    public int getLC(){
        return getCoefficient(this.getDegree());
    }

    public Polynomial add(Polynomial q){
        Polynomial n;
        Polynomial m;
        if(p.size() > q.size()){
            n = this.clone();
            m = q.clone();
        } else {
            n = q.clone();
            m = this.clone();
        }

        Iterator<Integer> iterator = m.iterator();
        while(iterator.hasNext()){
            int degree = iterator.next();
            int coefficient = m.getCoefficient(degree);
            n.addTerm(coefficient, degree);
        }

        return n;
    }

    public Polynomial subtract(Polynomial q){
        Polynomial m = q.clone().multiply(-1);
        return this.add(m);
    }

    public Polynomial multiply(Polynomial q){
        return null;
    }

    public Polynomial divide(Polynomial q){
        return null;
    }
}
