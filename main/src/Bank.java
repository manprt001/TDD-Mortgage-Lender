import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bank {
    Map<Integer, Applicant> applicantMap = new HashMap<>();

    private int total;
    private int totalpending;

    public Bank() {
    }

    public int getTotalpending() {
        return totalpending;
    }

    public void setTotalpending(int totalpending) {
        this.totalpending = totalpending;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    //checking available funds
    public int seeTotal(int current_amount, int deposit_amount){
        setTotal(current_amount + deposit_amount);
        return total;
    }

    //Adding an applicant object into applicant Map
    public void addApplicant(Applicant app){
        applicantMap.put(app.getId(),app);
    }

    /*
    This method help to detemine the status.We are passing Id as perameter to get applicant object. Setting status
    based DTI scores, credit scores, and savings. We using If else condition to determine different status
     */
    public Applicant determineStatus(int id){
        Applicant app = applicantMap.get(id);
        if(app.getDti()<36 && app.getCredit_score()>620 && app.getSavings()>=0.25*app.getRequested_amount()){
            app.setQualification("qualified"); // condition to get right status
        }
        else if(app.getDti()<36 && app.getCredit_score()>620 && (app.getSavings()<0.25*app.getRequested_amount())){
            app.setQualification("partially qualified");// condition to get right status
        }
        else{
            app.setQualification("not qualified");// condition to get right status
        }

        if(app.getQualification().equals("qualified")){
            app.setStatus("qualified");
            app.setLoan_amount(app.getRequested_amount());//If applicant Q then get the amount requested amount
        }
        else if(app.getQualification().equals("partially qualified")){
            app.setStatus("qualified");
            app.setLoan_amount(4*app.getSavings());// If applicant PQ then get 4 times there savings
             }
        else{
            app.setStatus("denied");
            app.setLoan_amount(0);// If applicant NQ they get nothing.
        }

        return app; // returning the whole applicant object with new status and loan amount
    }

    //This method is for only if there are qualified. We passing applicant and available fund.
    public String processQualified(Applicant app, int total){
        int loan_amount = app.getLoan_amount();
        if(app.getStatus().equals("denied")){
            System.out.println("Do not proceed!");
            app.setStatus("Do not proceed!"); // warn lender to not proceed
        }
        else{
            if(loan_amount <= total){
                app.setStatus("approved");// mark the status approved if loan is then available funds
            }
            else {
                app.setStatus("on hold");// if loan amount it more then we should wait.
            }
        }
        return app.getStatus(); // return the status.
    }

    //when loan is approved the Loan amount is moved from available funds to pending funds
    public String movetoPending(int reqAmount, int total, int pending){
        setTotalpending(pending + reqAmount); // setting pending funds= pending funds + loan amount
        setTotal(total - reqAmount);   // eliminating the loan amount from available funds

        return getTotal() + " " + getTotalpending(); // returning available funds and the pending funds.
    }

    //applicant either accepts or rejects loan offer and the funds are recalculated accordingly
    public String processResponse(Applicant app, boolean accept,int totalpending, int loan_amount, int total){
        //If applicant accepts the given loan amount then removed the loan amount from the pending funds
        if ( accept == true){
            totalpending = totalpending - loan_amount;
            app.setStatus("accepted");
        }
        //if applicant reject the given loan amount then removed the loan amount from the pending funds and added back to available funds
        else if (accept == false){
            totalpending = totalpending - loan_amount ;
            total = total + loan_amount;
            app.setStatus("rejected");
        }
        return app.getStatus(); //return new status
    }

    //checking for undecided and expired loans
    public String checkExpired(Applicant app, int days, int loan_amount, int total, int totalpending){
        //if loan was offered more than 3 days ago then it's expired and loan amount is added back to available funds
        if(days >3){
            totalpending = totalpending - loan_amount ;
            total = total + loan_amount;
            app.setStatus("expired");
        }
        //if loan was not accepted or rejected in 3 days then it's undecided ; loan amount stays the sam
         else if(days<3){
            app.setStatus("undecided");
        }
        return app.getStatus();// returning new status.
    }

    //to display the applicant information.
    public String getListOfApplicants(){
        List<Applicant> mapList = new ArrayList<>(applicantMap.values());
        ArrayList<String> list = new ArrayList<>();
        for(Applicant app : mapList){
            list.add("ApplicantID: " + app.getId() + " Status: " + app.getStatus() + " Loan Amount: " + app.getLoan_amount() + "\n");
        }
        return list.toString();
    }
}
