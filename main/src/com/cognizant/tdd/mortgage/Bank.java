package com.cognizant.tdd.mortgage;

import java.util.ArrayList;

public class Bank {
    ArrayList<Applicant> applicants = new ArrayList<>();

    private int current_amount;
    private int deposit_amount;
    private int total;

    public Bank(int current_amount, int deposit_amount, int total) {
        this.current_amount = current_amount;
        this.deposit_amount = deposit_amount;
        this.total = total;
    }

    public Bank() {
    }
    public Bank(int current_amount, int deposit_amount) {
        this.current_amount = current_amount;
        this.deposit_amount = deposit_amount;
    }


    public int getCurrent_amount() {
        return current_amount;
    }

    public void setCurrent_amount(int current_amount) {
        this.current_amount = current_amount;
    }

    public int getDeposit_amount() {
        return deposit_amount;
    }

    public void setDeposit_amount(int deposit_amount) {
        this.deposit_amount = deposit_amount;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal(Bank bank){
        total = bank.getCurrent_amount() + bank.getDeposit_amount();
        return total;
    }

    public void addApplicant(Applicant app){
        applicants.add(app);
    }

    public String qualification(Applicant app, int requestedamount){
        if(app.getDti()<36 && app.getCredit_score()>620 && app.getSavings()>=0.25*requestedamount){
            app.setQualification("qualified");
        }
        else if(app.getDti()<36 && app.getCredit_score()>620 && (app.getSavings()<0.25*requestedamount)){
            app.setQualification("partially qualified");
        }
        else{
            app.setQualification("not qualified");
        }

        if(app.getQualification().equals("qualified")){
            app.setStatus("qualified");
            app.setLoan_amount(requestedamount);
        }
        else if(app.getQualification().equals("partially qualified")){
            app.setStatus("qualified");
            app.setLoan_amount(4*app.getSavings());
        }
        else{
            app.setStatus("denied");
            app.setLoan_amount(0);
        }

        return("Status: " + app.getStatus());
    }



}
