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
    public static int emptyString=-1;
    private int input,pop,push;
    private State nextState;
    
    public NextState(State n, int i, int pop,int push){
        this.input=i;
        this.nextState=n;
        this.pop=pop;
        this.push=push;
    }
    public int getPopSymbol(){
        return this.pop;
    }
    public int getPushSymbol(){
        return this.push;
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
