package com.casamayor.raul.components.syntax.modules;

import java.io.IOException;
import java.util.HashMap;

import com.casamayor.raul.utils.TableParser;

/**
* Abstraction for the LR(1) tables
* @author Raul Casamayor Navas
* @version 1.0
* @since 29/11/2022
*/
public class Tables {

    private static final String TABLE_PATH = "files/tables.csv";

    private HashMap<Integer,HashMap<String,String>> table;
    
    /**
     * Constructor.
     * @throws IOException When an IO Error occurs
     */
    public Tables() throws IOException{
        TableParser tp = new TableParser(TABLE_PATH);
        table = tp.getTable();
        tp.close();
    }
    
    /**
     * Function to access to the action table.
     * @param state Current state of the syntax analysis
     * @param token Last token read
     * @return Next action to be done by the syntax analyzer
     */
    public String accionT(Integer state, String token){
        String act = table.get(state).get(token);
        if(act != null && !act.startsWith("r")){
            act = act.split("\\d*")[0];
        }
        return act;
    }

    /**
     * Function to access to the goto table.
     * @param state Current state of the syntax analysis
     * @param token Last token read
     * @return Next action to be done by the syntax analyzer
     */
    public String gotoT(Integer state, String term){
        String go = table.get(state).get(term);
        if(go == null || go.startsWith("r") || go.startsWith("a")) return null;
        return go.startsWith("s") ? go.substring(1) : go;
    }
}
