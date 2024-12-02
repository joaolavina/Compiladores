package com.pilador.compilercore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.pilador.compilercore.errors.SemanticError;
import com.pilador.compilercore.utils.LineCalculator;

public class SemanticContext {

    RelationalOperator operadorRelacional; // operador relacional reconhecido pela ação #121, para uso posterior na ação
                                           // #122
    Stack<ExpressionType> pilhaTipos; // determinar o tipo de uma expressão durante a compilação do programa
    Stack<String> pilhaRotulos; // análise dos comandos de seleção e de repetição
    List<Identifier> listaIdentificadores; // armazenar os identificadores reconhecidos pela ação #104, para uso
                                           // posterior nas ações #102 a #103
    Map<String, Identifier> tabelaSimbolos; // armazenar os identificadores declarados (variáveis, ação #102). Cada
                                            // linha da
    // tabela tem um campo: o identificador da variável declarada
    private OCGenerator ocGenerator; // armazenar o código objeto gerado

    private int indexRotulo; // indexador para a nomeação dos rótulos
    private LineCalculator lineCalculator;

    public SemanticContext(String input) {
        this.pilhaTipos = new Stack<ExpressionType>();
        this.pilhaRotulos = new Stack<String>();
        this.listaIdentificadores = new ArrayList<Identifier>();
        this.tabelaSimbolos = new HashMap<String, Identifier>();
        this.ocGenerator = new OCGenerator();
        this.lineCalculator = new LineCalculator(input);

        indexRotulo = 0;
    }

    public void handleProgramHeader() { // #100
        ocGenerator.geraCabecalho("_UNICA");
    }

    public void handleProgramEnd() { // #101
        ocGenerator.geraRodape();
    }

    public void verifyDuplicatedIdentifier(String idName, int position) throws SemanticError { 
        if (tabelaSimbolos.containsKey(idName)) {
            throw new SemanticError (idName + " já declarado", lineCalculator.getLine(position));
        }
    }

    public void verifyIdentifierExistence(String idName, int position) throws SemanticError { 
        if (!tabelaSimbolos.containsKey(idName)) {
            throw new SemanticError (idName + " não declarado", lineCalculator.getLine(position));
        }
    }

    public void handleIdentifierDeclaration(Token token) throws SemanticError { // #102
        String ilDeclaration = "";
        List<String> ilDeclarations = new ArrayList<>();

        for (int i = 0; i < listaIdentificadores.size(); i++) {
            Identifier id = listaIdentificadores.get(i);

            verifyDuplicatedIdentifier(id.getName(), token.getPosition());

            ExpressionType type = null;
            String prefix = getPrefixIdentifier(id.getName());
            switch (prefix) {
                case "i_":
                    type = ExpressionType.INT64;
                    break;
                case "f_":
                    type = ExpressionType.FLOAT64;
                    break;
                case "s_":
                    type = ExpressionType.STRING;
                    break;
                case "b_":
                    type = ExpressionType.BOOL;
                    break;
            }

            id.setType(type);
            tabelaSimbolos.put(id.getName(), id);

            // if (ilDeclaration != "")
            //     ilDeclaration += ", ";

            ilDeclaration = id.getType().getName() + " " + id.getName();

            ilDeclarations.add(ilDeclaration);
        }

        ocGenerator.declaraVariaveis(ilDeclarations);
        listaIdentificadores.removeAll(listaIdentificadores);
    }

    public void handleAtributionExpression(Token token) throws SemanticError { // #103
        ExpressionType tipoDesemp = pilhaTipos.pop();

        if (tipoDesemp == ExpressionType.INT64)
            ocGenerator.paraInt();

        for (int i = 0; i < listaIdentificadores.size() - 1; i++) {
            ocGenerator.duplica();
        }

        for (Identifier identifier : listaIdentificadores) {
            
            verifyIdentifierExistence(identifier.getName(), token.getPosition());

            ocGenerator.armazenaValorVariavel(identifier.getName());
            // if (tipoDesemp == ExpressionType.INT64)
            // ocGenerator.paraInt();
        }

        listaIdentificadores.removeAll(listaIdentificadores);
    }
    public void handleStoreIdentifier(Token token) { // #104
        listaIdentificadores.add(new Identifier(token.getLexeme()));
    }

