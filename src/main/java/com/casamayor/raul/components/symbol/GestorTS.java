package com.casamayor.raul.components.symbol;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.casamayor.raul.exceptions.PDLException;
import com.casamayor.raul.utils.STWriter;

/**
* Class responsible of manage the Symbols tables around the Language processor.
* @author Raul Casamayor Navas
* @version 1.0
* @since 08/01/2023
*/
public class GestorTS {

    private STWriter stw;
    private Map<String,STEntry> tsG, tsL;
    private int offsetG, offsetL;
    private int nTable;
    private boolean zDec;
    private String associatedFun;
    
    /**
     * Constructor
     * @throws IOException
     */
    public GestorTS() throws IOException{
        stw = new STWriter();
        tsG = new HashMap<String,STEntry>();
        tsL = null;
        offsetG = 0;
        nTable = 2;
        zDec = false;
        associatedFun = null;
    }
    
    /**
     * Method used to add an identifier to the current ST or ask its position it is already on the table.
     * @param id Identifier name
     * @return The position of the identifier on the ST or NULL if an error occurs
     */
    public String addIdentifier(String id){
        if(zDec){
            Map<String,STEntry> ts = tsL != null ? tsL : tsG;
            if(ts.containsKey(id)){
                return null;
            }
            ts.put(id, new STEntry());
        }else{
            if((tsL == null || !tsL.containsKey(id)) && !tsG.containsKey(id)){
                STEntry entry = new STEntry();
                entry.setType(STEntry.ENTERO);
                entry.setOffset(offsetG++);
                tsG.put(id, entry);
            }
        }
        return id;   
    }

    /**
     * Declaration zone setter.
     */
    public void setZDec(boolean zDec){
        this.zDec = zDec;
    }

    /**
     * Method used to add the type and offset attributes to the id entry.
     * @param key Id map key
     * @param type Type value
     * @param offset Offset increment value
     */
    public void setTypeOffset(String key, String type, int offset){
        STEntry entry;
        int off;
        if(tsL != null && tsL.containsKey(key)){
            entry = tsL.get(key);
            off = offsetL;
            offsetL += offset;
        }else{
            entry = tsG.get(key);
            off = offsetG;
            offsetG += offset;
        }
        entry.setType(type);
        if(STEntry.FUNCION.equals(type)){
            entry.setLabelF(key);
        }else{
            entry.setOffset(off);
        }
    }

    /**
     * Method used to add the return type attribute to the id entry.
     * @param key Id map key
     * @param type Return type value
     */
    public void setReturn(String key, String type){
        tsG.get(key).setReturnF(type);
    }

    /**
     * Method used to add the number of paramters and parameters type attributes to the id entry.
     * @param key Id map key
     * @param nParameters Number of parameters
     * @param typeParameters List with the types
     */
    public void setParameters(String key, int nParameters, List<String> typeParameters){
        STEntry entry = tsG.get(key);
        entry.setnParamF(nParameters);
        entry.setTypeParamF(typeParameters);
    }

    /**
     * Identifier type getter.
     * @param key Id map key
     * @return Type value of the identifier
     */
    public String getType(String key){
        STEntry entry = (tsL != null && tsL.containsKey(key)) ? tsL.get(key) : tsG.get(key);
        return entry.getType();
    }

    /**
     * Current area return type getter.
     * @return Return type value of the current area
     */
    public String getReturnType(){
        return tsL != null ? tsG.get(associatedFun).getReturnF() : STEntry.VACIO;
    }

    /**
     * Identifier return type getter.
     * @param key Id map key
     * @return Return type value of the identifier
     */
    public String getReturnType(String key){
        return tsG.get(key).getReturnF();
    }

    /**
     * Identifier number of parameters getter.
     * @param key Id map key
     * @return Number of parameters of the identifier
     */
    public int getNParameters(String key){
        return tsG.get(key).getnParamF();
    }

    /**
     * Identifier parameters types getter.
     * @param key Id map key
     * @return A list with the parameters types of the identifier
     */
    public List<String> getTypeParameters(String key){
        return tsG.get(key).getTypeParamF();
    }

    /**
     * Method to create a new local symbol table.
     * @param functionKey Function id key which generates the table
     * @throws PDLException If a local symbol table already exists
     */
    public void createLocalST(String functionKey) throws PDLException{
        if(tsL != null){
            throw new PDLException("Attempt to create a local ST when it already exists.");
        }
        tsL = new HashMap<String,STEntry>();
        offsetL = 0;
        associatedFun = functionKey;
    }

    /**
     * Methos used to close the current Symbols table
     */
    public void closeCurrentST(){
        if(tsL != null){
            stw.write(tsL, "Tabla local",nTable++);
            tsL = null;
            associatedFun = null;
        }else{
            stw.write(tsG, "Tabla global", 1);
            stw.close();
        }
    }
}
