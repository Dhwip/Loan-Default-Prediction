package com.loan.loandefault_predictor.service;

import com.loan.loandefault_predictor.model.LoanApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.LinkedHashMap;

@Service
public class LoanPredictionService {

    private static final Logger logger = LoggerFactory.getLogger(LoanPredictionService.class);
    private final String MODEL_API_URL = "http://localhost:9000/predict"; // Updated to correct port

    public String getPrediction(LoanApplication application) {
        logger.info("[Java Service] Starting loan prediction process");
        logger.info("[Java Service] Input application: {}", application);

        // Create RestTemplate with timeout configuration
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(5000); // 5 seconds connection timeout
        factory.setReadTimeout(10000); // 10 seconds read timeout
        RestTemplate restTemplate = new RestTemplate(factory);

        // Set headers for JSON content
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Transform camelCase field names to PascalCase for Python API
        logger.info("[Java Service] Transforming field names from camelCase to PascalCase");
        Map<String, Object> transformedData = new LinkedHashMap<>(); // Use LinkedHashMap to maintain order
        transformedData.put("Age", application.getAge());
        transformedData.put("Income", application.getIncome());
        transformedData.put("LoanAmount", application.getLoanAmount());
        transformedData.put("CreditScore", application.getCreditScore());
        transformedData.put("MonthsEmployed", application.getMonthsEmployed());
        transformedData.put("NumCreditLines", application.getNumCreditLines());
        transformedData.put("InterestRate", application.getInterestRate());
        transformedData.put("LoanTerm", application.getLoanTerm());
        transformedData.put("DTIRatio", application.getDtiRatio());
        transformedData.put("Education", application.getEducation());
        transformedData.put("EmploymentType", application.getEmploymentType());
        transformedData.put("MaritalStatus", application.getMaritalStatus());
        transformedData.put("HasMortgage", application.getHasMortgage());
        transformedData.put("HasDependents", application.getHasDependents());
        transformedData.put("LoanPurpose", application.getLoanPurpose());
        transformedData.put("HasCoSigner", application.getHasCoSigner());

        logger.info("[Java Service] Transformed data for Python API: {}", transformedData);

        // Create the request with the transformed data and headers
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(transformedData, headers);

        try {
            logger.info("[Java Service] Making POST request to Python ML API: {}", MODEL_API_URL);
            // Make a POST request to the model API
            ResponseEntity<Map> response = restTemplate.exchange(MODEL_API_URL, HttpMethod.POST, request, Map.class);

            logger.info("[Java Service] Received response from Python API - Status: {}", response.getStatusCode());

            // Check if the response is successful and contains the prediction
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Map<String, Object> responseBody = response.getBody();
                logger.info("[Java Service] Response body: {}", responseBody);

                if (responseBody.containsKey("prediction")) {
                    Integer prediction = (Integer) responseBody.get("prediction");
                    String result = prediction == 0 ? "Low risk of default" : "High risk of default";
                    logger.info("[Java Service] Prediction processed successfully: {} (raw: {})", result, prediction);
                    return result;
                } else {
                    logger.error("[Java Service] Prediction key not found in response body");
                    throw new RuntimeException("Prediction key not found in the response.");
                }
            } else {
                logger.error("[Java Service] Model API failed or returned invalid response - Status: {}",
                        response.getStatusCode());
                throw new RuntimeException("Model API failed or returned an invalid response.");
            }
        } catch (org.springframework.web.client.ResourceAccessException e) {
            logger.error("[Java Service] Cannot connect to ML API: {}", e.getMessage());
            throw new RuntimeException(
                    "Cannot connect to ML API. Please ensure the Python ML service is running on port 9000.");
        } catch (Exception e) {
            logger.error("[Java Service] Error calling ML API: {}", e.getMessage(), e);
            throw new RuntimeException("Error calling ML API: " + e.getMessage());
        }
    }
}
