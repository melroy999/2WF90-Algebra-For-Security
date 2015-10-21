package finiteField;

import core.Core;
import gui.PrintHandler;
import polynomial.Arithmetic;
import polynomial.Polynomial;

import java.util.ArrayList;

/**
 * Created by
 * Melroy van Nijnatten - 0849740.
 * Stefan Habets - 0854187
 */
public class FiniteField {
    /**
     * Returns all the equivalence classes of the given polynomial.
     *
     * @param q: The polynomial you want the equivalence classes of.
     * @return The equivalence classes of q.
     */
    public static ArrayList<Polynomial> getEquivalenceClasses(Polynomial q) {
        return Polynomial.getAllDegreePolynomials(q.degree(), q.getModulus());
    }

    /**
     * Calls the draw table method to draw a multiplication table.
     *
     * @param qq: The polynomial you want a multiplication table of.
     */
    public static void drawMultiplicationTable(final Polynomial qq) {
        drawTable(getEquivalenceClasses(qq), qq, "*", new Operation() {

            @Override
            public Polynomial operation(Polynomial p, Polynomial q) {
                return FiniteField.productFieldElements(p, q, qq);
            }
        }, true);
    }

    /**
     * Calls the draw table method to draw a addition table.
     *
     * @param qq: The polynomial you want a addition table of.
     */
    public static void drawAdditionTable(final Polynomial qq) {
        drawTable(getEquivalenceClasses(qq), qq, "+", new Operation() {

            @Override
            public Polynomial operation(Polynomial p, Polynomial q) {
                return FiniteField.sumFieldElements(p, q, qq);
            }
        }, true);
    }

    /**
     * Draws an inverse table of the given polynomial.
     *
     * @param q: The polynomial you want a inverse table of.
     */
    public static void drawInverseTable(Polynomial q) {
        ArrayList<Polynomial> classes = getEquivalenceClasses(q);
        Polynomial[][] polyTable = new Polynomial[classes.size()][1];

        //for all elements
        for (int start_i = 0; start_i < classes.size(); start_i++) {
            polyTable[start_i][0] = inverseFieldElements(classes.get(start_i), q);
            if (polyTable[start_i][0] != null) {
                polyTable[start_i][0] = polyTable[start_i][0].makeCompletelyPositive();
            }
        }

        //table to string, required different methods than the usual table print method.
        String table = "<table cellspacing='-1' cellpadding='-1'>";

        table += "<tr><td class='color'><div class='width'>X</div></td>";
        table += "<td class='color'><div class='width'>X^-1</div></td></tr>";

        for (int i = 0; i < classes.size(); i++) {
            Polynomial[] row = polyTable[i];
            table += "<tr>";
            table += "<td class='color'><div>" + classes.get(i) + "</div></td>";
            for (Polynomial p : row) {
                String p_value = "N.A.";
                if (p != null) {
                    p_value = p.toString();
                }
                table += "<td><div>" + p_value + "</div></td>";
            }
            table += "</tr>";
        }
        table += "</table>";

        //add it to the result
        Core.printHandler.appendResultNoPre(table);
    }

    /**
     * Calls the draw table method to draw a quotient table.
     *
     * @param qq: The polynomial you want a quotient table of.
     */
    public static void drawQuotientTable(final Polynomial qq) {
        drawTable(getEquivalenceClasses(qq), qq, "*^-1", new Operation() {

            @Override
            public Polynomial operation(Polynomial p, Polynomial q) {
                return FiniteField.quotientFieldElements(p, q, qq);
            }
        }, false);
    }

    /**
     * Get the values needed to draw a table of a specific type.
     *
     * @param classes:    The list of equivalence classes.
     * @param q:          The polynomial you want to draw.
     * @param symbol:     The symbol to be drawn on the table top left.
     * @param arithmetic: An abstract class containing the correct arithmetic.
     * @param mirror:     If the table should be mirrored through the diagonal.
     */
    public static void drawTable(ArrayList<Polynomial> classes, Polynomial q, String symbol, Operation arithmetic, boolean mirror) {
        PrintHandler.resultStyle.addRule(".width {width: " + 25 * (q.degree() + 1) + "px;}");
        Polynomial[][] polyTable = new Polynomial[classes.size()][classes.size()];

        //for all combinations.
        for (int start_i = 0; start_i < classes.size(); start_i++) {
            for (int i = (mirror ? start_i : 0); i < classes.size(); i++) {
                polyTable[start_i][i] = arithmetic.operation(classes.get(start_i), classes.get(i));
                if (i != start_i && mirror) {
                    polyTable[i][start_i] = polyTable[start_i][i];
                }
            }
        }

        //get the table in string format.
        String table = getTable(classes, classes, polyTable, symbol);

        //draw the table.
        Core.printHandler.appendResultNoPre(table);
    }

    /**
     * Gets the sum of two elements within the specified field.
     *
     * @param a: The first field element.
     * @param b: The second field element.
     * @param q: The specified polynomial generating the field.
     * @return returns the sum of the two elements mod q.
     */
    public static Polynomial sumFieldElements(Polynomial a, Polynomial b, Polynomial q) {
        Polynomial result = a.sum(b);
        return result.longDivision(q)[1];
    }

