package com.pilador.compilercore.utils;

public class LineCalculator {
    private String[] lines;

    public LineCalculator(String input){
        this.lines = input.split("\n");
    }

    public int getLine(int tokenPosition) {
        int charCount = 0;

        for (int i = 0; i < lines.length; i++) {
            charCount += lines[i].length() + 1;
            if (charCount > tokenPosition) {
                return i + 1;
            }
        }

        return -1;
    }

}
