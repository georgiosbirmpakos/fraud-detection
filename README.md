# 🚀 Fraud Detection System

## 📌 Overview
This is a **Fraud Detection System** built using **Spring Boot**, **Kafka**, **MySQL**, and **FastAPI** (for ML model inference). The system detects fraudulent transactions in real-time using machine learning and Kafka for message streaming.

## 🛠 Tech Stack
- **Backend:** Spring Boot (Java 17)
- **Database:** MySQL 8
- **Messaging:** Kafka with Zookeeper
- **Machine Learning:** FastAPI (Python)
- **Containerization:** Docker & Docker Compose

## 📁 Project Structure
```
fraud-detection/
│-- backend/             # Spring Boot Application
│-- ml_model/            # FastAPI Machine Learning Model
│-- .gitignore           # Ignoring unnecessary files
│-- docker-compose.yml   # Docker configuration
│-- README.md            # Project documentation
```

## 🚀 Getting Started
### 1️⃣ Clone the Repository
```sh
git clone https://github.com/georgiosbirmpakos/fraud-detection.git
cd fraud-detection
```

### 2️⃣ Set Up Environment Variables
Create a **.env** file in the root directory and define your database credentials:
```env
DB_USERNAME=root
DB_PASSWORD=yourpassword
MYSQL_ROOT_PASSWORD=yourpassword
DB_URL=jdbc:mysql://mysql-db:3306/fraud_detection
MYSQL_DATABASE=fraud_detection
KAFKA_BOOTSTRAP_SERVERS=kafka:9092
```

### 3️⃣ Run the Project Using Docker
```sh
docker-compose up --build -d
```
This will start **Kafka**, **Zookeeper**, **MySQL**, **Spring Boot**, and **FastAPI** services.

### 4️⃣ Check Running Containers
```sh
docker ps
```
Ensure all services are running properly.

### 5️⃣ Access Services
- **Spring Boot API:** http://localhost:8080
- **FastAPI ML Model:** http://localhost:8000/docs
- **Kafka UI (if configured):** http://localhost:9092

## 📝 API Endpoints
| Method | Endpoint                 | Description                          |
|--------|--------------------------|--------------------------------------|
| `POST` | `/transactions`          | Submit a transaction for validation |
| `GET`  | `/transactions/{id}`     | Get transaction status              |
| `POST` | `/predict/` (FastAPI)    | Predict fraud status using ML model |

## 🔄 Kafka Integration
- **Topic:** `transactions`
- **Consumer Group:** `fraud-detection-group`
- **Message Format:** JSON containing transaction details

## 🐞 Troubleshooting
### 🔥 Kafka Issues
If Spring Boot logs show `localhost:9092` errors, check:
```sh
docker logs kafka --tail=50
```
Ensure Kafka is properly configured in `docker-compose.yml`:
```yaml
KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://kafka:9092
```

### 🔥 MySQL Issues
If MySQL fails to start, verify the health status:
```sh
docker ps | grep mysql-db
```

### 🔥 Spring Boot Issues
Check Spring Boot logs:
```sh
docker logs spring-boot --tail=50
```