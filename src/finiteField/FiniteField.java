package finiteField;

import core.Core;
import polynomial.Polynomial;

import java.util.ArrayList;
import java.util.List;

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

    public static ArrayList<Polynomial> getEquivalenceClasses(Polynomial q) {
        return Polynomial.getAllDegreePolynomials(q.degree(), q.getModulus());
    }

    public static void drawMultiplicationTable(Polynomial q){
        drawMultiplicationTable(getEquivalenceClasses(q), q);
    }

    public static void drawMultiplicationTable(ArrayList<Polynomial> classes, Polynomial q) {
        Polynomial[][] polyTable = new Polynomial[classes.size()][classes.size()];
        for (int start_i = 0; start_i < classes.size(); start_i++) {
            for (int i = start_i; i < classes.size(); i++) {
                Polynomial result = classes.get(start_i).product(classes.get(i));
                polyTable[start_i][i] = result.longDivision(q)[1];
                polyTable[start_i][i] = polyTable[start_i][i].makeCompletelyPositive();
                if (i != start_i) {
                    polyTable[i][start_i] = polyTable[start_i][i];
                }
            }
        }
        String table = getTable(classes, polyTable, "*");
        Core.printHandler.appendResultFF(table);
    }

    public static void drawAdditionTable(Polynomial q) {
        drawAdditionTable(getEquivalenceClasses(q), q);
    }

    public static void drawAdditionTable(ArrayList<Polynomial> classes, Polynomial q) {
        Polynomial[][] polyTable = new Polynomial[classes.size()][classes.size()];
        for (int start_i = 0; start_i < classes.size(); start_i++) {
            for (int i = start_i; i < classes.size(); i++) {
                Polynomial result = classes.get(start_i).sum(classes.get(i));
                polyTable[start_i][i] = result.longDivision(q)[1];
                if (i != start_i) {
                    polyTable[i][start_i] = polyTable[start_i][i];
                }
            }
        }
        String table = getTable(classes, polyTable, "+");
        Core.printHandler.appendResultFF(table);
    }

    private static String getTable(ArrayList<Polynomial> classes, Polynomial[][] polyTable, String symbol) {
        String table = "<table cellspacing='-1' cellpadding='-1'>";

        table += "<tr><td><div class='color'>" + symbol + "</div></td>";
        for (int i = 0; i < polyTable.length; i++) {
            table += "<td><div class='color'>" + classes.get(i) + "</div></td>";
        }
        table += "</tr>";

        for (int i = 0; i < polyTable.length; i++) {
            Polynomial[] row = polyTable[i];
            table += "<tr>";
            table += "<td><div class='color'>" + classes.get(i) + "</div></td>";
            for (Polynomial p : row) {
                table += "<td><div>" + p.toString() + "</div></td>";
            }
            table += "</tr>";
        }
        table += "</table>";

        return table;
    }
}
