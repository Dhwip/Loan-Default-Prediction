from flask import Flask, request, jsonify
import joblib
import pandas as pd
import logging
import traceback
from datetime import datetime

# Configure logging
logging.basicConfig(
    level=logging.INFO,
    format='%(asctime)s - %(levelname)s - %(message)s',
    handlers=[
        logging.StreamHandler(),
        logging.FileHandler('ml_api.log')
    ]
)
logger = logging.getLogger(__name__)

app = Flask(__name__)

# Load model and label encoders
logger.info("🚀 [Python API] Starting ML API server")
try:
    logger.info("📦 [Python API] Loading ML model and label encoders")
    model = joblib.load("loan_model.pkl")
    label_encoders = joblib.load("label_encoders.pkl")
    logger.info("✅ [Python API] Model and encoders loaded successfully")
    logger.info("📊 [Python API] Available label encoders: %s", list(label_encoders.keys()))
except Exception as e:
    logger.error("❌ [Python API] Failed to load model or encoders: %s", str(e))
    raise

@app.route('/predict', methods=['POST'])
def predict():
    request_id = datetime.now().strftime("%Y%m%d_%H%M%S_%f")
    logger.info("🚀 [Python API] [%s] Received prediction request", request_id)
    
    try:
        data = request.get_json()
        logger.info("📊 [Python API] [%s] Request data: %s", request_id, data)
        
        if not data:
            logger.error("❌ [Python API] [%s] No JSON data received", request_id)
            return jsonify({"error": "No JSON data received"}), 400
        
        df = pd.DataFrame([data])
        logger.info("📋 [Python API] [%s] Created DataFrame with shape: %s", request_id, df.shape)
        logger.info("📋 [Python API] [%s] DataFrame columns: %s", request_id, list(df.columns))

        # Encode categorical fields
        logger.info("🔄 [Python API] [%s] Processing categorical fields", request_id)
        categorical_columns = df.select_dtypes(include=['object']).columns
        logger.info("📋 [Python API] [%s] Categorical columns found: %s", request_id, list(categorical_columns))
        
        for col in categorical_columns:
            if col in label_encoders:
                le = label_encoders[col]
                try:
                    original_value = df[col].values[0]
                    df[col] = le.transform(df[col])
                    encoded_value = df[col].values[0]
                    logger.info("✅ [Python API] [%s] Encoded %s: '%s' -> %s", request_id, col, original_value, encoded_value)
                except ValueError as e:
                    logger.error("❌ [Python API] [%s] Invalid value '%s' for column '%s': %s", 
                               request_id, df[col].values[0], col, str(e))
                    return jsonify({"error": f"Invalid value '{df[col].values[0]}' for column '{col}'"}), 400
            else:
                logger.error("❌ [Python API] [%s] Unexpected column '%s' not found in label encoders", request_id, col)
                return jsonify({"error": f"Unexpected column '{col}'"}), 400

        logger.info("🔮 [Python API] [%s] Making prediction with model", request_id)
        prediction = model.predict(df)[0]
        logger.info("✅ [Python API] [%s] Prediction result: %s", request_id, prediction)
        
        response = {"prediction": int(prediction)}
        logger.info("📤 [Python API] [%s] Sending response: %s", request_id, response)
        return jsonify(response)

    except Exception as e:
        logger.error("❌ [Python API] [%s] Exception occurred: %s", request_id, str(e))
        logger.error("❌ [Python API] [%s] Traceback: %s", request_id, traceback.format_exc())
        return jsonify({"error": str(e)}), 500

@app.route('/health', methods=['GET'])
def health_check():
    logger.info("🏥 [Python API] Health check requested")
    return jsonify({"status": "healthy", "model_loaded": model is not None})

if __name__ == "__main__":
    logger.info("🚀 [Python API] Starting Flask app on host=0.0.0.0, port=9000")
    app.run(host="0.0.0.0", port=9000)