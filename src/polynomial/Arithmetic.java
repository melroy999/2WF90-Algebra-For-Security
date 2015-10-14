package polynomial;

import core.Core;

import java.math.BigInteger;
import java.util.Random;

/**
 * Created by Melroy van Nijnatten - 0849740.
 */
public class Arithmetic {
    /**
     * Gives the sum of two polynomials.
     *
     * @param p1: The base polynomial.
     * @param p2: The polynomial that is added to p1.
     * @return  A new polynomial that satisfies p1 + p2.
     */
    public static Polynomial sum(Polynomial p1, Polynomial p2) {
        //create an empty polynomial with the modulus of p1.
        Polynomial result = new Polynomial(p1.getModulus());

        //add all terms of the first polynomial to the result.
        for (Integer i : p1.keySet()) {
            result.addTerm(p1.getCoefficient(i), i);
        }
        //add all terms of the second polynomial to the result.
        for (Integer i : p2.keySet()) {
            result.addTerm(p2.getCoefficient(i), i);
        }
        return result;
    }

    /**
     * Multiplies the coefficients by a scalar.
     *
     * @param p1: The polynomial that contains the coefficients that have to be multiplied.
     * @param scalar: The integer value that is multiplied with.
     * @return  A nwe polynomial that satisfies scalar * p1.
     */
    public static Polynomial scalar(Polynomial p1, int scalar) {
        //create empty polynomial with the modulus of p1.
        Polynomial result = new Polynomial(p1.getModulus());

        //multiply all terms in p1 by the scalar.
        for (int i : p1.keySet()) {
            result.addTerm(scalar * p1.getCoefficient(i), i);
        }
        return result;
    }

    /**
     * Gives the difference between two polynomials.
     *
     * @param p1: The base polynomial.
     * @param p2: The polynomial that is subtracted.
     * @return  A new polynomial that satisfies p1 - p2.
     */
    public static Polynomial difference(Polynomial p1, Polynomial p2) {
        //multiply polynomial p2 by -1.
        Polynomial sub = scalar(p2, -1);

        //add p1 and the negated polynomial 2.
        return sum(p1, sub);
    }

    /**
     * Gives the product of two polynomials.
     *
     * @param p1: The base polynomial.
     * @param p2: The polynomial that is multiplied by.
     * @return  A new polynomial that satisfies p1 * p2;
     */
    public static Polynomial product(Polynomial p1, Polynomial p2) {
        //an empty polynomial with the modulus of p1.
        Polynomial result = new Polynomial(p1.getModulus());

        //multiply each term of each polynomial.
        for (int p_d : p1.keySet()) {
            for (int q_d : p2.keySet()) {
                result.addTerm(p1.getCoefficient(p_d) * p2.getCoefficient(q_d), p_d + q_d);
            }
        }

        return result;
    }

    /**
     * Gives the quotient and remainder of a division between two polynomials.
     * Source: Algorithm 1.2.09 in 'Algebra for Security' handout.
     *
     * @param p1: The base polynomial.
     * @param p2: The polynomial that is divided by.
     * @return  A new polynomial that satisfies p1 / p2 = q * p2 + r.
     */
    public static Polynomial[] longDivision(Polynomial p1, Polynomial p2) {
        //polynomial with the value 0.
        Polynomial q = new Polynomial(p1.getModulus(), "0");

        //clone of polynomial p1.
        Polynomial r = p1.clone();

        //to avoid infinite looping.
        while (r.degree() >= p2.degree()) {
            //find the term that should be added in this iteration.
            Polynomial toAdd = new Polynomial(p1.getModulus());
            toAdd.addTerm(r.getLeadingCoefficient() * modularDivision(p2.getLeadingCoefficient(), p1.getModulus()), r.degree() - p2.degree());

            //set the quotient and the remainder.
            q = Arithmetic.sum(q, toAdd);
            Polynomial subtract = Arithmetic.product(toAdd, p2);
            r = Arithmetic.difference(r, subtract);

            //stop the loop if r has become 0.
            if(r.toString().equals("0")){
                break;
            }
        }
        return new Polynomial[]{q, r};
    }

