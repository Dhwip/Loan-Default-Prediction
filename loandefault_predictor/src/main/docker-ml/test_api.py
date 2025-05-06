import requests
import json


# URL of the running containerized API
url = "http://localhost:5000/predict"

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

response = requests.post(url, data=json.dumps(payload), headers=headers)

print("Status Code:", response.status_code)
print("Response JSON:", response.json())