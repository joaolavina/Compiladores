package com.pilador.compilercore.utils;

import java.io.File;
import java.io.FileWriter;
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

    public String generateILFile(String currentDir, String codigoObjeto) {
        File sourceFile = new File(currentDir);

        String fileNameWithoutExtension = sourceFile.getName().replaceAll("\\.txt$", "");

        File ilFile = new File(sourceFile.getParent(), fileNameWithoutExtension + ".il");

        if (!ilFile.getParentFile().exists()) {
            ilFile.getParentFile().mkdirs();
        }

        try (FileWriter writer = new FileWriter(ilFile)) {
            writer.write(codigoObjeto);

            return "";
        } catch (IOException e) {
            return ("Erro ao salvar o código objeto: " + e.getMessage());
        }
    }

}
