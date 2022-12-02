package com.casamayor.raul.exceptions;

import com.casamayor.raul.common.Constants;
import com.casamayor.raul.common.Token;

/**
 * Exception during syntax analysis
 * @author Raúl Casamayor Navas
 * @version 0.1
 * @since 27/11/2022
 */
public class SynException extends PDLException{

    private Token t;
    
    /**
     * Constructor.
     * @param t Token in which the exception ocurred 
     */
    public SynException(Token t){
        this.t = t;
    }
    
    @Override
    public void printStackTrace(){
        System.err.println(String.format("Syntax exception on token %s%s", Constants.getTokensTerminal(t.getId()), t.getAttribute() == null ? "" : String.format(" with attribute %s",t.getAttribute())));
        super.printStackTrace();
    }
}
