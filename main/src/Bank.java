import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bank {
    //ArrayList<Applicant> applicants = new ArrayList<>();
    Map<Integer, Applicant> applicantMap = new HashMap<>();

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
        applicantMap.put(app.getId(),app);
    }

    public Applicant determineStatus(int id, int requestedamount){
        Applicant app = applicantMap.get(id);
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

    public String processQualified(Applicant app, int total){
        String status;
        int loan_amount = app.getLoan_amount();
        if(app.getStatus().equals("denied")){
            System.out.println("Do not proceed!");
            app.setStatus("Do not proceed!");
        }
        else{
            if(loan_amount <= total){
                app.setStatus("approved");
            }
            else {
                app.setStatus("on hold");
            }

        }
        return app.getStatus();
    }

//    public void processLoan(Applicant app, int total){
//        if(app.getStatus().equals("denied")){
//            System.out.println("Do not proceed!");
//        }
//        else{
//            processQualified(app,total);
//        }
//    }

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

    public String getListOfApplicants(){
        List<Applicant> idk = new ArrayList<>(applicantMap.values());
        ArrayList<String> list = new ArrayList<>();
        for(Applicant app : idk){
            list.add("ApplicantID: " + app.getId() + " Status: " + app.getStatus() + " Loan Amount: " + app.getLoan_amount() + "\n");

            //System.out.println(app.getStatus());
        }
        String idkstring = list.toString();
        return idkstring;
    }
}
