package com.casamayor.raul.exceptions;

import com.casamayor.raul.common.Constants;
import com.casamayor.raul.common.Token;

/**
 * Exception during syntax analysis
 * @author Raúl Casamayor Navas
 * @version 1.0
 * @since 08/01/2023
 */
public class SynException extends PDLException{
    private int lineNumber;
    private String textLine;
    
    /**
     * Constructor.
     * @param errorCode Syntax error code
     * @param token Last token read
     * @param lineNumber Line in which the error ocurred
     * @param textLine  Content of the line
     */
    public SynException(int errorCode, Token token, int lineNumber, String textLine){
        super(genErrorMsg(errorCode, token));
        this.lineNumber = lineNumber;
        this.textLine = textLine;
    }

    /**
     * Method use to print the error message.
     */
    @Override
    public String toString() {
        return String.format(MSG_FORMAT, "Syntax", lineNumber, textLine, errorExp);
    }

    /**
     * Private method used to generate the appropiate error message for the code.
     * @param errorCode Code of the error which threw the exception
     * @param token Last token read
     * @return The error message
     */
    private static String genErrorMsg(int errorCode, Token token){
        switch(errorCode){
            case 5:
            case 10:
            case 12:
            case 43:
            case 84: return String.format("Expected token id but found token %s", Constants.getTokensTerminal(token.getId()));
            case 6:
            case 7:
            case 56:
            case 57: return String.format("Expected token ( but found token %s", Constants.getTokensTerminal(token.getId()));
            case 15: return String.format("Expected a dataype token (int, boolean or string) but found token %s", Constants.getTokensTerminal(token.getId()));
            case 17:
            case 62:
            case 89: return String.format("Expected token { but found token %s", Constants.getTokensTerminal(token.getId()));
            case 31:
            case 32:
            case 35:
            case 66: return String.format("Expected token ; but found token %s", Constants.getTokensTerminal(token.getId()));
            case 42:
            case 46:
            case 67:
            case 71:
            case 78:
            case 85:
            case 90: return String.format("Expected token ) but found token %s", Constants.getTokensTerminal(token.getId()));
            case 60:
            case 77:
            case 83:
            case 92: return String.format("Expected token } but found token %s", Constants.getTokensTerminal(token.getId()));
            default: return String.format("Found unexpected token (%s)", Constants.getTokensTerminal(token.getId()));
        }
    }
}
