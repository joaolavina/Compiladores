package com.pilador.model;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileHandler {

    private JFileChooser fileChooser;

    public FileHandler () {
        fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
        fileChooser.setFileFilter(filter);
        fileChooser.setAcceptAllFileFilterUsed(false);
    }


    public void saveFile(String path) throws IllegalArgumentException, IOException {

        if (path.isEmpty() && fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            path = fileChooser.getSelectedFile().getAbsolutePath();
        }

        File file = new File(path);
        String fileName = file.getName();

        if (fileName.isEmpty() || (!fileName.endsWith(".txt") && fileName.contains("."))) {
            throw new IllegalArgumentException();
        } else if (!fileName.endsWith(".txt")){
            file = new File(path.concat(".txt"));
        }

       // writeFile(file);
    }


}
