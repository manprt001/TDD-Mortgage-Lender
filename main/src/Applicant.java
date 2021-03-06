public class Applicant {

    private int id;
    private int loan_amount;
    private int requested_amount;
    private int dti;
    private int credit_score;
    private int savings;
    private String qualification;
    private String status;

    public Applicant(int id, int dti, int credit_score, int savings, int requested_amount) {
        this.id = id;
        this.dti = dti;
        this.credit_score = credit_score;
        this.savings = savings;
        this.requested_amount = requested_amount;
    }

    public int getDti() {
        return dti;
    }

    public void setDti(int dti) {
        this.dti = dti;
    }

    public int getCredit_score() {
        return credit_score;
    }

    public void setCredit_score(int credit_score) {
        this.credit_score = credit_score;
    }

    public int getSavings() {
        return savings;
    }

    public void setSavings(int savings) {
        this.savings = savings;
    }

    public int getLoan_amount() {
        return loan_amount;
    }

    public void setLoan_amount(int loan_amount) {
        this.loan_amount = loan_amount;
    }

    public int getRequested_amount() {
        return requested_amount;
    }

    public void setRequested_amount(int requested_amount) {
        this.requested_amount = requested_amount;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
