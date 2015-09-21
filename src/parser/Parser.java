package parser;

import polynomial.support.Numeric;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;
import java.util.TreeMap;

/**
 * Created by Melroy van Nijnatten - 0849740.
 */
public class Parser<T> {
    final Numeric<T> calc;

    public Parser(Numeric<T> calc) {
        this.calc = calc;
    }

    public TreeMap<Integer, T> parse(String rep){
        TreeMap<Integer, T> terms = new TreeMap<Integer, T>();
        Stack<Character> parser = new Stack<Character>();

        Iterator<Character> iterator = stringIterator(rep);

        while(iterator.hasNext()) {
            Character c = iterator.next();

            if(c == ')'){
                char next;
                do {
                    next = parser.pop();
                    //add to tree;
                } while (next != '(');
            } else {
                if(c != ' '){
                    parser.push(c);
                }
            }
        }

        return terms;
    }

    public static Iterator<Character> stringIterator(final String string) {
        if (string == null) {
            throw new NullPointerException();
        }

        return new Iterator<Character>() {
            int i = 0;

            @Override
            public boolean hasNext() {
                return i < string.length();
            }

            @Override
            public Character next() {
                if(!hasNext()){
                    throw new NoSuchElementException();
                }
                return string.charAt(i++);
            }
        };
    }

    /*parse    what to do?                Stack looks like
    (      push it onto the stack     (
    5      push 5                     (, 5
    +      push +                     (, 5, +
    2      push 2                     (, 5, +, 2
    )      evaluate until (           7
    *      push *                     7, *
    7      push 7                     +7, *, 7
    eof    evaluate until top         49
    */
}