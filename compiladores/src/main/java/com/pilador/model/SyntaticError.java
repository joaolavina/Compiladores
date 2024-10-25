package com.pilador.model;

public class SyntaticError extends AnalysisError
{
    public SyntaticError(String msg, int position)
	 {
        super(msg, position);
    }

    public SyntaticError(String msg, int position, String symbol) {
        super(msg, position, symbol);
    }

    public SyntaticError(String msg)
    {
        super(msg);
    }
}
