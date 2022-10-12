package com.mycompany.javafx;

import java.util.ArrayList;

public class Admin extends Account {

    private Company c;

    public Admin() {

    }

    public Admin(String name, String email, String password, Company c) {
        super(name, email, password);
        this.c = c;
        c.setAdmin(this);
    }
    
    public Company getCompany() {
        return this.c;
    }

    public void addPoster(JobPoster poster) {
        poster.setCompany(c);
        c.getPosters().add(poster);
        c.increaseEmployees();
    }
    
    public void changeCompanyName(String name) {
        c.setName(name);
    }
    
    public void changeCompanyEmployees(int no) {
        c.changeEmployees(no);
    }
    
    public static Admin login(String email , String Password){
        ArrayList<Company> companies = Company.getAllCompanies();
        for(int i = 0 ; i < companies.size() ; i ++){
            if(companies.get(i).getAdmin().getEmail().equals(email) && companies.get(i).getAdmin().getPassword().equals(Password)){
                return companies.get(i).getAdmin();
            }
        }
        return null;
    }
}
