package com.loan.loandefault_predictor.controller;

import com.loan.loandefault_predictor.model.LoanApplication;
import com.loan.loandefault_predictor.service.LoanPredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class LoanFormController {

    @Autowired
    private LoanPredictionService predictionService;

    @PostMapping("/predict")
    public PredictionResponse predictDefault(@RequestBody LoanApplication application) {
        String prediction = predictionService.getPrediction(application);
        return new PredictionResponse(prediction);
    }

    public static class PredictionResponse {
        private String prediction;

        public PredictionResponse(String prediction) {
            this.prediction = prediction;
        }

        public String getPrediction() {
            return prediction;
        }

        public void setPrediction(String prediction) {
            this.prediction = prediction;
        }
    }
}

// package com.loan.loandefault_predictor.controller;

// import com.loan.loandefault_predictor.model.LoanApplication;
// import org.springframework.web.bind.annotation.*;

// @CrossOrigin(origins = "*")
// @RestController
// @RequestMapping("/api")
// public class LoanFormController {

// @PostMapping("/predict")
// public PredictionResponse predictDefault(@RequestBody LoanApplication
// application) {
// String prediction;
// double dti = application.getDtiRatio();
// int creditScore = application.getCreditScore();
// if (dti > 0.5 || creditScore < 550) {
// prediction = "High risk of default";
// } else if (dti > 0.4 || creditScore < 600) {
// prediction = "Likely to default";
// } else {
// prediction = "Unlikely to default";
// }
// return new PredictionResponse(prediction);
// }

// public static class PredictionResponse {
// private String prediction;
// public PredictionResponse(String prediction) { this.prediction = prediction;
// }
// public String getPrediction() { return prediction; }
// public void setPrediction(String prediction) { this.prediction = prediction;
// }
// }
// }
