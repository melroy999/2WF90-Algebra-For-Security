package polynomial;

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
        while (r.degree() >= p2.degree()) {
            Polynomial toAdd = new Polynomial(p1.getModulus());
            toAdd.addTerm(modularDivision(r.getLeadingCoefficient(), p2.getLeadingCoefficient(), p1.getModulus()), r.degree() - p2.degree());

            q = Arithmetic.sum(q, toAdd);
            r = Arithmetic.difference(r, Arithmetic.product(toAdd, p2));
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
        while (!b.isEmpty()) {
            Polynomial[] temp = longDivision(a, b);
            Polynomial q = temp[0];
            a = b;
            b = temp[1];
            Polynomial x0 = x;
            Polynomial y0 = y;
            x = u;
            y = v;
            u = Arithmetic.difference(x0, Arithmetic.product(q, u));
            b = Arithmetic.difference(y0, Arithmetic.product(q, b));
        }
        return new Polynomial[]{x, y, Arithmetic.sum(Arithmetic.product(x, p1), Arithmetic.product(y, p2))};
        /*Input: polynomials a and b
        Output: polynomials x, y with gcd(a; b) = xa + yb
        Step 1: x   1, v   1, y   0, u   0
        Step 2: while b 6= 0 do
        q   quot(a; b),
        a   b,
        b   rem(a; b),
        x0   x,
        y0   y,
        x   u,
        y   v,
        u   x0 - qu,
        v   y0 - qv
        Step 3: output x, y*/
    }

    public static Object[] equalModuloPolynomial(Polynomial p1, Polynomial p2, Polynomial p3) {
        return new Object[]{};
    }

    private static int modularDivision(int p, int q, int modulus) {
        int[] gcd = gcd(q, modulus, modulus);
        int inverse = gcd[1];
        return p * inverse;
    }

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
}
