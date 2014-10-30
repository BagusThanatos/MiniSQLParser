/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tekom.minisqlparser;

/**
 *
 * @author BagusThanatos
 */
public class NextState {
    private int input;
    private State nextState;
    
    public NextState(State n, int i){
        this.input=i;
        this.nextState=n;
    }
    public State getNextState(){
        return this.nextState;
    }
    public int getInput(){
        return this.input;
    }
    public boolean equals(int i){
        return i==this.input;
    }
}
