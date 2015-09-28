package polynomial;

import java.util.List;

/**
 * Created by Melroy van Nijnatten - 0849740.
 */
public class Arithmetic {
    /*
    TODO:
    - sum p1 + p2
    - scalar multiple of p1
    - difference of p1 and p2
    - product of p1 and p2
    - quotient and remainder (long division) upon input of p1 and p2
    - (extended) euclidean algorithm for p1 and p2
    - decide whether p1 and p2 mod p are equal modulo p3.
     */

    public static Polynomial sum(Polynomial p1, Polynomial p2) {
        Polynomial result = new Polynomial(p1.getModulus());
        for (Integer i : p1.keySet()) {
            result.addTerm(p1.getCoefficient(i), i);
        }
        for (Integer i : p2.keySet()) {
            result.addTerm(p2.getCoefficient(i), i);
        }
        return result;
    }

    public static Polynomial scalar(Polynomial p1, int scalar) {
        Polynomial result = new Polynomial(p1.getModulus());
        for (int i : p1.keySet()) {
            result.addTerm(scalar * p1.getCoefficient(i), i);
        }
        return result;
    }

    public static Polynomial scalarDivision(Polynomial p1, int scalar) {
        Polynomial result = new Polynomial(p1.getModulus());
        for (int i : p1.keySet()) {
            result.addTerm(p1.getCoefficient(i) / scalar, i);
        }
        return result;
    }

    public static Polynomial difference(Polynomial p1, Polynomial p2) {
        Polynomial sub = scalar(p2, -1);
        return sum(p1, sub);
    }

    public static Polynomial product(Polynomial p1, Polynomial p2) {
        Polynomial result = new Polynomial(p1.getModulus());

        for (int p_d : p1.keySet()) {
            for (int q_d : p2.keySet()) {
                result.addTerm(p1.getCoefficient(p_d) * p2.getCoefficient(q_d), p_d + q_d);
            }
        }

        return result;
    }

    public static Polynomial[] longDivision(Polynomial p1, Polynomial p2) {
        Polynomial q = new Polynomial(p1.getModulus(), "0");
        Polynomial r = p1.clone();
        int i = 0;
        while (r.degree() >= p2.degree()) {
            System.out.println("q = " + q.toString() + ", r = " + r.toString());
            Polynomial toAdd = new Polynomial(p1.getModulus());
            System.out.println(r.getLeadingCoefficient() + ", " + p2.getLeadingCoefficient() + ", " + modularDivision(r.getLeadingCoefficient(), p2.getLeadingCoefficient(), p1.getModulus()));
            toAdd.addTerm(modularDivision(r.getLeadingCoefficient(), p2.getLeadingCoefficient(), p1.getModulus()), r.degree() - p2.degree());

            q = Arithmetic.sum(q, toAdd);
            r = Arithmetic.difference(r, Arithmetic.product(toAdd, p2));

            if(r.toString().equals("0")){
                break;
            }
            //debug
            i++;
            if(i > 10){
                throw new Error();
            }
        }
        return new Polynomial[]{q, r};
    }

    public static Polynomial[] extendedEuclideanAlgorithm(Polynomial p1, Polynomial p2) {
        Polynomial a = p1.clone();
        Polynomial b = p2.clone();
        Polynomial x = new Polynomial(p1.getModulus(), "1");
        Polynomial v = new Polynomial(p1.getModulus(), "1");
        Polynomial y = new Polynomial(p1.getModulus(), "0");
        Polynomial u = new Polynomial(p1.getModulus(), "0");
        while (!b.toString().equals("0")) {
            System.out.println(a + ", " + b + ", " + x + ", " + y + ", " + v + ", " + u);
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
        Polynomial result = Arithmetic.sum(Arithmetic.product(x, p1), Arithmetic.product(y, p2));
        /*int divide = Arithmetic.gcd_list(result.getAllCoefficients());
        if (divide != 1) {
            result = Arithmetic.scalarDivision(result, divide);
        }*/
        return new Polynomial[]{x, y, result, new Polynomial(Integer.MAX_VALUE, "" + 1)};
    }

    public static Object[] equalModuloPolynomial(Polynomial p1, Polynomial p2, Polynomial p3) {
        Polynomial p1mod = longDivision(p1, p3)[1];
        Polynomial p2mod = longDivision(p2, p3)[1];
        return new Object[]{p1mod, p2mod, p1mod.isEqual(p2mod)};
    }

    private static int modularDivision(int p, final int q, int modulus) {
        int[] gcd = gcd(q, modulus, modulus);
        int inverse = gcd[1];
        return p * inverse;
    }

    private static int gcd_list(List<Integer> y) {
        if (y.size() == 1) {
            return y.get(0);
        }
        if (y.size() == 2) {
            return gcd(y.get(0), y.get(1));
        }

        int h = y.size() / 2;
        return gcd(gcd_list(y.subList(0, h)), gcd_list(y.subList(h + 1, y.size())));
    }

    public static int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }

    //extended euclidean for integers
    private static int[] gcdMagic(int p, int q, int modulus) {
        if (q == 0) {
            return new int[]{p, 1, 0};
        }

        int[] results = gcd(q, p % q, modulus);
        int d = results[0];
        int a = results[2];
        int b = results[1] - (p / q) * results[2];

        if (a < 0) {
            a += modulus;
        }

        return new int[]{d, a, b};
    }

    //extended euclidean for integers
    private static int[] gcd(int p, int q, int modulus) {
        if (q == 0) {
            return new int[]{p, 1, 0};
        }

        int[] results = gcd(q, p % q, modulus);
        int d = results[0];
        int a = results[2];
        int b = results[1] - (p / q) * results[2];

        if (a < 0) {
            a += modulus;
        }

        return new int[]{d, a, b};
    }

    //http://stackoverflow.com/questions/14650360/very-simple-prime-number-test-i-think-im-not-understanding-the-for-loop
    public static boolean isPrime(long n) {
        // fast even test.
        if (n > 2 && (n & 1) == 0)
            return false;
        // only odd factors need to be tested up to n^0.5
        for (int i = 3; i * i <= n; i += 2)
            if (n % i == 0)
                return false;
        return true;
    }
}
