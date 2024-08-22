package com.pilador.view;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MessageArea extends JPanel {

    private JScrollPane scrollPane;
    private JTextArea messageArea;

    public String getMessageAreaText() {
        return messageArea.getText();
    }

    public void setMessageAreaText(String text) {
        this.cleanMessageArea();
        messageArea.insert(text, 0);
    }

    public void cleanMessageArea(){
        messageArea.replaceRange("", 0, this.getMessageAreaText().length());
    }

    public MessageArea() {
        messageArea = new JTextArea();
        messageArea.setEditable(false);
        messageArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        scrollPane = new JScrollPane(messageArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
    }

}
