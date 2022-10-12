package com.mycompany.javafx;
import java.util.ArrayList;
import com.mycompany.javafx.VacancyApp.State;

public class JobPoster extends Account {
    
    private Company c;
    private ArrayList<Vacancy> vacancies = new ArrayList<>();
    private static ArrayList<JobPoster> allPosters = new ArrayList<>();
    
    public JobPoster() {
        
    }
    
    public JobPoster(String name, String email, String password) {
        super(name, email, password);
        allPosters.add(this);
    }
    
    public void setCompany(Company c) {
        this.c = c;
    }


    public Company getCompany() {
        return this.c;
    }
    
    public ArrayList<Vacancy> getVacancies() {
        return this.vacancies;
    }
    
    public void addVacancy(String name, String description, int salary) {
        Vacancy v = new Vacancy(name, description, salary, this.c);
        c.getVacancies().add(v);
        Vacancy.addVacancy(v);
        vacancies.add(v);
    }
    
    public ArrayList<VacancyApp> getApplications() {
        ArrayList<VacancyApp> allApplications = new ArrayList<>();
        for(int i = 0; i < vacancies.size(); i++) {
            ArrayList<VacancyApp> apps = vacancies.get(i).getApplications();
            for(int x = 0; x < apps.size(); x++) {
                allApplications.add(apps.get(x));
            }
        }
        return allApplications;
    }
    
    public void printApplications() {
        int no = 1;
        for(int i = 0; i < vacancies.size(); i++) {
            ArrayList<VacancyApp> apps = vacancies.get(i).getApplications();
            for(int x = 0; x < apps.size(); x++) {
                System.out.println("-------------- (" + (no) + ") --------------");
                System.out.println("Name: " + apps.get(x).getSeeker().getName());
                System.out.println("Vacancy: " + apps.get(x).getVacancy().getName());
                System.out.println("Proposal: " + apps.get(x).getProposal());
                System.out.println("State: " + apps.get(x).getState());
                System.out.println("---------------------------------");
                no++;
            }
        }
    }
    
    public void acceptApplication(VacancyApp app) {
        app.setState(State.accepted);
        app.getVacancy().deleteVacancy();
        app.getVacancy().getCompany().increaseEmployees();
    }
    
    public void refuseApplication(VacancyApp app) {
        app.setState(State.refused);
    }
    
    public static JobPoster login(String email , String Password){
        for(int i = 0 ; i < allPosters.size() ; i ++){
            if(allPosters.get(i).getEmail().equals(email) && allPosters.get(i).getPassword().equals(Password)){
                return allPosters.get(i);
            }
        }
        return null;
    }
    
    public static ArrayList<JobPoster> getAllPosters() {
        return allPosters;
    }
    
    public static void printAllPosters() {
        for(int i = 0; i < allPosters.size(); i++) {
            String companyString = (allPosters.get(i).getCompany() == null) ? "No Company" : allPosters.get(i).getCompany().getName();
            System.out.println("(" + (i+1) + ") Name: " + allPosters.get(i).getName() + " - Current Company: " + companyString);
        }
    }
}
