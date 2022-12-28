package com.casamayor.raul.components.syntax;

import java.io.IOException;
import java.util.Deque;
import java.util.LinkedList;

import com.casamayor.raul.common.Constants;
import com.casamayor.raul.common.Token;
import com.casamayor.raul.components.AnalizadorLexico;
import com.casamayor.raul.components.syntax.modules.Grammar;
import com.casamayor.raul.components.syntax.modules.Tables;
import com.casamayor.raul.exceptions.PDLException;
import com.casamayor.raul.exceptions.SynException;
import com.casamayor.raul.utils.ParseWriter;

/**
* Class that models a syntax analyzer (part of a language processor).
* @author Raul Casamayor Navas
* @version 1.3
* @since 28/12/2022
*/
public class AnalizadorSintactico {

    private ParseWriter pw;
    private AnalizadorLexico al;
    private Tables table;
    private Grammar grammar;
    private Deque<String> stack;

    @SuppressWarnings("unused")
    private AnalizadorSintactico(){}
    
    /**
     * Constructor.
     * @param al Lexical analyzer
     * @throws IOException When an IO Error occurs
     */
    public AnalizadorSintactico(AnalizadorLexico al) throws IOException{
        pw = new ParseWriter();
        table = new Tables();
        grammar = new Grammar();
        this.al = al;
        stack = new LinkedList<>();
        stack.push("0"); 
    }

    /**
     * Function used to analyze the syntax of the current file.
     * @return The secuence of rules used during the analysis
     * @throws PDLException If the input file is wrong in terms of lexical and syntax analisis
     */
    public String parse() throws PDLException{
        StringBuilder parse = new StringBuilder();
        Token t = al.nextToken();
        Integer state;
        String action;
        while(true){
            state = Integer.valueOf(stack.peek());
            action = table.accionT(state,Constants.getTokensTerminal(t.getId()));
            if("s".equals(action)){
                stack.push(String.valueOf(t.getId()));
                stack.push(table.gotoT(state, Constants.getTokensTerminal(t.getId())));
                t = al.nextToken();
                continue;
            }
            if(action != null && action.startsWith("r")){
                int nRule = Integer.valueOf(action.substring(1));
                for(int i = 0; i < 2*grammar.getNCons(nRule); i++){
                    stack.pop();
                }
                Integer sj = Integer.valueOf(stack.peek());
                String ant = grammar.getAnt(nRule);
                stack.push(ant);
                stack.push(table.gotoT(sj, ant));
                parse.append(nRule).append(",");
                pw.write(nRule);
                //System.out.println(nRule);
                continue;
            }
            if("a".equals(action)) break;
            throw new SynException(t,al.getLineNumber());
        }
        return parse.deleteCharAt(parse.length()-1).toString();
    }

    /**
     * Method to close the opened streams
     */
    public void close(){
        pw.close();
    }
}
