import React, { useState } from 'react';
import { submitLoanForm } from '../api';
import './LoanForm.css';

const dropdownFields = {
  education: ['High School', "Bachelor's", "Master's", 'PhD'],
  employmentType: ['Part-time', 'Self-Employed', 'Unemployed', 'Full-time'],
  maritalStatus: ['Single', 'Married', 'Divorced'],
  hasMortgage: ['Yes', 'No'],
  hasDependents: ['Yes', 'No'],
  loanPurpose: ['Home', 'Business', 'Education', 'Auto', 'Other'],
  hasCoSigner: ['Yes', 'No'],
};

const fieldGroups = [
  {
    title: 'Personal Information',
    fields: ['age', 'education', 'employmentType', 'maritalStatus', 'hasDependents'],
  },
  {
    title: 'Financial Information',
    fields: ['income', 'creditScore', 'monthsEmployed', 'numCreditLines', 'hasMortgage', 'dtiRatio'],
  },
  {
    title: 'Loan Details',
    fields: ['loanAmount', 'interestRate', 'loanTerm', 'loanPurpose', 'hasCoSigner'],
  },
];

const fieldLabels = {
  age: 'Age',
  income: 'Annual Income ($)',
  loanAmount: 'Loan Amount ($)',
  creditScore: 'Credit Score',
  monthsEmployed: 'Months Employed',
  numCreditLines: 'Number of Credit Lines',
  interestRate: 'Interest Rate (%)',
  loanTerm: 'Loan Term (months)',
  dtiRatio: 'Debt-to-Income Ratio',
  education: 'Education Level',
  employmentType: 'Employment Type',
  maritalStatus: 'Marital Status',
  hasMortgage: 'Has Mortgage',
  hasDependents: 'Has Dependents',
  loanPurpose: 'Loan Purpose',
  hasCoSigner: 'Has Co-Signer',
};

const fieldHelp = {
  creditScore: 'Range: 300-850',
  dtiRatio: 'Range: 0.01-1.0 (e.g., 0.3 = 30%)',
  interestRate: 'Annual percentage rate',
  loanTerm: 'Duration in months (e.g., 24, 36, 60)',
};

