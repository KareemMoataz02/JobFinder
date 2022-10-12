package com.mycompany.javafx;
import java.util.ArrayList;

public class Vacancy {
    private String name, description;
    private int salary;
    private Company c;
    private ArrayList<VacancyApp> applications = new ArrayList<>();
    private static ArrayList<Vacancy> allVacancies = new ArrayList<>();
    
    public Vacancy() {
        
    }
    
    public Vacancy(String name, String description, int salary, Company c) {
        this.name = name;
        this.description = description;
        this.salary = salary;
        this.c = c;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getDescripton() {
        return this.description;
    }
    
    public int getSalary() {
        return this.salary;
    }
    
    public String companyName() { 
        return this.c.getName();
    }
    
    public Company getCompany() {
        return this.c;
    }
    
    public static void addVacancy(Vacancy v) {
        Vacancy.allVacancies.add(v);
    }
    
    public static ArrayList<Vacancy> getVacancies() {
        return Vacancy.allVacancies;
    }
    
    public static void printVacancies() {
        for(int i = 0; i < allVacancies.size(); i++) {
            System.out.println("-------------- (" + (i+1) + ") --------------");
            System.out.println("Vacancy: " + allVacancies.get(i).getName());
            System.out.println("Description: " + allVacancies.get(i).getDescripton());
            System.out.println("Salary: " + allVacancies.get(i).getSalary());
            System.out.println("Company: " + allVacancies.get(i).companyName());
            System.out.println("---------------------------------");
        }
    }
    
    public void deleteVacancy() {
        ArrayList<Vacancy> cVacancies = this.c.getVacancies();
        for(int i = 0; i < cVacancies.size(); i++) {
            if(cVacancies.get(i).equals(this)) {
                cVacancies.remove(i);
                break;
            }
        }
        for(int i = 0; i < allVacancies.size(); i++) {
            if(allVacancies.get(i).equals(this)) {
                allVacancies.remove(i);
                break;
            }
        }
    }
    
    public ArrayList<VacancyApp> getApplications() {
        return this.applications;
    }
}
