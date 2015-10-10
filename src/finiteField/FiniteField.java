package finiteField;

import core.Core;
import gui.PrintHandler;
import polynomial.Arithmetic;
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

    public static ArrayList<Polynomial> getEquivalenceClasses(Polynomial q) {
        return Polynomial.getAllDegreePolynomials(q.degree(), q.getModulus());
    }

    public static void drawMultiplicationTable(final Polynomial qq){
        drawTable(getEquivalenceClasses(qq), qq, "*", new Operation() {

            @Override
            public Polynomial operation(Polynomial p, Polynomial q) {
                return FiniteField.productFieldElements(p, q, qq);
            }
        }, true);
    }

    public static void drawAdditionTable(final Polynomial qq) {
        drawTable(getEquivalenceClasses(qq), qq, "+", new Operation() {

            @Override
            public Polynomial operation(Polynomial p, Polynomial q) {
                return FiniteField.sumFieldElements(p, q, qq);
            }
        }, true);
    }

    public static void drawInverseTable(Polynomial q){
        ArrayList<Polynomial> classes = getEquivalenceClasses(q);
        Polynomial[][] polyTable = new Polynomial[classes.size()][1];
        ArrayList<Polynomial> inverseTop = new ArrayList<Polynomial>();
        inverseTop.add(new Polynomial(q.getModulus(), "X^-1"));

        for (int start_i = 0; start_i < classes.size(); start_i++) {
            polyTable[start_i][0] = inverseFieldElements(classes.get(start_i), q);
            if(polyTable[start_i][0] != null){
                polyTable[start_i][0] = polyTable[start_i][0].makeCompletelyPositive();
            }
        }

        String table = "<table cellspacing='-1' cellpadding='-1'>";

        table += "<tr><td class='color'><div class='width'>X</div></td>";
        table += "<td class='color'><div class='width'>X^-1</div></td></tr>";

        for (int i = 0; i < classes.size(); i++) {
            Polynomial[] row = polyTable[i];
            table += "<tr>";
            table += "<td class='color'><div>" + classes.get(i) + "</div></td>";
            for (Polynomial p : row) {
                String p_value = "N.A.";
                if(p != null) {
                    p_value = p.toString();
                }
                table += "<td><div>" + p_value + "</div></td>";
            }
            table += "</tr>";
        }
        table += "</table>";

        Core.printHandler.appendResultNoPre(table);
    }

    public static void drawQuotientTable(final Polynomial qq) {
        drawTable(getEquivalenceClasses(qq), qq, "*^-1", new Operation() {

            @Override
            public Polynomial operation(Polynomial p, Polynomial q) {
                return FiniteField.quotientFieldElements(p, q, qq);
            }
        }, false);
    }

    public static void drawTable(ArrayList<Polynomial> classes, Polynomial q, String symbol, Operation arithmetic, boolean mirror){
        PrintHandler.resultStyle.addRule(".width {width: " + 25 * (q.degree() + 1) + "px;}");
        Polynomial[][] polyTable = new Polynomial[classes.size()][classes.size()];
        for (int start_i = 0; start_i < classes.size(); start_i++) {
            for (int i = (mirror ? start_i : 0); i < classes.size(); i++) {
                polyTable[start_i][i] = arithmetic.operation(classes.get(start_i), classes.get(i));
                if (i != start_i && mirror) {
                    polyTable[i][start_i] = polyTable[start_i][i];
                }
            }
        }
        String table = getTable(classes, classes, polyTable, symbol);
        Core.printHandler.appendResultNoPre(table);
    }

    private interface Operation {
        public Polynomial operation(Polynomial p, Polynomial q);
    }

    public static Polynomial sumFieldElements(Polynomial a, Polynomial b, Polynomial q){
        Polynomial result = a.sum(b);
        return result.longDivision(q)[1];
    }

    public static Polynomial productFieldElements(Polynomial a, Polynomial b, Polynomial q){
        Polynomial result = a.product(b);
        return result.longDivision(q)[1].makeCompletelyPositive();
    }

    public static Polynomial inverseFieldElements(Polynomial a, Polynomial q) {
        Polynomial[] result = Arithmetic.extendedEuclideanAlgorithm(a, q);
        if(!result[2].toString().equals("1")){
            return null;
        } else {
            return Arithmetic.longDivision(result[0], q)[1];
        }
    }

    public static Polynomial quotientFieldElements(Polynomial a, Polynomial b, Polynomial q){
        if(b.toString().equals("0")){
            return null;
        } else {
            Polynomial inverse = inverseFieldElements(b, q);
            if(inverse == null){
                return null;
            } else {
                return productFieldElements(a, inverse, q);
            }
        }
    }

    public static boolean isIrreducible(Polynomial p){
        return false;
    }

    public static Polynomial getIrreducible(int degree, int mod){
        return null;
    }

    public static boolean isPrimitiveElement(Polynomial a, Polynomial q){
        return false;
    }

    public static ArrayList<Polynomial> getPrivateElements(Polynomial q){
        return new ArrayList<Polynomial>();
    }

    private static String getTable(ArrayList<Polynomial> classesX, ArrayList<Polynomial> classesY, Polynomial[][] polyTable, String symbol) {
        String table = "<table cellspacing='-1' cellpadding='-1'>";

        table += "<tr><td class='color'><div class='width'>" + symbol + "</div></td>";
        for (int i = 0; i < classesY.size(); i++) {
            table += "<td class='color'><div class='width'>" + classesY.get(i) + "</div></td>";
        }
        table += "</tr>";

        for (int i = 0; i < classesX.size(); i++) {
            Polynomial[] row = polyTable[i];
            table += "<tr>";
            table += "<td class='color'><div>" + classesX.get(i) + "</div></td>";
            for (Polynomial p : row) {
                String p_value = "N.A.";
                if(p != null) {
                    p_value = p.toString();
                }
                table += "<td><div>" + p_value + "</div></td>";
            }
            table += "</tr>";
        }
        table += "</table>";

        return table;
    }
}
