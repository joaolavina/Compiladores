package com.pilador.view;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class EditorArea extends JPanel {

    private JScrollPane scrollPane;
    private JTextArea textArea;

    public EditorArea() {
        textArea = new JTextArea();
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        textArea.setBorder(new NumberedBorder());

        scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public String getEditorAreaText() {
        return textArea.getText();
    }

    public void setEditorAreaText(String text) {
        this.cleanEditorArea();
        textArea.insert(text, 0);
    }

    public void cleanEditorArea() {
        textArea.replaceRange("", 0, this.getEditorAreaText().length());
    }
}