package gui;

import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * Created by Melroy van Nijnatten - 0849740.
 */
public class PrintHandler implements Thread.UncaughtExceptionHandler {
    //html print
    HTMLDocument resultDoc;
    HTMLEditorKit resultEditorKit;
    HTMLDocument logDoc;
    HTMLEditorKit logEditorKit;
    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private GUICore instance;

    public PrintHandler(GUICore instance) {
        this.instance = instance;

        resultDoc = (HTMLDocument) instance.resultPane.getDocument();
        resultEditorKit = (HTMLEditorKit) instance.resultPane.getEditorKit();

        logDoc = (HTMLDocument) instance.logPane.getDocument();
        logEditorKit = (HTMLEditorKit) instance.logPane.getEditorKit();
    }

    public void appendDoc(HTMLDocument doc, HTMLEditorKit kit, String message) {
        try {
            kit.insertHTML(doc, doc.getLength(), message, 0, 0, null);
        } catch (BadLocationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void appendResult(String message) {
        appendDoc(resultDoc, resultEditorKit, message);
    }

    public void appendLog(String message, boolean isError) {
        message = message.replace("<", "&#60");
        message = message.replace(">", "&#62");
        message = "[" + dateFormat.format(new java.util.Date()) + "]: " + message;
        if (isError) {
            message = "<span color=\"red\"> ERROR: " + message + "</span>";
        }
        appendDoc(logDoc, logEditorKit, message);
    }

    public void appendLog(String message) {
        appendLog(message, false);
    }

    /**
     * Method invoked when the given thread terminates due to the
     * given uncaught exception.
     * <p>Any exception thrown by this method will be ignored by the
     * Java Virtual Machine.
     *
     * @param t the thread
     * @param e the exception
     */
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        appendLog(e.getMessage(), true);
        for (StackTraceElement se : e.getStackTrace()) {
            appendLog(se.toString(), true);
        }
    }
}
