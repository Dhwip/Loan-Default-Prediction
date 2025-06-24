import axios from 'axios';

const API = axios.create({
  baseURL: 'http://localhost:8080/api',
  headers: {
    'Content-Type': 'application/json',
  },
  timeout: 10000, // 10 seconds timeout
});

export const submitLoanForm = async (formData) => {
  console.log('[Frontend API] Starting loan prediction request');
  console.log('[Frontend API] Request data:', JSON.stringify(formData, null, 2));
  
  try {
    console.log('[Frontend API] Sending POST request to /loan/predict');
    const response = await API.post('/loan/predict', formData);
    
    console.log('[Frontend API] Request successful');
    console.log('[Frontend API] Response status:', response.status);
    console.log('[Frontend API] Response data:', response.data);
    
    return response;
  } catch (error) {
    console.error('[Frontend API] Request failed');
    
    if (error.response) {
      // The request was made and the server responded with a status code
      // that falls out of the range of 2xx
      console.error('[Frontend API] Server response error:', error.response.status);
      console.error('[Frontend API] Error data:', error.response.data);
      throw new Error(error.response.data.error || 'Server error occurred');
    } else if (error.request) {
      // The request was made but no response was received
      console.error('[Frontend API] No response received from server');
      console.error('[Frontend API] Request details:', error.request);
      throw new Error('No response from server. Please check if the server is running.');
    } else {
      // Something happened in setting up the request that triggered an Error
      console.error('[Frontend API] Request setup error:', error.message);
      throw new Error('Error setting up the request');
    }
  }
};
