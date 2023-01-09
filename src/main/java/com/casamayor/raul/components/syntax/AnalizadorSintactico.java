package com.casamayor.raul.components.syntax;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import com.casamayor.raul.common.Constants;
import com.casamayor.raul.common.Token;
import com.casamayor.raul.common.Triple;
import com.casamayor.raul.components.AnalizadorLexico;
import com.casamayor.raul.components.symbol.GestorTS;
import com.casamayor.raul.components.symbol.STEntry;
import com.casamayor.raul.components.syntax.modules.Grammar;
import com.casamayor.raul.components.syntax.modules.Tables;
import com.casamayor.raul.exceptions.PDLException;
import com.casamayor.raul.exceptions.SemException;
import com.casamayor.raul.exceptions.SynException;
import com.casamayor.raul.utils.ParseWriter;

/**
* Class that models a syntax analyzer (part of a language processor).
* @author Raul Casamayor Navas
* @version 2.0
* @since 09/01/2023
*/
public class AnalizadorSintactico {

    private ParseWriter pw;
    private AnalizadorLexico al;
    private Tables table;
    private Grammar grammar;
    private Deque<String> stack;

    private AnalizadorSemantico as;

    @SuppressWarnings("unused")
    private AnalizadorSintactico(){}
    
    /**
     * Constructor.
     * @param al Lexical analyzer
     * @throws IOException When an IO Error occurs
     */
    public AnalizadorSintactico(AnalizadorLexico al, GestorTS gts) throws IOException{
        pw = new ParseWriter();
        table = new Tables();
        grammar = new Grammar();
        this.al = al;
        stack = new LinkedList<>();
        stack.push("0"); 
        as = new AnalizadorSemantico(gts);
    }

    /**
     * Function used to analyze the syntax of the current file.
     * @return The secuence of rules used during the analysis
     * @throws PDLException If the input file is wrong in terms of lexical and syntax analisis
     */
    public String parse() throws PDLException{
        StringBuilder parse = new StringBuilder();
        Token t = al.nextToken();
        Integer state;
        String action;
        while(true){
            state = Integer.valueOf(stack.peek());
            action = table.accionT(state,Constants.getTokensTerminal(t.getId()));
            if("s".equals(action)){
                stack.push(String.valueOf(t.getId()));
                as.pushToken(t);//semantico
                stack.push(table.gotoT(state, Constants.getTokensTerminal(t.getId())));
                as.stack.push(null);//semantico
                t = al.nextToken();
                continue;
            }
            if(action != null && action.startsWith("r")){
                int nRule = Integer.valueOf(action.substring(1));
                for(int i = 0; i < 2*grammar.getNCons(nRule); i++){
                    stack.pop();
                }
                Integer sj = Integer.valueOf(stack.peek());
                String ant = grammar.getAnt(nRule);
                stack.push(ant);
                as.reduce(grammar.getSRule(nRule), nRule, 2*grammar.getNCons(nRule));//semantico
                stack.push(table.gotoT(sj, ant));
                parse.append(nRule).append(",");
                pw.write(nRule);
                continue;
            }
            if("a".equals(action)) break;
            throw new SynException(state, t, al.getLineNumber(), al.getLineText());
        }
        return parse.deleteCharAt(parse.length()-1).toString();
    }

    /**
     * Method to close the opened streams
     */
    public void close(){
        pw.close();
    }

    /**
    * Private class that models a semantic analyzer (part of a language processor).
     */
    private class AnalizadorSemantico{
        private static final String OK = "ok";
        private static final String ERROR = "err";

        private LinkedList<Triple<String,String,List<String>>> stack;
        private GestorTS gts;

        private AnalizadorSemantico(GestorTS gts){
            stack = new LinkedList<>();
            stack.push(null);
            this.gts = gts;
        }