    /**
     * Returns the gcd, and a x and y pair.
     * Source: Algorithm 1.2.11 in 'Algebra for Security' handout.
     *
     * @param p1: The first polynomial.
     * @param p2: The second polynomial.
     * @return  gcd, x and y such that gcd(p1, p2) = x * p1 + y * p2
     */
    public static Polynomial[] extendedEuclideanAlgorithm(Polynomial p1, Polynomial p2) {
        //initialize the parameters.
        Polynomial a = p1.clone();
        Polynomial b = p2.clone();
        Polynomial x = new Polynomial(p1.getModulus(), "1");
        Polynomial v = new Polynomial(p1.getModulus(), "1");
        Polynomial y = new Polynomial(p1.getModulus(), "0");
        Polynomial u = new Polynomial(p1.getModulus(), "0");

        //while b is not zero.
        while (!b.toString().equals("0")) {
            Polynomial[] temp = longDivision(a, b);
            Polynomial q = temp[0].clone();
            a = b.clone();
            b = temp[1].clone();
            Polynomial x0 = x.clone();
            Polynomial y0 = y.clone();
            x = u.clone();
            y = v.clone();
            u = Arithmetic.difference(x0, Arithmetic.product(q, u));
            v = Arithmetic.difference(y0, Arithmetic.product(q, v));
        }

        //calculate the result by finding x * p1 + y * p2.
        Polynomial result = Arithmetic.sum(Arithmetic.product(x, p1), Arithmetic.product(y, p2));

        //make the leading coefficient 1.
        Core.printHandler.appendLog("pre:" + result.toString() + ", modular inverse: " + modularDivision(result.getLeadingCoefficient(), p1.getModulus()));
        result = Arithmetic.scalar(result, modularDivision(result.getLeadingCoefficient(), p1.getModulus()));
        Core.printHandler.appendLog("post:" + result.toString());

        if(result.getLeadingCoefficient() < 0){
            result = result.scalar(-1);
        }

        //set the terms.
        return new Polynomial[]{x, y, result};
    }

    /**
     * Check if two polynomials are equal if taken modulo another polynomial.
     *
     * @param p1: The first polynomial.
     * @param p2: The second polynomial.
     * @param p3: The polynomial that is used as modulo.
     * @return  if the remainders are equal
     */
    public static Object[] equalModuloPolynomial(Polynomial p1, Polynomial p2, Polynomial p3) {
        //find the remainders.
        Polynomial p1mod = longDivision(p1, p3)[1];
        Polynomial p2mod = longDivision(p2, p3)[1];

        //check if they are equal.
        return new Object[]{p1mod, p2mod, p1mod.isEqual(p2mod)};
    }

    /**
     * Returns the inverse of an element.
     *
     * @param a: The value that has to be inverted.
     * @param modulus: The modulus.
     * @return  b such that a * b = 1 (mod modulus).
     */
    private static int modularDivision(int a, final int modulus) {
        //cannot divide by 0.
        if (a == 0) {
            throw new IllegalArgumentException("The divisor may not be 0!");
        }
        if(a < 0){
            a += modulus;
        }
        //find the inverse.
        return getInverse(a, modulus);
    }

    /**
     * Finds the inverse by using the extended euclidean algorithm.
     *
     * @param a: First integer.
     * @param b: Second integer.
     * @return  Only the value of x. Not the gcd and y.
     */
    private static int getInverse(int a, int b) {
        int x_1 = 1;
        int y_2 = 1;
        int x_2 = 0;
        int y_1 = 0;

        //while b is greater than 0.
        while (b > 0) {
            int q = a / b;

            int temp = a - q * b;
            a = b;
            b = temp;

            temp = x_1 - q * x_2;
            x_1 = x_2;
            x_2 = temp;

            temp = y_1 - q * y_2;
            y_1 = y_2;
            y_2 = temp;
        }

        //check if a is negative.
        int x;
        if (a >= 0) {
            x = x_1;
        } else {
            x = -x_1;
        }

        return x;
    }

    public static boolean millerRabin(int n, int trials){
        int i = 0;

        int m = n - 1;
        int t = m;
        int s = 0;
        while(t % 2 == 0){
            s++;
            t /= 2;
        }

        Random r = new Random();

        while(i < trials){
            int a = 2 + r.nextInt() % (n - 1 - 2);//number from 2 to 2 + (n - 3) - 1
            int k = 0;
            int x = BigInteger.valueOf(a).modPow(BigInteger.valueOf(t), BigInteger.valueOf(n)).intValue();
            int z = 1;
            while(k < s && x % n != 1){
                k++;
                z = x;
                x = (x * x) % n;
            }
            if(k==0){
                i++;
            } else {
                if(k == s && x % n != 1){
                    return false;
                } else {
                    if(z % n == -1){
                        i++;
                    } else {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    //http://stackoverflow.com/questions/14650360/very-simple-prime-number-test-i-think-im-not-understanding-the-for-loop
    public static boolean isPrime(int n) {
        // fast even test.
        if (n > 2 && (n & 1) == 0)
            return false;
        // only odd factors need to be tested up to n^0.5
        for (int i = 3; i * i <= n; i += 2)
            if (n % i == 0)
                return false;
        return true;
        //return millerRabin(n, 8);
    }
}
