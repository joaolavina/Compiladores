package com.pilador.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.pilador.model.FileHandler;
import com.pilador.model.LexicalError;
import com.pilador.model.Lexico;
import com.pilador.model.Token;
import com.pilador.view.EditorArea;
import com.pilador.view.MessageArea;
import com.pilador.view.StatusBar;

public class KeyEventController {

    private MessageArea resultArea;
    private EditorArea editorArea;
    private StatusBar statusBar;
    private JFileChooser fileChooser;
    private FileHandler fileHandler;

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

        String text = editorArea.getEditorAreaText();
        Lexico lexico = new Lexico(text);

        String message = "";

        try {
            Token t = null;
            ArrayList<Token> tokens = new ArrayList<>();

            while ((t = lexico.nextToken()) != null) {
                tokens.add(t);
            }

            message += "LINHA | CLASSE | LEXEMA\n";

            for (int i = 0; i < tokens.size(); i++) {
                Token tkn = tokens.get(i);
                message += (tkn.toString() + "\n");
            }

            message += "\n\nPrograma compilado com sucesso";

        } catch (LexicalError e) {
            message = "Linha " + e.getPosition() + ": " + e.getSymbol() + e.getMessage();
        } finally {
            resultArea.setMessageAreaText(message);
        }

    }

    public void showTeamInfo() {
        resultArea.setMessageAreaText("Equipe: Cristina, Daniel e João Gabriel.");
    }

    public void newFile() {
        editorArea.cleanEditorArea();
        resultArea.cleanMessageArea();
        statusBar.cleanStatusBar();
    }
    public void saveFile(){
        String path = statusBar.getText();

        try {
        fileHandler.saveFile(path);
        } catch (IllegalArgumentException | IOException e) {
            resultArea.setMessageAreaText("Extensão de arquivo inválida.");
        }

    }

    private void writeFile(File file) {
        try (PrintWriter writer = new PrintWriter(file, "UTF-8")) {
            String text = editorArea.getEditorAreaText();
            writer.print(text);

        } catch (IOException e) {
            resultArea.setMessageAreaText("Erro ao salvar arquivo: " + e.getMessage());
        }

        statusBar.setStatusBarText(file.getAbsolutePath());
    }

    public void openFile() {
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            try (Scanner scanner = new Scanner(file, "UTF-8")) {

                if (!(file.getName().endsWith(".txt"))) {
                    throw new IllegalArgumentException();
                }
                editorArea.cleanEditorArea();

                while (scanner.hasNextLine()) {
                    String linha = scanner.nextLine();
                    editorArea.getTextArea().append(linha + "\n");
                }

                statusBar.setStatusBarText(file.getAbsolutePath());
                resultArea.cleanMessageArea();
            } catch (IOException e) {
                resultArea.setMessageAreaText("Erro ao abrir arquivo: " + e.getMessage());
            }
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