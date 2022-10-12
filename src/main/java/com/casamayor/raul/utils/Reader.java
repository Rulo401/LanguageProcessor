package com.casamayor.raul.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Raúl Casamayor Navas
 * @version 1.0
 * @since 12/10/2022
 */
public class Reader {

    private FileReader fr;
    private BufferedReader br;
    
    private int lineCounter;
    private String currentLine;
    private int lineCursor;

    /**
     * Constructor
     * @throws IOException
     */
    public Reader(String filePath) throws IOException{
        fr = new FileReader(filePath);
        br = new BufferedReader(fr);
        lineCounter = 0;
        readNextLine();
    }

    /**
     * Function used to retrieve the next character of the file.
     * @return  The next character or null if eof has been reached
     */
    public Character getNextChar(){
        if(currentLine == null){
            return null;
        }
        char res = currentLine.charAt(lineCursor);
        lineCursor++;
        if(lineCursor >= currentLine.length()){
            
        }
        return res;
    }

    /**
     * Line number getter.
     * @return Current line number
     */
    public int getCurrentLineNumber(){
        return lineCounter;
    }

    /**
     * Private method used to renew the line buffer after reaching its end
     * @throws IOException
     */
    private void readNextLine() throws IOException{
        lineCursor = 0;
        currentLine = br.readLine();
        if(currentLine != null){
            lineCounter++;
        }
    }
}
