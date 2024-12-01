package com.pilador.compilercore;

import com.pilador.compilercore.errors.LexicalError;
import com.pilador.compilercore.errors.SemanticError;
import com.pilador.compilercore.errors.SyntaticError;

public class CompilerMain {

    public String compile(String text) {
        Lexico lexico = new Lexico(text);
        Sintatico sintatico = new Sintatico();
        Semantico semantico = new Semantico(text);

        String message = "";

        try {
            // Token t = null;
            // ArrayList<Token> tokens = new ArrayList<>();

            // while ((t = lexico.nextToken()) != null) {
            //     tokens.add(t);
            // }

            // message += "LINHA | CLASSE | LEXEMA\n";

            // for (int i = 0; i < tokens.size(); i++) {
            //     Token tkn = tokens.get(i);
            //     message += (tkn.toString() + "\n"); // trocar de novo depois para position = linha
            // }

            // System.out.println(message);

            sintatico.parse(lexico, semantico);

            message += "Programa compilado com sucesso";

        } catch (LexicalError e) {
            throw new RuntimeException("Erro na linha " + e.getPosition() + " – " + e.getSymbol() + e.getMessage());
        } catch (SyntaticError e) {
            throw new RuntimeException("Erro na linha " + e.getPosition() + " – " + e.getMessage());
        } catch (SemanticError e) {
            throw new RuntimeException("Erro na linha " + e.getPosition() + " – " + e.getMessage());
        }

        return message;
    }

}
