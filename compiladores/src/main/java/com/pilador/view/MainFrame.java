package com.pilador.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;

import com.pilador.controller.KeyEventController;

public class MainFrame extends JFrame {

    private ToolbarMenu toolbar;
    private EditorArea editorArea;
    private MessageArea messageArea;
    private DividerPane divider;
    private StatusBar statusBar;

    public MainFrame() {
        setTitle("Compilador");
        setSize(910, 600);
        setBackground(new Color(112, 73, 166));
        setMinimumSize(new Dimension(910, 600));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Ãrea de mensagem
        messageArea = new MessageArea();
        add(messageArea, BorderLayout.SOUTH);

        // Ãrea de ediÃ§Ã£o de cÃ³digo
        editorArea = new EditorArea();
        add(editorArea, BorderLayout.CENTER);

        // Barra de status
        statusBar = new StatusBar();
        add(statusBar, BorderLayout.SOUTH);

        // Barra de ferramentas
        KeyEventController controller = new KeyEventController(messageArea, editorArea, statusBar);
        toolbar = new ToolbarMenu(controller);
        add(toolbar, BorderLayout.NORTH);

        // Divisor editor/mensagem
        divider = new DividerPane(editorArea, messageArea);
        add(divider, BorderLayout.CENTER);
    }

    public ToolbarMenu getToolbar() {
        return toolbar;
    }

    public MessageArea getMessageArea() {
        return messageArea;
    }

    public void setMessageArea(MessageArea messageArea) {
        this.messageArea = messageArea;
    }

    public EditorArea getEditorArea() {
        return editorArea;
    }

    public void setEditorArea(EditorArea editorArea) {
        this.editorArea = editorArea;
    }

    public StatusBar getStatusBar() {
        return statusBar;
    }

    public void setStatusBar(StatusBar statusBar) {
        this.statusBar = statusBar;
    }
}