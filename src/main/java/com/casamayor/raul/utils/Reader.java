package com.casamayor.raul.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class used to manage the reading of the file.
 * @author Raúl Casamayor Navas
 * @version 1.4
 * @since 09/01/2022
 */
public class Reader {

    private FileReader fr;
    private BufferedReader br;
    
    private int lineCounter, prevLineCounter, lineCursor;
    private String currentLine, previousLine;
    private boolean lineChange;

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
        if(lineChange){
            lineChange = false;
            return '\n';
        }
        if(currentLine == null){
            return null;
        }
        char res = currentLine.charAt(lineCursor);
        lineCursor++;
        if(lineCursor >= currentLine.length()){
            try {
                readNextLine();
            } catch (IOException e) {   
                currentLine = null;
            }
        }
        return res;
    }

    /**
     * Line number getter.
     * @return Current line number
     */
    public int getCurrentLineNumber(){
        return lineChange ? prevLineCounter : lineCounter;
    }

    public String getCurrentLine(){
        return lineChange ? previousLine : currentLine;
    }

    /**
     * Line number getter.
     * @return Current line number
     */
    public int getCurrentTokenLineNumber(){
        return lineCursor==0 ? prevLineCounter : lineCounter;
    }

    /**
     * Line text getter.
     * @return Current line number
     */
    public String getCurrentTokenLine(){
        return lineCursor==0 ? previousLine : currentLine;
    }
    
    /**
     * Method used to skip the rest of the current line
     */
    public void skipLine(){
        try {
            readNextLine();
        } catch (IOException e) {   
            currentLine = null;
        }
    }

    /**
     * Private method used to renew the line buffer after reaching its end
     * @throws IOException
     */
    private void readNextLine() throws IOException{
        lineCursor = 0;
        previousLine = currentLine;
        prevLineCounter = lineCounter;
        currentLine = br.readLine();
        if(currentLine != null){
            lineCounter++;
            lineChange = true;
            if(currentLine.length() == 0){
                String saveLine = previousLine;
                int saveLC = prevLineCounter;
                readNextLine();
                prevLineCounter = saveLC;
                previousLine = saveLine;
            }
        }
    }
}