        private void pushToken(Token t){
            switch(t.getId()){
                case Constants.TokensCode.IDENT: stack.push(new Triple<String,String,List<String>>(t.getAttribute().toString(), null, null)); break;
                case Constants.TokensCode.PR_LET: gts.setZDec(true, 1); stack.push(null); break;
                case Constants.TokensCode.PR_FUN: gts.setZDec(true, null);
                default: stack.push(null);
            }
        }

        private void reduce(int reductionCode, int nRule, int nPops) throws PDLException{
            switch(reductionCode){
                case 0: break; // regla NT -> NT2 {NT.tipo := NT2.tipo}
                case 1: red1(); break;
                case 2: red2(); break;
                case 3: red3(); break;
                case 4: red4(); break;
                case 5: red5(); break;
                case 6: red6(); break;
                case 7: red7(); break;
                case 8: red8(); break;
                case 9: red9(); break;
                case 10: red10(); break;
                case 11: red11(nPops); break;
                case 12: red12(); break;
                case 13: red13(); break;
                case 14: red14(); break;
                case 15: red15(); break;
                case 16: red16(); break;
                case 17: red17(); break;
                case 18: red18(nPops); break;
                case 19: red19(nRule); break;
                case 20: red20(); break;
                case 21: red21(); break;
                case 22: red22(); break;
                case 23: red23(); break;
                case 24: red24(); break;
                case 25: red25(); break;
                case 26: red26(); break;
                case 27: red27(); break;
                case 28: red28(); break;
                case 29: red29(); break;
                default: throw new SemException(0,al.getLineNumber(),al.getLineText());
            }
        }

        //regla NT -> lambda {NT.tipo := vacio}
        private void red1(){
            stack.push(new Triple<String,String,List<String>>(STEntry.VACIO, null, null));
            stack.push(null);
        }

        //regla NT -> ent {NT.tipo := ent}
        private void red2(){
            stack.pop();
            stack.pop();
            stack.push(new Triple<String,String,List<String>>(STEntry.ENTERO, null, null));
            stack.push(null);
        }
        
        //regla NT -> ( NT2 ) {NT.tipo := NT2.tipo}
        private void red3(){
            String type = null;
            for(int i = 0; i < 6; i++){
                if(i==3){
                    type = stack.pop().getA();
                }else{
                    stack.pop();
                }
            }
            stack.push(new Triple<String,String,List<String>>(type, null, null));
            stack.push(null);
        }

        //regla NT -> cad {NT.tipo := cad}
        private void red4(){
            stack.pop();
            stack.pop();
            stack.push(new Triple<String,String,List<String>>(STEntry.CADENA, null, null));
            stack.push(null);
        }

        //regla NT -> id {NT.tipo := BuscarTipoTS(id.pos)}
        private void red5(){
            stack.pop();
            stack.push(new Triple<String,String,List<String>>(gts.getType(stack.pop().getA()), null, null));
            stack.push(null);
        }

        //regla NT -> NT1 NTb {NT.tipo := if NT1.tipo == tipo_ok AND NTb.tipo != tipo_error then tipo_ok else tipo_error}
        private void red6() throws SemException{
            stack.pop();
            String type2 = stack.pop().getA();
            stack.pop();
            String type1 = stack.pop().getA();
            if(OK.equals(type1) && !ERROR.equals(type2)){
                stack.push(new Triple<String,String,List<String>>(OK, null, null));
            }else{
                stack.push(new Triple<String,String,List<String>>(ERROR, null, null));
                throw new SemException(1,al.getLineNumber(),al.getLineText());
            }
            stack.push(null);
        }

        //regla NT -> ! NTb {NT.tipo := if NTb.tipo == bool then bool else tipo_error}
        private void red7() throws SemException{
            stack.pop();
            String type = stack.pop().getA();
            stack.pop();
            stack.pop();
            if(STEntry.LOGICO.equals(type)){
                stack.push(new Triple<String,String,List<String>>(type, null, null));
            }else{
                stack.push(new Triple<String,String,List<String>>(ERROR, null, null));
                throw new SemException(2,al.getLineNumber(),al.getLineText());
            }
            stack.push(null);
        }

