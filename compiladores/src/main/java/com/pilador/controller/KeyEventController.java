package com.pilador.controller;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.pilador.compilercore.CompilerMain;
import com.pilador.compilercore.utils.FileHandler;
import com.pilador.view.EditorArea;
import com.pilador.view.MessageArea;
import com.pilador.view.StatusBar;

public class KeyEventController {

    private MessageArea resultArea;
    private EditorArea editorArea;
    private StatusBar statusBar;
    private JFileChooser fileChooser;
    private FileHandler fileHandler;

    public KeyEventController(MessageArea resultArea, EditorArea editorArea, StatusBar statusBar, JFileChooser fileChooser) {
        this.resultArea = resultArea;
        this.editorArea = editorArea;
        this.statusBar = statusBar;
        this.fileChooser = fileChooser;

        fileHandler = new FileHandler();
    }

    public void compileProgramAction() {

        CompilerMain compiler = new CompilerMain();
       
        try {
            String text = editorArea.getEditorAreaText();

            String result = compiler.compile(text, statusBar.getStatusBarText());

            resultArea.setMessageAreaText(result);

        } catch (Exception e){
            resultArea.setMessageAreaText(e.getMessage());
        }
 
    }

    public void showTeamInfoAction() {
        resultArea.setMessageAreaText("Equipe: Cristina, Daniel e João Gabriel.");
    }

    public void newFileAction() {
        String text = editorArea.getEditorAreaText();

        if (!confirmNewFileAction(text))
            return;

        editorArea.cleanEditorArea();
        statusBar.cleanStatusBar();

        resultArea.setMessageAreaText("Novo arquivo aberto com sucesso.");
    }

    private boolean confirmNewFileAction (String text){
        if (text.isBlank())
            return true;

        int option = JOptionPane.showConfirmDialog(null,
        "Deseja realmente abrir um novo arquivo?", 
        "ATENÇÃO", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) 
            return true;
        else {
            resultArea.setMessageAreaText("Ação de novo arquivo cancelada.");
            return false;
        }
    }

    public void saveFileAction(){
        fileChooser.setDialogTitle("Salvar arquivo");
        fileChooser.setSelectedFile(new File(statusBar.getText()));

        if (fileChooser.showSaveDialog(fileChooser) != JFileChooser.APPROVE_OPTION) { 
            resultArea.setMessageAreaText("Ação de salvar arquivo cancelada.");
            return;
        }

        File currentFile = fileChooser.getSelectedFile();

        if (!confirmSaveFileAction(currentFile))
            return;

        String text = editorArea.getEditorAreaText();
        String path = currentFile.getAbsolutePath();
            
        try {
            String resultPath = fileHandler.saveFile(path, text);

            statusBar.setStatusBarText(resultPath);
        } catch (RuntimeException e) {
            resultArea.setMessageAreaText(e.getMessage());
        }

        resultArea.setMessageAreaText("Arquivo salvo com sucesso.");
    }

    private boolean confirmSaveFileAction (File currentFile){
        if (!currentFile.exists())
            return true;

        int option = JOptionPane.showConfirmDialog(fileChooser, 
            "O arquivo existente será sobrescrito.", 
            "ATENÇÃO", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) 
            return true;
        else {
            resultArea.setMessageAreaText("Ação de salvar arquivo cancelada.");
            return false;
        }
    }

    public void openFileAction() {

        if (fileChooser.showOpenDialog(fileChooser) != JFileChooser.APPROVE_OPTION) { 
            resultArea.setMessageAreaText("Ação de abrir arquivo cancelada.");
            return;
        }

        File currentFile = fileChooser.getSelectedFile();

        if (!confirmOpenFileAction(currentFile))
            return;

        try {
            File file = fileChooser.getSelectedFile();
            
            editorArea.cleanEditorArea();

            String[] result = fileHandler.openFile(file);

            editorArea.setEditorAreaText(result[0]);
            statusBar.setStatusBarText(result[1]);
        } catch (RuntimeException e) {
            resultArea.setMessageAreaText(e.getMessage());
        }

        resultArea.setMessageAreaText("Arquivo aberto com sucesso.");
    }

    private boolean confirmOpenFileAction (File currentFile) {
        if (!currentFile.getAbsolutePath().equals(statusBar.getText()))
            return true;

        int option = JOptionPane.showConfirmDialog(null, 
            "O arquivo já está aberto. Gostaria de abrí-lo novamente?", 
            "ATENÇÃO", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) 
            return true;
        else {
            resultArea.setMessageAreaText("Ação de abrir arquivo cancelada.");
            return false;
        }
    }

    public void copyAction() {
        editorArea.getTextArea().copy();
    }

    public void pasteAction() {
        editorArea.getTextArea().paste();
    }

    public void cutAction() {
        editorArea.getTextArea().cut();
    }
}