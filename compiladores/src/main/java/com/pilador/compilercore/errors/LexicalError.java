package com.pilador.compilercore.errors;

public class LexicalError extends AnalysisError {
    public LexicalError(String msg, int position) {
        super(msg, position);
    }
    
    public LexicalError(String msg, int position, String symbol) {
        super(msg, position, symbol);
    }

    public LexicalError(String msg) {
        super(msg);
    }
}
