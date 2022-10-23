package com.casamayor.raul;

import java.io.IOException;

import com.casamayor.raul.components.AnalizadorLexico;
import com.casamayor.raul.components.GestorTS;
import com.casamayor.raul.exceptions.LexException;

/**
 * Language processor for JavaScript-PDL
 * @author Raúl Casamayor Navas
 * @version 0.2
 * @since 13/10/2022
 */
public class App {

    private static GestorTS gts;
    private static AnalizadorLexico lexAnalyzer;
    public static void main( String[] args ){
        try {
            gts = new GestorTS();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error ocurred while creating the Symbol table manager");
            System.exit(1);
        }
        try {
            lexAnalyzer = new AnalizadorLexico(args[0], gts);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error ocurred while creating the Lexical analyzer");
            System.exit(1);
        }
        try{
        while(lexAnalyzer.nextToken() != null){}
        }catch(LexException e){
            System.out.println(e);
        }finally{
            lexAnalyzer.close();
            gts.closeCurrentST();
        }
    }
}