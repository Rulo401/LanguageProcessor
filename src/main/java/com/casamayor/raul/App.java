package com.casamayor.raul;

import java.io.IOException;

import com.casamayor.raul.components.AnalizadorLexico;
import com.casamayor.raul.components.GestorTS;
import com.casamayor.raul.components.syntax.AnalizadorSintactico;
import com.casamayor.raul.exceptions.PDLException;

/**
* Language processor for JavaScript-PDL
* @author Raúl Casamayor Navas
* @version 0.3
* @since 29/11/2022
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
            synAnalyzer = new AnalizadorSintactico(lexAnalyzer);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error ocurred while creating the Lexical analyzer");
            System.exit(1);
        }
        
        try{
            System.out.println(synAnalyzer.parse());
        }catch(PDLException e){
            e.printStackTrace();
        }finally{
            synAnalyzer.close();
            lexAnalyzer.close();
            gts.closeCurrentST();
        }
        
        /** Main para analizador lexico
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
        }*/
    }
}