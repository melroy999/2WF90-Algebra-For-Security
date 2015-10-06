package core;

import finiteField.FiniteField;
import gui.GUICore;
import polynomial.Polynomial;

import javax.swing.*;

/**
 * Created by Melroy van Nijnatten - 0849740.
 */
public class Core {
    public static GUICore guiCore;
    public Core() {
        JFrame frame = new JFrame("Assignment 2WF90 - October 2015 - Stefan Habets & Melroy van Nijnatten");
        guiCore = new GUICore();
        frame.setContentPane(guiCore.mainPane);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        frame.pack();
        frame.setVisible(true);

        System.out.println(Polynomial.getAllDegreePolynomials(2, 2));
        System.out.println(Polynomial.getAllDegreePolynomials(2, 3));
        System.out.println(Polynomial.getAllDegreePolynomials(3, 2));
        System.out.println(Polynomial.getAllDegreePolynomials(3, 3));
        System.out.println(Polynomial.getAllDegreePolynomials(4, 3));

        System.out.println(FiniteField.getEquivalenceClasses(new Polynomial(2, "X^2+X+1")));
        System.out.println(FiniteField.getEquivalenceClasses(new Polynomial(3, "X^2+X+1")));
        System.out.println(FiniteField.getEquivalenceClasses(new Polynomial(2, "X^4-X^2")));
        //start gui
        //GUICore.main(new String[]{});
        //new GUICore();
    }

    public static void main(String[] args){


        new Core();
    }
}
