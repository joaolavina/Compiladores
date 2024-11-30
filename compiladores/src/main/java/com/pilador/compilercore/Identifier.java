package com.pilador.compilercore;

public class Identifier {
    
    private String name;
    private ExpressionType type;
    
    public Identifier(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public ExpressionType getType() {
        return type;
    }
    public void setType(ExpressionType type) {
        this.type = type;
    }

}