        //regla  NT -> NTb - NT2 {NT.tipo := if NTb.tipo == NT2.tipo == ent then ent else tipo_error}
        private void red8() throws SemException{
            stack.pop();
            String type2 = stack.pop().getA();
            stack.pop();
            stack.pop();
            stack.pop();
            String type = stack.pop().getA();
            if(STEntry.ENTERO.equals(type) && type.equals(type2)){
                stack.push(new Triple<String,String,List<String>>(type, null, null));
            }else{
                stack.push(new Triple<String,String,List<String>>(ERROR, null, null));
                throw new SemException(3,al.getLineNumber(),al.getLineText());
            }
            stack.push(null);
        }

        //regla  NT -> NTb < NT2 {NT.tipo := if NTb.tipo == NT2.tipo == ent then bool else tipo_error}
        private void red9() throws SemException{
            stack.pop();
            String type2 = stack.pop().getA();
            stack.pop();
            stack.pop();
            stack.pop();
            String type = stack.pop().getA();
            if(STEntry.ENTERO.equals(type) && type.equals(type2)){
                stack.push(new Triple<String,String,List<String>>(STEntry.LOGICO, null, null));
            }else{
                stack.push(new Triple<String,String,List<String>>(ERROR, null, null));
                throw new SemException(4,al.getLineNumber(),al.getLineText());
            }
            stack.push(null);
        }

        //regla NT -> id ( NT2 ) {NT.tipo := if NT2.nParam == BuscarNParamTS(id.pos) AND NT2.tipoParam == BuscarTipoParamTS(id.pos) then BuscarTipoRetTS(id.pos) else tipo_error}
        private void red10() throws SemException{
            stack.pop();
            stack.pop();
            stack.pop();
            Triple<String,String,List<String>> param = stack.pop();
            stack.pop();
            stack.pop();
            stack.pop();
            String id = stack.pop().getA();
            if(gts.getNParameters(id)==Integer.valueOf(param.getB()) && gts.getTypeParameters(id).equals(param.getC())){
                stack.push(new Triple<String,String,List<String>>(gts.getReturnType(id), null, null));
            }else{
                stack.push(new Triple<String,String,List<String>>(ERROR, null, null));
                throw new SemException(5,al.getLineNumber(),al.getLineText());
            }
            stack.push(null);
        }

        //regla K -> , T id Kb {InsertarTipoTS(id.pos,T.tipo),InsertarDesplTS(T.ancho),if Kb.tipo == vacio then K.tipo := T.tipo,K.nParam := 1 else K.tipo := T.tipo x Kb.tipo,K.nParam := Kb.nParam + 1}
        //regla NT -> T id K {InsertarTipoTS(id.pos,T.tipo),InsertarDesplTS(T.ancho),if K.tipo == vacio then NT.tipo := T.tipo,NT.nParam := 1 else NT.tipo := T.tipo x K.tipo,NT.nParam := K.nParam + 1}
        private void red11(int nPops){
            stack.pop();
            Triple<String,String,List<String>> param = stack.pop();
            stack.pop();
            String id = stack.pop().getA();
            stack.pop();
            Triple<String,String,List<String>> type = stack.pop();
            if(nPops == 8){
                stack.pop();
                stack.pop();
            }
            gts.setTypeOffset(id, type.getA(), Integer.valueOf(type.getB()));
            if(STEntry.VACIO.equals(param.getA())){
                param.setC(new ArrayList<String>());
                param.setB("1"); 
                param.setA(null);
            }else{
                param.setB(String.valueOf(Integer.valueOf(param.getB())+1));  
            }
            param.getC().add(0, type.getA());
            stack.push(param);
            stack.push(null);
        }

        //regla NT -> lambda {NT.tipo := vacio,NT.nParam := 0}
        private void red12(){
            stack.push(new Triple<String,String,List<String>>(STEntry.VACIO, "0", new ArrayList<String>()));
            stack.push(null);
        }

