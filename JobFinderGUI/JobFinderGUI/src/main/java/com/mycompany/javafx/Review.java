package com.mycompany.javafx;

public class Review {
    private JobSeeker js;
    private String body;

    public Review() {
        
    }
    
    public Review(JobSeeker js, String body) {
        this.js = js;
        this.body = body;
    }
    
    public JobSeeker getJobSeeker() {
        return this.js;
    }
    
    public String getBody() {
        return this.body;
    }
    
  
}
