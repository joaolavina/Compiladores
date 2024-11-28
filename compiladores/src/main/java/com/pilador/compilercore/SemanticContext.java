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

    public SemanticContext() {
        this.pilhaTipos = new Stack<ExpressionType>();
        this.pilhaRotulos = new Stack<String>();
        this.listaIdentificadores = new ArrayList<Identifier>();
        this.tabelaSimbolos = new HashMap<String, Identifier>();
        this.ocGenerator = new OCGenerator();
    }

    public void handleProgramHeader() { // #100
        ocGenerator.geraCabecalho("teste");
    }

    public void handleProgramEnd() { // #101
        ocGenerator.geraRodape();
    }

    public void handleIdentifierDeclaration(Token token) { // !! #102
        String ilDeclaration = "";

        for (int i = 0; i < listaIdentificadores.size(); i++) {
            Identifier id = listaIdentificadores.get(i);
            if (tabelaSimbolos.containsKey(id.getName())) {
                throw new IllegalArgumentException(
                        "Linha " + token.getPosition() + ": " + id.getName() + " já declarado");
            } else {
                // ? aqui n sei se era essa forma q vc tinha pensado, mas resolvi assim
                // ? talvez tenha uma forma melhor, mas pelo menos funciona
                // ? acho que o atributo 'declared' de Identifier não precisa ter, é só chegar
                // se ta ou não no map
                String tipo = "";
                String teste = getPrefixIdentifier(id.getName());
                switch (teste) {
                    case "i_":
                        tipo = "int64";
                        break;
                    case "f_":
                        tipo = "float64";
                        break;
                    case "s_":
                        tipo = "string";
                        break;
                    case "b_":
                        tipo = "bool";
                        break;
                }

                id.setTypeString(tipo);
                tabelaSimbolos.put(id.getName(), id);

                if (ilDeclaration != "")
                    ilDeclaration += ", ";

                ilDeclaration += id.getTypeString() + " " + id.getName();
            }
        }

        ocGenerator.declararVariáveis(ilDeclaration);
        listaIdentificadores.removeAll(listaIdentificadores);
    }

    public void handleAtributionExpression(Token token) { // !! #103
        ExpressionType tipoDesemp = pilhaTipos.pop();

        if (tipoDesemp == ExpressionType.INT64)
            ocGenerator.paraInt();

        for (int i = 0; i <= listaIdentificadores.size() - 1; i++) {
            ocGenerator.duplicar();
        }

        for (Identifier identifier : listaIdentificadores) {
            if (!tabelaSimbolos.containsKey(identifier.getName())) {
                throw new IllegalArgumentException(
                        "Linha " + token.getPosition() + ": " + identifier.getName() + "não declarado");
            } else {
                ocGenerator.setValorVariavel(identifier.getName());
            }
        }

        listaIdentificadores.removeAll(listaIdentificadores);
    }

    /*
     * 
     * main
     *      i_integer;
     *      i_integer = 3;
     *      write(i_integer);
     * end
     * 
     * esse código não compila, diz q a variavel n foi declarada, hm, tem q ver isso dps
     */

    public void handleStoreIdentifier(Token token) { // !! #104
        listaIdentificadores.add(new Identifier(token.getLexeme()));
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

    public void handleAndOperator(Token token) { // !! #116
        ExpressionType tipoDesemp1 = pilhaTipos.pop();
        ExpressionType tipoDesemp2 = pilhaTipos.pop();

        if (tipoDesemp1 != ExpressionType.BOOL || tipoDesemp2 != ExpressionType.BOOL)
            throw new IllegalArgumentException(
                    "Operação relacional inválida para " + tipoDesemp2.getName() + " e " + tipoDesemp1.getName());

        pilhaTipos.push(ExpressionType.BOOL);

        ocGenerator.and();
    }

    public void handleOrOperator(Token token) { // !! #117
        ExpressionType tipoDesemp1 = pilhaTipos.pop();
        ExpressionType tipoDesemp2 = pilhaTipos.pop();

        if (tipoDesemp1 != ExpressionType.BOOL || tipoDesemp2 != ExpressionType.BOOL)
            throw new IllegalArgumentException(
                    "Operação relacional inválida para " + tipoDesemp2.getName() + " e " + tipoDesemp1.getName());

        pilhaTipos.push(ExpressionType.BOOL);

        ocGenerator.or();
    }

    // !! Como a gente não armazena os operadores lógicos binários em alguma
    // varíavel, tem que fazer 2 métodos diferentes, paia né

    public void handleBoolean(Token token) { // #118 e #119
        pilhaTipos.push(ExpressionType.BOOL);

        String valorConstante = token.getLexeme();

        if (valorConstante.equalsIgnoreCase("true"))
            ocGenerator.carregaTrue();
        else
            ocGenerator.carregaFalse();
    }

    public void handleNotOperator(Token token) { // !! #120
        ocGenerator.not();
    }

    public void handleRelationalOperator(Token token) { // #121
        operadorRelacional = RelationalOperator.fromSymbol(token.getLexeme());
    }

    public void handleRelationalOperation(Token token) { // #122
        ExpressionType tipoDesemp1 = pilhaTipos.pop();
        ExpressionType tipoDesemp2 = pilhaTipos.pop();

        if (tipoDesemp1 != tipoDesemp2)
            throw new IllegalArgumentException(
                    "Operação relacional inválida para " + tipoDesemp2.getName() + " e " + tipoDesemp1.getName());

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

    public boolean validNumericType(ExpressionType et) {
        return (et != ExpressionType.STRING && et != ExpressionType.BOOL);
    }

    public void handleAddition(Token token) { // #123
        ExpressionType tipoDesemp1 = pilhaTipos.pop();
        ExpressionType tipoDesemp2 = pilhaTipos.pop();

        if (!validNumericType(tipoDesemp1) || !validNumericType(tipoDesemp2))
            throw new IllegalArgumentException(
                    "Operação de adição inválida para " + tipoDesemp2.getName() + " e " + tipoDesemp1.getName());

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

        if (!validNumericType(tipoDesemp1) || !validNumericType(tipoDesemp2))
            throw new IllegalArgumentException(
                    "Operação de subtração inválida para " + tipoDesemp2.getName() + " e " + tipoDesemp1.getName());

        if (tipoDesemp1 == ExpressionType.FLOAT64 || tipoDesemp2 == ExpressionType.FLOAT64)
            tipoResultante = ExpressionType.FLOAT64;
        else
            tipoResultante = ExpressionType.INT64;

        pilhaTipos.push(tipoResultante);
        ocGenerator.subtracao();
    }

    public void handleMultiplication(Token token) { // #125
        ExpressionType tipoDesemp1 = pilhaTipos.pop();
        ExpressionType tipoDesemp2 = pilhaTipos.pop();
        ExpressionType tipoResultante;

        if (!validNumericType(tipoDesemp1) || !validNumericType(tipoDesemp2))
            throw new IllegalArgumentException(
                    "Operação de multiplicação inválida para " + tipoDesemp2.getName() + " e " + tipoDesemp1.getName());

        if (tipoDesemp1 == ExpressionType.FLOAT64 || tipoDesemp2 == ExpressionType.FLOAT64)
            tipoResultante = ExpressionType.FLOAT64;
        else
            tipoResultante = ExpressionType.INT64;

        pilhaTipos.push(tipoResultante);
        ocGenerator.multiplicacao();
    }

    public void handleDivision(Token token) { // #126
        ExpressionType tipoDesemp1 = pilhaTipos.pop();
        ExpressionType tipoDesemp2 = pilhaTipos.pop();

        if (!validNumericType(tipoDesemp1) || !validNumericType(tipoDesemp2))
            throw new IllegalArgumentException(
                    "Operação de divisão inválida para " + tipoDesemp2.getName() + " e " + tipoDesemp1.getName());

        ExpressionType tipoResultante = ExpressionType.FLOAT64;
        pilhaTipos.push(tipoResultante);
        ocGenerator.divisao();
    }

    public void handleIdentifier(Token token) { // !! #127
        if (tabelaSimbolos.containsKey(token.getLexeme())) {
            throw new IllegalArgumentException(
                    "Linha " + token.getPosition() + ": " + token.getLexeme() + " não declarado");
        } else {
            String prefix = getPrefixIdentifier(token.getLexeme());
            switch (prefix) {
                case "i_":
                    pilhaTipos.push(ExpressionType.INT64);
                    break;
                case "f_":
                    pilhaTipos.push(ExpressionType.FLOAT64);
                    break;
                case "s_":
                    pilhaTipos.push(ExpressionType.STRING);
                    break;
                case "b_":
                    pilhaTipos.push(ExpressionType.BOOL);
                    break;
            }

            ocGenerator.getValorVariavel(token.getLexeme());

            if (prefix == "i_")
                ocGenerator.paraFloat();
        }
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

    public void handleNegativeExpression(Token token) { // #131
        ExpressionType tipoDesemp = pilhaTipos.peek();

        if (!validNumericType(tipoDesemp))
            throw new IllegalArgumentException("Operação de inversão inválida para " + tipoDesemp.getName());

        ocGenerator.carregaFloat("-1.0");
        ocGenerator.multiplicacao();
    }

    // geração do arquivo .il

    public void generatedCodeToFile(File ilFile) throws IOException {
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

    // !! método pra extrair o prefixo identificador

    private String getPrefixIdentifier(String string) {
        return string.substring(0, 2);
    }
}
