package scenegraph;

import javax.swing.JOptionPane;

/**
 * A utility method to display an informational message box to the user.
 * 
 * 
 */
public class InfoBox {

    /**
     * Displays an informational message box with the specified message and title.
     * 
     * @param infoMessage The main text message to be displayed in the message box.
     * @param titleBar    The text to be displayed in the title bar of the message box.
     */
    public static void infoBox(String infoMessage, String titleBar) {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
}

