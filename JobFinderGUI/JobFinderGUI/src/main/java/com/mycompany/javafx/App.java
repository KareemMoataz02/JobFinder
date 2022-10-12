package com.mycompany.javafx;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



/**
 * JavaFX App
 */
public class App extends Application {
    private Button accAdmin = new Button("Admin");
    private Button accPoster = new Button("Job Poster");
    private Button accSeeker = new Button("Job Seeker");
    private Label loginerr;
    private int accountType = 0;
    private Admin loggedAdmin = new Admin();
    private JobPoster loggedPoster;
    private JobSeeker loggedSeeker;
    //--------- Scenes -------------
    private Scene loginScene;
    private Scene adminScene;
    private Scene postersScene;
    private Scene posterScene;
    private Scene appScene;
    private Scene seekerScene;
    private Scene seekerApps;
    private Scene seekerVac;
    private Scene applyVacScene;
    private Scene comapniesScene;
    private Scene reviewScene;
    //---------- Data --------------
    private ArrayList<JobPoster> allPosters = JobPoster.getAllPosters();
    private ArrayList<VacancyApp> applications;
    ArrayList<Vacancy> allVacancies = Vacancy.getVacancies();
    ArrayList<Company> allCompanies = Company.getAllCompanies();
    //------ Handler Objects -------
    private JobPoster posterToAssign;
    private VacancyApp applicationToRespond;
    private Vacancy vacToApply;
    private Company companyToReview;

