package com.loan.loandefault_predictor.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "loan_applications")
@Data
public class LoanApplication {
    @Id
    private String id;

    private Integer age;
    private Integer income;
    private Integer loanAmount;
    private Integer creditScore;
    private Integer monthsEmployed;
    private Integer numCreditLines;
    private Double interestRate;
    private Integer loanTerm;
    private Double dtiRatio;

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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getIncome() {
        return income;
    }

    public void setIncome(Integer income) {
        this.income = income;
    }

    public Integer getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Integer loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Integer getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(Integer creditScore) {
        this.creditScore = creditScore;
    }

    public Integer getMonthsEmployed() {
        return monthsEmployed;
    }

    public void setMonthsEmployed(Integer monthsEmployed) {
        this.monthsEmployed = monthsEmployed;
    }

    public Integer getNumCreditLines() {
        return numCreditLines;
    }

    public void setNumCreditLines(Integer numCreditLines) {
        this.numCreditLines = numCreditLines;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public Integer getLoanTerm() {
        return loanTerm;
    }

    public void setLoanTerm(Integer loanTerm) {
        this.loanTerm = loanTerm;
    }

    public Double getDtiRatio() {
        return dtiRatio;
    }

    public void setDtiRatio(Double dtiRatio) {
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
