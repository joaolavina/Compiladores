package com.pilador.compilercore;

public class OCGenerator {

    public String geraCabecalho(String nome) {
        return ".assembly extern mscorlib {}\r\n" + //
                        ".assembly _codigo_objeto{}\r\n" + //
                        ".module _codigo_objeto.exe\r\n" + //
                        ".class public UNICA{\r\n" + //
                        ".method static public void _principal() {\r\n" + //
                        ".entrypoint \r";
    }

    public String geraRodape(){
        return "ret\r\n" + //
                        "}\r\n" + //
                        "} ";
    }

    public String carregaFalse () {
        return "ldc.i4.0";
    }

    public String carregaTrue () {
        return "ldc.i4.1";
    }

}
