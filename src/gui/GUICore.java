package gui;

import javax.swing.*;

/**
 * Created by Melroy van Nijnatten - 0849740.
 */
public class GUICore {
    private JPanel mainPane;
    private JTextField polynomial1;
    private JTextField polynomial2;
    private JTextPane resultPane;
    private JComboBox comboBox1;
    private JButton solve;
    private JTextField textField1;
    private JPanel parameters;
    private JButton swapButton;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Assignment 2WF90 - October 2015 - Stefan Habets & Melroy van Nijnatten");
        frame.setContentPane(new GUICore().mainPane);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.pack();
        frame.setVisible(true);

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