    @Override
    public void start(Stage stage) {
        //------------- Define Scenes ------------
        
        
        
        //------------- Login Scene --------------
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20));
        Label title = new Label("Login");
        title.setStyle("-fx-font-size: 30px;");
        borderPane.setTop(title);
        borderPane.setAlignment(title, Pos.CENTER);
        
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        
        HBox accountBox = new HBox();
        accountBox.setSpacing(10);
        String accStyle = "-fx-background-color: #cccccc;";
        accAdmin.setStyle(accStyle);
        accPoster.setStyle(accStyle);
        accSeeker.setStyle(accStyle);
        accountBox.getChildren().addAll(accAdmin, accPoster, accSeeker);
        
        HandleAccountType selectAcc = new HandleAccountType();
        accAdmin.setOnAction(selectAcc);
        accPoster.setOnAction(selectAcc);
        accSeeker.setOnAction(selectAcc);
        
        Label email = new Label("Email:");
        TextField tfemail = new TextField();
        Label password = new Label("Password:");
        PasswordField tfpassword = new PasswordField();
        gridPane.add(accountBox, 1, 0);
        gridPane.add(email, 0, 1);
        gridPane.add(tfemail, 1, 1);
        gridPane.add(password, 0, 2);
        gridPane.add(tfpassword, 1, 2);
        borderPane.setCenter(gridPane);
        Button loginbtn = new Button("Login");
        loginerr = new Label();
        loginerr.setStyle("-fx-text-fill: #ff0000;");
        gridPane.add(loginerr, 1, 3);
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.BOTTOM_RIGHT);
        hbox.getChildren().add(loginbtn);
        gridPane.add(hbox, 1, 3);
       
        
        loginbtn.setOnAction((e) -> {
            String useremail = tfemail.getText();
            String userpassword = tfpassword.getText();
            try {
                if(accountType == 0) throw new LoginException("Please select account type");
                else if(accountType == 1 && Admin.login(useremail, userpassword) != null) {
                    loggedAdmin = Admin.login(useremail, userpassword);
                    loadAdminScene(stage);
                    stage.setScene(adminScene);
                }
                else if(accountType == 2 && JobPoster.login(useremail, userpassword) != null) {
                    loggedPoster = JobPoster.login(useremail, userpassword);
                    applications = loggedPoster.getApplications();
                    loadPosterScene(stage);
                    stage.setScene(posterScene);
                }
                else if(accountType == 3 && JobSeeker.login(useremail, userpassword) != null) {
                    loggedSeeker = JobSeeker.login(useremail, userpassword);
                    loadSeekerScene(stage);
                    stage.setScene(seekerScene);
                }
                else throw new LoginException("Invalid login data");
            }
            catch(LoginException ae) {
                loginerr.setText(ae.getMessage());
            }
        });
     
        loginScene = new Scene(borderPane, 500, 350);
        stage.setTitle("Job Finder System");
        stage.setScene(loginScene);
        stage.show();
    }
    
    
    public void loadAdminScene(Stage stage) {
        BorderPane adminPane = new BorderPane();
        adminPane.setPadding(new Insets(20));
        adminScene = new Scene(adminPane, 500, 350);
        Label welLabel = new Label("Welcome " + loggedAdmin.getName());
        welLabel.setStyle("-fx-font-size: 24px;");
        adminPane.setTop(welLabel);
        adminPane.setAlignment(welLabel, Pos.CENTER);
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(20);
        Label cname = new Label("Company's Name:");
        TextField tfcname = new TextField(loggedAdmin.getCompany().getName());
        Button cnameButton = new Button("Change");
        Label cemployee = new Label("Company's Employees:");
        TextField tfcemployee = new TextField(String.valueOf(loggedAdmin.getCompany().getEmployees()));
        Button cemoplyeeButton = new Button("Change");
        gridPane.add(cname, 0, 0);
        gridPane.add(tfcname, 1, 0);
        gridPane.add(cnameButton, 2, 0);
        gridPane.add(cemployee, 0, 1);
        gridPane.add(tfcemployee, 1, 1);
        gridPane.add(cemoplyeeButton, 2, 1);
        adminPane.setCenter(gridPane);
        Button poster = new Button("Add Poster");
        Button signout = new Button("Sign Out");
        gridPane.add(poster, 0, 2);
        gridPane.add(signout, 2, 2);
        Label msg = new Label();
        gridPane.add(msg, 1, 3);

        poster.setOnAction((e) -> {
            if(allPosters.size() == 0) {
                msg.setStyle("-fx-text-fill: #ff0000;");
                msg.setText("No posters to add");
            }
            else {
                loadAddPosterScene(stage, msg);
                stage.setScene(postersScene);
            }
        });
        
        cnameButton.setOnAction((e) -> {
            if(tfcname.getText() == "") {
                msg.setStyle("-fx-text-fill: #ff0000;");
                msg.setText("Company's name can't be empty");
            }
            else {
                loggedAdmin.getCompany().setName(tfcname.getText());
                msg.setStyle("-fx-text-fill: green;");
                msg.setText("Company's name changed");
            }
        });
        
        cemoplyeeButton.setOnAction((e) -> {
            try {
                loggedAdmin.getCompany().changeEmployees(Integer.parseInt(tfcemployee.getText()));
                msg.setStyle("-fx-text-fill: green;");
                msg.setText("Company's name changed");
            }
            catch(Exception ae) {
                msg.setStyle("-fx-text-fill: #ff0000;");
                msg.setText("Invalid number");
            }
        });
        
        signout.setOnAction((e) -> {
            loginerr.setText("");
            stage.setScene(loginScene);
        });
    }
    
    public void loadAddPosterScene(Stage stage, Label msg) {
        BorderPane posterPane = new BorderPane();
        posterPane.setPadding(new Insets(20));
        postersScene = new Scene(posterPane, 500, 300);
        Label posterTitle = new Label("Select a poster to join " + loggedAdmin.getCompany().getName());
        posterTitle.setStyle("-fx-font-size: 20px;");
        posterPane.setTop(posterTitle);
        posterPane.setAlignment(posterTitle, Pos.CENTER);
        
        FlowPane pflow = new FlowPane();
        pflow.setHgap(15);
        pflow.setVgap(20);
        pflow.setAlignment(Pos.CENTER);
        
        for(int i = 0; i < allPosters.size(); i++) {
            Label pname = new Label(allPosters.get(i).getName());
            String currentCompany = (allPosters.get(i).getCompany() == null) ? "No Company" : allPosters.get(i).getCompany().getName();
            Label pcompnay = new Label(currentCompany);
            pname.setStyle("-fx-text-fill: #ffffff");
            pcompnay.setStyle("-fx-text-fill: #ffffff");
            VBox vbox = new VBox();
            vbox.setAlignment(Pos.CENTER);
            vbox.setSpacing(10);
            vbox.setPadding(new Insets(12));
            vbox.setStyle("-fx-background-color: #1467cc; -fx-background-radius: 15px; -fx-border-radius: 15px;");
            vbox.getChildren().addAll(pname, pcompnay);
            HandleAddPoster handleAddPoster = new HandleAddPoster();
            vbox.setOnMouseClicked(handleAddPoster);
            vbox.setId(String.valueOf(i));
            pflow.getChildren().add(vbox);
        }
        posterPane.setCenter(pflow);
        Button back = new Button("Back");
        Region r = new Region();
        Button addPoster = new Button("Add Poster");
        HBox.setHgrow(r, Priority.ALWAYS);
        HBox buttons = new HBox(back, r, addPoster);
        posterPane.setBottom(buttons);
        
        back.setOnAction((e) -> {
            posterToAssign = null;
            loadAdminScene(stage);
            stage.setScene(adminScene);
        });
        
        addPoster.setOnAction((e) -> {
            if(posterToAssign == null) posterTitle.setStyle("-fx-text-fill: #ff0000; -fx-font-size: 20px;");
            else {
                loggedAdmin.addPoster(posterToAssign);
                msg.setStyle("-fx-text-fill: green;");
                msg.setText(posterToAssign.getName() + " Joined " + loggedAdmin.getCompany().getName() + " Successfully");
                stage.setScene(adminScene);              
                posterToAssign = null;
            }
        });
    }
    
    public void loadPosterScene(Stage stage) {
        BorderPane posterPane = new BorderPane();
        posterPane.setPadding(new Insets(20));
        posterScene = new Scene(posterPane, 500, 350);
        Label welLabel = new Label("Welcome " + loggedPoster.getName());
        welLabel.setStyle("-fx-font-size: 24px;");
        posterPane.setTop(welLabel);
        posterPane.setAlignment(welLabel, Pos.CENTER);
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(20);
        Label vTitle = new Label("Add new vacancy");
        vTitle.setStyle("-fx-font-size: 18px;");
        gridPane.add(vTitle, 1, 0);
        Label vname = new Label("Name:");
        Label vdesc = new Label("Description:");
        Label vsalary = new Label("Salary:");
        TextField tfname = new TextField();
        TextField tfdesc = new TextField();
        TextField tfsalary = new TextField();
        Button addv = new Button("Add Vacancy");
        HBox addbox = new HBox(addv);
        addbox.setAlignment(Pos.CENTER);
        Button showApp = new Button("Show Apps");
        Button signout = new Button("Signout");
        Label msg = new Label();
        HBox msgbox = new HBox(msg);
        msgbox.setAlignment(Pos.CENTER);
        gridPane.add(vname, 0, 1);
        gridPane.add(vdesc, 0, 2);
        gridPane.add(vsalary, 0, 3);
        gridPane.add(tfname, 1, 1);
        gridPane.add(tfdesc, 1, 2);
        gridPane.add(tfsalary, 1, 3);
        gridPane.add(addbox, 1, 4);
        gridPane.add(showApp, 0, 5);
        gridPane.add(msgbox, 1, 5);
        gridPane.add(signout, 2, 5);
        posterPane.setCenter(gridPane);
        
        addv.setOnAction((e) -> {
            String namev = tfname.getText();
            String descv = tfdesc.getText();
            if(namev.isEmpty() || descv.isEmpty()) {
                    msg.setText("All data are required");
                    msg.setStyle("-fx-text-fill: #ff0000;");
            }
            else {
                try {
                    int salarv = Integer.parseInt(tfsalary.getText());
                    loggedPoster.addVacancy(namev, descv, salarv);
                    msg.setText("New Vacancy Added");
                    msg.setStyle("-fx-text-fill: green;");
                    tfname.setText("");
                    tfdesc.setText("");
                    tfsalary.setText("");
                }
                catch(Exception ee) {
                    msg.setText("Invalid Salary");
                    msg.setStyle("-fx-text-fill: #ff0000;");
                }
            }
        });
        
        showApp.setOnAction((e) -> {
            if(applications.size() == 0) {
                msg.setText("No Applications");
                msg.setStyle("-fx-text-fill: #ff0000;");
            }
            else {
                loadAppScene(stage, msg);
                stage.setScene(appScene);
            }
        });
        
        signout.setOnAction((e) -> {
            loginerr.setText("");
            stage.setScene(loginScene);
        });
    }
    
    public void loadAppScene(Stage stage, Label msg) {
        BorderPane appPane = new BorderPane();
        appPane.setPadding(new Insets(20));
        appScene = new Scene(appPane, 600, 500);
        Label appTitle = new Label("Select an Application to take an action ");
        appTitle.setStyle("-fx-font-size: 20px;");
        appPane.setTop(appTitle);
        appPane.setAlignment(appTitle, Pos.CENTER);
        
        ScrollPane scrollpane = new ScrollPane();
        scrollpane.setStyle("-fx-focus-color: transparent;");
        scrollpane.setPadding(new Insets(20, 1, 20, 1)); 
        VBox vPane = new VBox();
        vPane.setSpacing(20);
        vPane.setAlignment(Pos.CENTER);
        HandleAppRespond handleAppRespond = new HandleAppRespond();
        for(int i = 0; i < applications.size(); i++) {
            Label aname = new Label("Name: " + applications.get(i).getSeeker().getName());
            Label avac = new Label("Vacancy: " + applications.get(i).getVacancy().getName());
            Label aprop = new Label("Proposal: " + applications.get(i).getProposal());
            Label astate = new Label("State: " + applications.get(i).getState());
            aname.setStyle("-fx-text-fill: white;");
            avac.setStyle("-fx-text-fill: white;");
            aprop.setStyle("-fx-text-fill: white;");
            astate.setStyle("-fx-text-fill: white;");
            VBox vbox = new VBox(aname, avac, aprop, astate);
            vbox.setStyle("-fx-background-color: #1467cc; -fx-background-radius: 15px; -fx-border-radius: 15px;");
            vbox.setAlignment(Pos.CENTER);
            vbox.setSpacing(10);
            vbox.setPadding(new Insets(12));
            vbox.translateXProperty().bind(scrollpane.widthProperty().subtract(vbox.widthProperty()).divide(2));
            vbox.setId(String.valueOf(i));
            vbox.setOnMouseClicked(handleAppRespond);
            vPane.getChildren().add(vbox);
        }
        scrollpane.setContent(vPane);
        appPane.setCenter(scrollpane);
        Button back = new Button("Back");
        Region r1 = new Region();
        Region r2 = new Region();
        Button accept = new Button("Accept");
        Button refuse = new Button("Refuse");
        Label appmsg = new Label();
        appmsg.setStyle("-fx-text-fill: #ff0000;");
        HBox.setHgrow(r1, Priority.ALWAYS);
        HBox.setHgrow(r2, Priority.ALWAYS);
        HBox buttons = new HBox(back, r1, appmsg, r2, accept, refuse);
        buttons.setSpacing(10);
        appPane.setBottom(buttons);
        
        back.setOnAction((e) -> {
            applicationToRespond = null;
            loadPosterScene(stage);
            stage.setScene(posterScene);
        });
        
        accept.setOnAction((e) -> {
            if(applicationToRespond == null) appTitle.setStyle("-fx-font-size: 20px; -fx-text-fill: #ff0000;");
            else if(applicationToRespond.isResponded()) appmsg.setText("Application is already " + applicationToRespond.getState());
            else {
                loggedPoster.acceptApplication(applicationToRespond);
                msg.setText(applicationToRespond.getSeeker().getName() + " joined " + loggedPoster.getCompany().getName());
                msg.setStyle("-fx-text-fill: green;");
                applicationToRespond = null;
                stage.setScene(posterScene);
            }
        });
        
        refuse.setOnAction((e) -> {
            if(applicationToRespond == null) appTitle.setStyle("-fx-font-size: 20px; -fx-text-fill: #ff0000;");
            else if(applicationToRespond.isResponded()) appmsg.setText("Application is already " + applicationToRespond.getState());
            else {
                loggedPoster.refuseApplication(applicationToRespond);
                msg.setText(applicationToRespond.getSeeker().getName() + " app is refused");
                msg.setStyle("-fx-text-fill: #ff0000;");
                applicationToRespond = null;
                stage.setScene(posterScene);
            }
        });
        
        
        
    }
    
    public void loadSeekerScene(Stage stage) {
        BorderPane seekerPane = new BorderPane();
        seekerPane.setPadding(new Insets(20));
        seekerScene = new Scene(seekerPane, 500, 350);
        Label welLabel = new Label("Welcome " + loggedSeeker.getName());
        welLabel.setStyle("-fx-font-size: 24px;");
        seekerPane.setTop(welLabel);
        seekerPane.setAlignment(welLabel, Pos.CENTER);

        Label alabel = new Label("View Applications");
        Label vlabel = new Label("Browse Vacancies");
        Label clabel = new Label("Browse Companies");
        Label slabel = new Label("Signout");
        alabel.setStyle("-fx-text-fill: #ffffff;");
        vlabel.setStyle("-fx-text-fill: #ffffff;");
        clabel.setStyle("-fx-text-fill: #ffffff;");
        slabel.setStyle("-fx-text-fill: #ffffff;");
        StackPane apps = new StackPane(alabel);
        StackPane vac = new StackPane(vlabel);
        StackPane com = new StackPane(clabel);
        StackPane signout = new StackPane(slabel);
        
        apps.setPrefSize(150, 100);
        vac.setPrefSize(150, 100);
        com.setPrefSize(150, 100);
        signout.setPrefSize(150, 100);
        
        apps.setStyle("-fx-background-color: #1467cc; -fx-background-radius: 15px; -fx-border-radius: 15px; -fx-padding: 30 10; -fx-text-fill: #ffffff");
        vac.setStyle("-fx-background-color: #1467cc; -fx-background-radius: 15px; -fx-border-radius: 15px; -fx-padding: 30 10; -fx-text-fill: #ffffff");
        com.setStyle("-fx-background-color: #1467cc; -fx-background-radius: 15px; -fx-border-radius: 15px; -fx-padding: 30 10; -fx-text-fill: #ffffff");
        signout.setStyle("-fx-background-color: #1467cc; -fx-background-radius: 15px; -fx-border-radius: 15px; -fx-padding: 30 10; -fx-text-fill: #ffffff");
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(20);
        gridPane.add(apps, 0, 0);
        gridPane.add(vac, 1, 0);
        gridPane.add(com, 0, 1);
        gridPane.add(signout, 1, 1);
        
        Label msg = new Label();
        msg.setStyle("-fx-text-fill: #ff0000");
        HBox hbox = new HBox(msg);
        hbox.setAlignment(Pos.CENTER);
        seekerPane.setBottom(hbox);
        
        apps.setOnMousePressed((e) -> {
            if(loggedSeeker.getApplications().size() == 0) msg.setText("No Applications");
            else {
                loadSeekerApps(stage);
                stage.setScene(seekerApps);
            }
        });
        seekerPane.setCenter(gridPane);
        
        vac.setOnMousePressed((e) -> {
            if(allVacancies.size() == 0) msg.setText("No Vacancies");
            else {
                loadSeekerVac(stage);
                stage.setScene(seekerVac);
            }
        });
        
        com.setOnMousePressed((e) -> {
            loadCompaniesScene(stage);
            stage.setScene(comapniesScene);
        });
        
        signout.setOnMousePressed((e) -> {
            loginerr.setText("");
            stage.setScene(loginScene);
        });
    }
    
    public void loadSeekerApps(Stage stage) {
        BorderPane seekerPane = new BorderPane();
        seekerPane.setPadding(new Insets(20));
        seekerApps = new Scene(seekerPane, 650, 550);
        Label welLabel = new Label("Your Applications");
        welLabel.setStyle("-fx-font-size: 24px;");
        seekerPane.setTop(welLabel);
        seekerPane.setAlignment(welLabel, Pos.CENTER);
        ScrollPane scrollpane = new ScrollPane();
        scrollpane.setStyle("-fx-focus-color: transparent;");
        scrollpane.setPadding(new Insets(20, 1, 20, 1)); 
        VBox vPane = new VBox();
        vPane.setSpacing(20);
        vPane.setAlignment(Pos.CENTER);
        
        Button back = new Button("Back");
        Region r1 = new Region();
        Region r2 = new Region();
        Label errmsg = new Label();
        errmsg.setStyle("-fx-text-fill: #ff0000;");
        HBox.setHgrow(r1, Priority.ALWAYS);
        HBox.setHgrow(r2, Priority.ALWAYS);
        HBox bottom = new HBox(back, r1, errmsg, r2);
        seekerPane.setBottom(bottom);
        
        ArrayList<VacancyApp> jsApps = loggedSeeker.getApplications();
        for(int i = 0; i < jsApps.size(); i++) {
            Label acompany = new Label("Company: " + jsApps.get(i).getVacancy().companyName());
            Label atitle = new Label("Title: " + jsApps.get(i).getVacancy().getName());
            Label asalary = new Label("Salary: " + String.valueOf(jsApps.get(i).getVacancy().getSalary()));
            Label aprop = new Label("Proposal: " + jsApps.get(i).getProposal());
            TextField tfaprop = new TextField(jsApps.get(i).getProposal());
            Label astate = new Label("State: " + String.valueOf(jsApps.get(i).getState()));
            acompany.setStyle("-fx-text-fill: white;");
            atitle.setStyle("-fx-text-fill: white;");
            asalary.setStyle("-fx-text-fill: white;");
            aprop.setStyle("-fx-text-fill: white;");
            astate.setStyle("-fx-text-fill: white;");
            Button editButton = new Button("Edit");
            editButton.setId(String.valueOf(i));
            Button deleteButton = new Button("Delete");
            deleteButton.setId(String.valueOf(i));
            HBox appBox = new HBox(editButton, deleteButton);
            appBox.setSpacing(5);
            appBox.setAlignment(Pos.CENTER);
            VBox vbox = new VBox();
            if(jsApps.get(i).isResponded()) vbox.getChildren().addAll(acompany, atitle, asalary, aprop, astate);
            else vbox.getChildren().addAll(acompany, atitle, asalary, tfaprop, astate, appBox);
            vbox.setStyle("-fx-background-color: #1467cc; -fx-background-radius: 15px; -fx-border-radius: 15px;");
            vbox.setAlignment(Pos.CENTER);
            vbox.setSpacing(10);
            vbox.setPadding(new Insets(12));
            vbox.translateXProperty().bind(scrollpane.widthProperty().subtract(vbox.widthProperty()).divide(2));
            vPane.getChildren().add(vbox);
            
            editButton.setOnAction((e) -> {
                String newprop = tfaprop.getText();
                if(newprop.isEmpty()) {
                    errmsg.setStyle("-fx-text-fill: #ff0000;");
                    errmsg.setText("Proposal cannot be empty");
                }
                else {
                    String id = ((Button)e.getSource()).getId();
                    loggedSeeker.editApplication(newprop, jsApps.get(Integer.parseInt(id)));
                    errmsg.setText("Application updated successfully!");
                    errmsg.setStyle("-fx-text-fill: green;");
                }
            });
            
            deleteButton.setOnAction((e) -> {
                String id = ((Button)e.getSource()).getId();
                vPane.getChildren().remove(vbox);
                loggedSeeker.removeApplication(jsApps.get(Integer.parseInt(id)));
                errmsg.setText("Application deleted successfully!");
                errmsg.setStyle("-fx-text-fill: green;");
            });
        }
        scrollpane.setContent(vPane);
        seekerPane.setCenter(scrollpane);
        
        
        back.setOnAction((e) -> {
            stage.setScene(seekerScene);
        });
        
    }
    
    public void loadSeekerVac(Stage stage) {
        BorderPane seekerVacPane = new BorderPane();
        seekerVacPane.setPadding(new Insets(20));
        seekerVac = new Scene(seekerVacPane, 700, 700);
        Label welLabel = new Label("Vacancies");
        welLabel.setStyle("-fx-font-size: 24px;");
        seekerVacPane.setTop(welLabel);
        seekerVacPane.setAlignment(welLabel, Pos.CENTER);
        VBox vPane = new VBox();
        vPane.setSpacing(20);
        vPane.setAlignment(Pos.CENTER);
        ScrollPane scrollpane = new ScrollPane();
        scrollpane.setStyle("-fx-focus-color: transparent;");
        scrollpane.setPadding(new Insets(20, 1, 20, 1)); 
        for(int i = 0; i < allVacancies.size(); i++) {
            Label vtitle = new Label("Title: " + allVacancies.get(i).getName());
            Label vdesc = new Label("Description: " + allVacancies.get(i).getDescripton());
            Label vsalary = new Label("Salary: " + allVacancies.get(i).getSalary());
            Label vcompany = new Label("Company: " + allVacancies.get(i).companyName());
            vtitle.setStyle("-fx-text-fill: #ffffff;");
            vdesc.setStyle("-fx-text-fill: #ffffff;");
            vsalary.setStyle("-fx-text-fill: #ffffff;");
            vcompany.setStyle("-fx-text-fill: #ffffff;");
            VBox vbox = new VBox(vtitle, vdesc, vsalary, vcompany);
            vbox.setId(String.valueOf(i));
            vbox.setStyle("-fx-background-color: #1467cc; -fx-background-radius: 15px; -fx-border-radius: 15px;");
            vbox.setAlignment(Pos.CENTER);
            vbox.setSpacing(10);
            vbox.setPadding(new Insets(12));
            vbox.translateXProperty().bind(scrollpane.widthProperty().subtract(vbox.widthProperty()).divide(2));
            vPane.getChildren().add(vbox);
            HandleApplyVac handkApplyVac = new HandleApplyVac();
            vbox.setOnMousePressed(handkApplyVac);
        }
        scrollpane.setContent(vPane);
        seekerVacPane.setCenter(scrollpane);
        Button back = new Button("Back");
        Region r1 = new Region();
        Region r2 = new Region();
        Button apply = new Button("Apply");
        Label vmsg = new Label();
        vmsg.setId("msg");
        HBox.setHgrow(r1, Priority.ALWAYS);
        HBox.setHgrow(r2, Priority.ALWAYS);
        HBox buttons = new HBox(back, r1, vmsg, r2, apply);
        seekerVacPane.setBottom(buttons);

        back.setOnAction((e) -> {
            vacToApply = null;
            loadSeekerScene(stage);
            stage.setScene(seekerScene);
        });
        
        apply.setOnAction((e) -> {
            if(vacToApply == null) {
                vmsg.setStyle("-fx-text-fill: #ff0000");
                vmsg.setText("Please select a vacancy first!");
            }
            else {
                loadApplyVac(stage, vmsg);
                stage.setScene(applyVacScene);
            }
        });
    }
    
    public void loadApplyVac(Stage stage, Label msg) {
        BorderPane applyVacPane = new BorderPane();
        applyVacPane.setPadding(new Insets(20));
        applyVacScene = new Scene(applyVacPane, 500, 350);
        Label welLabel = new Label("Apply to " + vacToApply.getName() + " position at " + vacToApply.companyName());
        welLabel.setStyle("-fx-font-size: 24px;");
        applyVacPane.setTop(welLabel);
        applyVacPane.setAlignment(welLabel, Pos.CENTER);
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(20);
        Label err = new Label();
        err.setStyle("-fx-text-fill: #ff0000");
        gridPane.add(err, 1, 0);
        Label prop = new Label("Proposal:");
        TextField tfprop = new TextField();
        Button apply = new Button("Apply");
        HBox hbox = new HBox(apply);
        hbox.setAlignment(Pos.CENTER);
        gridPane.add(prop, 0, 1);
        gridPane.add(tfprop, 1, 1);
        gridPane.add(hbox, 1, 2);
        applyVacPane.setCenter(gridPane);
        Button back = new Button("Back");
        applyVacPane.setBottom(back);
        
        apply.setOnAction((e) -> {
            String propv = tfprop.getText();
            if(propv.isEmpty()) err.setText("Proposal cannot be empty!");
            else {
                loggedSeeker.apply(propv, vacToApply);
                msg.setText("You have applied to " + vacToApply.getName() + " position at " + vacToApply.companyName() + " successfully!");
                msg.setStyle("-fx-text-fill: green");
                vacToApply = null;
                for(int x = 0; x < allVacancies.size(); x++) {
                    VBox svbox = (VBox) seekerVac.lookup("#" + String.valueOf(x));
                    svbox.setStyle("-fx-background-color: #1467cc; -fx-background-radius: 15px; -fx-border-radius: 15px;");
                }
                stage.setScene(seekerVac);
            }
        });
        
        back.setOnAction((e) -> {
            vacToApply = null;
            loadSeekerVac(stage);
            stage.setScene(seekerVac);
        });
    }
    
    public void loadCompaniesScene(Stage stage) {
        BorderPane companiesPane = new BorderPane();
        companiesPane.setPadding(new Insets(20));
        comapniesScene = new Scene(companiesPane, 600, 500);
        Label appTitle = new Label("Select a Company to show/add reviews");
        appTitle.setStyle("-fx-font-size: 20px;");
        companiesPane.setTop(appTitle);
        companiesPane.setAlignment(appTitle, Pos.CENTER);
        
        FlowPane cFlow = new FlowPane();
        cFlow.setHgap(15);
        cFlow.setVgap(20);
        cFlow.setAlignment(Pos.CENTER);
        
        for(int i = 0; i < allCompanies.size(); i++) {
            Label cname = new Label("Name: " + allCompanies.get(i).getName());
            Label cempl = new Label("Number of Employees: " + allCompanies.get(i).getEmployees());
            Label cvac = new Label("Number of Vacancies: " + allCompanies.get(i).getVacancies().size());
            cname.setStyle("-fx-text-fill: white;");
            cempl.setStyle("-fx-text-fill: white;");
            cvac.setStyle("-fx-text-fill: white;");
            VBox vbox = new VBox(cname, cempl, cvac);
            vbox.setStyle("-fx-background-color: #1467cc; -fx-background-radius: 15px; -fx-border-radius: 15px;");
            vbox.setAlignment(Pos.CENTER);
            vbox.setSpacing(10);
            vbox.setPadding(new Insets(12));
            vbox.setId(String.valueOf(i));
            cFlow.getChildren().add(vbox);
            vbox.setOnMousePressed((e) -> {
                String id = ((VBox)e.getSource()).getId();
                companyToReview = allCompanies.get(Integer.parseInt(id));
                loadCompanyReviewsScene(stage);
                stage.setScene(reviewScene);
            });
        }
        companiesPane.setCenter(cFlow);
        Button back = new Button("Back");
        companiesPane.setBottom(back);
        
        back.setOnAction((e) -> {
            loadSeekerScene(stage);
            stage.setScene(seekerScene);
        });
    }
    
    public void loadCompanyReviewsScene(Stage stage) {
        BorderPane reviewPane = new BorderPane();
        reviewPane.setPadding(new Insets(20));
        reviewScene = new Scene(reviewPane, 600, 500);
        Label welLabel = new Label("Reviews");
        welLabel.setStyle("-fx-font-size: 24px;");
        reviewPane.setTop(welLabel);
        reviewPane.setAlignment(welLabel, Pos.CENTER);
        VBox vPane = new VBox();
        vPane.setSpacing(20);
        vPane.setAlignment(Pos.CENTER);
        ScrollPane scrollpane = new ScrollPane();
        scrollpane.setStyle("-fx-focus-color: transparent;");
        scrollpane.setPadding(new Insets(20, 1, 20, 1));
        
        Label newreview = new Label("Add new review");
        newreview.setStyle("-fx-text-fill: #ffffff;");
        TextField tfreview = new TextField();
        Button addReview = new Button("Add");
        HBox addbox = new HBox(addReview);
        addbox.setAlignment(Pos.CENTER);
        VBox nbox = new VBox(newreview, tfreview, addbox);
        
        nbox.setStyle("-fx-background-color: #1467cc; -fx-background-radius: 15px; -fx-border-radius: 15px;");
        nbox.setAlignment(Pos.CENTER);
        nbox.setSpacing(10);
        nbox.setPadding(new Insets(12));
        nbox.translateXProperty().bind(scrollpane.widthProperty().subtract(nbox.widthProperty()).divide(2));
        vPane.getChildren().add(nbox);
        
        ArrayList<Review> reviews = companyToReview.getReviews();
        for(int i = 0; i < reviews.size(); i++) {
            Label rtitle = new Label("Name: " + reviews.get(i).getJobSeeker().getName());
            Label review = new Label("Description: " + reviews.get(i).getBody());
            rtitle.setStyle("-fx-text-fill: #ffffff;");
            review.setStyle("-fx-text-fill: #ffffff;");
            VBox vbox = new VBox(rtitle, review);
            vbox.setStyle("-fx-background-color: #1467cc; -fx-background-radius: 15px; -fx-border-radius: 15px;");
            vbox.setAlignment(Pos.CENTER);
            vbox.setSpacing(10);
            vbox.setPadding(new Insets(12));
            vbox.translateXProperty().bind(scrollpane.widthProperty().subtract(vbox.widthProperty()).divide(2));
            vPane.getChildren().add(vbox);
        }
        scrollpane.setContent(vPane);
        reviewPane.setCenter(scrollpane);
        Button back = new Button("Back");
        
        addReview.setOnAction((e) -> {
            String reviewv = tfreview.getText();
            if(reviewv.isEmpty()) newreview.setStyle("-fx-text-fill: #ff0000;");
            else {
                loggedSeeker.addReview(reviewv, companyToReview);
                Label rtitle = new Label("Name: " + loggedSeeker.getName());
                Label review = new Label("Description: " + reviewv);
                rtitle.setStyle("-fx-text-fill: #ffffff;");
                review.setStyle("-fx-text-fill: #ffffff;");
                VBox vbox = new VBox(rtitle, review);
                vbox.setStyle("-fx-background-color: #1467cc; -fx-background-radius: 15px; -fx-border-radius: 15px;");
                vbox.setAlignment(Pos.CENTER);
                vbox.setSpacing(10);
                vbox.setPadding(new Insets(12));
                vbox.translateXProperty().bind(scrollpane.widthProperty().subtract(vbox.widthProperty()).divide(2));
                vPane.getChildren().add(vbox);
                scrollpane.setContent(vPane);
                tfreview.setText("");
            }
        });
        
        back.setOnAction((e) -> {
            stage.setScene(comapniesScene);
        });
        reviewPane.setBottom(back);
    }
    
    class HandleAccountType implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            String deselect = "-fx-background-color: #cccccc;";
            String select  = "-fx-background-color: #aaaaaa;";
            accAdmin.setStyle(deselect);
            accPoster.setStyle(deselect);
            accSeeker.setStyle(deselect);
            String btn = ((Button)e.getSource()).getText();
            if(btn == "Admin") {
                accAdmin.setStyle(select);
                accountType = 1;
            }
            else if(btn == "Job Poster") {
                accPoster.setStyle(select);
                accountType = 2;
            }
            else if(btn == "Job Seeker") {
                accSeeker.setStyle(select);
                accountType = 3;
            }
        }
        
    }
    
    class HandleAddPoster implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent e) {
            for(int i = 0; i < allPosters.size(); i++) {
                VBox vbox = (VBox) postersScene.lookup("#" + String.valueOf(i));
                vbox.setStyle("-fx-background-color: #1467cc; -fx-background-radius: 15px; -fx-border-radius: 15px;");
            }
            VBox box = (VBox)e.getSource();
            box.setStyle("-fx-background-color: #09c; -fx-background-radius: 15px; -fx-border-radius: 15px;");
            String id = box.getId();
            posterToAssign = allPosters.get(Integer.parseInt(id));
        }
    }
    
    class HandleAppRespond implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent e) {
            for(int i = 0; i < applications.size(); i++) {
                VBox vbox = (VBox) appScene.lookup("#" + String.valueOf(i));
                vbox.setStyle("-fx-background-color: #1467cc; -fx-background-radius: 15px; -fx-border-radius: 15px;");
            }
            VBox box = (VBox)e.getSource();
            box.setStyle("-fx-background-color: #09c; -fx-background-radius: 15px; -fx-border-radius: 15px;");
            String id = box.getId();
            applicationToRespond = applications.get(Integer.parseInt(id));
        }
    }
    
    class HandleApplyVac implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent e) {     
            for(int x = 0; x < allVacancies.size(); x++) {
                VBox svbox = (VBox) seekerVac.lookup("#" + String.valueOf(x));
                svbox.setStyle("-fx-background-color: #1467cc; -fx-background-radius: 15px; -fx-border-radius: 15px;");
            }
            int id = Integer.parseInt(((VBox)e.getSource()).getId());
            Label msg = (Label) seekerVac.lookup("#msg");
            if(loggedSeeker.checkPreApplication(allVacancies.get(id)) != null) {
                msg.setStyle("-fx-text-fill: #ff0000");
                msg.setText("You have applied to this position before!");
            }
            else {
                vacToApply = allVacancies.get(id);
                VBox box = (VBox)e.getSource();
                box.setStyle("-fx-background-color: #09c; -fx-background-radius: 15px; -fx-border-radius: 15px;");
                msg.setText("");
            }
        }
    }
    

    public static void main(String[] args) {
        
        Company c1 = new Company("Google");
        Company c2 = new Company("Facebook");
        Company c3 = new Company("Microsoft");
        Company c4 = new Company("Apple");
        Company c5 = new Company("Dell");
        
        Admin a1 = new Admin("Ahmed", "ahmed@gmail.com","0000", c1);
        Admin a2 = new Admin("Ali", "ali@gmail.com", "0000" , c2);
        Admin a3 = new Admin("Khalid", "khalid@gmail.com", "0000" , c3);
        Admin a4 = new Admin("Baher", "Baher@gmail.com", "0000" , c4);
        Admin a5 = new Admin("Omar", "omar@gmail.com", "0000" , c5);

        JobPoster jp1 = new JobPoster("Nada", "Nada@gmail.com", "0000");
        JobPoster jp2 = new JobPoster("Essam", "Essam@gmail.com", "0000");
        JobPoster jp3 = new JobPoster("Doaa", "doaa@gmail.com", "0000");
        JobPoster jp4 = new JobPoster("Yasmina", "Yasmina@gmail.com", "0000");
        JobPoster jp5 = new JobPoster("Noura", "noura@gmail.com", "0000");
        JobPoster jp6 = new JobPoster("Ali", "ali@gmail.com", "0000");
        JobPoster jp7 = new JobPoster("Ali", "ali@gmail.com", "0000");
        JobPoster jp8 = new JobPoster("Ali", "ali@gmail.com", "0000");
        JobPoster jp9 = new JobPoster("Ali", "ali@gmail.com", "0000");

        
        a1.addPoster(jp1);
        a2.addPoster(jp2);
        a3.addPoster(jp3);
        a4.addPoster(jp4);
        a5.addPoster(jp5);
        
        jp1.addVacancy("Designer", "Required a Designer to design the required resources", 2400);
        jp1.addVacancy("Developer", "Required a Developer to develop the required resources", 2000);
        jp1.addVacancy("Software Engineer", "Required a Software Engineer to manage the required resources", 4400);
        jp2.addVacancy("Manager", "Require an Expert Manager to manage on of the department", 4500);
        jp3.addVacancy("Web Developer", "Require a junior web devoloper to work on the back end of our website", 3600);
        jp4.addVacancy("Software Tester", "Require an Expert Software tester to test our software", 4700);
        jp5.addVacancy("Hardware Developer", "Require an Expert Hardware Developer in our Hardware department", 7300);
        
        JobSeeker s1 = new JobSeeker("kareem", "kareem@gmail.com", "0000");
        JobSeeker s2 = new JobSeeker("der3", "der3@gmail.com", "0000");
        JobSeeker s3 = new JobSeeker("mahmoud", "mahmoud@gmail.com", "0000");
        JobSeeker s4 = new JobSeeker("nezar", "nezar@gmail.com", "0000");
        JobSeeker s5 = new JobSeeker("ameen", "ameen@gmail.com", "0000");
        JobSeeker s6 = new JobSeeker("yassin", "yassin@gmail.com", "0000");


        s1.apply("any", Vacancy.getVacancies().get(0));
        s2.apply("any", Vacancy.getVacancies().get(1));
        s3.apply("any", Vacancy.getVacancies().get(2));
        s4.apply("any", Vacancy.getVacancies().get(3));
        s4.apply("any", Vacancy.getVacancies().get(4));
        s4.apply("any", Vacancy.getVacancies().get(6));
        s4.apply("any", Vacancy.getVacancies().get(0));
        s5.apply("any", Vacancy.getVacancies().get(4));

        s1.addReview("Very good company", c1);
        s2.addReview("Bad company", c1);
        s3.addReview("Amazing Community", c1);
        s4.addReview("Good Community", c2);
        s4.addReview("Good Community", c1);
        s5.addReview("Amazing Place", c2);
        
        launch();
    }

}
