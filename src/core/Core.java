package core;

import polynomial.Polynomial;
import polynomial.support.implementation.DoubleNumeric;
import polynomial.support.implementation.FloatNumeric;
import polynomial.support.implementation.IntegerNumeric;
import polynomial.support.implementation.ModNumeric;

/**
 * Created by Melroy van Nijnatten - 0849740.
 */
public class Core {
    public Core() {
        Polynomial<Integer> p = new Polynomial<Integer>(new ModNumeric(2), "3X^2");
        System.out.println(p.toString());
        System.out.println(p.add(p));
    }

    public static void main(String[] args){
        new Core();
    }
}
