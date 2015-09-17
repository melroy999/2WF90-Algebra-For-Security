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

    public Polynomial(String poly) {
        toPolynomial(poly);
    }

    private Polynomial(TreeMap<Integer, Integer> poly){
        this.polynomial = poly;
    }

    public String toString(){
        String standardForm = "";
        Iterator<Integer> iterator = polynomial.descendingKeySet().iterator();
        while(iterator.hasNext()){
            int exponent = iterator.next();
            int base = polynomial.get(exponent);
            standardForm += (base == 1 && exponent != 0 ? "" : base);
            standardForm += (exponent == 0 ? "" : "X");
            standardForm += (exponent > 1 ? "^" + exponent : "");
            standardForm += (iterator.hasNext()? " + " : "");
        }
        standardForm = standardForm.replace("+ -","-");
        return standardForm;
    }

    public int getDeg(){
        return polynomial.lastKey();
    }

    public int getBase(int exp){
        int base = 0;
        if(polynomial.containsKey(exp)) {
            base = polynomial.get(exp);
        }
        return base;
    }

    private void toPolynomial(String poly) {
        /**
         * Could be improved / made more robust.
         */
        poly = poly.replace(" ","");
        poly = poly.replaceAll("[^0-9\\^\\+\\-]", "x");
        poly = poly.replace("-", "+-");
        poly = poly.replace("-x", "-1x");
        poly = poly.replace("+x","+1x");
        String[] parts = poly.split("\\+");
        for (String part : parts) {
            int deg;
            int base;
            if(part.contains("^")){
                part = part.replace("x","");
                String[] components = part.split("\\^");
                base = Integer.parseInt(components[0]);
                deg = Integer.parseInt(components[1]);
            } else {
                if(part.contains("x")){
                    part = part.replace("x","");
                    deg = 1;
                } else {
                    deg = 0;
                }
                base = Integer.parseInt(part);
            }
            if(polynomial.containsKey(deg)){
                base += polynomial.get(deg);
            }
            polynomial.put(deg, base);
        }
    }

    public TreeMap<Integer, Integer> getTreeMap(){
        return new TreeMap<Integer, Integer>(polynomial);
    }

    public int size(){
        return polynomial.size();
    }

    public Polynomial add(Polynomial poly){
        TreeMap<Integer, Integer> firstMap;
        TreeMap<Integer, Integer> secondMap;
        if(poly.size() < polynomial.size()){
            firstMap = this.getTreeMap();
            secondMap = poly.getTreeMap();
        } else {
            firstMap = new TreeMap<Integer, Integer>(poly.getTreeMap());
            secondMap = this.getTreeMap();
        }

        Iterator<Integer> iterator = secondMap.keySet().iterator();
        while(iterator.hasNext()){
            int exponent = iterator.next();
            int base = polynomial.get(exponent);
            if(firstMap.containsKey(exponent)){
                firstMap.put(exponent, base + firstMap.get(exponent));
            } else {
                firstMap.put(exponent, base);
            }
        }

        return new Polynomial(firstMap);
    }

    public Polynomial subtract(Polynomial poly){
        TreeMap<Integer, Integer> result = this.getTreeMap();
        Iterator<Integer> iterator = poly.getTreeMap().keySet().iterator();
        while(iterator.hasNext()){
            int exponent = iterator.next();
            int base = result.get(exponent);
            if(result.containsKey(exponent)){
                result.put(exponent, base - result.get(exponent));
            } else {
                result.put(exponent, -base);
            }
        }

        return new Polynomial(result);
    }

    public Polynomial multiply(Polynomial poly){
        return null;
    }

    public Polynomial divide(Polynomial poly){
        return null;
    }

    public Polynomial remainder(Polynomial poly){
        return null;
    }
}
