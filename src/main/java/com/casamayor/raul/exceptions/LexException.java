package com.casamayor.raul.exceptions;

import com.casamayor.raul.common.Constants;

/**
 * Exception during lexical analysis
 * @author Raúl Casamayor Navas
 * @version 1.0
 * @since 08/01/2023
 */
public class LexException extends PDLException{
    private int lineNumber;
    private String textLine;
    
    /**
     * Constructor.
     * @param errorCode Lexical error code
     * @param lineNumber Line in which the error ocurred
     * @param textLine Content of the line
     * @param c The unexpected character read
     */
    public LexException(int errorCode, int lineNumber, String textLine, String c){
        super(genErrorMsg(errorCode,c));
        this.lineNumber = lineNumber;
        this.textLine = textLine;
    }

    /**
     * Method use to print the error message.
     */
    @Override
    public String toString() {
        return String.format(MSG_FORMAT, "Lexical", lineNumber, textLine, errorExp);
    }

    /**
     * Private method used to generate the appropiate error message for the code.
     * @param errorCode Code of the error which threw the exception
     * @param c The unexpected character read
     * @return The error message
     */
    private static String genErrorMsg(int errorCode, String c){
        switch(errorCode){
            case 0: return String.format("Unexpected character found (%s)",c);
            case 1: return String.format("String exceeds the maximum permited length (%d)", Constants.Restriction.C_CAD_LENGTH);
            case 2: return String.format("Number exceeds the maximum permited value (%d)", Constants.Restriction.C_ENT_MAX_VALUE);
            case 3: return String.format("Lexeme %s already exist in the currant table", c);
            default: return "Incompatible character";
        }
    }
}
