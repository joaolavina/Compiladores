package com.pilador.compilercore;

public enum ExpressionType {
    INT64("int64"),
    FLOAT64("float64"),
    BOOL("bool"),
    STRING("string");

    private final String name;

    
    ExpressionType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    public static ExpressionType fromName(String name) {
        for (ExpressionType type : values()) {
            if (type.name.equalsIgnoreCase(name)) {
                return type;
            }
        }
        
        return null;
    }
}
