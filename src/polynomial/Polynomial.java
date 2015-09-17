package polynomial;

import java.util.Iterator;
import java.util.TreeMap;

/**
 * Created by Melroy van Nijnatten - 0849740.
 */
public class Polynomial {
    /**
     * Calculations with +,-,*,/ return a new polynomial. The original polynomials are not edited.
     */

    //use TreeMap to store the polynomial
    private TreeMap<Integer, Integer> polynomial = new TreeMap<Integer, Integer>();

    private Polynomial() {
    }

    public Polynomial(String polynomial) {
        toPolynomial(polynomial);
    }

    private Polynomial(TreeMap<Integer, Integer> polynomial) {
        this.polynomial = polynomial;
    }

    public String toString() {
        String standardForm = "";
        Iterator<Integer> iterator = polynomial.descendingKeySet().iterator();
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

    public int getDegree() {
        return polynomial.lastKey();
    }

    public int getCoefficient(int degree) {
        int coefficient = 0;
        if (polynomial.containsKey(degree)) {
            coefficient = polynomial.get(degree);
        }
        return coefficient;
    }

    protected void add(int coefficient, int degree) {
        if (polynomial.containsKey(degree)) {
            coefficient += polynomial.get(degree);
        }

        if(coefficient == 0){
            polynomial.remove(degree);
        } else {
            polynomial.put(degree, coefficient);
        }
    }

    private void toPolynomial(String polynomial) {
        /**
         * Could be improved / made more robust.
         */
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
            int exp;
            int coefficient;
            if (term.contains("^")) {
                term = term.replace("x", "");
                String[] components = term.split("\\^");
                coefficient = Integer.parseInt(components[0]);
                exp = Integer.parseInt(components[1]);
            } else {
                if (term.contains("x")) {
                    term = term.replace("x", "");
                    exp = 1;
                } else {
                    exp = 0;
                }
                coefficient = Integer.parseInt(term);
            }
            add(coefficient, exp);
        }
    }

    public TreeMap<Integer, Integer> getTreeMap() {
        return new TreeMap<Integer, Integer>(polynomial);
    }

    public int size() {
        return polynomial.size();
    }

    public Polynomial add(Polynomial polynomial2) {
        TreeMap<Integer, Integer> result;
        TreeMap<Integer, Integer> addition;
        if (polynomial2.size() < polynomial2.size()) {
            result = this.getTreeMap();
            addition = polynomial2.getTreeMap();
        } else {
            result = new TreeMap<Integer, Integer>(polynomial2.getTreeMap());
            addition = this.getTreeMap();
        }

        Iterator<Integer> iterator = addition.keySet().iterator();
        while (iterator.hasNext()) {
            int exponent = iterator.next();
            int coefficient = polynomial.get(exponent);
            add(coefficient, exponent);
        }

        return new Polynomial(result);
    }

    public Polynomial subtract(Polynomial polynomial2) {
        TreeMap<Integer, Integer> result = this.getTreeMap();
        Iterator<Integer> iterator = polynomial2.getTreeMap().keySet().iterator();
        while (iterator.hasNext()) {
            int exponent = iterator.next();
            if(!result.containsKey(exponent)){
                //as items can be removed...
                continue;
            }
            int base = result.get(exponent);
            add(-base, exponent);
            /*if (result.containsKey(exponent)) {
                result.put(exponent, base - result.get(exponent));
            } else {
                result.put(exponent, -base);
            }*/
        }

        return new Polynomial(result);
    }

    /**
     * !Make this use a more efficient algorithm
     */
    public Polynomial multiply(Polynomial polynomial2) {
        Polynomial result = new Polynomial();
        TreeMap<Integer, Integer> multiply = polynomial2.getTreeMap();
        for (int exp1 : polynomial.keySet()) {
            for (int exp2 : polynomial2.getTreeMap().keySet()) {
                int base1 = polynomial.get(exp1);
                int base2 = multiply.get(exp2);
                result.add(base1 * base2, exp1 + exp2);
            }
        }
        return result;
    }

    public Pair division(Polynomial polynomial2) {
        Polynomial q = new Polynomial();
        Polynomial r = new Polynomial(new TreeMap<Integer, Integer>(polynomial));
        while(r.getDegree() >= polynomial2.getDegree()){
            q.add(r.getLeadingCoefficient() / polynomial2.getLeadingCoefficient(), r.getDegree() - polynomial2.getDegree());

            Polynomial temp = new Polynomial();
            temp.add(r.getLeadingCoefficient() / polynomial2.getLeadingCoefficient(), r.getDegree() - polynomial2.getDegree());
            temp.multiply(polynomial2);
            r.subtract(temp);
        }
        return new Pair(q, r);
    }

    public int getLeadingCoefficient(){
        return getCoefficient(getDegree());
    }

    public class Pair {
        public Polynomial quotient;
        public Polynomial remainder;

        public Pair(Polynomial quotient, Polynomial remainder) {
            this.quotient = quotient;
            this.remainder = remainder;
        }
    }

    public Pair gcd(Polynomial polynomial2) {
        return null;
    }
}
