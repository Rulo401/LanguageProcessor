package com.casamayor.raul.exceptions;

/**
 * Exception during semantic analysis
 * @author Raúl Casamayor Navas
 * @version 1.0
 * @since 08/01/2023
 */
public class SemException extends PDLException{
    private int lineNumber;
    private String textLine;
    
    /**
     * Constructor.
     * @param errorCode Semantic error code
     * @param lineNumber Line in which the error ocurred
     * @param textLine Content of the line
     */
    public SemException(int errorCode, int lineNumber, String textLine){
        super(genErrorMsg(errorCode));
        this.lineNumber = lineNumber;
        this.textLine = textLine;
    }

    /**
     * Method use to print the error message.
     */
    @Override
    public String toString() {
        return String.format(MSG_FORMAT, "Semantic", lineNumber, textLine, errorExp);
    }

    /**
     * Private method used to generate the appropiate error message for the code.
     * @param errorCode Code of the error which threw the exception
     * @return The error message
     */
    private static String genErrorMsg(int errorCode){
        switch(errorCode){
            case 0: return "Attempt to reduce with an unknown semantic rule";
            case 1: return "Error in the function or the instruction";
            case 2: return "Expected a boolean type expression after token \'!\'";
            case 3: return "Arithmetic operation substraction (token\'-\') can only be done between integer expressions";
            case 4: return "Relational operation \'<\' can only be done between integer expressions";
            case 5: return "Wrong number or types of the parameters in the function invocation";
            case 6: return "Type mismatch on the asignation";
            case 7: return "Asignation with sum (token\'+=\') can only be done between integer datatypes";
            case 8: return "Print command needs to be followed by a string or integer expression";
            case 9: return "Input command needs to be followed by a string or integer variable";
            case 10: return "The if condition must be boolean type";
            case 11: return "Error in the if or else body";
            case 12: return "Error in the else body";
            case 13: return "Error in the command";
            case 14: return "Type mismatch between the function return type and the return command value";
            case 15: return "Error in the function body";
            default: return "Unspecific error";
        }
    }
}