package com.pilador.model;

import java.util.HashMap;
public interface Constants extends ScannerConstants
{
    int EPSILON  = 0;
    int DOLLAR   = 1;

    int t_pr = 2;
    int t_id = 3;
    int t_int = 4;
    int t_float = 5;
    int t_string = 6;
    int t_main = 7;
    int t_end = 8;
    int t_if = 9;
    int t_elif = 10;
    int t_else = 11;
    int t_false = 12;
    int t_true = 13;
    int t_read = 14;
    int t_write = 15;
    int t_writeln = 16;
    int t_repeat = 17;
    int t_until = 18;
    int t_while = 19;
    int t_TOKEN_20 = 20; //"&&"
    int t_TOKEN_21 = 21; //"||"
    int t_TOKEN_22 = 22; //"=="
    int t_TOKEN_23 = 23; //"!="
    int t_TOKEN_24 = 24; //">"
    int t_TOKEN_25 = 25; //"<"
    int t_TOKEN_26 = 26; //"+"
    int t_TOKEN_27 = 27; //"-"
    int t_TOKEN_28 = 28; //"*"
    int t_TOKEN_29 = 29; //"/"
    int t_TOKEN_30 = 30; //","
    int t_TOKEN_31 = 31; //";"
    int t_TOKEN_32 = 32; //"="
    int t_TOKEN_33 = 33; //"("
    int t_TOKEN_34 = 34; //")"


    HashMap<Integer, String> id_string = new HashMap<>() {{
        put(EPSILON, "ε");
        put(DOLLAR, "$");
        put(t_pr, "pr");
        put(t_id, "id");    
        put(t_int, "int");  
        put(t_float,  "float");
        put(t_string, "string");
        put(t_main, "main");
        put(t_end, "end");
        put(t_if, "if");
        put(t_elif, "elif");
        put(t_else, "else");
        put(t_false, "false");
        put(t_true, "true");
        put(t_read, "read");
        put(t_write, "write");
        put(t_writeln, "writeln");
        put(t_repeat, "repeat");
        put(t_until, "until");
        put(t_while, "while");
        put(t_TOKEN_20, "&&");
        put(t_TOKEN_21, "||");
        put(t_TOKEN_22, "==");
        put(t_TOKEN_23, "!=");
        put(t_TOKEN_24, ">");
        put(t_TOKEN_25, "<");
        put(t_TOKEN_26, "+");
        put(t_TOKEN_27, "-");
        put(t_TOKEN_28, "*");
        put(t_TOKEN_29, "/");
        put(t_TOKEN_30, ",");
        put(t_TOKEN_31, ";");
        put(t_TOKEN_32, "=");
        put(t_TOKEN_33, "(");
        put(t_TOKEN_34, ")");
    }};
    
}