        //regla NT -> id = NT2 ; {NT.tipo := if NT2.tipo == BuscarTipoTS(id.pos) then tipo_ok else tipo_error}
        private void red13() throws SemException{
            stack.pop();
            stack.pop();
            stack.pop();
            String type = stack.pop().getA();
            stack.pop();
            stack.pop();
            stack.pop();
            String id = stack.pop().getA();
            if(gts.getType(id).equals(type)){
                stack.push(new Triple<String,String,List<String>>(OK, null, null));
            }else{
                stack.push(new Triple<String,String,List<String>>(ERROR, null, null));
                throw new SemException(6,al.getLineNumber(),al.getLineText());
            }
            stack.push(null);
        }

        //regla NT -> id += NT2 ; {NT.tipo := if NT2.tipo == BuscarTipoTS(id.pos) == ent then tipo_ok else tipo_error}
        private void red14() throws SemException{
            stack.pop();
            stack.pop();
            stack.pop();
            String type = stack.pop().getA();
            stack.pop();
            stack.pop();
            stack.pop();
            String id = stack.pop().getA();
            if(STEntry.ENTERO.equals(gts.getType(id)) && STEntry.ENTERO.equals(type)){
                stack.push(new Triple<String,String,List<String>>(OK, null, null));
            }else{
                stack.push(new Triple<String,String,List<String>>(ERROR, null, null));
                throw new SemException(7,al.getLineNumber(),al.getLineText());
            }
            stack.push(null);
        }

        //regla NT -> id ( V ) ; {NT.tipo := if V.nParam == BuscarNParamTS(id.pos) AND V.tipoParam == BuscarTipoParamTS(id.pos) then tipo_ok else tipo_error}
        private void red15() throws SemException{
            stack.pop();
            stack.pop();
            stack.pop();
            stack.pop();
            stack.pop();
            Triple<String,String,List<String>> param = stack.pop();
            stack.pop();
            stack.pop();
            stack.pop();
            String id = stack.pop().getA();
            if(gts.getNParameters(id) == Integer.valueOf(param.getB()) && param.getC() != null && param.getC().equals(gts.getTypeParameters(id))){
                stack.push(new Triple<String,String,List<String>>(OK, null, null));
            }else{
                stack.push(new Triple<String,String,List<String>>(ERROR, null, null));
                throw new SemException(5,al.getLineNumber(),al.getLineText());
            }
            stack.push(null);
        }

        //regla NT -> print NT2 ; {NT.tipo := if NT2.tipo == ent OR NT2.tipo == cad then tipo_ok else tipo_error}
        private void red16() throws SemException{
            stack.pop();
            stack.pop();
            stack.pop();
            String type = stack.pop().getA();
            stack.pop();
            stack.pop();
            if(STEntry.ENTERO.equals(type) || STEntry.CADENA.equals(type)){
                stack.push(new Triple<String,String,List<String>>(OK, null, null));
            }else{
                stack.push(new Triple<String,String,List<String>>(ERROR, null, null));
                throw new SemException(8,al.getLineNumber(),al.getLineText());
            }
            stack.push(null);
        }

        //regla NT -> input id ; {NT.tipo := if BuscarTipoTS(id.pos) == ent OR BuscarTipoTS(id.pos) == cad then tipo_ok else tipo_error}
        private void red17() throws SemException{
            stack.pop();
            stack.pop();
            stack.pop();
            String id = stack.pop().getA();
            stack.pop();
            stack.pop();
            if(STEntry.ENTERO.equals(gts.getType(id)) || STEntry.CADENA.equals(gts.getType(id))){
                stack.push(new Triple<String,String,List<String>>(OK, null, null));
            }else{
                stack.push(new Triple<String,String,List<String>>(ERROR, null, null));
                throw new SemException(9,al.getLineNumber(),al.getLineText());
            }
            stack.push(null);
        }

