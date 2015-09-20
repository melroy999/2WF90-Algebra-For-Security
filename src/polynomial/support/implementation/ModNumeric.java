package polynomial.support.implementation;

/**
 * Created by Melroy van Nijnatten - 0849740.
 */
public class ModNumeric extends IntegerNumeric {
    private final int modulus;
    public ModNumeric(int modulus) {
        this.modulus = modulus;
    }

    @Override
    public Integer add(Integer n1, Integer n2) {
        return super.add(n1, n2) % modulus;
    }

    @Override
    public Integer subtract(Integer n1, Integer n2) {
        return super.subtract(n1, n2) % modulus;
    }

    @Override
    public Integer quotient(Integer n1, Integer n2) {
        int[] gcd = gcd(n2, modulus);
        int inverse = gcd[1];
        return multiply(n1, inverse);
    }

    @Override
    public Integer remainder(Integer n1, Integer n2) {
        return 0;
    }

    @Override
    public Integer multiply(Integer n1, Integer n2) {
        return super.multiply(n1, n2) % modulus;
    }

    int[] gcd(int p, int q) {
        if (q == 0)
            return new int[] { p, 1, 0 };

        int[] vals = gcd(q, p % q);
        int d = vals[0];
        int a = vals[2];
        int b = vals[1] - (p / q) * vals[2];

        if(a < 0) {
            a += modulus;
        }

        return new int[] { d, a, b };
    }
}
