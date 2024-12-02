package com.pilador.compilercore;

import com.pilador.compilercore.constants.Constants;
import com.pilador.compilercore.errors.SemanticError;

public class Semantico implements Constants {

    private SemanticContext semanticContext;

    public Semantico(String text) {
        this.semanticContext = new SemanticContext(text);
    }

    public void executeAction(int action, Token token) throws SemanticError {

        //System.out.println("Ação #" + action + ", Token: " + token);

        switch (action) {
            case 100:
                semanticContext.handleProgramHeader();
                break;
            case 101:
                semanticContext.handleProgramEnd();
                break;
            case 102:
                semanticContext.handleIdentifierDeclaration(token);
                break;
            case 103:
                semanticContext.handleAtributionExpression(token);
                break;
            case 104:
                semanticContext.handleStoreIdentifier(token);
                break;
            case 105:
                semanticContext.handleReadAttribution(token);
                break;
            case 106:
                semanticContext.handleReadCommand(token);
                break;
            case 107:
                semanticContext.handleWriteLnCommand();
                break;
            case 108:
                semanticContext.handleWriteCommand(token);
                break;
            case 109:
                semanticContext.handleIfExpression(token);
                break;
            case 110:
                semanticContext.handleElifExpression(token);
                break;
            case 111:
                semanticContext.handleElseExpression(token);
                break;
            case 112:
                semanticContext.handleElifBreakExpression(token);
                break;
            case 113:
                semanticContext.handleRepeatExpression(token);
                break;
            case 114:
                semanticContext.handleWhileExpression(token);
                break;
            case 115:
                semanticContext.handleUntilExpression(token);
                break;
            case 116:
                semanticContext.handleAndOperator(token); 
                break;
            case 117:
                semanticContext.handleOrOperator(token); 
                break;
            case 118:
            case 119:
                semanticContext.handleBoolean(token);
                break;
            case 120:
                semanticContext.handleNotOperator(token); 
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
            case 127:
                semanticContext.handleIdentifier(token);
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
                semanticContext.handleInversionExpression(token);
                break;
        }

    }

    public String getObjectCode() {
        return semanticContext.getCodigoObjeto();
    }

}
