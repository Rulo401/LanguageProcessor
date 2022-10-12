package com.casamayor.raul.components;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.casamayor.raul.utils.STWriter;

/**
* Class responsible of manage the Symbols tables around the Language processor.
* @author Raul Casamayor Navas
* @version 0.1
* @since 12/10/2022
*/
public class GestorTS {

    private STWriter stw;
    private List<String> ts;
    
    /**
     * Constructor
     * @throws IOException
     */
    public GestorTS() throws IOException{
        stw = new STWriter();
        ts = new ArrayList<String>();
    }
    
    /**
     * Method used to add an identifier to the current ST or ask its position it is already on the table.
     * @param id Identifier name
     * @return The position of the identifier on the ST
     */
    public int addIdentifier(String id){
        if(!ts.contains(id)){
            ts.add(id);
        }
        return ts.indexOf(id);        
    }

    /**
     * Methos used to close the current Symbols table
     */
    public void closeCurrentST(){
        stw.write(ts);
    }
}
