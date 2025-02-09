# **Fraud Detection System**

## **📌 Overview**
This application is a **real-time fraud detection system** that utilizes machine learning to detect fraudulent transactions. It integrates multiple technologies to ensure **scalability, reliability, and real-time processing** of transaction data.

## **🔹 Key Features**
- **Machine Learning Model**: Uses a **Random Forest Classifier** to detect fraud.
- **Spring Boot Backend**: Manages API requests and transaction processing.
- **FastAPI ML Service**: Python-based service for real-time fraud predictions.
- **Kafka Streaming**: Enables real-time transaction data processing.
- **Dockerized Deployment**: Runs all services in containers for easy scalability.
- **Database Integration**: Stores transaction logs and fraud reports.

## **🔹 Technologies Used**
- **Java (Spring Boot)** – Backend service for transaction management.
- **Python (FastAPI, Scikit-learn)** – Machine learning model for fraud detection.
- **Kafka** – Message queue for real-time streaming.
- **Docker** – Containerized deployment for all services.
- **MySQL/PostgreSQL** – Database for storing transaction history.

## **🔹 How It Works**
1. **User initiates a transaction**.
2. **Kafka Producer** sends transaction data to the Kafka topic.
3. **Kafka Consumer (Java)** reads the transaction data.
4. **Java Backend calls the Python API**, which predicts whether the transaction is fraudulent.
5. **Fraud status is returned** and stored in the database.

## **🔹 Running the Application**
To run the entire system using Docker, execute:
```sh
docker-compose up --build
```

To test a transaction manually:
```sh
curl -X POST "http://localhost:8080/api/check-fraud" \
     -H "Content-Type: application/json" \
     -d '{"features": [1500.00, -2.3, 1.5, 0.2, 0.9, -1.2, 2.8, 0.3, 1.1, -0.8, 1.9, -0.7, 0.6, -1.5, 2.4, -0.2, 0.5, -2.1, 1.3, 0.8, -0.5, 1.7, 0.4, -1.0, 2.6, -0.3, 0.7, 1.4, -2.0]}'
```

## **🔹 Contribution**
Feel free to submit **issues** or **pull requests** to improve the system!

## **🚀 Next Steps**
- **Deploy on AWS (EC2, Lambda, RDS)**
- **Integrate a Frontend Dashboard**
- **Optimize Model Performance**

