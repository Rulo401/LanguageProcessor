package com.casamayor.raul.exceptions;

/**
 * Exception parent for the project
 * @author Raúl Casamayor Navas
 * @version 1.0
 * @since 08/01/2023
 */
public class PDLException extends Exception{
    protected static final String MSG_FORMAT = "%s error at line %d (%s): %s";

    protected String errorExp;
    
    @SuppressWarnings("unused")
    private PDLException(){}

    /**
     * Constructor
     * @param errorExp Exception message
     */
    public PDLException(String errorExp){
        super();
        this.errorExp = errorExp;
    }

    /**
     * Method use to print the error message
     */
    @Override
    public String toString() {
        return String.format("An error ocurred in the engine during the analysis: %s", errorExp);
    }
}
