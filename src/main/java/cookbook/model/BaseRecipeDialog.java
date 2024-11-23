package cookbook.model;

import cookbook.view.StyleManager;

import javax.swing.*;
import java.awt.*;

public class BaseRecipeDialog extends JDialog {
    protected JTextField nameField;
    protected JTextField timeField;
    protected JTextArea ingredientsArea;
    protected JTextArea descriptionArea;

    public BaseRecipeDialog(Frame owner, String title) {
        super(owner, title, true);
        setSize(800, 600);
        setLocationRelativeTo(owner);
    }

    protected JPanel createEditableField(String label, String value, boolean isSingleLine, boolean editable) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(StyleManager.MAIN_BACKGROUND_COLOR);

        JLabel jLabel = new JLabel(label);
        StyleManager.styleLabel(jLabel);
        panel.add(jLabel, BorderLayout.NORTH);

        if (isSingleLine) {
            JTextField textField = new JTextField(value);
            textField.setEditable(editable);
            StyleManager.styleTextField(textField);
            textField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(StyleManager.BORDER_COLOR, 1),
                    BorderFactory.createEmptyBorder(15, 25, 15, 25)
            ));
            if (label.startsWith("Elkészítési idő")) {
                timeField = textField;
            } else {
                nameField = textField;
            }
            panel.add(textField, BorderLayout.CENTER);
        } else {
            JTextArea textArea = new JTextArea(value);
            textArea.setEditable(editable);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            StyleManager.styleTextArea(textArea);
            textArea.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));

            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(StyleManager.BORDER_COLOR, 1),
                    BorderFactory.createEmptyBorder(20, 20, 20, 20)
            ));
            StyleManager.styleScrollPane(scrollPane);

            if (label.startsWith("Hozzávalók")) {
                ingredientsArea = textArea;
            } else {
                descriptionArea = textArea;
            }

            panel.add(scrollPane, BorderLayout.CENTER);
        }

        return panel;
    }
}
