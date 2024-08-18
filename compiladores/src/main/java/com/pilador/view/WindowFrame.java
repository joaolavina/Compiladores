package com.pilador.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.*;

public class WindowFrame extends JFrame {

    private ToolbarMenu toolbar;
    private MessageArea messageArea;

    public WindowFrame() {
        setTitle("Compilador");
        setSize(910, 600);
        setMinimumSize(new Dimension(910, 600));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BorderLayout());


        // Barra de ferramentas
        toolbar = new ToolbarMenu();
        add(toolbar, BorderLayout.NORTH);

        // Área de mensagem

    }

    public ToolbarMenu getToolbar() {
        return toolbar;
    }

    public void setToolbar(ToolbarMenu toolbar) {
        this.toolbar = toolbar;
    }

    public MessageArea getMessageArea() {
        return messageArea;
    }

    public void setMessageArea(MessageArea messageArea) {
        this.messageArea = messageArea;
    }
}
