package com.loan.loandefault_predictor.controller;

import com.loan.loandefault_predictor.model.LoanApplication;
import com.loan.loandefault_predictor.service.LoanPredictionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class LoanFormController {

    private static final Logger logger = LoggerFactory.getLogger(LoanFormController.class);

    @Autowired
    private LoanPredictionService predictionService;

    @PostMapping("/loan/predict")
    public String predictDefault(@RequestBody LoanApplication application) {
        logger.info("[Java Controller] Received loan prediction request");
        logger.info("[Java Controller] Request data: {}", application);

        try {
            // Basic validation
            if (application == null) {
                logger.error("[Java Controller] Application data is null");
                return "Error: Application data is required";
            }

            logger.info("[Java Controller] Validating required fields");
            if (application.getAge() == null || application.getIncome() == null ||
                    application.getLoanAmount() == null || application.getCreditScore() == null ||
                    application.getDtiRatio() == null) {
                logger.error(
                        "[Java Controller] Missing required fields - Age: {}, Income: {}, LoanAmount: {}, CreditScore: {}, DTIRatio: {}",
                        application.getAge(), application.getIncome(), application.getLoanAmount(),
                        application.getCreditScore(), application.getDtiRatio());
                return "Error: All required fields must be provided";
            }

            logger.info("[Java Controller] Validation passed, calling prediction service");
            // Get prediction from the service
            String prediction = predictionService.getPrediction(application);
            logger.info("[Java Controller] Prediction service returned: {}", prediction);

            // Return prediction string directly
            return prediction;
        } catch (Exception e) {
            logger.error("[Java Controller] Exception occurred during prediction: {}", e.getMessage(), e);
            // Return error response
            return "Error: " + e.getMessage();
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
