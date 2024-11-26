package cookbook.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class StyleManager {
    // Színek
    public static final Color MAIN_BACKGROUND_COLOR = new Color(255, 248, 220);  // Bézs
    public static final Color PANEL_BACKGROUND_COLOR = new Color(245, 245, 220);  // Világosabb bézs
    public static final Color TEXT_COLOR = new Color(50, 50, 50);  // Sötét szürke
    public static final Color BORDER_COLOR = new Color(192, 192, 192);  // Világos szürke

    // Fontok
    public static final Font MAIN_FONT = new Font("Segoe UI", Font.PLAIN, 21);  // Modern betűtípus nagyobb méretben

    //TextField
    public static void styleTextField(JTextField textField) {
        textField.setFont(MAIN_FONT);
        textField.setBackground(PANEL_BACKGROUND_COLOR);
        textField.setForeground(TEXT_COLOR);
        textField.setHorizontalAlignment(JTextField.LEFT); // Balra igazítás
        textField.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
    }

    //TextArea stílusa
    public static void styleTextArea(JTextArea textArea) {
        textArea.setFont(MAIN_FONT);
        textArea.setBackground(PANEL_BACKGROUND_COLOR);
        textArea.setForeground(TEXT_COLOR);
        textArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR),
                new EmptyBorder(5, 25, 5, 5)
        ));
        textArea.setAlignmentX(JTextArea.LEFT_ALIGNMENT); // Balra igazítás
    }


    // Lista stílusa
    public static void styleList(JList list) {
        list.setFont(MAIN_FONT);
        list.setBackground(PANEL_BACKGROUND_COLOR);
        list.setForeground(TEXT_COLOR);
        list.setSelectionBackground(TEXT_COLOR);
        list.setSelectionForeground(MAIN_BACKGROUND_COLOR);
        list.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));
    }

    public static void styleButton(JButton button) {
        button.setFont(MAIN_FONT);
        button.setBackground(PANEL_BACKGROUND_COLOR);
        button.setForeground(TEXT_COLOR);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(10, 20, 10, 20));
    }

    public static void stylePanel(JPanel panel) {
        panel.setBackground(MAIN_BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));
    }

    public static void styleLabel(JLabel label) {
        label.setFont(new Font("Segoe UI", Font.BOLD, 21));
        label.setForeground(TEXT_COLOR);
    }

    public static void styleScrollPane(JScrollPane scrollPane) {
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
    }

    // Stílus beállítások a rádiógombokhoz
    public static void styleRadioButton(JRadioButton radioButton) {
        radioButton.setFont(MAIN_FONT);
        radioButton.setBackground(PANEL_BACKGROUND_COLOR);
        radioButton.setForeground(TEXT_COLOR);
        radioButton.setFocusPainted(false);
        radioButton.setBorder(new EmptyBorder(5, 5, 5, 5));
    }
}

