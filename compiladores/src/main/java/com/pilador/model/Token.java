package com.pilador.model;
public class Token
{
    private String id;
    private String lexeme;
    private int position;

    public Token(String id, String lexeme, int position)
    {
        this.id = id;
        this.lexeme = lexeme;
        this.position = position;
    }

    public final String getId()
    {
        return id;
    }

    public final String getLexeme()
    {
        return lexeme;
    }

    public final int getPosition()
    {
        return position;
    }

    public String toString()
    {
        return lexeme + " | " + id + " | " + String.valueOf(position);
    };
}
