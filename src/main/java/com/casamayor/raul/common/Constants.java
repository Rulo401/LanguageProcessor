package com.casamayor.raul.common;

public class Constants {
    
    public static class TokensCode{
        public static final int PR_INT = 101;
        public static final int PR_BOOL = 102;
        public static final int PR_STRING = 103;
        public static final int PR_IF = 201;
        public static final int PR_ELSE = 202;
        public static final int PR_INPUT = 203;
        public static final int PR_LET = 204;
        public static final int PR_FUN = 205;
        public static final int PR_RET = 206;
        public static final int PR_PRINT = 207;
        public static final int IDENT = 301;
        public static final int C_ENT = 302;
        public static final int C_CAD = 303;
        public static final int PCOMA = 801;
        public static final int APARENTESIS = 802;
        public static final int CPARENTESIS = 803;
        public static final int COMA = 804;
        public static final int ALLAVE = 805;
        public static final int CLLAVE = 806;
        public static final int ASIG = 811;
        public static final int ASIGPLUS = 812;
        public static final int OA_RESTA = 821;
        public static final int OR_MENOR = 822;
        public static final int OL_NEG = 823;
        public static final int EOF = 999;
    }
    
    public static String getTokensTerminal(int tcode){
        switch(tcode){
            case TokensCode.PR_INT: return "int";
            case TokensCode.PR_BOOL: return "boolean";
            case TokensCode.PR_STRING: return "string";
            case TokensCode.PR_IF: return "if";
            case TokensCode.PR_ELSE: return "else";
            case TokensCode.PR_INPUT: return "input";
            case TokensCode.PR_LET: return "let";
            case TokensCode.PR_FUN: return "function";
            case TokensCode.PR_RET: return "return";
            case TokensCode.PR_PRINT: return "print";
            case TokensCode.IDENT: return "id";
            case TokensCode.C_ENT: return "ent";
            case TokensCode.C_CAD: return "cad";
            case TokensCode.PCOMA: return ";";
            case TokensCode.APARENTESIS: return "(";
            case TokensCode.CPARENTESIS: return ")";
            case TokensCode.COMA: return ",";
            case TokensCode.ALLAVE: return "{";
            case TokensCode.CLLAVE: return "}";
            case TokensCode.ASIG: return "=";
            case TokensCode.ASIGPLUS: return "+=";
            case TokensCode.OA_RESTA: return "-";
            case TokensCode.OR_MENOR: return "<";
            case TokensCode.OL_NEG: return "!";
            case TokensCode.EOF: return "$";
            default: return null;
        }
    }
    
    public static class Restriction{
        public static final int C_CAD_LENGTH = 64;
        public static final int C_ENT_MAX_VALUE = 32767;
    }
}
