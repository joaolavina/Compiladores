package com.pilador;

import javax.swing.*;

import com.pilador.view.MainFrame;

public class Main extends JFrame {

    public Main() {
        // Escolhedor de arquivo para abrir e salvar arquivos
        // fileChooser = new JFileChooser();
    }

    // private void newFile() {
    //     editorArea.setText("");
    //     messageArea.setText("");
    //     statusBar.setText("Arquivo: Nenhum arquivo aberto");
    // }

    // private void openFile() {
    //     int returnValue = fileChooser.showOpenDialog(this);
    //     if (returnValue == JFileChooser.APPROVE_OPTION) {
    //         File file = fileChooser.getSelectedFile();
    //         try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
    //             editorArea.read(reader, null);
    //             messageArea.setText("");
    //             statusBar.setText("Arquivo: " + file.getAbsolutePath());
    //         } catch (IOException e) {
    //             messageArea.setText("Erro ao abrir o arquivo.");
    //         }
    //     }
    // }

    // private void saveFile() {
    //     int returnValue = fileChooser.showSaveDialog(this);
    //     if (returnValue == JFileChooser.APPROVE_OPTION) {
    //         File file = fileChooser.getSelectedFile();
    //         try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
    //             editorArea.write(writer);
    //             messageArea.setText("");
    //             statusBar.setText("Arquivo: " + file.getAbsolutePath());
    //         } catch (IOException e) {
    //             messageArea.setText("Erro ao salvar o arquivo.");
    //         }
    //     }
    // }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame gui = new MainFrame();
            gui.setVisible(true);
        });
    }
}