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
                break;
            case 108:
                semanticContext.handleWriteCommand(token);
            case 123:
            case 128:
            case 129:
                break;
        }
        
        System.out.println(semanticContext.getCodigoObjeto());

        /* switch (action) {
	case 1: acao_semantica01 ();
        case 2:
	...
	case 5: acao_semantica05 (token);
	default -> mensagem ação ainda não implementada
	}
    }	

    acao_semantica01 () {
        tipo1 = pilha_tipos.pop()
        tipo2 = pilha_tipos.pop()
	// verificar se os tipos estão de acordo com a tabela de tipos
	se tipos incomptíveis
		gerar exceção
	fimse
	empilhar o tipo resultante
        codigo.add("add")
    }

    acao_semantica05 (Token token) {
	pilha_tipos.push ("int64");
	codigo.add("ldc.i8 " + token.getLexeme())
        codigo.add("conv.r8")
    } */
    }

     private void generatedCodeToFile() {
        //String currentDir = Paths.get("").toAbsolutePath().toString();

        String currentDir = System.getProperty("user.dir");
        File sourceFile = new File(currentDir);

        String fileNameWithoutExtension = sourceFile.getName().replaceAll("\\.txt$", "");

      
        File ilFile = new File(sourceFile.getParent(), fileNameWithoutExtension + ".il");

        try {
            // Chama o método para salvar o código objeto gerado
            semanticContext.generatedCodeToFile(ilFile);
            System.out.println("Código objeto salvo em: " + ilFile.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Erro ao salvar o código objeto: " + e.getMessage());
        }
    }

}
