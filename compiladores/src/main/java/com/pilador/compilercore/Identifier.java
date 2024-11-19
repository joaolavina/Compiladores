package com.pilador.compilercore;

public class Identifier {
    private String name;
    private String typeString;
    private boolean declared;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getTypeString() {
        return typeString;
    }
    public void setTypeString(String typeString) {
        this.typeString = typeString;
    }
    public boolean isDeclared() {
        return declared;
    }
    public void setDeclared(boolean declared) {
        this.declared = declared;
    }
}
