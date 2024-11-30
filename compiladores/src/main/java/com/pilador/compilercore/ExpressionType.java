package com.pilador.compilercore;

public enum ExpressionType {
    INT64("int64", "Int64"),
    FLOAT64("float64", "Double"),
    BOOL("bool", "Boolean"),
    STRING("string", "String");

    private final String name;
    private final String className;

    
    ExpressionType(String name, String className) {
        this.name = name;
        this.className = className;
    }

    public String getName() {
        return name;
    }

    public String getClassName(){
        return className;
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
