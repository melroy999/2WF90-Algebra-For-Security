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

        System.out.println("Degree of p and lc: " + p.getDegree() + ", " + p.getLC());
        System.out.println("Degree of q and lc: " + q.getDegree() + ", " + q.getLC());
        System.out.println("Degree of r and lc: " + r.getDegree() + ", " + r.getLC());

        p.addTerm(1, 6);
        System.out.println("Add term X^6 to p: " + p.toString());
        System.out.println("Degree of p and lc: " + p.getDegree() + ", " + p.getLC());

        q.addTerm(-1, 6);
        System.out.println("Add term X^6 to q: " + q.toString());
        System.out.println("Degree of q and lc: " + q.getDegree() + ", " + q.getLC());


    }

    public static void main(String[] args){
        new Core();
    }
}
