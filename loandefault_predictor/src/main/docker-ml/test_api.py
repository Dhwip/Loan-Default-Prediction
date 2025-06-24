import requests
import json
import logging
from datetime import datetime

# Configure logging
logging.basicConfig(
    level=logging.INFO,
    format='%(asctime)s - %(levelname)s - %(message)s'
)
logger = logging.getLogger(__name__)

url = "http://localhost:9000/predict"

# Sample input
payload = {
    "Age": 35,
    "Income": 50000,
    "LoanAmount": 10000,
    "CreditScore": 720,
    "MonthsEmployed": 24,
    "NumCreditLines": 5,
    "InterestRate": 10,
    "LoanTerm": 24,
    "DTIRatio": 0.3,
    "Education": "Bachelor's",
    "EmploymentType": "Full-time",
    "MaritalStatus": "Married",
    "HasMortgage": "No",
    "HasDependents": "Yes",
    "LoanPurpose": "Business",
    "HasCoSigner": "Yes"
}

headers = {
    "Content-Type": "application/json"
}

def test_api():
    test_id = datetime.now().strftime("%Y%m%d_%H%M%S_%f")
    logger.info("[Test API] [%s] Starting API test", test_id)
    logger.info("[Test API] [%s] Target URL: %s", test_id, url)
    logger.info("[Test API] [%s] Request payload: %s", test_id, json.dumps(payload, indent=2))
    
    try:
        logger.info("[Test API] [%s] Sending POST request", test_id)
        response = requests.post(url, data=json.dumps(payload), headers=headers, timeout=30)
        
        logger.info("[Test API] [%s] Response received - Status Code: %s", test_id, response.status_code)
        logger.info("[Test API] [%s] Response headers: %s", test_id, dict(response.headers))
        
        if response.status_code == 200:
            response_data = response.json()
            logger.info("[Test API] [%s] Test successful - Response: %s", test_id, response_data)
            
            # Analyze prediction
            if "prediction" in response_data:
                prediction = response_data["prediction"]
                risk_level = "Low risk" if prediction == 0 else "High risk"
                logger.info("[Test API] [%s] Prediction: %s (%s)", test_id, prediction, risk_level)
            else:
                logger.warning("[Test API] [%s] No prediction field in response", test_id)
        else:
            logger.error("[Test API] [%s] Test failed - Status: %s", test_id, response.status_code)
            try:
                error_data = response.json()
                logger.error("[Test API] [%s] Error response: %s", test_id, error_data)
            except:
                logger.error("[Test API] [%s] Error response text: %s", test_id, response.text)
                
    except requests.exceptions.ConnectionError as e:
        logger.error("[Test API] [%s] Connection error: %s", test_id, str(e))
        logger.error("[Test API] [%s] Make sure the Python ML API is running on port 9000", test_id)
    except requests.exceptions.Timeout as e:
        logger.error("[Test API] [%s] Request timeout: %s", test_id, str(e))
    except Exception as e:
        logger.error("[Test API] [%s] Unexpected error: %s", test_id, str(e))
    
    logger.info("[Test API] [%s] Test completed", test_id)

if __name__ == "__main__":
    test_api()