package core;

import gui.GUICore;

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
        //start gui
        //GUICore.main(new String[]{});
        //new GUICore();
    }

    public static void main(String[] args){


        new Core();
    }
}
