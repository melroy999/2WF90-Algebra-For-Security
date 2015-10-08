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
    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    public PrintHandler(GUICore instance) {
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

    public void appendDoc(HTMLDocument doc, HTMLEditorKit kit, String message) {
        try {
            kit.insertHTML(doc, doc.getLength(), "<pre>" + message + "</pre>", 0, 0, null);
        } catch (BadLocationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void appendResultP(String message) {
        appendDoc(resultDocP, resultEditorKitP, message);
        //Core.guiCore.resultPaneP.setCaretPosition(Core.guiCore.resultPaneP.getText().length() - 1);
    }

    public void appendResultFF(String message) {
        appendDoc(resultDocFF, resultEditorKitFF, message);
        //Core.guiCore.resultPaneFF.setCaretPosition(Core.guiCore.resultPaneFF.getText().length() - 1);
    }

    public void appendResultNoPre(String message) {
        try {
            resultEditorKitFF.insertHTML(resultDocFF, resultDocFF.getLength(), message, 0, 0, null);
        } catch (BadLocationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Core.guiCore.resultPaneFF.setCaretPosition(Core.guiCore.resultPaneFF.getText().length() - 1);
    }

    public void appendLog(String message, boolean isError) {
        message = message.replace("<", "&#60");
        message = message.replace(">", "&#62");
        if (isError) {
            message = "<span color=\"red\">[" + dateFormat.format(new java.util.Date()) + "] ERROR: " + message + "</span>";
        } else {
            message = "[" + dateFormat.format(new java.util.Date()) + "]: " + message;
        }

        appendDoc(logDoc, logEditorKit, message);
        //Core.guiCore.logPane.setCaretPosition(Core.guiCore.logPane.getText().length() - 1);
    }

    public void appendLog(String message) {
        appendLog(message, false);
    }

    public void clearResultPaneP() {
        Core.guiCore.resultPaneP.setText("");
    }

    public void clearResultPaneFF() {
        Core.guiCore.resultPaneFF.setText(" ");
        Core.guiCore.resultPaneFF.setText("");
    }

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
