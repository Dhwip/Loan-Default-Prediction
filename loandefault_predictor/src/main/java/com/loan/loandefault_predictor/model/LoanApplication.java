package com.loan.loandefault_predictor.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "loan_applications")
@Data
public class LoanApplication {
    @Id
    private String id;

    private int age;
    private int income;
    private int loanAmount;
    private int creditScore;
    private int monthsEmployed;
    private int numCreditLines;
    private double interestRate;
    private int loanTerm;
    private double dtiRatio;

    private String education;
    private String employmentType;
    private String maritalStatus;
    private String hasMortgage;
    private String hasDependents;
    private String loanPurpose;
    private String hasCoSigner;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public int getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(int loanAmount) {
        this.loanAmount = loanAmount;
    }

    public int getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(int creditScore) {
        this.creditScore = creditScore;
    }

    public int getMonthsEmployed() {
        return monthsEmployed;
    }

    public void setMonthsEmployed(int monthsEmployed) {
        this.monthsEmployed = monthsEmployed;
    }

    public int getNumCreditLines() {
        return numCreditLines;
    }

    public void setNumCreditLines(int numCreditLines) {
        this.numCreditLines = numCreditLines;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public int getLoanTerm() {
        return loanTerm;
    }

    public void setLoanTerm(int loanTerm) {
        this.loanTerm = loanTerm;
    }

    public double getDtiRatio() {
        return dtiRatio;
    }

    public void setDtiRatio(double dtiRatio) {
        this.dtiRatio = dtiRatio;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getHasMortgage() {
        return hasMortgage;
    }

    public void setHasMortgage(String hasMortgage) {
        this.hasMortgage = hasMortgage;
    }

    public String getHasDependents() {
        return hasDependents;
    }

    public void setHasDependents(String hasDependents) {
        this.hasDependents = hasDependents;
    }

    public String getLoanPurpose() {
        return loanPurpose;
    }

    public void setLoanPurpose(String loanPurpose) {
        this.loanPurpose = loanPurpose;
    }

    public String getHasCoSigner() {
        return hasCoSigner;
    }

    public void setHasCoSigner(String hasCoSigner) {
        this.hasCoSigner = hasCoSigner;
    }
}
