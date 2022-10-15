package com.casamayor.raul.components;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.casamayor.raul.common.Constants;
import com.casamayor.raul.common.SAPair;
import com.casamayor.raul.common.Token;
import com.casamayor.raul.exceptions.TestExcp;
import com.casamayor.raul.utils.Reader;
import com.casamayor.raul.utils.TokenWriter;

/**
 * Class that models a lexical analyzer (part of a language processor).
 * @author Raúl Casamayor Navas
 * @version 1.1
 * @since 15/10/2022
 */
public class AnalizadorLexico {
    
    private Reader rd;
    private TokenWriter tw;
    private AFD afd;
    private GestorTS gts;

    private Character char_read;
    private Integer current_state;
    private Character action;

    private int value;
    private StringBuilder lex;

    @SuppressWarnings("unused")
    private AnalizadorLexico(){}

    /**
     * Constructor
     * @throws IOException
     */
    public AnalizadorLexico(String filename, GestorTS gts) throws IOException{
        rd = new Reader(filename);
        tw = new TokenWriter();
        afd = new AFD();
        current_state = 0;
        char_read = rd.getNextChar();
        this.gts = gts;
    }

    /**
     * Method used to ask the lexical analyzer for a token
     * @return Next token object or null if there are no tokens left
     * @throws TestExcp
     */
    public Token nextToken() throws TestExcp{
        Token t;
        if(char_read == null){
            return null;
        }
        while(current_state/100 < 1){ 
            SAPair transition = afd.nextTransition(current_state, char_read);
            current_state = transition.getState();
            action = transition.getAction();

            if(current_state == null){
                throw new TestExcp();
            }

            if(action == null) continue;
            switch(action){
                case 'L':
                    lex = new StringBuilder();
                    lex.append(char_read);
                    break;
                case 'l': 
                    lex.append(char_read);
                    break;
                case 'V': 
                    value = Character.getNumericValue(char_read); 
                    break;
                case 'v':
                    value *= 10; 
                    value += Character.getNumericValue(char_read);
                    break;
                case 'S': rd.skipLine();
            }
            char_read = rd.getNextChar();
        }
        t = generateToken(current_state);
        tw.write(t);
        current_state = 0;
        action = null;
        return t;
    }

    /**
     * Method used to close the LexAnalyzer
     */
    public void close(){
        tw.close();
    }

    private Token generateToken(int state){
        Token t;
        if(state == 104){
            String l = lex.toString();
            switch(l.toLowerCase()){
                case "int": t = new Token(Constants.TokensCode.PR_INT, null); break;
                case "boolean": t = new Token(Constants.TokensCode.PR_BOOL, null); break;
                case "string": t = new Token(Constants.TokensCode.PR_STRING, null); break;
                case "if": t = new Token(Constants.TokensCode.PR_IF, null); break;
                case "else": t = new Token(Constants.TokensCode.PR_ELSE, null); break;
                case "input": t = new Token(Constants.TokensCode.PR_INPUT, null); break;
                case "let": t = new Token(Constants.TokensCode.PR_LET, null); break;
                case "function": t = new Token(Constants.TokensCode.PR_FUN, null); break;
                case "return": t = new Token(Constants.TokensCode.PR_RET, null); break;
                case "print": t = new Token(Constants.TokensCode.PR_PRINT, null); break;
                default: t = new Token(Constants.TokensCode.IDENT, gts.addIdentifier(l));
            }
            return t;
        }
        switch(state){
            case 101: t = new Token(Constants.TokensCode.C_ENT, value); break;
            case 102: t = new Token(Constants.TokensCode.ASIGPLUS, null); break;
            case 103: t = new Token(Constants.TokensCode.IDENT, gts.addIdentifier(lex.toString())); break;
            case 105: t = new Token(Constants.TokensCode.C_CAD, lex.toString()); break;
            case 106: t = new Token(Constants.TokensCode.ALLAVE, null); break;
            case 107: t = new Token(Constants.TokensCode.CLLAVE, null); break;
            case 108: t = new Token(Constants.TokensCode.APARENTESIS, null); break;
            case 109: t = new Token(Constants.TokensCode.CPARENTESIS, null); break;
            case 110: t = new Token(Constants.TokensCode.PCOMA, null); break;
            case 111: t = new Token(Constants.TokensCode.COMA, null); break;
            case 112: t = new Token(Constants.TokensCode.ASIG, null); break;
            case 113: t = new Token(Constants.TokensCode.OA_RESTA, null); break;
            case 114: t = new Token(Constants.TokensCode.OL_NEG, null); break;
            case 115: t = new Token(Constants.TokensCode.OR_MENOR, null); break;
            default: t = null;
        }
        return t;
    }

