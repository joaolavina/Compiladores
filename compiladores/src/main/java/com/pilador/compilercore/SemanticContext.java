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

    public void handleProgramHeader() {
        ocGenerator.geraCabecalho("teste");
    }

    public void handleProgramEnd() {
        ocGenerator.geraRodape();
    }

    public void handleWriteCommand(Token token) {
        ExpressionType tipoDesemp = pilhaTipos.pop();

        if (tipoDesemp == ExpressionType.FLOAT64){
            ocGenerator.paraInt(token.getLexeme());
        }
        ocGenerator.geraSaida(tipoDesemp.getName());
    }

    public void generatedCodeToFile (File ilFile) throws IOException {
        if (!ilFile.getParentFile().exists()) {
            ilFile.getParentFile().mkdirs();
        }

        try (FileWriter writer = new FileWriter(ilFile)) {
            writer.write(ocGenerator.getCodigoObjeto());
        }
    }

    public void handleBinaryOperation(Token token) {
        ExpressionType tipoDesemp = pilhaTipos.pop();
        //ExpressionType tipoDesemp = pilhaTipos.pop();
    }

    public String getCodigoObjeto() {
        return ocGenerator.getCodigoObjeto();
    }
    

    

    
}
