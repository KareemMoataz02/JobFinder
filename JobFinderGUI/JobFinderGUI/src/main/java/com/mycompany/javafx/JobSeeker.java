package com.mycompany.javafx;
import java.util.ArrayList;

public class JobSeeker extends Account {
    
    private ArrayList<VacancyApp> applications = new ArrayList<>();
    private static ArrayList<JobSeeker> allSeekers = new ArrayList<>();
    
    public JobSeeker() {
        
    }
    
    public JobSeeker(String name, String email, String password) {
        super(name, email, password);
        allSeekers.add(this);
    }
    
    public void viewAllVacancies() {
        Vacancy.printVacancies();
    }
    
    public void viewCompanyVacancies(Company c) {
        c.printVacancies();
    }
    
    public void apply(String proposal, Vacancy v) {
        VacancyApp app = new VacancyApp(this, proposal, v);
        applications.add(app);
    }
    
    public ArrayList<VacancyApp> getApplications() {
        return this.applications;
    }
    
    public void showApplications() {
        for(int i = 0; i < applications.size(); i++) {
            System.out.println("-------------- (" + (i+1) + ") --------------");
            System.out.println("Company: " + applications.get(i).getVacancy().companyName());
            System.out.println("Title: " + applications.get(i).getVacancy().getName());
            System.out.println("Salary: " + applications.get(i).getVacancy().getSalary());
            System.out.println("Propsoal: " + applications.get(i).getProposal());
            System.out.println("State: " + applications.get(i).getState());
            System.out.println("---------------------------------");
        }
    }
    
    public void editApplication(String proposal, VacancyApp app) {
        app.setProposal(proposal);
    }
    
    public void removeApplication(VacancyApp app) {
        app.deleteApplication();
    }
    
    public void addReview(String body, Company c) {
        Review review = new Review(this, body);
        ArrayList<Review> reviews = c.getReviews();
        reviews.add(review);
    }
    
    public static JobSeeker login(String email , String Password){
        for(int i = 0 ; i < allSeekers.size() ; i ++){
            if(allSeekers.get(i).getEmail().equals(email) && allSeekers.get(i).getPassword().equals(Password)){
                return allSeekers.get(i);
            }
        }
        return null;
    }
    
    public VacancyApp checkPreApplication(Vacancy vacancy) {
        for(int i = 0; i < applications.size(); i++) {
            if(applications.get(i).getVacancy().equals(vacancy)) return applications.get(i);
        }
        return null;
    }
    
}
