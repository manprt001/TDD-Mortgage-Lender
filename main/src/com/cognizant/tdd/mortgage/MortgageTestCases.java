package com.cognizant.tdd.mortgage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MortgageTestCases {

    Bank bank;

    @BeforeEach
    public void setUp(){
        bank = new Bank();
        bank.setCurrent_amount(500000);
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
       assertEquals("Status: qualified", bank.qualification(new Applicant(21,700,100000),250000));
        assertEquals("Status: denied", bank.qualification(new Applicant(37,700,100000), 250000));
        assertEquals("Status: denied", bank.qualification(new Applicant(30,600,100000), 250000));
        assertEquals("Status: qualified", bank.qualification(new Applicant(30,700,50000), 250000));

    }


    //As a lender, I want to only approve loans when I have available funds, so that I don't go bankrupt.
    //Given I have <available_funds> in available funds
    //When I process a qualified loan
    //Then the loan status is set to <status>


    //When I process a not qualified loan
    //Then I should see a warning to not proceed


    //As a lender, I want to keep pending loan amounts in a separate account, so I don't extend too many offers and bankrupt myself.
    //Given I have approved a loan
    //Then the requested loan amount is moved from available funds to pending funds
    //And I see the available and pending funds reflect the changes accordingly


    //As a lender, I want to process response for approved loans, so that I can move forward with the loan.
    //Given I have an approved loan
    //When the applicant accepts my loan offer
    //Then the loan amount is removed from the pending funds
    //And the loan status is marked as accepted


    //Given I have an approved loan
    //When the applicant rejects my loan offer
    //Then the loan amount is moved from the pending funds back to available funds
    //And the loan status is marked as rejected


    //As a lender, I want to check if there are any undecided loans, so that I can manage my time and money wisely.
    //Given there is an approved loan offered more than 3 days ago
    //When I check for expired loans
    //Then the loan amount is move from the pending funds back to available funds
    //And the loan status is marked as expired


    //As a lender, I want to filter loans by status, so that I can have an overview.
    //Given there are loans in my system
    //When I search by loan status (qualified, denied, on hold, approved, accepted, rejected, expired)
    //Then I should see a list of loans and their details



}