package core;

import gui.GUICore;
import gui.PrintHandler;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Melroy van Nijnatten - 0849740.
 */
public class Core {
    //TODO make finite field input robust...

    public static GUICore guiCore;
    public static PrintHandler printHandler;

    public Core() {
        //do some display settings.
        UIManager.put("TabbedPane.contentBorderInsets", new Insets(1, 0, 1, 0));
        UIManager.put("TabbedPane.selected", Color.white);
        UIManager.put("TabbedPane.focus", Color.white);

        //create the jframe.
        JFrame frame = new JFrame("Assignment 2WF90 - October 2015 - Stefan Habets & Melroy van Nijnatten");
        guiCore = new GUICore();

        //register the printHandler.
        printHandler = new PrintHandler(guiCore);
        Thread.setDefaultUncaughtExceptionHandler(Core.printHandler);

        //set the content of the frame.
        frame.setContentPane(guiCore.mainPane);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        //display the frame
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new Core();
    }
}
