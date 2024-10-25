package com.pilador.model;

public class Token {
    private int id;
    private String lexeme;
    private int position;

    public Token(int id, String lexeme, int position) {
        this.id = id;
        this.lexeme = lexeme;
        this.position = position;
    }

    public final int getId() {
        return id;
    }

    public final String getLexeme() {
        return lexeme;
    }

    public final int getPosition() {
        return position;
    }

    public String toString() {
        return String.valueOf(position) + " | " + getClass(id) + " | " + lexeme;
    };

    private String getClass(int token) {
        if (token > 6 && token < 20) {
            return "pr";
        } else if (token > 19 && token < 36) {
            return "se";
        } else {
            return Constants.id_string[token];
        }
    }
}