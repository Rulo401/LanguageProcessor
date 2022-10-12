package com.casamayor.raul.common;

/**
 * @author Raúl Casamayor Navas
 * @version 0.1
 * @since 05/10/2022
 */
public class Token {
    
    private int id;
    private Object attribute;

    @SuppressWarnings("unused")
    private Token(){}

    /**
     * Token constructor.
     * @param id Token id code
     * @param attribute Token attribute
     */
    public Token(int id, Object attribute){
        switch (id) {
            case Constants.TokensCode.IDENT: 
                if(!(attribute instanceof Integer)){
                    //lanzar excepcion
                }
                break;
            case Constants.TokensCode.C_ENT: 
                if(!(attribute instanceof Integer)){
                    //lanzar excepcion
                }
                break;
            case Constants.TokensCode.C_CAD: 
                if(!(attribute instanceof String)){
                    //lanzar excepcion
                }
                break;
            default:
                if(attribute != null){
                    //lanzar excepcion
                }
                break;
        }
        this.id = id;
        this.attribute = attribute;
    }

    /**
     * Token id getter.
     * @return Id
     */
    public int getId(){
        return id;
    }

    /**
     * Token attribute getter.
     * @return Attribute
     */
    public Object getAttribute(){
        return attribute;
    }
}
