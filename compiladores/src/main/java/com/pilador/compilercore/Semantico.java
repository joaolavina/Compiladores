package com.pilador.compilercore;

import java.util.ArrayList;
import java.util.Stack;

import com.pilador.compilercore.constants.Constants;
import com.pilador.compilercore.errors.SemanticError;

public class Semantico implements Constants {

    String operadorRelacional; // operador relacional reconhecido pela ação #121, para uso posterior na ação #122
    String codigoObjeto; // armazenar o código objeto gerado
    Stack<String> pilhaTipos; // determinar o tipo de uma expressão durante a compilação do programa
    Stack<String> pilhaRotulos; // análise dos comandos de seleção e de repetição
    ArrayList<Identifier> listaIdentificadores; // armazenar os identificadores reconhecidos pela ação #104, para uso posterior nas ações #102 a #103
    ArrayList<String> tabelaSimbolos; // armazenar os identificadores declarados (variáveis, ação #102). Cada linha da tabela tem um campo: o identificador da variável declarada 


    public void executeAction(int action, Token token)	throws SemanticError {

        System.out.println("Ação #"+action+", Token: "+token);

        switch (action) {
            case 100:
                System.out.println("a");
        }
        


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

}
