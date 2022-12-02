package com.casamayor.raul.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

/**
* Object for building the tables needed by the syntax analyser from a csv file.
* @author Raul Casamayor Navas
* @version 1.0
* @since 29/11/2022
*/
public class TableParser {
    
    private FileReader fr;
    private BufferedReader br;
    CSVReader csvr;
    
    private String[] headers;
    
    /**
    * Constructor
    * @throws IOException When an IO Error occurs
    */
    public TableParser(String filePath) throws IOException{
        fr = new FileReader(filePath);
        br = new BufferedReader(fr);
        csvr = new CSVReader(fr);
        try {
            headers = csvr.readNext();
        } catch (CsvValidationException e) {
            e.printStackTrace();
            throw new IOException();
        }
    }
    
    /**
     * LR(1) table generator
     * @return Object containing action and goto tables
     * @throws IOException When an IO Error occurs
     */
    public HashMap<Integer,HashMap<String,String>> getTable() throws IOException{
        HashMap<Integer,HashMap<String,String>> table = new HashMap<>();
        String[] state;
        try{
            while((state = csvr.readNext()) != null){
                /**for(int i = 0; i < state.length; i++){
                    System.out.println(state[i]);
                }
                System.out.println(state.length + " " + headers.length);*/
                if(state.length != headers.length){
                    throw new IOException();
                }
                HashMap<String,String> stateMap = new HashMap<>();
                for(int i = 1; i < state.length; i++){
                    stateMap.put(headers[i], "".equals(state[i]) ? null : state[i]);
                }
                table.put(Integer.valueOf(state[0]), stateMap);
            }
        }catch (CsvValidationException e) {
            e.printStackTrace();
            throw new IOException();
        }
        return table;
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
