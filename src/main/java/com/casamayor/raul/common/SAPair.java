package com.casamayor.raul.common;

public class SAPair {
    private Integer state;
    private Character action;

    @SuppressWarnings("unused")
    private SAPair(){}

    public SAPair(Integer state, Character action){
        this.state = state;
        this.action = action;
    }

    public Integer getState() {
        return state;
    }

    public Character getAction() {
        return action;
    }
}
