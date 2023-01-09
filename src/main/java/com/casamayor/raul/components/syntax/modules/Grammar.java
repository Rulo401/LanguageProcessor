package com.casamayor.raul.components.syntax.modules;

import java.io.IOException;
import java.util.List;

import com.casamayor.raul.common.Triple;
import com.casamayor.raul.utils.GrammarReader;

/**
* Object to work as the grammar of the Syntax and Semantic analyzers
* @author Raul Casamayor Navas
* @version 2.0
* @since 09/01/2023
*/
public class Grammar {

    private static final String GRAMMAR_PATH = "files/grammarS.txt";

    private List<Triple<Integer,String,Integer>> rules;

    /**
     * Constructor.
     * @throws IOException When an IO Error occurs
     */
    public Grammar() throws IOException{
        GrammarReader gr = new GrammarReader(GRAMMAR_PATH);
        rules = gr.getRules();
        gr.close();
    }

    /**
     * Number of consequents getter
     * @param nRule Rule to get it number of consequents
     * @return The number of consequents of the rule
     */
    public int getNCons(int nRule){
        return rules.get(nRule).getC();
    }

    /**
     * Antecedent getter
     * @param nRule Rule to get it antecedent
     * @return The antecendent of the rule
     */
    public String getAnt(int nRule){
        return rules.get(nRule).getB();
    }

    /**
     * Semantic rule getter
     * @param nRule Rule to get it semantic rule
     * @return The semantic rule code of the syntax rule
     */
    public int getSRule(int nRule){
        return rules.get(nRule).getA();
    }
}
