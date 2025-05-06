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

const fieldGroups = [
  {
    title: 'Personal Information',
    fields: ['Age', 'Education', 'EmploymentType', 'MaritalStatus', 'HasDependents'],
  },
  {
    title: 'Financial Information',
    fields: ['Income', 'CreditScore', 'MonthsEmployed', 'NumCreditLines', 'HasMortgage'],
  },
  {
    title: 'Loan Details',
    fields: ['LoanAmount', 'InterestRate', 'LoanTerm', 'DTIRatio', 'LoanPurpose', 'HasCoSigner'],
  },
];

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
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value,
    }));
  };

  const onSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setPrediction(null);
    try {
      const response = await submitLoanForm(formData);
      setPrediction(response.data.prediction);
    } catch (error) {
      setPrediction('Error: Could not get prediction.');
      console.error(error);
    }
    setLoading(false);
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
      <div className="form-sections">
        {fieldGroups.map((group) => (
          <fieldset className="form-section" key={group.title}>
            <legend className="section-title">{group.title}</legend>
            <div className="form-fields">
              {group.fields.map((key) => (
                <div className="form-group" key={key}>
                  <label className="form-label" htmlFor={key}>
                    {key.replace(/([A-Z])/g, ' $1').replace(/^./, str => str.toUpperCase()).trim()}
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
        <div className="prediction-result formal-prediction">
          <h3>Prediction Result:</h3>
          <p>{prediction}</p>
        </div>
      )}
    </form>
  );
};

export default LoanForm;