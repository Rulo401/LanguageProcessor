package com.casamayor.raul.utils;

import java.io.FileWriter;
import java.io.IOException;

import com.casamayor.raul.common.Token;

/**
* Object for writting tokens with the format specified in the project.
* @author Raul Casamayor Navas
* @version 1.0
* @since 12/10/2022
*/
public class TokenWriter {

    private static final String FILE_PATH = "src/main/resources/Tokens.txt";
    private static final String TOKEN_FORMAT = "\n<%d , %s>";


    private FileWriter fw;
    private boolean firstWrite;

    public TokenWriter() throws IOException{
        fw = new FileWriter(FILE_PATH);
        fw.flush();
        firstWrite = true;
    }
    
    public void write(Token t){
        String format = firstWrite ? TOKEN_FORMAT.substring(1) : TOKEN_FORMAT;
        try {
            fw.write(String.format(format, t.getId(), t.getAttribute().toString()));
            firstWrite = false;
        } catch (Exception e) {
            System.err.printf("Error while printing a token (TYPE: %d, VALUE: %s)\n", t.getId(), t.getAttribute().toString());
        }
    }

    public void close(){
        try {
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
