package com.casamayor.raul.common;

/**
* Triple generic class
* @author Raul Casamayor Navas
* @version 1.0
* @since 08/01/2023
*/
public class Triple<A,B,C> {

    private A a;
    private B b;
    private C c;

    @SuppressWarnings("unused")
    private Triple(){}

    public Triple(A a, B b, C c){
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public A getA() {
        return a;
    }

    public void setA(A a) {
        this.a = a;
    }

    public B getB() {
        return b;
    }

    public void setB(B b) {
        this.b = b;
    }

    public C getC() {
        return c;
    }

    public void setC(C c) {
        this.c = c;
    }
}
