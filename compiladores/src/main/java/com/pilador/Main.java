package com.pilador;

import javax.swing.*;

import com.pilador.view.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Main extends JFrame {

    private JTextArea editorArea;
    private JTextArea messageArea;
    private JLabel statusBar;
    private JFileChooser fileChooser;

    public Main() {
   
        // Editor com numeraçao de linhas
        editorArea = new JTextArea();
        editorArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        editorArea.setLineWrap(true);
        JScrollPane editorScrollPane = new JScrollPane(editorArea);
        editorScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        editorScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        // Area de mensagem
        messageArea = new JTextArea();
        messageArea.setEditable(false);
        messageArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane messageScrollPane = new JScrollPane(messageArea);
        messageScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        messageScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        // Status
        statusBar = new JLabel("Arquivo: Nenhum arquivo aberto");
        statusBar.setPreferredSize(new Dimension(900, 25));
        add(statusBar, BorderLayout.SOUTH);

        // SplitPane entre editor e area de mensagens
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, editorScrollPane, messageScrollPane);
        splitPane.setResizeWeight(0.75);
        add(splitPane, BorderLayout.CENTER);

        // Escolhedor de arquivo para abrir e salvar arquivos
        fileChooser = new JFileChooser();
    }

    private void newFile() {
        editorArea.setText("");
        messageArea.setText("");
        statusBar.setText("Arquivo: Nenhum arquivo aberto");
    }

    private void openFile() {
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                editorArea.read(reader, null);
                messageArea.setText("");
                statusBar.setText("Arquivo: " + file.getAbsolutePath());
            } catch (IOException e) {
                messageArea.setText("Erro ao abrir o arquivo.");
            }
        }
    }

    private void saveFile() {
        int returnValue = fileChooser.showSaveDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                editorArea.write(writer);
                messageArea.setText("");
                statusBar.setText("Arquivo: " + file.getAbsolutePath());
            } catch (IOException e) {
                messageArea.setText("Erro ao salvar o arquivo.");
            }
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WindowFrame gui = new WindowFrame();
            gui.setVisible(true);
        });
    }
}