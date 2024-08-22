package com.pilador.controller;

import com.pilador.view.EditorArea;
import com.pilador.view.MessageArea;

public class KeyEventController {

    private MessageArea resultArea;
    private EditorArea editorArea;

    public KeyEventController(MessageArea resultArea, EditorArea editorArea) {
        this.resultArea = resultArea;
        this.editorArea = editorArea;
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
}