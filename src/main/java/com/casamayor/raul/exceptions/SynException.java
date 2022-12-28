package com.casamayor.raul.exceptions;

import com.casamayor.raul.common.Constants;
import com.casamayor.raul.common.Token;

/**
 * Exception during syntax analysis
 * @author Raúl Casamayor Navas
 * @version 0.2
 * @since 28/12/2022
 */
public class SynException extends PDLException{

    private Token t;
    private int line;
    
    /**
     * Constructor.
     * @param t Token in which the exception ocurred 
     * @param line Line in which the exception ocurred
     */
    public SynException(Token t, int line){
        this.t = t;
        this.line = line;
    }
    
    @Override
    public void printStackTrace(){
        System.err.println(String.format("Syntax exception on token %s%s at line %d", Constants.getTokensTerminal(t.getId()), t.getAttribute() == null ? "" : String.format(" with attribute %s",t.getAttribute()),line));
        super.printStackTrace();
    }
}
