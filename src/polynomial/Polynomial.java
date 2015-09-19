package polynomial;

import java.util.Iterator;
import java.util.Set;
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
            standardForm += (coefficient > 0 ? (coefficient == 1 && degree != 0 ? "" : coefficient) : (coefficient == -1 && degree != 0 ? "-" : coefficient));
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
     * Returns the coefficient of the term with the given degree.
     *
     * @param degree: Degree of the term in the p.
     * @return   0 if not present, the coefficient connected to the degree if present.
     */
    protected int get(int degree) {
        return getCoefficient(degree);
    }

    /**
     * Adds a term to this coefficient, in the form coefficient * X^degree.
     * If the coefficient becomes 0, it removes the term from the TreeMap.
     *
     * @param coefficient: The coefficient of this term.
     * @param degree: The degree of this term.
     * @return  this p, to allow chaining.
     */
    public Polynomial addTerm(int coefficient, int degree) {
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

        return this;
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
     *
     * @return  p.size();
     */
    public int size(){
        return p.size();
    }

    /**
     * Clones the polynomial.
     *
     * @return new Polynomial(this.p);
     */
    public Polynomial clone(){
        return new Polynomial(this.p);
    }

    public Set<Integer> keySet(){
        return p.keySet();
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

    /**
     * Adds q to this.
     *
     * @param q: Polynomial that should be added to this one.
     * @return  A new polynomial that is the addition p and q.
     */
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

    /**
     * Subtracts q from this.
     *
     * @param q: Polynomial that should subtract this one.
     * @return  A new polynomial that is the subtraction of p by q.
     */
    public Polynomial subtract(Polynomial q){
        q = q.multiply(-1);
        return this.add(q);
    }

    public Polynomial multiply(Polynomial q){
        Polynomial result = new Polynomial();
        for(int degree_x : p.keySet()){
            int coefficient_x = p.get(degree_x);
            for(int degree_y : q.keySet()){
                int coefficient_y = q.getCoefficient(degree_y);
                result.addTerm(coefficient_x * coefficient_y, degree_x + degree_y);
            }
        }
        return result;
    }

    /**
     * Performs long division of p by q.
     *
     * @param q: Polynomial to divide by.
     * @return  A LCD pair containing the quotient and remainder.
     */
    public LCD divide(Polynomial q){
        Polynomial quotient = new Polynomial("0");
        Polynomial remainder = this.clone();

        while(remainder.getDegree() >= q.getDegree()){
            int coefficient = remainder.getLC() / q.getLC();
            int degree = remainder.getDegree() - q.getDegree();
            quotient.addTerm(coefficient, degree);
            Polynomial b = new Polynomial().addTerm(coefficient, degree).multiply(q);
            remainder = remainder.subtract(b);
        }
        return new LCD(quotient, remainder);
    }
}