    public void handleReadAttribution(Token token) throws SemanticError { // #105

        verifyIdentifierExistence(token.getLexeme(), token.getPosition());
    
        Identifier id = tabelaSimbolos.get(token.getLexeme());
        String prefix = getPrefixIdentifier(id.getName());

        ExpressionType type = null;

        ocGenerator.geraEntrada();
        if (!prefix.equals("s_")) {
            switch (prefix) {
                case "i_":
                    type = ExpressionType.INT64;
                    break;
                case "f_":
                    type = ExpressionType.FLOAT64;
                    break;
                case "b_":
                    type = ExpressionType.BOOL;
                    break;
            }

            ocGenerator.converteEntrada(type);
            

            ocGenerator.armazenaValorVariavel(id.getName());
        }
    }

    public void handleReadCommand(Token token) { // #106
        ocGenerator.carregaString(token.getLexeme());
        ocGenerator.geraSaida(ExpressionType.STRING.getName());
    }

    public void handleWriteLnCommand() { // #107
        ocGenerator.geraSaidaLinha();
    }

    public void handleWriteCommand(Token token) { // #108
        ExpressionType tipoDesemp = pilhaTipos.pop();

        if (tipoDesemp == ExpressionType.INT64)
            ocGenerator.paraInt();

        ocGenerator.geraSaida(tipoDesemp.getName());
    }

    public void handleIfExpression(Token token){ // #109
        String novoRotulo1 = "L" + indexRotulo;
        indexRotulo++;
        pilhaRotulos.push(novoRotulo1);
        
        String novoRotulo2 = "L" + indexRotulo;
        indexRotulo++;
        ocGenerator.pulaParaRotulo("false", novoRotulo2);
        pilhaRotulos.push(novoRotulo2);
    }

    public void handleElifExpression(Token token){ // #110
        String rotuloDesempilhado2 = pilhaRotulos.pop();
        String rotuloDesempilhado1 = pilhaRotulos.pop();

        ocGenerator.pulaParaRotulo("", rotuloDesempilhado1);
        pilhaRotulos.push(rotuloDesempilhado1);
        ocGenerator.criaRotulo(rotuloDesempilhado2);
    }

    public void handleElseExpression(Token token){ // #111
        String rotuloDesempilhado = pilhaRotulos.pop();
        ocGenerator.criaRotulo(rotuloDesempilhado);
    }

    public void handleElifBreakExpression(Token token){ // #112
        String novoRotulo = "L" + indexRotulo;
        indexRotulo++;

        ocGenerator.pulaParaRotulo("false", novoRotulo);
        pilhaRotulos.push(novoRotulo);
    }

    public void handleRepeatExpression(Token token){ // #113
        String novoRotulo = "L" + indexRotulo;
        indexRotulo++;
        ocGenerator.criaRotulo(novoRotulo);
        pilhaRotulos.push(novoRotulo);
    }

    public void handleWhileExpression(Token token) { // #114
        String rotuloDesempilhado = pilhaRotulos.pop();
        ocGenerator.pulaParaRotulo("true", rotuloDesempilhado);
    }

    public void handleUntilExpression(Token token){ // #115
        String rotuloDesempilhado = pilhaRotulos.pop();
        ocGenerator.pulaParaRotulo("false", rotuloDesempilhado);
    }

    public void handleAndOperator(Token token) throws SemanticError { // #116
        ExpressionType tipoDesemp1 = pilhaTipos.pop();
        ExpressionType tipoDesemp2 = pilhaTipos.pop();

        if (tipoDesemp1 != ExpressionType.BOOL || tipoDesemp2 != ExpressionType.BOOL)
            throw new SemanticError("Operação lógica inválida para " + tipoDesemp2.getFormatName() + " e " +
            tipoDesemp1.getFormatName(), lineCalculator.getLine(token.getPosition()));

        pilhaTipos.push(ExpressionType.BOOL);

        ocGenerator.and();
    }

