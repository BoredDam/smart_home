package userFacade;

import java.awt.*;
import java.util.function.Consumer;
import javax.swing.*;

/**
 * GUIWindow is a simple graphical user interface for the Smart Home Menu Interface.
 * The GUI is based on javax.swing library and separates the output of the devices (in the standard output <code>stdout</code>)
 * from the output of the user interface (in the <code>outputArea</code>).
 * The input is also processed only in the GUIWindow <code>inputField</code>.
 * 
 * @author Paolo Volpini
 * @author Damiano Trovato 
 */
public class GUIWindow {
    private final JTextArea outputArea;
    private final JTextField inputField;
    private Consumer<String> inputHandler;
    private final JFrame frame;

    /**
     * Constructor of the GUIWindow class.
     * The constructor first creates the frame, of size 750x450, then sets 
     * the layout, the output area and the input field.
     */
    public GUIWindow () {
        frame = new JFrame("Smart Home Menu Interface");
        frame.setSize(1000, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 13));

        JScrollPane scrollPane = new JScrollPane(outputArea);

        inputField = new JTextField();
        inputField.setFont(new Font("Monospaced", Font.PLAIN, 13));
        inputField.addActionListener(_ -> {
            String input = inputField.getText().trim();
            inputField.setText("");
            if (inputHandler != null) {
                inputHandler.accept(input); // send input to current handler
            }
        });

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(inputField, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    public int getWidth() {
        return frame.getWidth();
    }

    /**
     * Prints a message to the output area of the GUI.
     *
     * @param msg the message to print
     */
    public void print(String msg) {
        SwingUtilities.invokeLater(() -> {
            outputArea.append(msg + "\n");
            outputArea.setCaretPosition(outputArea.getDocument().getLength());
        });
    }

    /**
     * Clears the output area of the GUI.
     */
    public void clear() {
        SwingUtilities.invokeLater(() -> {
            outputArea.setText("");
        });
    }
    /**
     * Sets the input handler for the GUI.
     * The input handler in the context of the simulation represents the loop (aka the menu) chosen.
     *
     * @param handler the loop that handles the input
     */
    public void setInputHandler(Consumer<String> handler) {
        this.inputHandler = handler;
    }
}
