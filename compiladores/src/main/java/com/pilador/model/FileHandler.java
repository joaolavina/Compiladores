package com.pilador.model;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileHandler {

    public FileHandler () {
    }

    public String saveFile(String path, String text) {

        if (!path.endsWith(".txt"))
            path += ".txt";

        File file = new File(path);
        String fileName = file.getName();
    
        if (!isExtensionValid(fileName)) 
            throw new IllegalArgumentException("O arquivo deve ter a extensão .txt");

        writeFile(file, text);

        return file.getAbsolutePath();
        
    }

    private boolean isExtensionValid (String fileName){
        
        return !fileName.isEmpty() && 
        (fileName.endsWith(".txt") ||
        !fileName.contains("."));
    }

    private void writeFile(File file, String text) {
        try (PrintWriter writer = new PrintWriter(file, "UTF-8")) {
            writer.print(text);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar arquivo: " + e.getMessage(), e);
        }
    }

    public String[] openFile (File file) {

    String resultText = "";

        try (Scanner scanner = new Scanner(file, "UTF-8")) {

            String fileName = file.getName();

            if (!isExtensionValid(fileName)) 
                throw new IllegalArgumentException("O arquivo deve ter a extensão .txt");
       
            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                resultText += linha + "\n";
            }
        } catch (IOException e) {
                throw new RuntimeException("Erro ao abrir arquivo: " + e.getMessage(), e);
        }

        return new String[]{resultText, file.getAbsolutePath()};
    }

}
