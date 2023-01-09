package com.casamayor.raul;

import java.io.IOException;

import com.casamayor.raul.components.AnalizadorLexico;
import com.casamayor.raul.components.symbol.GestorTS;
import com.casamayor.raul.components.syntax.AnalizadorSintactico;
import com.casamayor.raul.exceptions.PDLException;

/**
* Language processor for JavaScript-PDL
* @author Raúl Casamayor Navas
* @version 1.0
* @since 09/01/2023
*/
public class App {
    
    private static GestorTS gts;
    private static AnalizadorLexico lexAnalyzer;
    private static AnalizadorSintactico synAnalyzer;
    
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
        try {
            synAnalyzer = new AnalizadorSintactico(lexAnalyzer,gts);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error ocurred while creating the Lexical analyzer");
            System.exit(1);
        }
        
        try{
            synAnalyzer.parse();//System.out.println(synAnalyzer.parse());
        }catch(PDLException e){
            gts.closeCurrentST();
            e.printStackTrace();
        }finally{
            synAnalyzer.close();
            lexAnalyzer.close();
            gts.closeCurrentST();
        }
    }
}