package com.pilador.compilercore;

public class OCGenerator {

    private StringBuilder codigoObjeto = new StringBuilder();

    public String getCodigoObjeto() {
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

    public void geraRodape() {
        addCode("ret");
        addCode("}");
        addCode("}");
    }

    public void geraSaida(String tipo) {
        addCode("call void [mscorlib]System.Console::Write(" + tipo + ")");
    }

    public void geraSaidaLinha(String tipo) {
        addCode("call void [mscorlib]System.Console::WriteLn(" + tipo + ")");
    }

    public void carregaInt(String valorConstante) {
        addCode("ldc.i8 " + valorConstante);
    }

    public void carregaFloat(String valorConstante) {
        addCode("ldc.r8 " + valorConstante);
    }

    public void carregaString(String valorConstante) {
        addCode("ldstr " + valorConstante);
    }

    public void carregaFalse() {
        addCode("ldc.i4.0");
    }

    public void carregaTrue() {
        addCode("ldc.i4.1");
    }

    public void paraInt() {
        addCode("conv.i8");
    }

    public void paraFloat() {
        addCode("conv.r8");
    }

    public void adicao() {
        addCode("add");
    }

    public void subtracao() {
        addCode("sub");
    }

    public void multiplicacao() {
        addCode("mul");
    }

    public void divisao() {
        addCode("div");
    }

    public void igualA() {
        addCode("ceq");
    }

    public void menorQue() {
        addCode("clt");
    }

    public void maiorQue() {
        addCode("cgt");
    }

    public void diferenteDe() {
        addCode("ceq");
        carregaFalse();
        igualA();
    }

    // !! FIZ ISSO AQUI EMBAIXO

    public void not() {
        carregaTrue();
        addCode("xor");
    }

    public void and() {
        addCode("and");
    }

    public void or() {
        addCode("or");
    }

    public void getValorVariavel(String nomeVariavel) {
        addCode("ldcloc " + nomeVariavel);
    }

    public void declararVariáveis(String variaveis) {
        addCode(".locals (" + variaveis + ")");
    }

    public void duplicar(){
        addCode("dup");
    }

    public void setValorVariavel(String nomeVariavel){
        addCode("stloc " + nomeVariavel);
    }
}
