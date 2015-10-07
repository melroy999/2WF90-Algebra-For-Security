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
    //html print
    private static HTMLDocument resultDoc;
    private static HTMLEditorKit resultEditorKit;
    private static HTMLDocument logDoc;
    private static HTMLEditorKit logEditorKit;
    private static StyleSheet resultStyle;
    private static StyleSheet logStyle;

    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    public PrintHandler(GUICore instance) {
        resultDoc = (HTMLDocument) instance.resultPane.getDocument();
        resultEditorKit = (HTMLEditorKit) instance.resultPane.getEditorKit();

        logDoc = (HTMLDocument) instance.logPane.getDocument();
        logEditorKit = (HTMLEditorKit) instance.logPane.getEditorKit();

        resultStyle = resultEditorKit.getStyleSheet();

        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 16);
        resultStyle.addRule("body { font-family: " + font.getFamily() + "; " + "font-size: " + font.getSize() + "pt;}");
        resultStyle.addRule("pre {margin: 0; padding: 0; font-family: \" + font.getFamily() + \"; \" + \"font-size: \" + font.getSize() + \"pt;}");
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

    public void appendResult(String message) {
        message = message.replace("<", "&#60");
        message = message.replace(">", "&#62");
        appendDoc(resultDoc, resultEditorKit, message);
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
    }

    public void appendLog(String message) {
        appendLog(message, false);
    }

    public void clearResultPane() {
        Core.guiCore.resultPane.setText("");
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
