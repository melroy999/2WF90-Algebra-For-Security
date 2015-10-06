package finiteField;

import polynomial.Arithmetic;
import polynomial.Polynomial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Melroy van Nijnatten - 0849740.
 */
public class FiniteField {
    /**
     * TODO
     * 1. input prime p + irreducible polynomial q(X) = addition and multiplication table for Z/pZ[X]/q(X)
     * 2. check if polynomial is irreducible mod p.
     * 3. field elements a and b, produce a + b, a * b and quotient of ab^-1 (b!=0)(2.3.3)
     * 4. check primitivity of a field element (3.4.3)
     * 5. generate primitive elements in a field (3.4.4)
     * 6. produce irreducible polynomials of a prescribed degree (4.1.6)
     */

    public static Polynomial[] getEquivalenceClasses(Polynomial q){
        HashMap<Integer, Polynomial> polynomials = new HashMap<Integer, Polynomial>();
        ArrayList<Polynomial> candidates = Polynomial.getAllDegreePolynomials(q.degree(), q.getModulus());
        for(Polynomial p : candidates){
            Polynomial candidate = Arithmetic.longDivision(p, q)[1];
            polynomials.put(candidate.getRanking(), candidate);
        }

        Polynomial[] resultList = new Polynomial[polynomials.size()];
        int counter = 0;
        for(int i : polynomials.keySet()){
            Polynomial p = polynomials.get(i);
            resultList[counter] = p;
            counter ++;
        }
        return resultList;
    }

    public static void drawMultiplicationTable(){

    }

    public static void drawAdditionTable(){

    }
}
