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
        return null;
    }

    public static Polynomial[] extendedEuclideanAlgorithm(Polynomial p1, Polynomial p2) {
        return null;
    }

    public static Object[] equalModuloPolynomial(Polynomial p1, Polynomial p2, Polynomial p3) {
        return new Object[]{};
    }
}
