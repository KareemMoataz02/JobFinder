package com.mycompany.javafx;

import java.util.ArrayList;


public class VacancyApp {
    enum State {
        pending,
        accepted,
        refused
    }
    private JobSeeker js;
    private String proposal;
    private Vacancy v;
    private State appState;
    
    public VacancyApp() {
        
    }
    
    public VacancyApp(JobSeeker js, String proposal, Vacancy v) {
        this.js = js;
        this.proposal = proposal;
        this.v = v;
        this.appState = State.pending;
        v.getApplications().add(this);
    }
    
    public Vacancy getVacancy() {
        return this.v;
    }
    
    public String getProposal() {
        return this.proposal;
    }
    
    public void setProposal(String proposal) {
        this.proposal = proposal;
    }
    
    public JobSeeker getSeeker() {
        return this.js;
    }
    
    public State getState() {
        return this.appState;
    }
    
    public void setState(State state) {
        this.appState = state;
    }
    
    public void deleteApplication() {
        ArrayList<VacancyApp> cApplications = this.v.getApplications();
        for(int i = 0; i < cApplications.size(); i++) {
            if(cApplications.get(i).equals(this)) {
                cApplications.remove(i);
                break;
            }
        }
        ArrayList<VacancyApp> jsApplications = this.js.getApplications();
        for(int i = 0; i < jsApplications.size(); i++) {
            if(jsApplications.get(i).equals(this)) {
                jsApplications.remove(i);
                break;
            }
        }
    }
    
    public boolean isResponded() {
        if(this.appState != State.pending) return true;
        else return false;
    }
}
