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
    private String stateName;
    private boolean acceptedState;
    private ArrayList<NextState> nextStates;
    
    
    public State(String n, boolean acc){
        this.stateName=n;
        this.acceptedState=acc;
        this.nextStates= new ArrayList();
    }
    private State getNextStates(int i){
        for (NextState nextState : this.nextStates) {
            if (nextState.getInput() == i) {
                return nextState.getNextState();
            }
        }
        return null;
    }
    
    public void insertNextState(State s,int input){
        this.nextStates.add(new NextState(s, input));
    }
    
    public boolean getStatus(){
        return this.acceptedState;
    }
}
