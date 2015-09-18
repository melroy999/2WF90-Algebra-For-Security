package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import polynomial.Polynomial;

import java.util.TreeMap;

import static org.junit.Assert.*;

public class PolynomialTest {
    int seed = 129112;
    TreeMap<Integer, Integer> tree;
    TreeMap<Integer, Integer> expected;

    @Before
    public void setUp() {
        tree = new TreeMap<Integer, Integer>();
        expected = new TreeMap<Integer, Integer>();
    }

    @After
    public void tearDown() {

    }

    private void checkMakePoly(String input, String expected){
        Polynomial p = new Polynomial(input);
        assertEquals("The string representation is not what we expected.", p.toString(), expected);
    }

    /**
     * most simple polynomial.
     */
    @Test
    public void testMakePoly1(){
        checkMakePoly("0", "0");
    }

    /**
     * inserted negative zero, should be zero.
     */
    @Test
    public void testMakePoly2(){
        checkMakePoly("-0", "0");
    }

    /**
     * adding two of the same terms but with different signs should yield zero.
     */
    @Test
    public void testMakePoly3(){
        checkMakePoly("-X^2+X^2", "0");
    }

    /**
     * adding two of the same terms should add them.
     */
    @Test
    public void testMakePoly4(){
        checkMakePoly("X^2 + X^2", "2X^2");
    }

    /**
     * polynomial with unsorted terms.
     */
    @Test
    public void testMakePoly5(){
        checkMakePoly("-10 + 23X^123 - 232X^18", "23X^123 - 232X^18 - 10");
    }

    /**
     * check if first term negative has no space.
     */
    @Test
    public void testMakePoly6(){
        checkMakePoly("-10 - 23X^123 - 232X^18", "-23X^123 - 232X^18 - 10");
    }

    /**
     * check if we can also handle different symbols.
     */
    @Test
    public void testMakePoly7(){
        checkMakePoly("-10 - 23x^123 - 232x^18", "-23X^123 - 232X^18 - 10");
    }

    /**
     * check if we can also handle different symbols.
     */
    @Test
    public void testMakePoly8(){
        checkMakePoly("-10 - 23a^123 - 232a^18", "-23X^123 - 232X^18 - 10");
    }

    /**
     * check if we can also handle different symbols.
     */
    @Test
    public void testMakePoly9(){
        checkMakePoly("-10 - 23a^123 - 232b^18", "-23X^123 - 232X^18 - 10");
    }

    @Test
    public void testGetCoefficient() {

    }

    @Test
    public void testAddTerm() {

    }

    @Test
    public void testIterator() {

    }

    @Test
    public void testInverseIterator() {
        
    }

    private void checkTestMultiply(TreeMap<Integer, Integer> input, int multiplier, TreeMap<Integer, Integer> expected){
        Polynomial p = new Polynomial(input);
        Polynomial q = new Polynomial(expected);
        assertEquals("The multiplied polynomial is not correct.", p.multiply(multiplier).toString(), q.toString());
    }

    @Test
    public void testMultiplyDegreeZeroPos() {
        tree.put(0, 1);
        expected.put(0, 3);
        checkTestMultiply(tree, 3, expected);
    }

    @Test
    public void testMultiplyDegreeZeroNeg1() {
        tree.put(0, -1);
        expected.put(0, -3);
        checkTestMultiply(tree, 3, expected);
    }

    @Test
    public void testMultiplyDegreeZeroNegMult() {
        tree.put(0, 1);
        expected.put(0, -3);
        checkTestMultiply(tree, -3, expected);
    }

    @Test
    public void testMultiplyMultipleTermsNegMult() {
        tree.put(0, 1);
        tree.put(4, -12);
        tree.put(2, 3);
        expected.put(0, -3);
        expected.put(4, 36);
        expected.put(2, -9);
        checkTestMultiply(tree, -3, expected);
    }

