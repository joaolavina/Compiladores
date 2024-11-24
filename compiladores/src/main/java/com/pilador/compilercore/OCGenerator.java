package com.pilador.compilercore;

public class OCGenerator {
    
    private StringBuilder codigoObjeto = new StringBuilder();

    public String getCodigoObjeto () {
        return codigoObjeto.toString();
    }

    private void addCode(String line) {
        codigoObjeto.append(line).append("\n");
    }

    public void geraCabecalho(String nomeClasse) {
        addCode(".assembly extern mscorlib {}");
        addCode(".assembly _codigo_objeto{}");
        addCode(".module _codigo_objeto.exe");
        addCode(".class public " + nomeClasse + "{");
        addCode(".method static public void _principal() {");
        addCode(".entrypoint");
    }

    public void geraRodape(){
        addCode("ret");
        addCode("}");
        addCode("}");
    }

    public void geraSaida(String tipo){
        addCode("call void [mscorlib]System.Console::Write(<" + tipo + ">)");
    }

    public void geraSaidaLinha(String tipo){
        addCode("call void [mscorlib]System.Console::WriteLn(<" + tipo + ">)");
    }

    public void carregaFalse () {
        addCode("ldc.i4.0");
    }

    public void carregaTrue () {
        addCode("ldc.i4.1");
    }

    public void paraInt(String nomeVariavel) {
        addCode("conv.i8 " + nomeVariavel);
    }

    public void paraFloat(String nomeVariavel) {
        addCode("conv.r8 " + nomeVariavel);
    }

}
