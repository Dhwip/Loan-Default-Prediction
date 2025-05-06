import React, { useState } from 'react';
import { submitLoanForm } from '../api';
import './LoanForm.css';

const dropdownFields = {
  Education: ['High School', 'Bachelor', 'Master', 'PhD', 'Other'],
  EmploymentType: ['Salaried', 'Self-Employed', 'Unemployed', 'Retired'],
  MaritalStatus: ['Single', 'Married', 'Divorced', 'Widowed'],
  HasMortgage: ['Yes', 'No'],
  HasDependents: ['Yes', 'No'],
  LoanPurpose: ['Home', 'Car', 'Education', 'Personal', 'Medical', 'Other'],
  HasCoSigner: ['Yes', 'No'],
};

const LoanForm = () => {
  const [formData, setFormData] = useState({
    Age: '',
    Income: '',
    LoanAmount: '',
    CreditScore: '',
    MonthsEmployed: '',
    NumCreditLines: '',
    InterestRate: '',
    LoanTerm: '',
    DTIRatio: '',
    Education: '',
    EmploymentType: '',
    MaritalStatus: '',
    HasMortgage: '',
    HasDependents: '',
    LoanPurpose: '',
    HasCoSigner: '',
  });

  const [prediction, setPrediction] = useState(null);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value,
    }));
  };

  const onSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await submitLoanForm(formData);
      setPrediction(response.data.prediction);
    } catch (error) {
      setPrediction('Error: Could not get prediction.');
      console.error(error);
    }
  };

  return (
    <form onSubmit={onSubmit} className="loan-form">
      <h2 className="form-title">Loan Default Prediction</h2>
      <div className="form-fields">
        {Object.keys(formData).map((key) => (
          <div className="form-group" key={key}>
            <label className="form-label" htmlFor={key}>
              {key.replace(/([A-Z])/g, ' $1').trim()}
            </label>
            {dropdownFields[key] ? (
              <select
                id={key}
                name={key}
                value={formData[key]}
                onChange={handleChange}
                className="form-select"
                required
              >
                <option value="">Select {key.replace(/([A-Z])/g, ' ')}</option>
                {dropdownFields[key].map((option) => (
                  <option key={option} value={option}>{option}</option>
                ))}
              </select>
            ) : (
              <input
                type="number"
                id={key}
                name={key}
                value={formData[key]}
                onChange={handleChange}
                className="form-input"
                required
                placeholder={`Enter ${key.replace(/([A-Z])/g, ' ')}`}
              />
            )}
          </div>
        ))}
        <div className="form-group full-width">
          <button type="submit" className="submit-btn">Predict Eligibility</button>
        </div>
      </div>
      {prediction && (
        <div className="prediction-result">
          <h3>Prediction Result:</h3>
          <p>{prediction}</p>
        </div>
      )}
    </form>
  );
};

export default LoanForm;