    @Test
    public void testMultiplyMultipleTermsPosMult() {
        tree.put(0, 1);
        tree.put(4, -12);
        tree.put(2, 3);
        expected.put(0, 3);
        expected.put(4, -36);
        expected.put(2, 9);
        checkTestMultiply(tree, 3, expected);
    }

    private void checkGetDegree(TreeMap<Integer, Integer> tree, int expected) {
        Polynomial p = new Polynomial(tree);
        assertEquals("The degree is not equal to the expected degree.", p.getDegree(), expected);
    }

    /**
     * Degree 0, positive coefficient
     */
    @Test
    public void testGetDegreeZeroPos() {
        tree.put(0, 1);
        checkGetDegree(tree, 0);
    }

    /**
     * Degree 0, negative coefficient
     */
    @Test
    public void testGetDegreeZeroNeg() {
        tree.put(0, -1);
        checkGetDegree(tree, 0);
    }

    /**
     * Degree 1, one element
     */
    @Test
    public void testGetDegreeOne1() {
        tree.put(1, 1);
        checkGetDegree(tree, 1);
    }

    /**
     * Degree 1, includes zero element, Positive coefficient
     */
    @Test
    public void testGetDegreeOnePos2() {
        tree.put(1, 1);
        tree.put(0, 1);
        checkGetDegree(tree, 1);
    }

    /**
     * Degree 1, includes zero element, negative coefficient
     */
    @Test
    public void testGetDegreeOneNeg2() {
        tree.put(1, -1);
        tree.put(0, 1);
        checkGetDegree(tree, 1);
    }

    /**
     * Degree is max integer.
     */
    @Test
    public void testGetDegreeLarge1() {
        tree.put(Integer.MAX_VALUE, 1);
        checkGetDegree(tree, Integer.MAX_VALUE);
    }

    /**
     * Degree is max integer, multiple terms.
     */
    @Test
    public void testGetDegreeLarge2() {
        tree.put(Integer.MAX_VALUE, 1);
        tree.put(10, -15);
        tree.put(3, 2);
        tree.put(524, 213);
        checkGetDegree(tree, Integer.MAX_VALUE);
    }

    public void checkGetLC(TreeMap<Integer, Integer> tree, int expected) {
        Polynomial p = new Polynomial(tree);
        assertEquals("The lc is not equal to the expected lc.", p.getLC(), expected);
    }
    
    /**
     * Degree 0, positive coefficient
     */
    @Test
    public void testLCZeroPos() {
        tree.put(0, 1);
        checkGetLC(tree, 1);
    }

    /**
     * Degree 0, negative coefficient
     */
    @Test
    public void testLCZeroNeg() {
        tree.put(0, -1);
        checkGetLC(tree, -1);
    }

    /**
     * Degree 1, one element
     */
    @Test
    public void testLCOne1() {
        tree.put(1, 1);
        checkGetLC(tree, 1);
    }

    /**
     * Degree 1, includes zero element, Positive coefficient
     */
    @Test
    public void testLCOnePos2() {
        tree.put(1, 3);
        tree.put(0, 1);
        checkGetLC(tree, 3);
    }

    /**
     * Degree 1, includes zero element, negative coefficient
     */
    @Test
    public void testLCOneNeg2() {
        tree.put(1, -1);
        tree.put(0, 1);
        checkGetLC(tree, -1);
    }

    /**
     * Degree is max integer.
     */
    @Test
    public void testLCLarge1() {
        tree.put(Integer.MAX_VALUE, Integer.MAX_VALUE);
        checkGetLC(tree, Integer.MAX_VALUE);
    }

    /**
     * Degree is max integer, multiple terms.
     */
    @Test
    public void testLCLarge2() {
        tree.put(Integer.MAX_VALUE, 125);
        tree.put(10, -15);
        tree.put(3, 2);
        tree.put(524, 213);
        checkGetLC(tree, 125);
    }   
}