    public void handleOrOperator(Token token) throws SemanticError{ // #117
        ExpressionType tipoDesemp1 = pilhaTipos.pop();
        ExpressionType tipoDesemp2 = pilhaTipos.pop();

        if (tipoDesemp1 != ExpressionType.BOOL || tipoDesemp2 != ExpressionType.BOOL)
            throw new SemanticError("Operação lógica inválida para " + tipoDesemp2.getFormatName() + " e " +
            tipoDesemp1.getFormatName(), lineCalculator.getLine(token.getPosition()));

        pilhaTipos.push(ExpressionType.BOOL);

        ocGenerator.or();
    }

    public void handleBoolean(Token token) { // #118 e #119
        pilhaTipos.push(ExpressionType.BOOL);

        String valorConstante = token.getLexeme();

        if (valorConstante.equalsIgnoreCase("true"))
            ocGenerator.carregaTrue();
        else
            ocGenerator.carregaFalse();
    }

    public void handleNotOperator(Token token) throws SemanticError { // #120
        ExpressionType tipoDesemp = pilhaTipos.peek();

        if (tipoDesemp != ExpressionType.BOOL)
            throw new SemanticError("Operação de negação lógica inválida para " + tipoDesemp.getFormatName(),
            lineCalculator.getLine(token.getPosition()));

        ocGenerator.not();
    }

    public void handleRelationalOperator(Token token) { // #121
        operadorRelacional = RelationalOperator.fromSymbol(token.getLexeme());
    }

    public void handleRelationalOperation(Token token) throws SemanticError { // #122
        ExpressionType tipoDesemp1 = pilhaTipos.pop();
        ExpressionType tipoDesemp2 = pilhaTipos.pop();

        if ((tipoDesemp1 != tipoDesemp2) || (tipoDesemp1 == ExpressionType.BOOL || tipoDesemp2 == ExpressionType.BOOL))
            throw new SemanticError("Operação relacional inválida para " + tipoDesemp2.getFormatName() + " e " +
            tipoDesemp1.getFormatName(), lineCalculator.getLine(token.getPosition()));
            
        pilhaTipos.push(ExpressionType.BOOL);

        switch (operadorRelacional.getSymbol()) {
            case "==":
                ocGenerator.igualA();
                break;
            case ">":
                ocGenerator.maiorQue();
                break;
            case "<":
                ocGenerator.menorQue();
                break;
            case "!=":
                ocGenerator.diferenteDe();
                break;
        }
    }

    public boolean verifyValidNumericType(ExpressionType et) {
        return (et != ExpressionType.STRING && et != ExpressionType.BOOL);
    }

    public void handleAddition(Token token) throws SemanticError { // #123
        ExpressionType tipoDesemp1 = pilhaTipos.pop();
        ExpressionType tipoDesemp2 = pilhaTipos.pop();

        if (!verifyValidNumericType(tipoDesemp1) || !verifyValidNumericType(tipoDesemp2))
            throw new SemanticError("Operação de adição inválida para " + tipoDesemp2.getFormatName() + " e " + tipoDesemp1.getFormatName(), lineCalculator.getLine(token.getPosition()));

        ExpressionType tipoResultante;

        if (tipoDesemp1 == ExpressionType.FLOAT64 || tipoDesemp2 == ExpressionType.FLOAT64)
            tipoResultante = ExpressionType.FLOAT64;
        else
            tipoResultante = ExpressionType.INT64;

        pilhaTipos.push(tipoResultante);
        ocGenerator.adicao();
    }

