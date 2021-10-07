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

    public Applicant determineStatus(Applicant app, int requestedamount){
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

        return app;
    }

    public String processQualified(int loan_amount, int total){
        String status;
        if(loan_amount <= total){
            status = "approved";
        }
        else {

            status="on hold";
        }
        return status;
    }

    public String processLoan(Applicant app, int total){
        if(app.getStatus().equals("denied")){
            return("Do not proceed!");
        }
        else{
            return processQualified(app.getLoan_amount(),total);
        }
    }

    public String movetoPending(int reqAmount, int total){
        int totalpending;
        totalpending = reqAmount;
        total = total - totalpending;

        return total + " " + totalpending;

    }

    public String processResponse(boolean accept,int totalpending, int loan_amount, int total){
        String loan_status = null;
        if ( accept == true){
            totalpending = totalpending - loan_amount;
            loan_status = "accepted";
        }
        else if (accept == false){
            totalpending = totalpending - loan_amount ;
            total = total + loan_amount;
            loan_status ="rejected";
           

        }
        return loan_status;
    }

    public String checkExpired(int days, int loan_amount, int total, int totalpending){
      String loan_status =  null;
        if(days >3){
            totalpending = totalpending - loan_amount ;
            total = total + loan_amount;
            loan_status ="expired";
        }
         else if(days<3){
            loan_status ="undecided";

        }
        return loan_status;
    }

}