        //regla V -> Exp V2 {V.tipo := if V2.tipo == vacio then V.tipo := Exp.tipo,V.nParam := 1 else V.tipo := Exp.tipo x V2.tipo,V.nParam := V2.nParam + 1}
        //regla V2 -> , Exp V2b {V.tipo := if V2b.tipo == vacio then V2.tipo := Exp.tipo,V2.nParam := 1 else V2.tipo := Exp.tipo x V2b.tipo,V2.nParam := V2b.nParam + 1}
        private void red18(int nPops){
            stack.pop();
            Triple<String,String,List<String>> param = stack.pop();
            stack.pop();
            String type = stack.pop().getA();
            if(nPops == 6){
                stack.pop();
                stack.pop();
            }
            if(STEntry.VACIO.equals(param.getA())){
                param.setC(new ArrayList<String>());
                param.setB("1"); 
                param.setA(null);
            }else{
                param.setB(String.valueOf(Integer.valueOf(param.getB())+1));  
            }
            param.getC().add(0, type);
            stack.push(param);
            stack.push(null);
        }

        //regla T -> int {T.tipo := ent, T.ancho := 1}
        //regla T -> boolean {T.tipo := bool, T.ancho := 1}
        //regla T -> string {T.tipo := cad, T.ancho := 64}
        private void red19(int nRule){
            stack.pop();
            stack.pop();
            if(nRule == 9){
                stack.push(new Triple<String,String,List<String>>(STEntry.CADENA, "64", null));
            }else{
                stack.push(new Triple<String,String,List<String>>(nRule == 7 ? STEntry.ENTERO : STEntry.LOGICO, "1", null));
            }
            stack.push(null);
        }

        //regla S -> let id T ; {zDec := false, InsertarTipoTS(id.pos,T.tipo),InsertarDesplTS(id.pos,T.ancho),S.tipo := tipo_ok}
        private void red20(){
            stack.pop();
            stack.pop();
            stack.pop();
            Triple<String,String,List<String>> type = stack.pop();
            stack.pop();
            String id = stack.pop().getA();
            stack.pop();
            stack.pop();
            gts.setTypeOffset(id, type.getA(), Integer.valueOf(type.getB()));
            stack.push(new Triple<String,String,List<String>>(OK, null, null));
            stack.push(null);
        }

        //regla S -> if ( Exp ) If {S.tipo := if Exp.tipo == bool then If.tipo else tipo_error}
        private void red21() throws SemException{
            stack.pop();
            String then = stack.pop().getA();
            stack.pop();
            stack.pop();
            stack.pop();
            String exp = stack.pop().getA();
            stack.pop();
            stack.pop();
            stack.pop();
            stack.pop();
            if(STEntry.LOGICO.equals(exp)){
                stack.push(new Triple<String,String,List<String>>(then, null, null));
            }else{
                stack.push(new Triple<String,String,List<String>>(ERROR, null, null));
                throw new SemException(10,al.getLineNumber(),al.getLineText());
            }
            stack.push(null);
        }

        //regla If -> { C } Else {If.tipo := if C.tipo == tipo_ok AND Else.tipo != tipo_error then tipo_ok else tipo_error}
        private void red22() throws SemException{
            stack.pop();
            String els = stack.pop().getA();
            stack.pop();
            stack.pop();
            stack.pop();
            String c = stack.pop().getA();
            stack.pop();
            stack.pop();
            if(OK.equals(c) && !ERROR.equals(els)){
                stack.push(new Triple<String,String,List<String>>(OK, null, null));
            }else{
                stack.push(new Triple<String,String,List<String>>(ERROR, null, null));
                throw new SemException(11,al.getLineNumber(),al.getLineText());
            }
            stack.push(null);
        }

        //regla Else -> else { C } {Else.tipo := if C.tipo == tipo_ok then tipo_ok else tipo_error}
        private void red23() throws SemException{
            stack.pop();
            stack.pop();
            stack.pop();
            String c = stack.pop().getA();
            stack.pop();
            stack.pop();
            stack.pop();
            stack.pop();
            if(OK.equals(c)){
                stack.push(new Triple<String,String,List<String>>(OK, null, null));
            }else{
                stack.push(new Triple<String,String,List<String>>(ERROR, null, null));
                throw new SemException(12,al.getLineNumber(),al.getLineText());
            }
            stack.push(null);
        }

