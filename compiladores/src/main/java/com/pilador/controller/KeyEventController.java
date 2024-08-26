package com.pilador.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.pilador.view.EditorArea;
import com.pilador.view.MessageArea;
import com.pilador.view.StatusBar;

public class KeyEventController {

    private MessageArea resultArea;
    private EditorArea editorArea;
    private StatusBar statusBar;
    private JFileChooser fileChooser;

    public KeyEventController(MessageArea resultArea, EditorArea editorArea, StatusBar statusBar) {
        this.resultArea = resultArea;
        this.editorArea = editorArea;
        this.statusBar = statusBar;

        fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
        fileChooser.setFileFilter(filter);
        fileChooser.setAcceptAllFileFilterUsed(false);
    }

    public void compileProgram() {
        resultArea.setMessageAreaText("Compilação de programas ainda não foi implementada.");
    }

    public void showTeamInfo() {
        resultArea.setMessageAreaText("Equipe: Cristina, Daniel e João Gabriel.");
    }

    public void newFile() {
        editorArea.cleanEditorArea();
        resultArea.cleanMessageArea();
        statusBar.cleanStatusBar();
    }

    public void saveFile() {
        String path = statusBar.getText();

        if (statusBar.getText().isEmpty() && fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION && fileChooser.getSelectedFile().getName().endsWith(".txt")){
            path = fileChooser.getSelectedFile().getAbsolutePath();        
        }
    
        File file = new File(path);
        writeFile(file);
    }

    private void writeFile(File file) {
        try (PrintWriter writer = new PrintWriter(file, "UTF-8")) {
            String text = editorArea.getEditorAreaText();
            writer.print(text);
        } catch (IOException e) {
            resultArea.setMessageAreaText("Erro ao escrever arquivo: " + e.getMessage());
        }

        // ! VER COM A JOYCE
        // statusBar.setStatusBarText(" " + file.getParentFile().getName() + "/" +
        // file.getName());
        statusBar.setStatusBarText(file.getAbsolutePath());
    }

    public void openFile() {
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION && fileChooser.getSelectedFile().getName().endsWith(".txt")) {
            File file = fileChooser.getSelectedFile();

            try (Scanner scanner = new Scanner(file, "UTF-8")) {
                editorArea.cleanEditorArea();

                while (scanner.hasNextLine()) {
                    String linha = scanner.nextLine();
                    editorArea.getTextArea().append(linha + "\n");
                }
                // ! VER COM A JOYCE
                // statusBar.setStatusBarText(" " + file.getParentFile().getName() + "/" +
                // file.getName());
                statusBar.setStatusBarText(file.getAbsolutePath());
            } catch (IOException e) {
                resultArea.setMessageAreaText("Erro ao abrir arquivo:" + e.getMessage());
            }

            resultArea.cleanMessageArea();
        }
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