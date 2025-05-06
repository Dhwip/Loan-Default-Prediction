import pandas as pd
from sklearn.preprocessing import LabelEncoder
from sklearn.model_selection import train_test_split
from sklearn.ensemble import GradientBoostingClassifier
from sklearn.metrics import accuracy_score, classification_report, confusion_matrix
import joblib

# Load dataset
df = pd.read_csv('Loan_default.csv')

# Drop missing values
df = df.dropna()

# Encode categorical columns and store encoders
label_encoders = {}
for column in df.select_dtypes(include=['object']).columns:
    le = LabelEncoder()
    df[column] = le.fit_transform(df[column])
    label_encoders[column] = le

# Separate features and target
X = df.drop('Default', axis=1)
y = df['Default']

# Drop 'LoanID' if present
if 'LoanID' in X.columns:
    X.drop('LoanID', axis=1, inplace=True)

# Train-test split
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

# Train Gradient Boosting Classifier
gbc = GradientBoostingClassifier(
    n_estimators=200,
    learning_rate=0.1,
    max_depth=3,
    random_state=42
)
gbc.fit(X_train, y_train)

# Evaluate model
y_pred = gbc.predict(X_test)
print("Accuracy:", accuracy_score(y_test, y_pred))
print(confusion_matrix(y_test, y_pred))
print(classification_report(y_test, y_pred))

# Save model and encoders
joblib.dump(gbc, 'loan_model.pkl')
joblib.dump(label_encoders, 'label_encoders.pkl')
