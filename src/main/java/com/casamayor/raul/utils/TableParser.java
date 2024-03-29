package com.casamayor.raul.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

/**
* Object for building the tables needed by the syntax analyser from a csv file.
* @author Raul Casamayor Navas
* @version 1.1
* @since 09/01/2023
*/
public class TableParser {
    
    private InputStreamReader isr;
    private CSVReader csvr;
    
    private String[] headers;
    
    /**
    * Constructor
    * @throws IOException When an IO Error occurs
    */
    public TableParser(String filePath) throws IOException{
        isr = new InputStreamReader(getClass().getClassLoader().getResourceAsStream(filePath));
        csvr = new CSVReader(isr);
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
            if(isr != null) isr.close();
            if(csvr != null) csvr.close();
        } catch (IOException e) {}
    }
}
