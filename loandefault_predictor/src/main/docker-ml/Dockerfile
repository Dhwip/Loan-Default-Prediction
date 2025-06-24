FROM python:3.9-slim

# Set workdir
WORKDIR /app

# Copy files
COPY api_server.py .
COPY loan_model.pkl .
COPY label_encoders.pkl .
COPY requirements.txt .

# Install dependencies
RUN pip install --no-cache-dir -r requirements.txt

# Expose Flask port
EXPOSE 9000

# Start Flask app
CMD ["python", "api_server.py"]
