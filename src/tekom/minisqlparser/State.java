/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tekom.minisqlparser;

import java.util.ArrayList;

/**
 *
 * @author BagusThanatos
 */
public class State {
    final private String stateName;
    final private boolean acceptedState;
    final private ArrayList<NextState> nextStates;
    
    
    public State(String n, boolean acc){
        this.stateName=n;
        this.acceptedState=acc;
        this.nextStates= new ArrayList();
    }
    public State getNextStates(final int i, final int pop){
        for (NextState nextState : this.nextStates) {
            if (nextState.getInput() == i && nextState.getPopSymbol()==pop) 
                return nextState.getNextState();
            
        }
        return null;
    }
    
    public void insertNextState(State s,int input){
        this.nextStates.add(new NextState(s, input,NextState.emptyString,NextState.emptyString));
    }
    
    public boolean getStatus(){
        return this.acceptedState;
    }
}
