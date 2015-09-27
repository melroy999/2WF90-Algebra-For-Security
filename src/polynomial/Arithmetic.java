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
            System.out.println(r.degree() + ", " + p2.degree());
            Polynomial toAdd = new Polynomial(p1.getModulus());
            toAdd.addTerm(modularDivision(r.getLeadingCoefficient(), p2.getLeadingCoefficient(), p1.getModulus()), r.degree() - q.degree());

            q = q.sum(toAdd);
            r = r.difference(toAdd.product(p2));
        }
        return new Polynomial[]{q, r};
    }

    public static Polynomial[] extendedEuclideanAlgorithm(Polynomial p1, Polynomial p2) {
        return null;
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
