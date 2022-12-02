package com.casamayor.raul.exceptions;

/**
 * Exception during lexical analysis
 * @author Raúl Casamayor Navas
 * @version 1.0
 * @since 23/10/2022
 */
public class LexException extends PDLException{
    
    private static final String MSG_FORMAT = "Lexical error at line %d (%s)";

    private int errorCode, lineNumber;
    private String textLine;
    
    /**
     * Constructor
     * @param errorCode Lexical error code
     * @param lineNumber Line in which the error ocurred
     * @param textLine  Content of the line
     */
    public LexException(int errorCode, int lineNumber, String textLine){
        super();
        this.errorCode = errorCode;
        this.lineNumber = lineNumber;
        this.textLine = textLine;
    }

    /**
     * Method use to print the error message
     */
    @Override
    public String toString() {
        return String.format(MSG_FORMAT, lineNumber, textLine);
    }
}
