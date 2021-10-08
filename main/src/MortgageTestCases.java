import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MortgageTestCases {

    Bank bank;

    @BeforeEach
    public void setUp(){
        bank = new Bank();
        bank.setCurrent_amount(500000);
        bank.setTotal(300000);
    }

    //As a lender, I want to be able to check my available funds, so that I know how much money I can offer for loans.
    //When I check my available funds
    //Then I should see how much funds I currently have
    @Test
    public void testSeeFunds(){
        assertEquals(500000,bank.getCurrent_amount());

    }

    //As a lender, I want to add money to my available funds, so that I can offer loans to potential home buyers.
    //Given I have <current_amount> available funds
    //When I add <deposit_amount>
    //Then my available funds should be <total>
    @Test
    public void testSeeTotal(){
        assertEquals(150000, bank.getTotal(new Bank(100000,50000)));
        assertEquals(230000,bank.getTotal(new Bank(200000,30000)));
    }



    //As a lender, I want to accept and qualify loan applications, so that I can ensure I get my money back.
    //Given a loan applicant with <dti>, <credit_score>, and <savings>
    // When they apply for a loan with <requested_amount>
    // Then their qualification is <qualification>And their loan amount is <loan_amount>
    // And their loan status is <status>
    @Test
    public void testSeeStatus(){
        bank.addApplicant(new Applicant(1, 21,700,100000,250000));
        String st1 = bank.determineStatus(1).getStatus();
        assertEquals("qualified", st1);

        bank.addApplicant(new Applicant(2, 37,700,100000, 250000));
        String st2 = bank.determineStatus(2).getStatus();
        assertEquals("denied", st2);

        bank.addApplicant(new Applicant(3,30,600,100000, 250000));
        String st3 = bank.determineStatus(3).getStatus();
        assertEquals("denied", st3);

        bank.addApplicant(new Applicant(4,30,700,50000, 250000));
        String st4 = bank.determineStatus(4).getStatus();
        assertEquals("qualified", st4);

//        assertEquals("Status: denied", bank.determineStatus(new Applicant(37,700,100000), 250000));
//        assertEquals("Status: denied", bank.determineStatus(new Applicant(30,600,100000), 250000));
//        assertEquals("Status: qualified", bank.determineStatus(new Applicant(30,700,50000), 250000));
        //System.out.println(bank.getListOfApplicants());
    }


    //As a lender, I want to only approve loans when I have available funds, so that I don't go bankrupt.
    //Given I have <available_funds> in available funds
    //When I process a qualified loan
    //Then the loan status is set to <status>
    //When I process a not qualified loan
    //Then I should see a warning to not proceed
    @Test
    public void testApproveLoan() {
//        assertEquals("on hold", bank.processQualified(125000,100000));
//        assertEquals("approved", bank.processQualified(125000,200000));
//        assertEquals("approved", bank.processQualified(125000,125000));

        bank.addApplicant(new Applicant(1, 21,700,100000, 125000));
        Applicant st1 = bank.determineStatus(1);
//        Applicant info = bank.determineStatus(new Applicant(1,21, 700, 100000), 125000);
        assertEquals("on hold", bank.processQualified(st1,100000));

        bank.addApplicant(new Applicant(2, 30,700,100000, 125000));
        Applicant st2 = bank.determineStatus(2);
//        Applicant info2 = bank.determineStatus(new Applicant(2,30,700,100000), 125000);
        assertEquals("approved", bank.processQualified(st2,200000));

        bank.addApplicant(new Applicant(3,30,600,100000, 125000));
        Applicant st3 = bank.determineStatus(3);
//       Applicant info3 = bank.determineStatus(new Applicant(3,30,600,100000), 125000);
        assertEquals("Do not proceed!", bank.processQualified(st3,125000));

        bank.addApplicant(new Applicant(4,30,700,50000, 125000));
        Applicant st4 = bank.determineStatus(4);
//       Applicant info4 = bank.determineStatus(new Applicant(4,30,700,50000), 125000);
        assertEquals("approved", bank.processQualified(st4,125000));

        //System.out.println(bank.getListOfApplicants());

        }


    //As a lender, I want to keep pending loan amounts in a separate account, so I don't extend too many offers and bankrupt myself.
    //Given I have approved a loan
    //Then the requested loan amount is moved from available funds to pending funds
    //And I see the available and pending funds reflect the changes accordingly
    @Test
    public void testPending(){
        bank.addApplicant(new Applicant(2, 30,700,100000, 125000));
        Applicant st2 = bank.determineStatus(2);
        bank.processQualified(st2,bank.getTotal());
        assertEquals("175000 125000", bank.movetoPending(st2.getRequested_amount(),bank.getTotal(), bank.getTotalpending()));

        bank.addApplicant(new Applicant(4,30,700,50000, 125000));
        Applicant st4 = bank.determineStatus(4);
        bank.processQualified(st4,bank.getTotal());
        assertEquals("50000 250000", bank.movetoPending(st4.getRequested_amount(),bank.getTotal(), bank.getTotalpending()));
    }



        //As a lender, I want to process response for approved loans, so that I can move forward with the loan.
        //Given I have an approved loan
        //When the applicant accepts my loan offer
        //Then the loan amount is removed from the pending funds
        //And the loan status is marked as accepted
        //Given I have an approved loan
        //When the applicant rejects my loan offer
        //Then the loan amount is moved from the pending funds back to available funds
        //And the loan status is marked as rejected
    @Test
    public void testLoanStatus(){
        //assertEquals("accepted", bank.processResponse(true, 250000,150000,500000));
        //assertEquals("rejected", bank.processResponse(false, 250000,50000,500000));


        bank.addApplicant(new Applicant(2, 30,700,100000, 125000));
        Applicant st2 = bank.determineStatus(2);
        bank.processQualified(st2,bank.getTotal());
        bank.movetoPending(st2.getRequested_amount(),bank.getTotal(), bank.getTotalpending());
        assertEquals("accepted", bank.processResponse(st2,true, bank.getTotalpending(),st2.getRequested_amount(),bank.getTotal()));

        bank.addApplicant(new Applicant(4, 30,700,100000, 125000));
        Applicant st3 = bank.determineStatus(2);
        bank.processQualified(st3,bank.getTotal());
        bank.movetoPending(st3.getRequested_amount(),bank.getTotal(), bank.getTotalpending());
        assertEquals("rejected", bank.processResponse(st3,false, bank.getTotalpending(),st3.getRequested_amount(),bank.getTotal()));

    }

        //As a lender, I want to check if there are any undecided loans, so that I can manage my time and money wisely.
        //Given there is an approved loan offered more than 3 days ago
        //When I check for expired loans
        //Then the loan amount is move from the pending funds back to available funds
        //And the loan status is marked as expired
    @Test
    public void testundecidedLoan(){
//        assertEquals("expired", bank.checkExpired(4,250000,500000,250000));
//        assertEquals("undecided", bank.checkExpired(2,250000,500000,250000));

        bank.addApplicant(new Applicant(2, 30,700,100000, 125000));
        Applicant st2 = bank.determineStatus(2);
        bank.processQualified(st2,bank.getTotal());
        bank.movetoPending(st2.getRequested_amount(),bank.getTotal(), bank.getTotalpending());
        bank.processResponse(st2,true, bank.getTotalpending(),st2.getRequested_amount(),bank.getTotal());
        assertEquals("expired", bank.checkExpired(st2,4,st2.getRequested_amount(),bank.getTotal(),bank.getTotalpending()));

        bank.addApplicant(new Applicant(4, 30,700,50000, 125000));
        Applicant st3 = bank.determineStatus(2);
        bank.processQualified(st3,bank.getTotal());
        bank.movetoPending(st3.getRequested_amount(),bank.getTotal(), bank.getTotalpending());
        bank.processResponse(st3, true, bank.getTotalpending(),st3.getRequested_amount(),bank.getTotal());
        assertEquals("undecided", bank.checkExpired(st2,2,st3.getRequested_amount(),bank.getTotal(),bank.getTotalpending()));




    }

        //As a lender, I want to filter loans by status, so that I can have an overview.
        //Given there are loans in my system
        //When I search by loan status (qualified, denied, on hold, approved, accepted, rejected, expired)
        //Then I should see a list of loans and their details
        @Test
        public void testFilterLoans() {
            bank.addApplicant(new Applicant(1, 21, 700, 100000, 250000));
            String st1 = bank.determineStatus(1).getStatus();
            assertEquals("qualified", st1);
            bank.addApplicant(new Applicant(2, 37, 700, 100000, 250000));
            String st2 = bank.determineStatus(2).getStatus();
            assertEquals("denied", st2);
            bank.addApplicant(new Applicant(3, 30, 600, 100000, 250000));
            String st3 = bank.determineStatus(3).getStatus();
            assertEquals("denied", st3);
            bank.addApplicant(new Applicant(4, 30, 700, 50000, 250000));
            String st4 = bank.determineStatus(4).getStatus();
            assertEquals("qualified", st4);
            bank.addApplicant(new Applicant(5, 21, 700, 100000, 125000));
            Applicant st5 = bank.determineStatus(5);
            assertEquals("on hold", bank.processQualified(st5, 100000));
            bank.addApplicant(new Applicant(6, 30, 700, 100000, 125000));
            Applicant st6 = bank.determineStatus(6);
            assertEquals("approved", bank.processQualified(st6, 200000));
            bank.addApplicant(new Applicant(7, 30, 600, 100000, 125000));
            Applicant st7 = bank.determineStatus(7);
            assertEquals("Do not proceed!", bank.processQualified(st7, 125000));
            bank.addApplicant(new Applicant(8, 30, 700, 50000, 125000));
            Applicant st8 = bank.determineStatus(8);
            assertEquals("approved", bank.processQualified(st8, 125000));

            bank.addApplicant(new Applicant(9, 30,700,100000, 125000));
            Applicant st9 = bank.determineStatus(9);
            bank.processQualified(st9,bank.getTotal());
            bank.movetoPending(st9.getRequested_amount(),bank.getTotal(), bank.getTotalpending());
            assertEquals("accepted", bank.processResponse(st9,true, bank.getTotalpending(),st9.getRequested_amount(),bank.getTotal()));

            bank.addApplicant(new Applicant(10, 30,700,100000, 125000));
            Applicant st10 = bank.determineStatus(10);
            bank.processQualified(st10,bank.getTotal());
            bank.movetoPending(st10.getRequested_amount(),bank.getTotal(), bank.getTotalpending());
            assertEquals("rejected", bank.processResponse(st10,false, bank.getTotalpending(),st10.getRequested_amount(),bank.getTotal()));

            bank.addApplicant(new Applicant(11, 30,700,100000, 125000));
            Applicant st11 = bank.determineStatus(11);
            bank.processQualified(st11,bank.getTotal());
            bank.movetoPending(st11.getRequested_amount(),bank.getTotal(), bank.getTotalpending());
            bank.processResponse(st11,true, bank.getTotalpending(),st11.getRequested_amount(),bank.getTotal());
            assertEquals("expired", bank.checkExpired(st11,4,st11.getRequested_amount(),bank.getTotal(),bank.getTotalpending()));



            assertEquals("[ApplicantID: 1 Status: qualified Loan Amount: 250000\n" +
                    ", ApplicantID: 2 Status: denied Loan Amount: 0\n" +
                    ", ApplicantID: 3 Status: denied Loan Amount: 0\n" +
                    ", ApplicantID: 4 Status: qualified Loan Amount: 200000\n" +
                    ", ApplicantID: 5 Status: on hold Loan Amount: 125000\n" +
                    ", ApplicantID: 6 Status: approved Loan Amount: 125000\n" +
                    ", ApplicantID: 7 Status: Do not proceed! Loan Amount: 0\n" +
                    ", ApplicantID: 8 Status: approved Loan Amount: 125000\n" +
                    ", ApplicantID: 9 Status: accepted Loan Amount: 125000\n" +
                    ", ApplicantID: 10 Status: rejected Loan Amount: 125000\n" +
                    ", ApplicantID: 11 Status: expired Loan Amount: 125000\n" +
                    "]", bank.getListOfApplicants());

            //System.out.println(bank.getListOfApplicants());






        }
}