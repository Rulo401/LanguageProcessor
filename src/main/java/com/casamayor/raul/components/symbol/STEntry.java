package com.casamayor.raul.components.symbol;

import java.util.List;

/**
* Abstraction for an entry pat of a Symbol Table
* @author Raul Casamayor Navas
* @version 0.1
* @since 06/01/2023
*/
public class STEntry {
    public static final String ENTERO = "ent";
    public static final String CADENA = "cad";
    public static final String LOGICO = "bool";
    public static final String FUNCION = "fun";
    public static final String VACIO = "emp";

    private String type;
    private int offset;
    private String returnF;
    private int nParamF;
    private List<String> typeParamF;
    private String labelF;
    
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public int getOffset() {
        return offset;
    }
    public void setOffset(int offset) {
        this.offset = offset;
    }
    public String getReturnF() {
        return returnF;
    }
    public void setReturnF(String returnF) {
        this.returnF = returnF;
    }
    public int getnParamF() {
        return nParamF;
    }
    public void setnParamF(int nParamF) {
        this.nParamF = nParamF;
    }
    public List<String> getTypeParamF() {
        return typeParamF;
    }
    public void setTypeParamF(List<String> typeParamF) {
        this.typeParamF = typeParamF;
    }
    public String getLabelF() {
        return labelF;
    }
    public void setLabelF(String labelF) {
        this.labelF = labelF;
    }
    public String printType() {
        return VACIO.equals(type) ? "-" : type;
    }
    public String printReturnF() {
        return VACIO.equals(returnF) ? "-" : returnF;
    }
}