    private class AFD{

        private List<Character> spChars;

        public AFD(){
            Character[] sp = {' ','¡','!','#','%','&','(',')','*','+',',','-','.','/',':',';','<','=','>','?','¿','[',']','^','_','{','|','}','~','@','"','·'};
            spChars = Arrays.asList(sp);
        }

        public SAPair nextTransition(int current_state, char char_read){
            switch (current_state) {
                case 0: return nextTransitionF0(char_read);
                case 1: return nextTransitionF1(char_read);
                case 2: return nextTransitionF2(char_read);
                case 3: return nextTransitionF3(char_read);
                case 4: return nextTransitionF4(char_read);
                case 5: return nextTransitionF5(char_read);
                case 6: return nextTransitionF6(char_read);
                case 7: return nextTransitionF7(char_read);
                default: return new SAPair(null, null);
            }
        }

        private SAPair nextTransitionF0(char char_read){
            if(Character.isWhitespace(char_read)){
                return new SAPair(0, 'R');//'R' is symbolic
            }
            if(Character.isDigit(char_read)){
                return new SAPair(1, 'V');
            }
            if(Character.isUpperCase(char_read) || char_read == '_'){
                return new SAPair(3, 'L');
            }
            if(Character.isLowerCase(char_read)){
                return new SAPair(4, 'L');
            }
            int state;
            switch(char_read){
                case '+': state = 2; break;
                case '\'': state = 5; break;
                case '/': state = 6; break;
                case '{': state = 106; break;
                case '}': state = 107; break;
                case '(': state = 108; break;
                case ')': state = 109; break;
                case ';': state = 110; break;
                case ',': state = 111; break;
                case '=': state = 112; break;
                case '-': state = 113; break;
                case '!': state = 114; break;
                case '<': state = 115; break;
                default: return new SAPair(null, null);
            }
            return new SAPair(state, 'R');//'R' is symbolic
        }
        private SAPair nextTransitionF1(char char_read){
            if(Character.isDigit(char_read)){
                return new SAPair(1, 'v');
            }
            return new SAPair(101, null);
        }
        private SAPair nextTransitionF2(char char_read){
            return new SAPair(char_read == '=' ? 102 : null, 'R');
        }
        private SAPair nextTransitionF3(char char_read){
            if(!(Character.isLetterOrDigit(char_read) || char_read == '_')){
                return new SAPair(103,null);
            }
            return new SAPair(3, 'l');
        }
        private SAPair nextTransitionF4(char char_read){
            if(Character.isLowerCase(char_read)){
                return new SAPair(4, 'l');
            }
            if(Character.isLetterOrDigit(char_read) || char_read == '_'){
                return new SAPair(3, 'l');
            }
            return new SAPair(104, null);
        }
        private SAPair nextTransitionF5(char char_read){
            Integer state;
            Character act;
            if(char_read == '\''){
                state = 105;
                act = 'R';
            }else if(Character.isLetterOrDigit(char_read) || spChars.contains(char_read)){
                state = 5;
                act = 'l';
            }else{
                state = null;
                act = null;
            }
            return new SAPair(state, act);
        }
        private SAPair nextTransitionF6(char char_read){
            return new SAPair(char_read == '/' ? 7 : null, 'R');
        }
        private SAPair nextTransitionF7(char char_read){
            return new SAPair(0, 'S');
        }
    }
}
