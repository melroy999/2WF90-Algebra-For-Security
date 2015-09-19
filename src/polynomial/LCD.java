package polynomial;

/**
 * Created by Melroy van Nijnatten - 0849740.
 */
public class LCD {
    public Polynomial quotient;
    public Polynomial remainder;

    public LCD(Polynomial quotient, Polynomial remainder) {
        this.quotient = quotient;
        this.remainder = remainder;
    }
}
