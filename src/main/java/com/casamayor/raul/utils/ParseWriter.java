package com.casamayor.raul.utils;

import java.io.FileWriter;
import java.io.IOException;

/**
* Object for writting the parse file.
* @author Raul Casamayor Navas
* @version 1.0
* @since 21/11/2022
*/
public class ParseWriter {

    private static final String FILE_PATH = "Parse.txt";
    private static final String TYPE = "Ascendente";
    private static final String FORMAT = " %d";



    private FileWriter fw;

    /**
     * Constructor
     * @throws IOException When creating the file writer or truncating the file.
     */
    public ParseWriter() throws IOException{
        fw = new FileWriter(FILE_PATH);
        fw.write(TYPE);
    }
    
    /**
     * Method used to write rule applied during the syntax anylisis.
     * @param rule Rule number
     */
    public void write(int rule){
        try {
            fw.write(String.format(FORMAT, rule));
        } catch (Exception e) {
            System.err.printf("Error while printing a rule in the parse file\n");
        }
    }

    /**
     * Method use to close the opened streams
     */
    public void close(){
        try {
            if(fw != null) fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
