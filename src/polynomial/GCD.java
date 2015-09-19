package polynomial;

/**
 * Created by Melroy van Nijnatten - 0849740.
 */
public class GCD {
    public Polynomial quotient;
    public Polynomial remainder;

    public GCD(Polynomial quotient, Polynomial remainder) {
        this.quotient = quotient;
        this.remainder = remainder;
    }
}
