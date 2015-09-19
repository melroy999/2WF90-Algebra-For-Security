package core;

import polynomial.EEA;
import polynomial.GCD;
import polynomial.Polynomial;

/**
 * Created by Melroy van Nijnatten - 0849740.
 */
public class Core {
    public Core() {
        Polynomial p = new Polynomial("x^4-9x^2-4x+12");
        Polynomial q = new Polynomial("x^3+5x^2+2x-8");
        EEA eea = p.extendedEuclidean(q);

        System.out.println(eea.x + ", " + eea.y);
        Polynomial r = eea.x.multiply(p).add(eea.y.multiply(q));
        System.out.println(r.toString());

        System.out.println(p.euclidean(q).toString());
    }

    public static void main(String[] args){
        new Core();
    }
}
