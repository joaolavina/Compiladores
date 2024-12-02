package com.pilador.compilercore;

import java.util.ArrayList;
import java.util.List;

public class OCGenerator {

    private List<String> codigoObjeto = new ArrayList<>();

    public String getCodigoObjeto() {
        return String.join("\n", codigoObjeto);
    }

    private void insereCodigo(String line) {
        codigoObjeto.add(line);
    }

    public void geraCabecalho(String nomeClasse) {
        insereCodigo(".assembly extern mscorlib {}");
        insereCodigo(".assembly _codigo_objeto{}");
        insereCodigo(".module _codigo_objeto.exe");
        insereCodigo("");
        insereCodigo(".class public " + nomeClasse + "{");
        insereCodigo(".method static public void _principal(){");
        insereCodigo(".entrypoint");
    }

    public void geraRodape() {
        insereCodigo("ret");
        insereCodigo("}");
        insereCodigo("}");
    }

    public void geraSaida(String tipo) {
        insereCodigo("call void [mscorlib]System.Console::Write(" + tipo + ")");
    }

    public void geraSaidaLinha() {
        String ultimaAtual = codigoObjeto.removeLast();

        String novaUltima = ultimaAtual.replace("Write", "WriteLine");

        insereCodigo(novaUltima);
    }

    public void carregaInt(String valorConstante) {
        insereCodigo("ldc.i8 " + valorConstante);
    }

    public void carregaFloat(String valorConstante) {
        insereCodigo("ldc.r8 " + valorConstante);
    }

    public void carregaString(String valorConstante) {
        insereCodigo("ldstr " + valorConstante);
    }

    public void carregaFalse() {
        insereCodigo("ldc.i4.0");
    }

    public void carregaTrue() {
        insereCodigo("ldc.i4.1");
    }

    public void paraInt() {
        insereCodigo("conv.i8");
    }

    public void paraFloat() {
        insereCodigo("conv.r8");
    }

    public void adicao() {
        insereCodigo("add");
    }

    public void subtracao() {
        insereCodigo("sub");
    }

    public void multiplicacao() {
        insereCodigo("mul");
    }

    public void divisao() {
        insereCodigo("div");
    }

    public void igualA() {
        insereCodigo("ceq");
    }

    public void menorQue() {
        insereCodigo("clt");
    }

    public void maiorQue() {
        insereCodigo("cgt");
    }

    public void diferenteDe() {
        insereCodigo("ceq");
        carregaFalse();
        igualA();
    }

    public void not() {
        carregaTrue();
        insereCodigo("xor");
    }

    public void and() {
        insereCodigo("and");
    }

    public void or() {
        insereCodigo("or");
    }

    public void carregaValorVariavel(String nomeVariavel) {
        insereCodigo("ldloc " + nomeVariavel);
    }

    public void declaraVariaveis(List<String> variaveis) {
        for (String variavel : variaveis)
            insereCodigo(".locals (" + variavel + ")");
    }

    public void duplica() {
        insereCodigo("dup");
    }

    public void armazenaValorVariavel(String nomeVariavel) {
        insereCodigo("stloc " + nomeVariavel);
    }

    public void geraEntrada() {
        insereCodigo("call string [mscorlib]System.Console::ReadLine()");
    }

    public void converteEntrada(ExpressionType type) {
        insereCodigo("call " + type.getName() + " [mscorlib]System." + type.getClassName() +"::Parse(string)");
    }

    public void criaRotulo(String rotulo){
        insereCodigo(rotulo + ":");
    }

    public void pulaParaRotulo(String condicao, String rotulo){
        insereCodigo("br" + condicao + " " + rotulo);
    }
}
