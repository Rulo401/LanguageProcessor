package com.casamayor.raul.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import com.casamayor.raul.components.symbol.STEntry;

/**
* Object for writting symbol tables with the format specified in the project.
* @author Raul Casamayor Navas
* @version 1.0
* @since 06/01/2023
*/
public class STWriter {

    private static final String FILE_PATH = "src/main/resources/output/STables.txt";
    private static final String ST_HEADER_FORMAT = "%s# %d :\n";
    private static final String ST_LEX_FORMAT = " * LEXEMA : \'%s\'\n";
    private static final String ST_ATR_CAD_FORMAT = "   + %s : \'%s\'\n";
    private static final String ST_ATR_NUM_FORMAT = "   + %s : %d\n";


    private FileWriter fw;

    /**
     * Constructor
     * @throws IOException When creating the file writer or truncating the file.
     */
    public STWriter() throws IOException{
        fw = new FileWriter(FILE_PATH);
    }
    
    /**
     * Method used to write the symbol table into the file using the appropiate format.
     * @param st Symbol table to write on the file
     */
    public void write(Map<String,STEntry> st, String header, int counter){
        try {
            fw.write(String.format(ST_HEADER_FORMAT, header, counter));//todo
            for (String id : st.keySet()) {
                fw.write(String.format(ST_LEX_FORMAT, id));
                STEntry att = st.get(id);
                fw.write(String.format(ST_ATR_CAD_FORMAT, "tipo", att.printType()));
                if(STEntry.FUNCION.equals(att.getType())){
                    fw.write(String.format(ST_ATR_CAD_FORMAT, "etiqFuncion", att.getLabelF()));
                    fw.write(String.format(ST_ATR_NUM_FORMAT, "numParam", att.getnParamF()));
                    int pNumber = 1;
                    for(String pType : att.getTypeParamF()){
                        fw.write(String.format(ST_ATR_CAD_FORMAT, String.format("tipoParam%d",pNumber++),pType));
                    }
                    fw.write(String.format(ST_ATR_CAD_FORMAT, "tipoRetorno", att.printReturnF()));
                }else{
                    fw.write(String.format(ST_ATR_NUM_FORMAT, "despl", att.getOffset()));
                }
            }
        } catch (Exception e) {
            System.err.printf("Error while printing a symbol table \n");
        }
    }

    /**
     * Method use to close the opened streams
     */
    public void close(){
        try {
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