const LoanForm = () => {
  const [formData, setFormData] = useState({
    age: '',
    income: '',
    loanAmount: '',
    creditScore: '',
    monthsEmployed: '',
    numCreditLines: '',
    interestRate: '',
    loanTerm: '',
    dtiRatio: '',
    education: '',
    employmentType: '',
    maritalStatus: '',
    hasMortgage: '',
    hasDependents: '',
    loanPurpose: '',
    hasCoSigner: '',
  });

  const [prediction, setPrediction] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  // Calculate form completion percentage
  const getFormProgress = () => {
    const totalFields = Object.keys(formData).length;
    const filledFields = Object.values(formData).filter(value => value !== '').length;
    return Math.round((filledFields / totalFields) * 100);
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value,
    }));
    setError(null); // Clear error when user makes changes
  };

  const validateForm = () => {
    console.log('[Frontend Form] Starting form validation');
    console.log('[Frontend Form] Current form data:', formData);
    
    const requiredFields = ['age', 'income', 'loanAmount', 'creditScore', 'monthsEmployed', 
                          'numCreditLines', 'interestRate', 'loanTerm', 'dtiRatio', 'education', 
                          'employmentType', 'maritalStatus', 'hasMortgage', 'hasDependents', 
                          'loanPurpose', 'hasCoSigner'];
    
    for (const field of requiredFields) {
      if (!formData[field]) {
        console.error(`[Frontend Form] Validation failed: Missing required field '${field}'`);
        setError(`Please fill in all required fields`);
        return false;
      }
    }

    // Validate numeric fields
    const numericFields = ['age', 'income', 'loanAmount', 'creditScore', 'monthsEmployed', 
                          'numCreditLines', 'interestRate', 'loanTerm', 'dtiRatio'];
    
    for (const field of numericFields) {
      const value = parseFloat(formData[field]);
      if (isNaN(value) || value <= 0) {
        console.error(`[Frontend Form] Validation failed: Invalid numeric value for '${field}' = ${formData[field]}`);
        setError(`Please enter a valid positive number for ${field}`);
        return false;
      }
      
      // Special validation for DTIRatio (should be between 0 and 1)
      if (field === 'dtiRatio' && (value < 0.01 || value > 1)) {
        console.error(`[Frontend Form] Validation failed: DTI Ratio out of range = ${value}`);
        setError('DTI Ratio should be between 0.01 and 1.0');
        return false;
      }
    }

    console.log('[Frontend Form] Form validation passed');
    return true;
  };

  const onSubmit = async (e) => {
    e.preventDefault();
    console.log('[Frontend Form] Form submission started');
    setError(null);
    setPrediction(null);
    
    if (!validateForm()) {
      console.log('[Frontend Form] Form validation failed, submission aborted');
      return;
    }

    console.log('[Frontend Form] Calling API with validated data');
    setLoading(true);
    try {
      const response = await submitLoanForm(formData);
      console.log('[Frontend Form] API call successful, setting prediction');
      setPrediction(response.data);
    } catch (error) {
      console.error('[Frontend Form] API call failed:', error.message);
      setError(error.message || 'Error: Could not get prediction.');
      console.error(error);
    } finally {
      console.log('[Frontend Form] Form submission completed');
      setLoading(false);
    }
  };

  const getPlaceholder = (key) => {
    const label = key.replace(/([A-Z])/g, ' $1').replace(/^./, str => str.toUpperCase()).trim();
    return `Enter ${label}`;
  };

  const getDropdownPlaceholder = (key) => {
    const label = key.replace(/([A-Z])/g, ' $1').replace(/^./, str => str.toUpperCase()).trim();
    return `Select ${label}`;
  };

  return (
    <form onSubmit={onSubmit} className="loan-form formal-form">
      <h2 className="form-title">Loan Default Prediction</h2>
      <p className="form-description">Please fill out the form below to check your loan default risk. All fields are required.</p>
      
      {/* Progress Bar */}
      <div className="progress-container">
        <div className="progress-bar">
          <div 
            className="progress-fill" 
            style={{ width: `${getFormProgress()}%` }}
          ></div>
        </div>
        <span className="progress-text">{getFormProgress()}% Complete</span>
      </div>
      
      {error && (
        <div className="error-message">
          {error}
        </div>
      )}
      <div className="form-sections">
        {fieldGroups.map((group) => (
          <fieldset className="form-section" key={group.title}>
            <legend className="section-title">{group.title}</legend>
            <div className="form-fields">
              {group.fields.map((key) => (
                <div className="form-group" key={key}>
                  <label className="form-label" htmlFor={key}>
                    {fieldLabels[key]}
                  </label>
                  {fieldHelp[key] && (
                    <span className="form-help">{fieldHelp[key]}</span>
                  )}
                  {dropdownFields[key] ? (
                    <select
                      id={key}
                      name={key}
                      value={formData[key]}
                      onChange={handleChange}
                      className="form-select"
                      required
                    >
                      <option value="">{getDropdownPlaceholder(key)}</option>
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
                      placeholder={getPlaceholder(key)}
                      step={key === 'dtiRatio' ? '0.01' : '1'}
                      min={key === 'dtiRatio' ? '0.01' : key === 'age' ? '18' : key === 'creditScore' ? '300' : '1'}
                      max={key === 'dtiRatio' ? '1' : key === 'creditScore' ? '850' : key === 'age' ? '100' : ''}
                    />
                  )}
                </div>
              ))}
            </div>
          </fieldset>
        ))}
        <div className="form-group full-width">
          <button type="submit" className="submit-btn" disabled={loading}>
            {loading ? 'Checking...' : 'Check My Loan Default Risk'}
          </button>
        </div>
      </div>
      {prediction && (
        <div className={`prediction-result formal-prediction ${prediction.includes('High risk') ? 'high-risk' : 'low-risk'}`}>
          <h3>Prediction Result:</h3>
          <p>{prediction}</p>
        </div>
      )}
    </form>
  );
};

export default LoanForm;