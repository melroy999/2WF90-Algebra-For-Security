package polynomial;

import parser.Parser;
import parser.tree.TreeNode;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by Melroy van Nijnatten - 0849740.
 */
public class Polynomial {
    private final int modulus;
    private TreeMap<Integer, Integer> terms = new TreeMap<Integer, Integer>();

    public Polynomial(int modulus) {
        this.modulus = modulus;
    }

    public Polynomial(int modulus, String s) {
        this.modulus = modulus;
        this.terms = nodesToPoly(Parser.parse(s)).getTerms();
        System.out.println("polynomial:" + this);
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
            if (coefficient != 0) {
                standardForm += (coefficient > 0 ? (coefficient == 1 && degree != 0 ? "" : coefficient) : coefficient == -1 && degree != 0 ? "-" : coefficient);
                standardForm += (degree == 0 ? "" : "X");
                standardForm += (degree > 1 ? superscript(String.valueOf(degree)) : "");
                standardForm += (iterator.hasNext() ? " + " : "");
            }
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
        c += this.getCoefficient(d) % modulus;
        if (c == 0) {
            terms.remove(d);
            return this;
        }
        terms.put(d, c % modulus);
        return this;
    }

    public Polynomial scalar(int scalar) {
        return Arithmetic.scalar(this, scalar);
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
        return null;
    }

    public void extendedEuclideanAlgorithm(Polynomial q) {

    }

    public void equalityModPolynomial(Polynomial q, Polynomial r) {

    }

    /*
    TODO:
    - sum p1 + p2
    - scalar multiple of p1
    - difference of p1 and p2
    - product of p1 and p2
    - quotient and remainder (long division) upon input of p1 and p2
    - (extended) euclidean algorithm for p1 and p2
    - decide whether p1 and p2 mod p are equal modulo p3.
     */
}
