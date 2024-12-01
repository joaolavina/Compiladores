package com.pilador.compilercore;

import java.util.Stack;

import com.pilador.compilercore.constants.Constants;
import com.pilador.compilercore.errors.LexicalError;
import com.pilador.compilercore.errors.SemanticError;
import com.pilador.compilercore.errors.SyntaticError;
import com.pilador.compilercore.utils.LineCalculator;

public class Sintatico implements Constants
{
    private Stack stack = new Stack();
    private Token currentToken;
    private Token previousToken;
    private Lexico scanner;
    private Semantico semanticAnalyser;
    private LineCalculator lineCalculator;

    private static final boolean isTerminal(int x)
    {
        return x < FIRST_NON_TERMINAL;
    }

    private static final boolean isNonTerminal(int x)
    {
        return x >= FIRST_NON_TERMINAL && x < FIRST_SEMANTIC_ACTION;
    }

    // private static final boolean isSemanticAction(int x)
    // {
    //     return x >= FIRST_SEMANTIC_ACTION;
    // }

    private boolean step() throws LexicalError, SyntaticError, SemanticError
    {
        if (currentToken == null)
        {
            int pos = 0; // = 0
            if (previousToken != null)
                pos = previousToken.getPosition()+previousToken.getLexeme().length();

            currentToken = new Token(DOLLAR, "$", pos);
        }

        int x = ((Integer)stack.pop()).intValue();
        int a = currentToken.getId();

        if (x == EPSILON)
        {
            return false;
        }
        else if (isTerminal(x))
        {
            if (x == a)
            {
                if (stack.empty())
                    return true;
                else
                {
                    previousToken = currentToken;
                    currentToken = scanner.nextToken();
                    return false;
                }
            }
            else
            {
                throw new SyntaticError(errorMessageConstructor(currentToken, x), lineCalculator.getLine(currentToken.getPosition()));
            }
        }
        else if (isNonTerminal(x))
        {
            if (pushProduction(x, a))
                return false;
            else
                throw new SyntaticError(errorMessageConstructor(currentToken, x), lineCalculator.getLine(currentToken.getPosition()));
        }
        else // isSemanticAction(x)
        {
            semanticAnalyser.executeAction(x-FIRST_SEMANTIC_ACTION, previousToken);
            return false;
        }
    }

    @SuppressWarnings({ "removal", "unchecked" })
    private boolean pushProduction(int topStack, int tokenInput)
    {
        int p = PARSER_TABLE[topStack-FIRST_NON_TERMINAL][tokenInput-1];
        if (p >= 0)
        {
            int[] production = PRODUCTIONS[p];
            //empilha a produção em ordem reversa
            for (int i=production.length-1; i>=0; i--)
            {
                stack.push(new Integer(production[i]));
            }
            return true;
        }
        else
            return false;
    }

    private String errorMessageConstructor(Token currToken, int x) {
        String message = "Encontrado ";

        String lexeme = currToken.getLexeme();
        int tokenId = currToken.getId();

        switch (tokenId) {
            case 1:
                message += "EOF";
                break;
            case 6:
                message += "constante_string";
                break;
            default:
                message += lexeme;
        }

        message += " " + PARSER_ERROR[x];;

        return message;
    }


    @SuppressWarnings({ "removal", "unchecked" })
    public void parse(Lexico scanner, Semantico semanticAnalyser) throws LexicalError, SyntaticError, SemanticError
    {
        this.scanner = scanner;
        this.semanticAnalyser = semanticAnalyser;
        this.lineCalculator = new LineCalculator(scanner.getInput());

        stack.clear();
        stack.push(new Integer(DOLLAR));
        stack.push(new Integer(START_SYMBOL));

        currentToken = scanner.nextToken();

        while ( ! step() );
    }

}
