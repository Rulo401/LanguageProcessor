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
* @version 0.3
* @since 06/01/2023
*/
public class GestorTS {

    private STWriter stw;
    private Map<String,STEntry> tsG, tsL;
    private int offsetG, offsetL;
    private int nTable;
    private boolean zDec;
    
    /**
     * Constructor
     * @throws IOException
     */
    public GestorTS() throws IOException{
        stw = new STWriter();
        tsG = new HashMap<String,STEntry>();
        tsL = null;
        offsetG = 0;
        nTable = 1;
        zDec = false;
    }
    
    /**
     * Method used to add an identifier to the current ST or ask its position it is already on the table.
     * @param id Identifier name
     * @return The position of the identifier on the ST
     */
    public String addIdentifier(String id){
        if(zDec){
            Map<String,STEntry> ts = tsL != null ? tsL : tsG;
            if(ts.containsKey(id)){
                return null;
            }
            ts.put(id, new STEntry());
        }else{
            if((tsL != null && !tsL.containsKey(id)) || !tsG.containsKey(id)){
                STEntry entry = new STEntry();
                entry.setType(STEntry.ENTERO);
                tsG.put(id, entry);
            }
        }
        return id;   
    }

    public void setZDec(boolean zDec){
        this.zDec = zDec;
    }

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

    public void setReturn(String key, String type){
        STEntry entry = (tsL != null && tsL.containsKey(key)) ? tsL.get(key) : tsG.get(key);
        entry.setReturnF(type);
    }

    public void setParameters(String key, int nParameters, List<String> typeParameters){
        STEntry entry = (tsL != null && tsL.containsKey(key)) ? tsL.get(key) : tsG.get(key);
        entry.setnParamF(nParameters);
        entry.setTypeParamF(typeParameters);
    }

    public String getType(String key){
        STEntry entry = (tsL != null && tsL.containsKey(key)) ? tsL.get(key) : tsG.get(key);
        return entry.getType();
    }

    public String getReturnType(String key){
        STEntry entry = (tsL != null && tsL.containsKey(key)) ? tsL.get(key) : tsG.get(key);
        return entry.getReturnF();
    }

    public int getNParameters(String key){
        STEntry entry = (tsL != null && tsL.containsKey(key)) ? tsL.get(key) : tsG.get(key);
        return entry.getnParamF();
    }

    public List<String> getTypeParameters(String key){
        STEntry entry = (tsL != null && tsL.containsKey(key)) ? tsL.get(key) : tsG.get(key);
        return entry.getTypeParamF();
    }

    public void createLocalST() throws PDLException{
        if(tsL != null){
            throw new PDLException();//todo
        }
        tsL = new HashMap<String,STEntry>();
        offsetL = 0;
    }

    /**
     * Methos used to close the current Symbols table
     */
    public void closeCurrentST(){
        if(tsL != null){
            stw.write(tsL, "Tabla local",nTable++);
            tsL = null;
        }else{
            stw.write(tsG, "Tabla global", 1);
            stw.close();
        }
    }
}
