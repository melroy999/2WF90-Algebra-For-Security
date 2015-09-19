package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.Timeout;
import polynomial.LCD;
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
        assertEquals("The string representation is not what we expected.", expected, p.toString());
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
        assertEquals("The multiplied polynomial is not correct.", q.toString(), p.multiply(multiplier).toString());
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
        assertEquals("The degree is not equal to the expected degree.", expected, p.getDegree());
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
        assertEquals("The lc is not equal to the expected lc.", expected, p.getLC());
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

    public void checkAdd(Polynomial p, Polynomial q, String expected){
        Polynomial r = p.add(q);
        assertEquals("The result is not as expected. ", expected, r.toString());
    }

    @Test
    public void testAdd1(){
        Polynomial p = new Polynomial("1");
        Polynomial q = new Polynomial("0");
        checkAdd(p, q, "1");
    }

    @Test
    public void testAdd2(){
        Polynomial p = new Polynomial("1");
        Polynomial q = new Polynomial("1");
        checkAdd(p, q, "2");
    }

    @Test
    public void testAdd3(){
        Polynomial p = new Polynomial("1");
        Polynomial q = new Polynomial("-1");
        checkAdd(p, q, "0");
    }

    @Test
    public void testAdd4(){
        Polynomial p = new Polynomial("-1");
        Polynomial q = new Polynomial("1");
        checkAdd(p, q, "0");
    }

    @Test
    public void testAdd5(){
        Polynomial p = new Polynomial("-1");
        Polynomial q = new Polynomial("-1");
        checkAdd(p, q, "-2");
    }

    @Test
    public void testAdd6(){
        Polynomial p = new Polynomial("X");
        Polynomial q = new Polynomial("X");
        checkAdd(p, q, "2X");
    }

    @Test
    public void testAdd7(){
        Polynomial p = new Polynomial("X");
        Polynomial q = new Polynomial("-X");
        checkAdd(p, q, "0");
    }

    @Test
    public void testAdd8(){
        Polynomial p = new Polynomial("-X");
        Polynomial q = new Polynomial("-X");
        checkAdd(p, q, "-2X");
    }

    @Test
    public void testAdd9(){
        Polynomial p = new Polynomial("-X");
        Polynomial q = new Polynomial("X");
        checkAdd(p, q, "0");
    }

    @Test
    public void testAdd10(){
        Polynomial p = new Polynomial("X^2");
        Polynomial q = new Polynomial("X^2");
        checkAdd(p, q, "2X^2");
    }

    @Test
    public void testAdd11(){
        Polynomial p = new Polynomial("X^2");
        Polynomial q = new Polynomial("-X^2");
        checkAdd(p, q, "0");
    }

    @Test
    public void testAdd12(){
        Polynomial p = new Polynomial("-X^2");
        Polynomial q = new Polynomial("-X^2");
        checkAdd(p, q, "-2X^2");
    }

    @Test
    public void testAdd13(){
        Polynomial p = new Polynomial("-X^2");
        Polynomial q = new Polynomial("X^2");
        checkAdd(p, q, "0");
    }

    @Test
    public void testAdd14(){
        Polynomial p = new Polynomial("-X^2 + 9X^3 - 2");
        Polynomial q = new Polynomial("X^4 + 8X^3 + 9");
        checkAdd(p, q, "X^4 + 17X^3 - X^2 + 7");
    }
    
    public void checkSubtract(Polynomial p, Polynomial q, String expected){
        Polynomial r = p.subtract(q);
        assertEquals("The result is not as expected. ", expected, r.toString());
    }

    @Test
    public void testSubtract1(){
        Polynomial p = new Polynomial("1");
        Polynomial q = new Polynomial("0");
        checkSubtract(p, q, "1");
    }

    @Test
    public void testSubtract2(){
        Polynomial p = new Polynomial("1");
        Polynomial q = new Polynomial("1");
        checkSubtract(p, q, "0");
    }

    @Test
    public void testSubtract3(){
        Polynomial p = new Polynomial("1");
        Polynomial q = new Polynomial("-1");
        checkSubtract(p, q, "2");
    }

    @Test
    public void testSubtract4(){
        Polynomial p = new Polynomial("-1");
        Polynomial q = new Polynomial("1");
        checkSubtract(p, q, "-2");
    }

    @Test
    public void testSubtract5(){
        Polynomial p = new Polynomial("-1");
        Polynomial q = new Polynomial("-1");
        checkSubtract(p, q, "0");
    }

    @Test
    public void testSubtract6(){
        Polynomial p = new Polynomial("X");
        Polynomial q = new Polynomial("X");
        checkSubtract(p, q, "0");
    }

    @Test
    public void testSubtract7(){
        Polynomial p = new Polynomial("X");
        Polynomial q = new Polynomial("-X");
        checkSubtract(p, q, "2X");
    }

    @Test
    public void testSubtract8(){
        Polynomial p = new Polynomial("-X");
        Polynomial q = new Polynomial("-X");
        checkSubtract(p, q, "0");
    }

    @Test
    public void testSubtract9(){
        Polynomial p = new Polynomial("-X");
        Polynomial q = new Polynomial("X");
        checkSubtract(p, q, "-2X");
    }

    @Test
    public void testSubtract10(){
        Polynomial p = new Polynomial("X^2");
        Polynomial q = new Polynomial("X^2");
        checkSubtract(p, q, "0");
    }

    @Test
    public void testSubtract11(){
        Polynomial p = new Polynomial("X^2");
        Polynomial q = new Polynomial("-X^2");
        checkSubtract(p, q, "2X^2");
    }

    @Test
    public void testSubtract12(){
        Polynomial p = new Polynomial("-X^2");
        Polynomial q = new Polynomial("-X^2");
        checkSubtract(p, q, "0");
    }

    @Test
    public void testSubtract13(){
        Polynomial p = new Polynomial("-X^2");
        Polynomial q = new Polynomial("X^2");
        checkSubtract(p, q, "-2X^2");
    }

    @Test
    public void testSubtract14(){
        Polynomial p = new Polynomial("-X^2 + 9X^3 - 2");
        Polynomial q = new Polynomial("X^4 + 8X^3 + 9");
        checkSubtract(p, q, "-X^4 + X^3 - X^2 - 11");
    }

    public void checkDivide(Polynomial p, Polynomial q, String expected_quotient, String expected_remainder){
        LCD r = p.divide(q);
        assertEquals("The quotient is not as expected. ", expected_quotient, r.quotient.toString());
        assertEquals("The remainder is not as expected. ", expected_remainder, r.remainder.toString());
    }

    @Test(timeout = 100)
    public void testDivide1(){
        Polynomial p = new Polynomial("X^4 + 2X^3 + 3X^2 + 2X + 1");
        Polynomial q = new Polynomial("X^2 - 1");
        checkDivide(p, q, "X^2 + 2X + 4", "4X + 5");
    }

    public void checkMultiply(Polynomial p, Polynomial q, String expected){
        Polynomial r = p.multiply(q);
        assertEquals("The result is not as expected. ", expected, r.toString());
    }
}