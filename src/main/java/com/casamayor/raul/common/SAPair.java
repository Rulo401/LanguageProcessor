package com.casamayor.raul.common;

/**
 * Class that models a pair of a state and an action.
 * @author Raúl Casamayor Navas
 * @version 1.0
 * @since 13/10/2022
 */
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
