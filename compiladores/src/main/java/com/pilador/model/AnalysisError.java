package com.pilador.model;

public class AnalysisError extends Exception {
    private int position;
    // private String symbol = "";

    public AnalysisError(String msg, int position) {
        super(msg);
        this.position = position;
    }
    
    // public AnalysisError(String msg, int position, String symbol) {
    //     super(msg);
    //     this.position = position;
    //     this.symbol = symbol;
    // }

    public AnalysisError(String msg) {
        super(msg);
        this.position = -1;
    }

    public int getPosition() {
        return position;
    }

    // public String getSymbol() {
    //     return symbol;
    // }

    public String toString() {
        return super.toString() + ", @ " + position;
    }
}
