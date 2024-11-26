package com.pilador.compilercore;

import java.io.File;
import java.io.IOException;

import com.pilador.compilercore.constants.Constants;
import com.pilador.compilercore.errors.SemanticError;

public class Semantico implements Constants {

    private SemanticContext semanticContext;

    public Semantico () {
        this.semanticContext = new SemanticContext();
    }
  
    public void executeAction(int action, Token token)	throws SemanticError {

        System.out.println("Ação #"+action+", Token: "+ token);

        switch (action) {
            case 100:
                semanticContext.handleProgramHeader();
                break;
            case 101:
                semanticContext.handleProgramEnd();
                generatedCodeToFile();
                break;
            case 107:
                semanticContext.handleWriteLnCommand(token);
                break;
            case 108:
                semanticContext.handleWriteCommand(token);
                break;
            case 118:
            case 119:
                semanticContext.handleBoolean(token);
                break;
            case 121:
                semanticContext.handleRelationalOperator(token);
                break;
            case 122:
                semanticContext.handleRelationalOperation(token);
                break;
            case 123:
                semanticContext.handleAddition(token);
                break;
            case 124:
                semanticContext.handleSubtraction(token);
                break;
            case 125:
                semanticContext.handleMultiplication(token);
                break;
            case 126:
                semanticContext.handleDivision(token);
                break;
            case 128:
                semanticContext.handleIntExpression(token);
                break;
            case 129:
                semanticContext.handleFloatExpression(token);
                break;
            case 130:
                semanticContext.handleStringExpression(token);
                break;
            case 131:
                semanticContext.handleNegativeExpression(token);
                break;
        }

    }

     private void generatedCodeToFile() {
        //String currentDir = Paths.get("").toAbsolutePath().toString();

        String currentDir = System.getProperty("user.dir");
        File sourceFile = new File(currentDir);

        String fileNameWithoutExtension = sourceFile.getName().replaceAll("\\.txt$", "");

      
        File ilFile = new File(sourceFile.getParent(), fileNameWithoutExtension + ".il");

        try {
            semanticContext.generatedCodeToFile(ilFile);
            System.out.println("Código objeto salvo em: " + ilFile.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Erro ao salvar o código objeto: " + e.getMessage());
        }
    }

}
