package com.mycompany.javafx;
import java.util.ArrayList;

public class Company {
    private String name;
    private int noOfEmployees = 1;
    private ArrayList<JobPoster> posters = new ArrayList<>();
    private ArrayList<Vacancy> vacancies = new ArrayList<>();
    private ArrayList<Review> reviews = new ArrayList<>();
    private Admin admin;

    private static ArrayList<Company> allCompanies = new ArrayList<>();
    
    public Company() {
        
    }
    
    public Company(String name) {
        this.name = name;
        allCompanies.add(this);
    }
    
    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
    
    public Admin getAdmin() {
        return admin;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public static ArrayList<Company> getAllCompanies() {
        return allCompanies;
    }
    
    public static void printAllCompanies() {
        for(int i = 0; i < allCompanies.size(); i++) {
            System.out.println("-------------- (" + (i+1) + ") --------------");
            System.out.println("Name: " + allCompanies.get(i).getName());
            System.out.println("No of Employees: " + allCompanies.get(i).getEmployees());
            System.out.println("No of Vacancies: " + allCompanies.get(i).getVacancies().size());
            System.out.println("---------------------------------");
        }
    }
    
    public ArrayList<JobPoster> getPosters() {
        return this.posters;
    }
    
    public void increaseEmployees() {
        this.noOfEmployees++;
    }
    
    public void changeEmployees(int employees) {
        this.noOfEmployees = employees;
    }
    
    public int getEmployees() {
        return this.noOfEmployees;
    }
    
    public void printPosters() {
        for(int i = 0; i < posters.size(); i++) {
            System.out.println(posters.get(i).getName());
            System.out.println(posters.get(i).getEmail());
            System.out.println(posters.get(i).getPassword());
        }
    }
    
    public ArrayList<Vacancy> getVacancies() {
        return this.vacancies;
    }
    
    public void setVacancies(ArrayList<Vacancy> vacancies) {
        this.vacancies = vacancies;
    }
    
    public void printVacancies() {
        for(int i = 0; i < vacancies.size(); i++) {
            System.out.println("-----------------------------");
            System.out.println("Vacancy: " + vacancies.get(i).getName());
            System.out.println("Description: " + vacancies.get(i).getDescripton());
            System.out.println("Salary: " + vacancies.get(i).getSalary());
            System.out.println("Company: " + vacancies.get(i).companyName());
            System.out.println("-----------------------------");
        }
    }
    
    public ArrayList<Review> getReviews() {
        return this.reviews;
    }
    
    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }
    
    public void printReviews() {
        for(int i = 0; i < reviews.size(); i++) {
            System.out.println("-----------------------------");
            System.out.println("Name: " + reviews.get(i).getJobSeeker().getName());
            System.out.println("Review: " + reviews.get(i).getBody());
            System.out.println("-----------------------------");
        }
    }
}
