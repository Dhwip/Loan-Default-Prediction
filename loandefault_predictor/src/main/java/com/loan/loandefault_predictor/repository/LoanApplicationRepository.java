package com.loan.loandefault_predictor.repository;

import com.loan.loandefault_predictor.model.LoanApplication;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LoanApplicationRepository extends MongoRepository<LoanApplication, String> {
}