    /**
     * Gets the product of two elements within the specified field.
     *
     * @param a: The first field element.
     * @param b: The second field element.
     * @param q: The specified polynomial generating the field.
     * @return returns the product of the two elements mod q.
     */
    public static Polynomial productFieldElements(Polynomial a, Polynomial b, Polynomial q) {
        Polynomial result = a.product(b);
        return result.longDivision(q)[1].makeCompletelyPositive();
    }

    /**
     * Gets the inverse of a field element within the specified field.
     *
     * @param a: The field element.
     * @param q: The specified polynomial generating the field.
     * @return returns the inverse of a field element in the field q. It returns null if it does not exist.
     */
    public static Polynomial inverseFieldElements(Polynomial a, Polynomial q) {
        Polynomial[] result = Arithmetic.extendedEuclideanAlgorithm(a, q);
        if (!result[3].equals(new Polynomial(result[3].getModulus(), "1"))) {
            return null;
        } else {
            return Arithmetic.longDivision(result[0], q)[1];
        }
    }

    /**
     * Gets the quotient of two elements within the specified field.
     *
     * @param a: The first field element.
     * @param b: The second field element.
     * @param q: The specified polynomial generating the field.
     * @return returns the quotient of the two elements mod q.
     */
    public static Polynomial quotientFieldElements(Polynomial a, Polynomial b, Polynomial q) {
        if (b.toString().equals("0")) {
            return null;
        } else {
            Polynomial inverse = inverseFieldElements(b, q);
            if (inverse == null) {
                return null;
            } else {
                return productFieldElements(a, inverse, q);
            }
        }
    }

    /**
     * Check if a polynomial is irreducible.
     *
     * @param p: The polynomial you should check.
     * @return If p is irreducible.
     */
    public static boolean isIrreducible(Polynomial p) {
        return p.isIrreducible();
    }

    /**
     * Gets an irreducible polynomial of the specified degree.
     *
     * @param degree: The required degree.
     * @param mod:    The prime modulus.
     * @return A irreducible polynomial of the given degree.
     */
    public static Polynomial getIrreducible(int degree, int mod) {
        Polynomial q = new Polynomial(mod);
        do {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Core.printHandler.appendLog("The worker has been successfully interrupted.", true);
                System.out.println("The worker has been successfully interrupted.");
                throw new Error();
            }
            //generate a random polynomial of the given degree until we find one that is irreducible.
            q.randomize(degree, true);
        }
        while (!q.isIrreducible());
        return q;
    }

    /**
     * Check if the element is a primitive element within the specified field.
     * TODO specify the algorithm that is used.
     *
     * @param a: The field element that should be checked.
     * @param q: The polynomial that generates the field.
     * @return If a is a primitive element.
     */
    public static boolean isPrimitiveElement(Polynomial a, Polynomial q) {
        ArrayList<Polynomial> classes = getEquivalenceClasses(q);
        int n = classes.size();
        Polynomial result;

        for (int i = 2; i < n; i++) {
            if ((n - 1) % i == 0) {
                result = a.powRec(i, a, q);
                System.out.println("Calculating: (" + a + ")^" + i + " = " + result + " (mod " + q.toString() + ").");
                if (result.equals(new Polynomial(a.getModulus(), "1"))) {
                    return i == n - 1;
                }
            }

        }
        return false;
    }

    /**
     * Gets a primitive element within the specified field.
     *
     * @param q: The polynomial that generates the field.
     * @return A primitive element within q.
     */
    public static Polynomial getPrimitiveElement(Polynomial q) {
        Polynomial element = new Polynomial(q.getModulus());
        element.randomize(q.degree() - 1, false);
        while (!isPrimitiveElement(element, q)) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Core.printHandler.appendLog("The worker has been successfully interrupted.", true);
                System.out.println("The worker has been successfully interrupted.");
                throw new Error();
            }
            element.randomize(q.degree() - 1, false);
        }
        return element;
    }

    /**
     * Returns the table in string form.
     *
     * @param classesX:  The equivalence classes in the X direction.
     * @param classesY:  The equivalence classes in the Y direction.
     * @param polyTable: The poly table generated in the generate table method.
     * @param symbol:    The symbol that has to be drawn.
     * @return Returns the table in string format with the correct formatting.
     */
    private static String getTable(ArrayList<Polynomial> classesX, ArrayList<Polynomial> classesY, Polynomial[][] polyTable, String symbol) {
        String table = "<table cellspacing='-1' cellpadding='-1'>";

        //draw the top row.
        table += "<tr><td class='color'><div class='width'>" + symbol + "</div></td>";
        //print all the equivalence classes.
        for (Polynomial aClassesY : classesY) {
            table += "<td class='color'><div class='width'>" + aClassesY + "</div></td>";
        }
        table += "</tr>";

        for (int i = 0; i < classesX.size(); i++) {
            Polynomial[] row = polyTable[i];
            //make the first column the polynomial that is connected to the result.
            table += "<tr>";
            table += "<td class='color'><div>" + classesX.get(i) + "</div></td>";
            //draw all the results of the polynomial.
            for (Polynomial p : row) {
                //if no result is available, print N.A.
                String p_value = "N.A.";
                if (p != null) {
                    p_value = p.toString();
                }
                table += "<td><div>" + p_value + "</div></td>";
            }
            table += "</tr>";
        }
        table += "</table>";

        return table;
    }

    /**
     * An interface that can contain any of the possible operators (+, * etc) that can be plugged into the the generate table method.
     */
    private interface Operation {
        Polynomial operation(Polynomial p, Polynomial q);
    }
}
