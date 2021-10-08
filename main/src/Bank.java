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

    public int getTotalpending() {
        return totalpending;
    }

    public void setTotalpending(int totalpending) {
        this.totalpending = totalpending;
    }

    private int totalpending;

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

    public Applicant determineStatus(int id){
        Applicant app = applicantMap.get(id);
        if(app.getDti()<36 && app.getCredit_score()>620 && app.getSavings()>=0.25*app.getRequested_amount()){
            app.setQualification("qualified");
        }
        else if(app.getDti()<36 && app.getCredit_score()>620 && (app.getSavings()<0.25*app.getRequested_amount())){
            app.setQualification("partially qualified");
        }
        else{
            app.setQualification("not qualified");
        }

        if(app.getQualification().equals("qualified")){
            app.setStatus("qualified");
            app.setLoan_amount(app.getRequested_amount());
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

    public String movetoPending(int reqAmount, int total, int pending){
        setTotalpending(pending + reqAmount);   //250000
        //totalpending = reqAmount;
        setTotal(total - reqAmount);    //50000
        //total = total - totalpending;

        return getTotal() + " " + getTotalpending();

    }

    public String processResponse(Applicant app, boolean accept,int totalpending, int loan_amount, int total){
        if ( accept == true){
            totalpending = totalpending - loan_amount;
            app.setStatus("accepted");
        }
        else if (accept == false){
            totalpending = totalpending - loan_amount ;
            total = total + loan_amount;
            app.setStatus("rejected");
           

        }
        return app.getStatus();
    }

    public String checkExpired(Applicant app, int days, int loan_amount, int total, int totalpending){
        if(days >3){
            totalpending = totalpending - loan_amount ;
            total = total + loan_amount;
            app.setStatus("expired");
        }
         else if(days<3){
            app.setStatus("undecided");

        }
        return app.getStatus();
    }

    public String getListOfApplicants(){
        List<Applicant> mapList = new ArrayList<>(applicantMap.values());
        ArrayList<String> list = new ArrayList<>();
        for(Applicant app : mapList){
            list.add("ApplicantID: " + app.getId() + " Status: " + app.getStatus() + " Loan Amount: " + app.getLoan_amount() + "\n");

            //System.out.println(app.getStatus());
        }
        String listString = list.toString();
        return listString;
    }

}
