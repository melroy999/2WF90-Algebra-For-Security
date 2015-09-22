package polynomial;

import polynomial.support.Numeric;
import sun.reflect.generics.tree.Tree;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by Melroy van Nijnatten - 0849740.
 */
public class Polynomial<T extends Number> {
    private Numeric<T> calc;
    private TreeMap<Integer, T> terms = new TreeMap<Integer, T>();

    /**
     *
     * #-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#
     *                                  Constructors
     * #-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#
     *
     */

    public Polynomial(Numeric<T> calc, String rep) {
        this.calc = calc;
        toPolynomial(rep);
    }

    public Polynomial(Numeric<T> calc, TreeMap<Integer, T> terms) {
        this.calc = calc;
        this.terms = terms;
    }

    public Polynomial(Numeric<T> calc) {
        this.calc = calc;
    }

    public void addTerm(T coefficient, int degree){
        if(this.terms.containsKey(degree)){
            coefficient = calc.add(coefficient, terms.get(degree));
            System.out.println(coefficient);
        }
        if(calc.isZero(coefficient) && degree != 0){
            terms.remove(degree);
            if(terms.isEmpty()){
                terms.put(0, coefficient);
            }
            return;
        }
        terms.put(degree, calc.format(coefficient));
    }

    public void printTree(){
        for(int degree : terms.keySet()){
            System.out.println(terms.get(degree) + "X^" + degree);
        }
    }

    /**
     *
     * #-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#
     *                                     Getters
     * #-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#
     *
     */

    /**
     *
     * @param degree
     * @return
     */
    public T getCoefficient(int degree) {
        return terms.get(degree);
    }

    /**
     *
     * @param degree
     * @return
     */
    public T get(int degree) {
        return getCoefficient(degree);
    }

    /**
     *
     * @return
     */
    public int getDegree(){
        return terms.lastKey();
    }

    /**
     *
     * @return
     */
    public Iterator<Integer> iterator(){
        return terms.keySet().iterator();
    }

    /**
     *
     * @return
     */
    public Iterator<Integer> inverseIterator(){
        return terms.descendingKeySet().iterator();
    }

    /**
     * Converts a given string to a p object.
     *
     * @param polynomial: String representation of the p.
     */
    private void toPolynomial(String polynomial) {
        polynomial = polynomial.replace(" ", "");
        polynomial = polynomial.replaceAll("[^0-9\\^\\+\\-\\.,]", "x");
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
            T coefficient;
            if (term.contains("^")) {
                term = term.replace("x", "");
                String[] components = term.split("\\^");
                coefficient = calc.parse(components[0]);
                degree = Integer.parseInt(components[1]);
            } else {
                if (term.contains("x")) {
                    term = term.replace("x", "");
                    degree = 1;
                } else {
                    degree = 0;
                }
                coefficient = calc.parse(term);
            }
            addTerm(coefficient, degree);
        }
    }

    protected Polynomial<T> clone(){
        return new Polynomial<T>(calc, terms);
    }

    /**
     *
     * #-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#
     *                                     Getters
     * #-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#
     *
     */

    protected Set<Integer> getTerms(){
        return terms.keySet();
    }

    public boolean isEmpty(){
        return terms.isEmpty();
    }


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
            T coefficient = terms.get(degree);
            standardForm += (calc.isPositive(coefficient) ? (calc.isOne(coefficient) && degree != 0 ? "" : coefficient) : calc.isMinusOne(coefficient) && degree != 0 ? "-" : coefficient);
            standardForm += (degree == 0 ? "" : "X");
            standardForm += (degree > 1 ? "^" + degree : "");
            standardForm += (iterator.hasNext() ? " + " : "");
        }
        standardForm = standardForm.replace("+ -", "- ");
        return standardForm;
    }

    public Polynomial<T> add(final Polynomial<T> q){
        final Polynomial<T> p = this;
        Polynomial<T> result = (this.getDegree() < q.getDegree() ? q.clone() : p.clone());
        Polynomial<T> add = (this.getDegree() < q.getDegree() ? p.clone() : q.clone());

        System.out.println("result: ");
        result.printTree();
        System.out.println("add: ");
        add.printTree();
        System.out.println("p: ");
        p.printTree();
        System.out.println("q: ");
        q.printTree();
        System.out.println();

        for(int degree : add.getTerms()){
            result.addTerm(add.getCoefficient(degree), degree);
        }

        System.out.println("result: ");
        result.printTree();
        System.out.println("add: ");
        add.printTree();
        System.out.println("p: ");
        p.printTree();
        System.out.println("q: ");
        q.printTree();

        return result;
    }

    public Polynomial<T> subtract(Polynomial<T> q){
        Polynomial<T> result = new Polynomial<T>(calc);
        Iterator<Integer> iterator = q.iterator();
        while(iterator.hasNext()){
            int degree = iterator.next();
            result.addTerm(calc.negate(q.getCoefficient(degree)), degree);
        }
        return this.add(q);
    }

    public Polynomial<T> multiply(Polynomial<T> q){
        Polynomial<T> result = new Polynomial<T>(calc);
        for(int degree_p: getTerms()){
            T coefficient_p = getCoefficient(degree_p);
            for(int degree_q: q.getTerms()){
                T coefficient_q = q.getCoefficient(degree_q);
                result.addTerm(calc.add(coefficient_p, coefficient_q), degree_p + degree_q);
            }
        }
        return result;
    }

}
