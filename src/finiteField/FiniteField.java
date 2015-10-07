package finiteField;

import polynomial.Polynomial;

import java.util.ArrayList;

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
        ArrayList<Polynomial> classes = Polynomial.getAllDegreePolynomials(q.degree(), q.getModulus());
        return classes.toArray(new Polynomial[classes.size()]);
    }

    public static void drawMultiplicationTable(ArrayList<Polynomial> classes, Polynomial q) {
        Polynomial[][] polyTable = new Polynomial[classes.size()][classes.size()];
        for (int start_i = 0; start_i < classes.size(); start_i++) {
            for (int i = start_i; i < classes.size(); i++) {
                polyTable[start_i][i] = classes.get(start_i).product(classes.get(i));
                if (i != start_i) {
                    polyTable[i][start_i] = polyTable[start_i][i];
                }
            }
        }

        String table = "<table>";
        for (int i = 0; i < polyTable.length; i++) {
            Polynomial[] row = polyTable[i];
            table += "<tr>";
            table += "<td>" + classes.get(i) + "</td>";
            for (Polynomial p : row) {
                table += "<td>" + p.toString() + "</td>";
            }
            table += "</tr>";
        }
        //draw table...
    }

    public static void drawAdditionTable(){

    }
}
