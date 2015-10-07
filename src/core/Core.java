package core;

import finiteField.FiniteField;
import gui.GUICore;
import gui.PrintHandler;
import polynomial.Polynomial;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

/**
 * Created by Melroy van Nijnatten - 0849740.
 */
public class Core {
    public static GUICore guiCore;
    public static PrintHandler printHandler;

    public Core() {
        UIManager.put("TabbedPane.contentBorderInsets", new Insets(1, 0, 1, 0));
        UIManager.put("TabbedPane.selected", Color.white);
        UIManager.put("TabbedPane.focus", Color.white);

        JFrame frame = new JFrame("Assignment 2WF90 - October 2015 - Stefan Habets & Melroy van Nijnatten");
        guiCore = new GUICore();

        printHandler = new PrintHandler(guiCore);
        Thread.setDefaultUncaughtExceptionHandler(Core.printHandler);

        frame.setContentPane(guiCore.mainPane);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        frame.pack();
        frame.setVisible(true);

        System.out.println(Arrays.toString(FiniteField.getEquivalenceClasses(new Polynomial(2, "X^2+X+1"))));
        System.out.println(Arrays.toString(FiniteField.getEquivalenceClasses(new Polynomial(3, "X^2+X+1"))));
        System.out.println(Arrays.toString(FiniteField.getEquivalenceClasses(new Polynomial(3, "X^4+X^2"))));
        System.out.println(Arrays.toString(FiniteField.getEquivalenceClasses(new Polynomial(3, "X^2+1"))));

        int i = 1 / 0;
        //start gui
        //GUICore.main(new String[]{});
        //new GUICore();
    }

    public static void main(String[] args){


        new Core();
    }
}
