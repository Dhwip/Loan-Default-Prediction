# Loan Default Prediction

A machine learning system that predicts loan default risk using a React frontend, Spring Boot backend, and Python ML API.

## Start Services

```bash
# ML Service
cd loandefault_predictor/src/main/docker-ml && docker-compose up --build

# Backend
cd loandefault_predictor && mvn spring-boot:run

# Frontend
cd frontend && npm install && npm start
```

## URLs

- Frontend: http://localhost:3000
- Backend: http://localhost:8080
- ML API: http://localhost:9000
