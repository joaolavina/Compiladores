package com.pilador.controller;

import com.pilador.view.EditorArea;
import com.pilador.view.MessageArea;
import com.pilador.view.StatusBar;

public class KeyEventController {

    private MessageArea resultArea;
    private EditorArea editorArea;
    private StatusBar statusBar;

    public KeyEventController(MessageArea resultArea, EditorArea editorArea, StatusBar statusBar) {
        this.resultArea = resultArea;
        this.editorArea = editorArea;
        this.statusBar = statusBar;
    }

    public void compileProgram() {
        this.resultArea.setMessageAreaText("Compilação de programas ainda não foi implementada.");
    }

    public void showTeamInfo() {
        this.resultArea.setMessageAreaText("Equipe: Cristina, Daniel e João Gabriel.");
    }

    public void newFile() {
        this.editorArea.cleanEditorArea();
        this.resultArea.cleanMessageArea();
    }

    public void copy() {
        editorArea.getTextArea().copy();
    }

    public void paste() {
        editorArea.getTextArea().paste();
    }

    public void cut() {
        editorArea.getTextArea().cut();
    }
}