package com.casamayor.raul.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
* Object for writting symbol tables with the format specified in the project.
* @author Raul Casamayor Navas
* @version 0.1
* @since 12/10/2022
*/
public class STWriter {

    private static final String FILE_PATH = "src/main/resources/STables.txt";
    private static final String ST_HEADER_FORMAT = "%s# %d :\n";
    private static final String ST_LEX_FORMAT = " * LEXEMA : \'%s\'\n";
    private static final String ST_ATR_FORMAT = "   + %s : %s\n";


    private FileWriter fw;

    /**
     * Constructor
     * @throws IOException When creating the file writer or truncating the file.
     */
    public STWriter() throws IOException{
        fw = new FileWriter(FILE_PATH);
    }
    
    /**
     * Method used to write the symbol table into the file using the appropiate format.
     * @param st Symbol table to write on the file
     */
    public void write(List<String> st){
        try {
            fw.write(String.format(ST_HEADER_FORMAT, "GLOBAL", 1));//
            for (String string : st) {
                fw.write(String.format(ST_LEX_FORMAT, string));
            }
        } catch (Exception e) {
            System.err.printf("Error while printing a symbol table \n");
        }
    }

    /**
     * Method use to close the opened streams
     */
    public void close(){
        try {
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
