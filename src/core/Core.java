package core;

import polynomial.Polynomial;

/**
 * Created by Melroy van Nijnatten - 0849740.
 */
public class Core {
    public Core() {
        Polynomial p = new Polynomial("2X^2 + 4X + 1");
        System.out.println(p.toString());

        Polynomial q = new Polynomial("2X^2 + 4X + 1 + 5X - X^2");
        System.out.println(q.toString());

        Polynomial r = new Polynomial("2X^2 + 4X + 1 + 5X - 3X^2 + X^3");
        System.out.println(r.toString());

        System.out.println(p.add(q).toString());
        System.out.println(p.subtract(q).toString());
        System.out.println(p.multiply(q).toString());
        System.out.println("Degree: " + p.multiply(q).getDegree());
        System.out.println("Base for p exp:2: " + p.getCoefficient(2));
        System.out.println("Base for p exp:3: " + p.getCoefficient(3));

        Polynomial t = new Polynomial("X^4 + 2X^3 + 3X^2 + 2X + 1");
        Polynomial s = new Polynomial("X^2 - 1");

        System.out.println("Division of t by s: " + t.division(s).quotient.toString() + " " + t.division(s).remainder.toString());
    }

    public static void main(String[] args){
        new Core();
    }
}
