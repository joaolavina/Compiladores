package com.pilador.compilercore;

public enum ExpressionType {
    INT64("int64", "Int64", "int"),
    FLOAT64("float64", "Double", "float"),
    BOOL("bool", "Boolean", "boolean"),
    STRING("string", "String", "string");

    private final String name;
    private final String className;
    private final String formatName;
    
    ExpressionType(String name, String className, String formatName) {
        this.name = name;
        this.className = className;
        this.formatName = formatName;
    }

    public String getName() {
        return name;
    }

    public String getClassName(){
        return className;
    }

    public String getFormatName() {
        return formatName;
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
