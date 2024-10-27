package com.pilador.compilercore;

import com.pilador.compilercore.constants.Constants;
import com.pilador.compilercore.errors.SemanticError;

public class Semantico implements Constants
{
    public void executeAction(int action, Token token)	throws SemanticError
    {
        System.out.println("Ação #"+action+", Token: "+token);
    }	
}
