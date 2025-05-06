from flask import Flask, request, jsonify
import joblib
import pandas as pd


app = Flask(__name__)

# Load model and label encoders
model = joblib.load("loan_model.pkl")
label_encoders = joblib.load("label_encoders.pkl")

@app.route('/predict', methods=['POST'])
def predict():
    try:
        data = request.get_json()
        df = pd.DataFrame([data])

        # Encode categorical fields
        for col in df.select_dtypes(include=['object']).columns:
            if col in label_encoders:
                le = label_encoders[col]
                try:
                    df[col] = le.transform(df[col])
                except ValueError as e:
                    return jsonify({"error": f"Invalid value '{df[col].values[0]}' for column '{col}'"}), 400
            else:
                return jsonify({"error": f"Unexpected column '{col}'"}), 400

        prediction = model.predict(df)[0]
        return jsonify({"prediction": int(prediction)})

    except Exception as e:
        return jsonify({"error": str(e)}), 500

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000)