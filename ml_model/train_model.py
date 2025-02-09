# %%
import pandas as pd
import pickle
from sklearn.ensemble import RandomForestClassifier
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import StandardScaler

# Load dataset
df = pd.read_csv("../dataset/creditcard.csv")
df.head(10)

# %%
df.info()

# %%

# Features & Labels
X = df.drop(columns=["Time", "Class"])  # Remove time & target column
y = df["Class"]  # 1 = Fraud, 0 = Legitimate

# %%
y.value_counts()

# %%

# Train-test split
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

# Normalize amount feature
scaler = StandardScaler()
X["Amount"] = scaler.fit_transform(X[["Amount"]])

# %%

from sklearn.metrics import classification_report

# Train a fraud detection model
model = RandomForestClassifier(class_weight="balanced", random_state=42)
model.fit(X_train, y_train)

print(
    'Random Forest score for training stratified dataset without any Suspicious Variable set: %f'
    % model.score(X_test, y_test))

# %%
y_true, y_pred = y_test, model.predict(X_test)
print(classification_report(y_true, y_pred))

# %%
pickle.dump(model, open('ml_model.pkl', 'wb'))
pickle.dump(model, open('scaler.pkl', 'wb'))

print("Model trained & saved!")


