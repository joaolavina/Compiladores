package com.pilador.model;

public class Token {
    private String className;
    private String lexeme;
    private int position;
    private int id;

    public Token(String className, String lexeme, int position, int id) {
        this.className = className;
        this.lexeme = lexeme;
        this.position = position;
        this.id = id;
    }

    public final String getClassName() {
        return className;
    }

    public final String getLexeme() {
        return lexeme;
    }

    public final int getPosition() {
        return position;
    }

    public int getId() {
        return id;
    }

    public String toString() {
        return String.valueOf(position) + " | " + className + " | " + lexeme;
    };
}
