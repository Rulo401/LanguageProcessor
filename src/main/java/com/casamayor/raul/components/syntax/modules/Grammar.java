package com.casamayor.raul.components.syntax.modules;

import java.io.IOException;
import java.util.List;

import com.casamayor.raul.common.Pair;
import com.casamayor.raul.utils.GrammarReader;

/**
* 
* @author Raul Casamayor Navas
* @version 1.0
* @since 29/11/2022
*/
public class Grammar {

    private static final String GRAMMAR_PATH = "src/main/resources/files/grammar.txt";

    private List<Pair<String,Integer>> rules;

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
        return rules.get(nRule).getB();
    }

    /**
     * Antecedent getter
     * @param nRule Rule to get it antecedent
     * @return The antecendent of the rule
     */
    public String getAnt(int nRule){
        return rules.get(nRule).getA();
    }
}