    public void handleSubtraction(Token token) throws SemanticError { // #124
        ExpressionType tipoDesemp1 = pilhaTipos.pop();
        ExpressionType tipoDesemp2 = pilhaTipos.pop();
        ExpressionType tipoResultante;

        if (!verifyValidNumericType(tipoDesemp1) || !verifyValidNumericType(tipoDesemp2))
            throw new SemanticError("Operação de subtração inválida para " + tipoDesemp2.getFormatName() + " e " +
            tipoDesemp1.getFormatName(), lineCalculator.getLine(token.getPosition()));

        if (tipoDesemp1 == ExpressionType.FLOAT64 || tipoDesemp2 == ExpressionType.FLOAT64)
            tipoResultante = ExpressionType.FLOAT64;
        else
            tipoResultante = ExpressionType.INT64;

        pilhaTipos.push(tipoResultante);
        ocGenerator.subtracao();
    }

    public void handleMultiplication(Token token) throws SemanticError { // #125
        ExpressionType tipoDesemp1 = pilhaTipos.pop();
        ExpressionType tipoDesemp2 = pilhaTipos.pop();
        ExpressionType tipoResultante;

        if (!verifyValidNumericType(tipoDesemp1) || !verifyValidNumericType(tipoDesemp2))
            throw new SemanticError("Operação de multiplicação inválida para " + tipoDesemp2.getFormatName() + " e " +
            tipoDesemp1.getFormatName(), lineCalculator.getLine(token.getPosition()));
            
        if (tipoDesemp1 == ExpressionType.FLOAT64 || tipoDesemp2 == ExpressionType.FLOAT64)
            tipoResultante = ExpressionType.FLOAT64;
        else
            tipoResultante = ExpressionType.INT64;

        pilhaTipos.push(tipoResultante);
        ocGenerator.multiplicacao();
    }

    public void handleDivision(Token token) throws SemanticError { // #126
        ExpressionType tipoDesemp1 = pilhaTipos.pop();
        ExpressionType tipoDesemp2 = pilhaTipos.pop();

        if (!verifyValidNumericType(tipoDesemp1) || !verifyValidNumericType(tipoDesemp2))
            throw new SemanticError("Operação de divisão inválida para " + tipoDesemp2.getFormatName() + " e " +
            tipoDesemp1.getFormatName(), lineCalculator.getLine(token.getPosition()));

        ExpressionType tipoResultante = ExpressionType.FLOAT64;
        pilhaTipos.push(tipoResultante);
        ocGenerator.divisao();
    }

    public void handleIdentifier(Token token) throws SemanticError { // #127

        verifyIdentifierExistence(token.getLexeme(), token.getPosition());
        
        Identifier id = tabelaSimbolos.get(token.getLexeme());

        pilhaTipos.push(id.getType());

        ocGenerator.carregaValorVariavel(id.getName());

        if (id.getType() == ExpressionType.INT64)
            ocGenerator.paraFloat();    
    }

    public void handleIntExpression(Token token) { // #128
        pilhaTipos.push(ExpressionType.INT64);

        String valorConstante = token.getLexeme();
        ocGenerator.carregaInt(valorConstante);
        ocGenerator.paraFloat();
    }

    public void handleFloatExpression(Token token) { // #129
        pilhaTipos.push(ExpressionType.FLOAT64);

        String valorConstante = token.getLexeme().replace(",", ".");
        ocGenerator.carregaFloat(valorConstante);
    }

    public void handleStringExpression(Token token) { // #130
        pilhaTipos.push(ExpressionType.STRING);

        String valorConstante = token.getLexeme();
        ocGenerator.carregaString(valorConstante);
    }

    public void handleInversionExpression(Token token) throws SemanticError{ // #131
        ExpressionType tipoDesemp = pilhaTipos.peek();

        if (!verifyValidNumericType(tipoDesemp))
            throw new SemanticError("Operação de inversão inválida para " + tipoDesemp.getFormatName(),
            lineCalculator.getLine(token.getPosition()));

        ocGenerator.carregaFloat("-1.0");
        ocGenerator.multiplicacao();
    }

    public String getCodigoObjeto() {
        return ocGenerator.getCodigoObjeto();
    }

    private String getPrefixIdentifier(String string) {
        return string.substring(0, 2);
    }
}
