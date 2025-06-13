package userFacade;

import java.awt.*;
import java.util.function.Consumer;
import javax.swing.*;

public class GUIWindow {
    private final JTextArea outputArea;
    private final JTextField inputField;
    private Consumer<String> inputHandler;

    public GUIWindow () {
        JFrame frame = new JFrame("Smart Home Menu Interface");
        frame.setSize(700, 450);
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
                inputHandler.accept(input); // send input to app
            }
        });

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(inputField, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    public void print(String msg) {
        SwingUtilities.invokeLater(() -> {
            outputArea.append(msg + "\n");
            outputArea.setCaretPosition(outputArea.getDocument().getLength());
        });
    }

    public void clear() {
        SwingUtilities.invokeLater(() -> {
            outputArea.setText("");
        });
    }
    public void setInputHandler(Consumer<String> handler) {
        this.inputHandler = handler;
    }
}
