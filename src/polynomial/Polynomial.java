package polynomial;

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

    public int degree() {
        if (!terms.isEmpty()) {
            return terms.lastKey();
        }
        return 0;
    }

    public int getLeadingCoefficient() {
        return getCoefficient(degree());
    }

    public Polynomial clone() {
        Polynomial result = new Polynomial(modulus);
        result.terms = this.getTerms();
        return result;
    }

    public int getModulus() {
        return modulus;
    }

    public int size(){
        return terms.size();
    }

    public Set<Integer> keySet(){
        return terms.keySet();
    }

    public Set<Integer> inverseKeySet(){
        return terms.descendingKeySet();
    }

    public TreeMap<Integer, Integer> getTerms() {
        return (TreeMap<Integer, Integer>) terms.clone();
    }

    /**
     * Returns a string representation of the p.
     *
     * @return   The p in string format.
     */
    public String toString() {
        String standardForm = "";
        Iterator<Integer> iterator = inverseKeySet().iterator();
        while (iterator.hasNext()) {
            int degree = iterator.next();
            int coefficient = terms.get(degree);
            //if (coefficient != 0) {
                standardForm += (coefficient > 0 ? (coefficient == 1 && degree != 0 ? "" : coefficient) : coefficient == -1 && degree != 0 ? "-" : coefficient);
                standardForm += (degree == 0 ? "" : "X");
                standardForm += (degree > 1 ? superscript(String.valueOf(degree)) : "");
                standardForm += (iterator.hasNext() ? " + " : "");
            //}
        }
        if (standardForm.equals("")) {
            standardForm = "0";
        }
        standardForm = standardForm.replace("+ -", "- ");
        return standardForm;
    }

    public int getCoefficient(int degree){
        if(terms.containsKey(degree)){
            return terms.get(degree);
        } else {
            return 0;
        }

    }

    private Polynomial nodesToPoly(TreeNode current){
        Polynomial leftChildP = null;
        Polynomial rightChildP = null;
        String value = current.getValue();

        TreeNode leftChild = current.getLeftNode();
        if(leftChild!=null){
            leftChildP = nodesToPoly(current.getLeftNode());
        }

        TreeNode rightChild = current.getRightNode();
        if(rightChild!=null){
            rightChildP = nodesToPoly(current.getRightNode());
        }

        Polynomial result;

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
            if(value.contains("^")){
                String[] split = value.split("\\^");
                d = Integer.parseInt(split[1]);
                c = Integer.parseInt(split[0]);
            } else {
                c = Integer.parseInt(value);
            }
            result = new Polynomial(modulus).addTerm(c, d);
        }
        return result;
    }

    protected Polynomial addTerm(int c, int d) {
        c += this.getCoefficient(d);
        c %= modulus;
        if (c == 0) {
            terms.remove(d);
            return this;
        }
        terms.put(d, c);
        return this;
    }

    public Polynomial scalar(int scalar) {
        return Arithmetic.scalar(this, scalar);
    }

    public ArrayList<Integer> getAllCoefficients() {
        ArrayList<Integer> result = new ArrayList<Integer>();
        for (int degree : terms.keySet()) {
            result.add(getCoefficient(degree));
        }
        return result;
    }

    public Polynomial difference(Polynomial q) {
        return Arithmetic.difference(this, q);
    }

    public Polynomial sum(Polynomial q) {
        return Arithmetic.sum(this, q);
    }

    public Polynomial product(Polynomial q) {
        return Arithmetic.product(this, q);
    }

    public Polynomial[] longDivision(Polynomial q) {
        return Arithmetic.longDivision(this, q);
    }

    public Polynomial[] extendedEuclideanAlgorithm(Polynomial q) {
        return Arithmetic.extendedEuclideanAlgorithm(this, q);
    }

    public Object[] equalityModPolynomial(Polynomial q, Polynomial r) {
        return Arithmetic.equalModuloPolynomial(this, q, r);
    }

    public boolean isEqual(Polynomial q) {
        Polynomial p1 = this.makeABSMinimal();
        Polynomial p2 = q.makeABSMinimal();
        return p1.getAllCoefficients().equals(p2.getAllCoefficients()) && p1.keySet().equals(p2.keySet());
    }

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

    public static ArrayList<Polynomial> getAllDegreePolynomials(int degree, int modulo){
        ArrayList<Polynomial> polynomials = new ArrayList<Polynomial>();
        int max_polynomial_rep = (int) Math.pow(modulo, degree);
        for(int i = 0; i < max_polynomial_rep; i++){
            Polynomial result = new Polynomial(modulo);
            Stack<Integer> base = toBaseRepresentation(modulo, i);
            int counter = base.size() - 1;
            while(!base.isEmpty()){
                result.addTerm(base.pop(), counter);
                counter--;
            }
            polynomials.add(result);
        }
        return polynomials;
    }

    private static Stack<Integer> toBaseRepresentation(int modulo, int i) {
        Stack<Integer> baseModulo = new Stack<Integer>();
        int quotient;
        do {
            quotient = i / modulo;
            int remainder = i % modulo;
            i = quotient;
            baseModulo.push(remainder);
        }
        while(quotient != 0);
        return baseModulo;
    }

    public int getRanking(){
        Polynomial toRank = this.makeCompletelyPositive();
        int rank = 0;
        for(int i : toRank.keySet()){
            rank += toRank.getCoefficient(i) * Math.pow(modulus, i);
        }
        return rank;
    }
}
