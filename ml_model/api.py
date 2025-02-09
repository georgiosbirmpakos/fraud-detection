from fastapi import FastAPI, HTTPException, Depends
from pydantic import BaseModel
import pickle
import numpy as np
import os

# Define constants for model and scaler paths
MODEL_PATH = "ml_model.pkl"
SCALER_PATH = "scaler.pkl"

# Check if model and scaler exist
if not os.path.exists(MODEL_PATH) or not os.path.exists(SCALER_PATH):
    raise FileNotFoundError("Model or Scaler file not found! Please train the model first.")

# Load model and scaler
with open(MODEL_PATH, "rb") as f:
    model = pickle.load(f)
with open(SCALER_PATH, "rb") as f:
    scaler = pickle.load(f)

# Initialize FastAPI app
app = FastAPI()

# Define request model
class TransactionRequest(BaseModel):
    features: list[float]

@app.post("/predict/")
def predict(transaction: TransactionRequest):
    try:
        # Convert list to NumPy array and reshape
        features = np.array(transaction.features).reshape(1, -1)

        # Ensure correct feature size
        expected_features = model.n_features_in_
        if features.shape[1] != expected_features:
            raise ValueError(f"Expected {expected_features} features, but got {features.shape[1]}")

        # Predict fraud probability
        prediction = model.predict(features)
        return {"fraud": int(prediction[0])}
    
    except Exception as e:
        print(f"Server Error: {e}")  # Log error
        raise HTTPException(status_code=500, detail=str(e))  # Return error message