        //regla C -> S Cb {C.tipo := if Cb.tipo == tipo_error then tipo_error else S.tipo}
        private void red24() throws SemException{
            stack.pop();
            String type2 = stack.pop().getA();
            stack.pop();
            String type = stack.pop().getA();
            if(ERROR.equals(type2) || ERROR.equals(type)){
                stack.push(new Triple<String,String,List<String>>(ERROR, null, null));
                throw new SemException(13,al.getLineNumber(),al.getLineText());
            }else{
                stack.push(new Triple<String,String,List<String>>(type, null, null));
            }
            stack.push(null);
        }

        //regla Ss -> return X ; {Ss.tipo := if (tsL==NULL AND X.tipo == vacio) OR BuscarTipoRetTS(associatedFun) == X.tipo then tipo_ok else tipo_error}
        private void red25() throws SemException{
            stack.pop();
            stack.pop();
            stack.pop();
            String type = stack.pop().getA();
            stack.pop();
            stack.pop();
            if(gts.getReturnType().equals(type)){
                stack.push(new Triple<String,String,List<String>>(OK, null, null));
            }else{
                stack.push(new Triple<String,String,List<String>>(ERROR, null, null));
                throw new SemException(14,al.getLineNumber(),al.getLineText());
            }
            stack.push(null);
        }

        //regla Fun3 -> { C } {Fun3.tipo := if C.tipo == tipo_ok then tipo_ok else tipo_error}
        private void red26() throws SemException{
            stack.pop();
            stack.pop();
            stack.pop();
            String type = stack.pop().getA();
            stack.pop();
            stack.pop();
            if(OK.equals(type)){
                stack.push(new Triple<String,String,List<String>>(OK, null, null));
            }else{
                stack.push(new Triple<String,String,List<String>>(ERROR, null, null));
                throw new SemException(15,al.getLineNumber(),al.getLineText());
            }
            stack.push(null);
        }

        //regla Fun2 -> ( A ) {Fun2.nParam := A.nParam, Fun2.tipo := A.tipo, zDec := false}
        private void red27(){
            stack.pop();
            stack.pop();
            stack.pop();
            Triple<String,String,List<String>> param = stack.pop();
            stack.pop();
            stack.pop();
            stack.push(param);
            stack.push(null);
            gts.setZDec(false, null);
            //{InsertarTipoParamTS(Fun1.pos,Fun2.tipo),InsertarNParamTS(Fun1.pos,Fun2.nParam)}
            Triple<String,String,List<String>> f1 = stack.get(3);
            gts.setParameters(f1.getB(), Integer.valueOf(param.getB()), param.getC());
        }

        //regla Fun1 -> function {zDec := true} id H {InsertarTipoTS(id.pos,fun),InsertarTipoRetTS(id.pos,H.tipo),InsertarEtiquetaTS(id.pos,GenEtiq()),CrearTSL(),Fun1.pos := id.pos}
        private void red28() throws PDLException{
            stack.pop();
            String type = stack.pop().getA();
            stack.pop();
            String id = stack.pop().getA();
            stack.pop();
            stack.pop();
            gts.setTypeOffset(id, STEntry.FUNCION, 0);
            gts.setReturn(id, type);
            gts.createLocalST(id);
            stack.push(new Triple<String,String,List<String>>(null, id, null));
            stack.push(null);
        }

        //regla Fun -> Fun1 Fun2 {InsertarTipoParamTS(Fun1.pos,Fun2.tipo),InsertarNParamTS(Fun1.pos,Fun2.nParam)} Fun3 {Fun.tipo := Fun3.tipo,BorrarTSL()}
        private void red29(){
            stack.pop();
            String type = stack.pop().getA();
            stack.pop();
            stack.pop();
            stack.pop();
            stack.pop();
            stack.push(new Triple<String,String,List<String>>(type, null, null));
            stack.push(null);
            gts.closeCurrentST();
        }
    }
}
