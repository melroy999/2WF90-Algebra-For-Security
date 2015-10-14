package gui;

import java.awt.*;

/**
 * Created by Melroy van Nijnatten - 0849740.
 */
public class AWT extends AWTEvent {
    public static final int EVENT_ID = AWTEvent.RESERVED_ID_MAX + 1;
    private final String target;
    private final String message;
    private final boolean isError;

    public AWT(Object source, String target, String message, boolean isError) {
        super(source, EVENT_ID);
        this.target = target;
        this.message = message;
        this.isError = isError;
    }

    public AWT(Object source, String target, String message) {
        this(source, target, message, false);
    }

    public String getTarget() {
        return target;
    }

    public String getMessage() {
        return message;
    }

    public boolean isError() {
        return isError;
    }
}
