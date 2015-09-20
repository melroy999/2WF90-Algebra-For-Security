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
        return super.quotient(n1, n2) % modulus;
    }

    @Override
    public Integer remainder(Integer n1, Integer n2) {
        return super.quotient(n1, n2) % modulus;
    }

    @Override
    public Integer multiply(Integer n1, Integer n2) {
        return super.multiply(n1, n2) % modulus;
    }
}
