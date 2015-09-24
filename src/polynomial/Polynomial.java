package polynomial;

import java.util.TreeMap;

/**
 * Created by Melroy van Nijnatten - 0849740.
 */
public class Polynomial {
    private final int modulus;
    private TreeMap<Integer, Integer> terms = new TreeMap<Integer, Integer>();

    public Polynomial(int modulus) {
        this.modulus = modulus;
    }
}
