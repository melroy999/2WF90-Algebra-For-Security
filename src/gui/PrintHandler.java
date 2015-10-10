package gui;

import core.Core;

import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import java.awt.*;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * Created by Melroy van Nijnatten - 0849740.
 */
public class PrintHandler implements Thread.UncaughtExceptionHandler {
    public static StyleSheet resultStyle;
    //html print
    private static HTMLDocument resultDocP;
    private static HTMLEditorKit resultEditorKitP;
    private static HTMLDocument resultDocFF;
    private static HTMLEditorKit resultEditorKitFF;
    private static HTMLDocument logDoc;
    private static HTMLEditorKit logEditorKit;

    //format the time should be shown in.
    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    public PrintHandler(GUICore instance) {
        //gets everything needed to do html formatting correctly.
        resultDocP = (HTMLDocument) instance.resultPaneP.getDocument();
        resultEditorKitP = (HTMLEditorKit) instance.resultPaneP.getEditorKit();

        resultDocFF = (HTMLDocument) instance.resultPaneFF.getDocument();
        resultEditorKitFF = (HTMLEditorKit) instance.resultPaneFF.getEditorKit();

        logDoc = (HTMLDocument) instance.logPane.getDocument();
        logEditorKit = (HTMLEditorKit) instance.logPane.getEditorKit();

        resultStyle = resultEditorKitP.getStyleSheet();

        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 16);
        resultStyle.addRule("body { font-family: " + font.getFamily() + "; " + "font-size: " + font.getSize() + "pt;}");
        resultStyle.addRule("pre {margin: 0; padding: 0; font-family: " + font.getFamily() + "; " + "font-size: " + font.getSize() + "pt;}");
        resultStyle.addRule("th, td {text-align: center; border: 1px black solid;}");
        resultStyle.addRule("div {padding: 7px;}");
        resultStyle.addRule(".color {background-color: red;}");
        resultStyle.addRule("table {margin: 10px;}");
    }

    /**
     * Append one of the result panels.
     *
     * @param doc:     The html document.
     * @param kit:     the html editor kit.
     * @param message: the message that should be added.
     */
    public void appendDoc(HTMLDocument doc, HTMLEditorKit kit, String message) {
        //try to do it, if not working, print stacktrace.
        try {
            //at pre to conserve formatting.
            kit.insertHTML(doc, doc.getLength(), "<pre>" + message + "</pre>", 0, 0, null);
        } catch (BadLocationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add message to the result panel of the polynomial tab.
     *
     * @param message: the message that should be added.
     */
    public void appendResultP(String message) {
        appendDoc(resultDocP, resultEditorKitP, message);
    }

    /**
     * Add message to the result panel of the finite fields tab.
     *
     * @param message: the message that should be added.
     */
    public void appendResultFF(String message) {
        appendDoc(resultDocFF, resultEditorKitFF, message);
    }

    /**
     * Add message to the result panel of the finite fields tab, without using pre (for tables).
     *
     * @param message: the message that should be added.
     */
    public void appendResultNoPre(String message) {
        try {
            resultEditorKitFF.insertHTML(resultDocFF, resultDocFF.getLength(), message, 0, 0, null);
        } catch (BadLocationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add message to the text panel of the log.
     *
     * @param message: the message that should be added.
     * @param isError: if the input is an error or not. becomes red and has "ERROR" as prefix.
     */
    public void appendLog(String message, boolean isError) {
        message = message.replace("<", "&#60");
        message = message.replace(">", "&#62");
        if (isError) {
            message = "<span color=\"red\">[" + dateFormat.format(new java.util.Date()) + "] ERROR: " + message + "</span>";
        } else {
            message = "[" + dateFormat.format(new java.util.Date()) + "]: " + message;
        }

        appendDoc(logDoc, logEditorKit, message);
    }

    /**
     * Append the log with non error message.
     *
     * @param message: the message that should be added.
     */
    public void appendLog(String message) {
        appendLog(message, false);
    }

    /**
     * Reset the text of the polynomial result pane.
     */
    public void clearResultPaneP() {
        Core.guiCore.resultPaneP.setText("");
    }

    /**
     * Reset the text of the finite field result pane.
     */
    public void clearResultPaneFF() {
        Core.guiCore.resultPaneFF.setText(" ");
        Core.guiCore.resultPaneFF.setText("");
    }

    /**
     * Clear the log.
     */
    public void clearLogPane() {
        Core.guiCore.logPane.setText("");
    }

    /**
     * Method invoked when the given thread terminates due to the
     * given uncaught exception.
     * Any exception thrown by this method will be ignored by the
     * Java Virtual Machine.
     *
     * @param t the thread
     * @param e the exception
     */
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        //print the error, nothing special here.
        System.out.println("\u001B[31m" + e.toString() + "\u001B[0m");
        appendLog(e.toString(), true);
        for (StackTraceElement se : e.getStackTrace()) {
            System.out.println("\u001B[31m    at" + se.toString() + "\u001B[0m");
            appendLog("&nbsp;&nbsp;&nbsp;&nbsp;at " + se.toString(), true);
        }

        Throwable cause = e.getCause();
        while (true) {
            if (cause != null) {
                System.out.println("\u001B[31m" + cause.toString() + "\u001B[0m");
                appendLog(cause.toString(), true);
                for (StackTraceElement se : cause.getStackTrace()) {
                    System.out.println("\u001B[31m    at" + se.toString() + "\u001B[0m");
                    appendLog("&nbsp;&nbsp;&nbsp;&nbsp;at" + se.toString(), true);
                }
                cause = cause.getCause();
            } else {
                break;
            }
        }
    }
}
