package com.pilador.compilercore;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;


public class SemanticContext {

    RelationalOperator operadorRelacional; // operador relacional reconhecido pela ação #121, para uso posterior na ação #122
    Stack<ExpressionType> pilhaTipos; // determinar o tipo de uma expressão durante a compilação do programa
    Stack<String> pilhaRotulos; // análise dos comandos de seleção e de repetição
    List<Identifier> listaIdentificadores; // armazenar os identificadores reconhecidos pela ação #104, para uso posterior nas ações #102 a #103
    Map<String, String> tabelaSimbolos; // armazenar os identificadores declarados (variáveis, ação #102). Cada linha da tabela tem um campo: o identificador da variável declarada 
    private OCGenerator ocGenerator; // armazenar o código objeto gerado

    public SemanticContext() {
        this.pilhaTipos =  new Stack<ExpressionType>();
        this.pilhaRotulos = new Stack<String>();
        this.listaIdentificadores = new ArrayList<Identifier>();
        this.tabelaSimbolos = new HashMap<String,String>();
        this.ocGenerator = new OCGenerator();
    }

    public void handleProgramHeader() { // #100
        ocGenerator.geraCabecalho("teste");
    }

    public void handleProgramEnd() { // #101
        ocGenerator.geraRodape();
    }

    public void handleWriteLnCommand(Token token) { // #107
        ExpressionType tipoDesemp = pilhaTipos.pop();

        if (tipoDesemp == ExpressionType.FLOAT64)
           ocGenerator.paraInt();

        ocGenerator.geraSaidaLinha(tipoDesemp.getName());
    }

    public void handleWriteCommand(Token token) { // #108
        ExpressionType tipoDesemp = pilhaTipos.pop();

        if (tipoDesemp == ExpressionType.INT64)
           ocGenerator.paraInt();

        ocGenerator.geraSaida(tipoDesemp.getName());
    }

    public boolean validExpressionType (ExpressionType et){
        return (et!=ExpressionType.STRING && et!=ExpressionType.BOOL);
    }

    public void handleAddition(Token token) { // #123
        ExpressionType tipoDesemp1 = pilhaTipos.pop(); 
        ExpressionType tipoDesemp2 = pilhaTipos.pop();

        if (!validExpressionType(tipoDesemp1) || !validExpressionType(tipoDesemp2))
            throw new IllegalArgumentException("Operação de adição inválida para " + tipoDesemp1.getName() + " e " +   tipoDesemp2.getName());

        ExpressionType tipoResultante;

        if (tipoDesemp1 == ExpressionType.FLOAT64 || tipoDesemp2 == ExpressionType.FLOAT64)
            tipoResultante = ExpressionType.FLOAT64;
        else 
            tipoResultante = ExpressionType.INT64;

        pilhaTipos.push(tipoResultante);
        ocGenerator.adicao();
    }

    public void handleSubtraction(Token token) { // #124
        ExpressionType tipoDesemp1 = pilhaTipos.pop();
        ExpressionType tipoDesemp2 = pilhaTipos.pop();
        ExpressionType tipoResultante;

        if (tipoDesemp1 == ExpressionType.FLOAT64 || tipoDesemp2 == ExpressionType.FLOAT64)
            tipoResultante = ExpressionType.FLOAT64;
        else 
            tipoResultante = ExpressionType.INT64;

        pilhaTipos.push(tipoResultante);
    }

    public void handleMultiplication(Token token) { // #125
        ExpressionType tipoDesemp1 = pilhaTipos.pop();
        ExpressionType tipoDesemp2 = pilhaTipos.pop();
        ExpressionType tipoResultante;

        if (tipoDesemp1 == ExpressionType.FLOAT64 || tipoDesemp2 == ExpressionType.FLOAT64)
            tipoResultante = ExpressionType.FLOAT64;
        else 
            tipoResultante = ExpressionType.INT64;

        pilhaTipos.push(tipoResultante);
    }

    public void handleDivision(Token token) { // #126
        pilhaTipos.pop();
        pilhaTipos.pop();

        ExpressionType tipoResultante = ExpressionType.FLOAT64;
        pilhaTipos.push(tipoResultante);
    }

    public void handleIntExpression(Token token){ // #128
        pilhaTipos.push(ExpressionType.INT64);

        String nome = token.getLexeme();
        ocGenerator.carregaInt(nome);
        ocGenerator.paraFloat();
    }

    public void handleFloatExpression(Token token){ // #129
        pilhaTipos.push(ExpressionType.FLOAT64);

        String nome = token.getLexeme();
        ocGenerator.carregaFloat(nome);
    }

    public void handleStringExpression(Token token){ // #130
        pilhaTipos.push(ExpressionType.STRING);
    }

    // geração do arquivo .il

    public void generatedCodeToFile (File ilFile) throws IOException {
        if (!ilFile.getParentFile().exists()) {
            ilFile.getParentFile().mkdirs();
        }

        try (FileWriter writer = new FileWriter(ilFile)) {
            writer.write(ocGenerator.getCodigoObjeto());
        }
    }

    public String getCodigoObjeto() {
        return ocGenerator.getCodigoObjeto();
    }
    
}
