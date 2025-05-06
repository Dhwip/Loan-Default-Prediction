package com.loan.loandefault_predictor.controller;

import com.loan.loandefault_predictor.model.LoanApplication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class LoanFormController {

    @PostMapping("/predict")
    public PredictionResponse predictDefault(@RequestBody LoanApplication application) {
        // Simple rule-based prediction for demo
        String prediction;
        if (application.getCreditScore() < 600 || application.getDtiRatio() > 0.4) {
            prediction = "Likely to default";
        } else {
            prediction = "Unlikely to default";
        }
        return new PredictionResponse(prediction);
    }

    public static class PredictionResponse {
        private String prediction;
        public PredictionResponse(String prediction) { this.prediction = prediction; }
        public String getPrediction() { return prediction; }
        public void setPrediction(String prediction) { this.prediction = prediction; }
    }
}

