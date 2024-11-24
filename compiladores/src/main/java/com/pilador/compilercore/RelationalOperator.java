package com.pilador.compilercore;

public enum RelationalOperator {
    EQUAL("=="),
    NOT_EQUAL("!="),
    GREATER_THAN(">"),
    LESS_THAN("<");

    private final String symbol;

    RelationalOperator(String symbol) {
        this.symbol = symbol;
    }

   
    public String getSymbol() {
        return symbol;
    }

    public static RelationalOperator fromSymbol(String symbol) {
        for (RelationalOperator op : values()) {
            if (op.symbol.equals(symbol)) {
                return op;
            }
        }
        return null;
    }
}
