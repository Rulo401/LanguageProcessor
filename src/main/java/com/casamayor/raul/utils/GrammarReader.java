package com.casamayor.raul.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.casamayor.raul.common.Pair;

/**
* Reader for the LR(1) grammar.
* @author Raul Casamayor Navas
* @version 1.0
* @since 28/11/2022
*/
public class GrammarReader {
    
    private FileReader fr;
    private BufferedReader br;

    /**
     * Constructor.
     * @throws IOException When an IO Error occurs
     */
    public GrammarReader(String filePath) throws IOException{
        fr = new FileReader(filePath);
        br = new BufferedReader(fr);
    }

    /**
     * Method used to retrieve the rules.
     * @return List with the head of rule and it number of consecuences
     * @throws IOException When an IO Error occurs
     */
    public List<Pair<String,Integer>> getRules() throws IOException{
        List<Pair<String,Integer>> rules = new ArrayList<>();
        String line;
        while((line = br.readLine()) != null){
            String[] ls = line.split(" -> ");
            if(ls.length != 2) throw new IOException();
            String[] cons = ls[1].split(" ");
            if(cons.length < 1) throw new IOException();
            rules.add(new Pair<String,Integer>(ls[0], (cons.length == 1 && "lambda".equals(cons[0])) ? 0 : cons.length));
        }
        return rules;
    }

    /**
     * Method use to close the opened streams
     */
    public void close(){
        try {
            fr.close();
            br.close();
        } catch (IOException e) {}
    }
}
