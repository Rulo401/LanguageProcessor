package com.casamayor.raul.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.casamayor.raul.common.Triple;

/**
* Reader for the LR(1) grammar.
* @author Raul Casamayor Navas
* @version 1.1
* @since 09/01/2023
*/
public class GrammarReader {
    
    private InputStreamReader isr;
    private BufferedReader br;

    /**
     * Constructor.
     * @throws IOException When an IO Error occurs
     */
    public GrammarReader(String filePath) throws IOException{
        isr = new InputStreamReader(getClass().getClassLoader().getResourceAsStream(filePath));
        br = new BufferedReader(isr);
    }

    /**
     * Method used to retrieve the rules.
     * @return List with the head of rule and it number of consecuences
     * @throws IOException When an IO Error occurs
     */
    public List<Triple<Integer,String,Integer>> getRules() throws IOException{
        List<Triple<Integer,String,Integer>> rules = new ArrayList<>();
        String line;
        while((line = br.readLine()) != null){
            String[] ls = line.split(" -> ");
            if(ls.length != 2) throw new IOException();
            String[] cons = ls[1].split(" ");
            if(cons.length < 1) throw new IOException();
            String[] sa = ls[0].split(" ");
            if(sa.length != 2) throw new IOException();
            rules.add(new Triple<Integer,String,Integer>(Integer.valueOf(sa[0]), sa[1], (cons.length == 1 && "lambda".equals(cons[0])) ? 0 : cons.length));
        }
        return rules;
    }

    /**
     * Method use to close the opened streams
     */
    public void close(){
        try {
            if(isr != null) isr.close();
            if(br != null) br.close();
        } catch (IOException e) {}
    }
}
