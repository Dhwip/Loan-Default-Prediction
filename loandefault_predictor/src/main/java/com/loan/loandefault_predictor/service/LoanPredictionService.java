package com.loan.loandefault_predictor.service;

import com.loan.loandefault_predictor.model.LoanApplication;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class LoanPredictionService {

    private final String MODEL_API_URL = "http://localhost:5000/predict"; // Docker container's port

    public String getPrediction(LoanApplication application) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<LoanApplication> request = new HttpEntity<>(application, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(MODEL_API_URL, request, Map.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return response.getBody().get("prediction").toString();
        } else {
            throw new RuntimeException("Model API failed or returned invalid response.");
        }
    }
}
