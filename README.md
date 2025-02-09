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

## Run KAFKA, FAST API and Spring Boot Locally (for further development or testing) :

0. Got to .env files (backend and global) and comment out the "# RUNNING LOCALHOST" (Comment "# RUNNING DOCKER PRODUCTION")

1. KAFKA :
STARTING ZOOKEEPER
docker run -d --name zookeeper -p 2181:2181 wurstmeister/zookeeper
STARTING KAFKA
docker run -d --name kafka --link zookeeper:zookeeper -p 9092:9092 \
    -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092 \
    -e KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:9092 \
    -e KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181 \
    -e KAFKA_BROKER_ID=1 \
    -e KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR=1 \
    wurstmeister/kafka
(Check with : docker ps)    
CREATE KAFKA TOPIC
docker exec -it kafka kafka-topics.sh --create --topic transactions \
    --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1
(Check with : docker exec -it kafka kafka-topics.sh --list --bootstrap-server localhost:9092)    

2. FAST API :
cd ml_model
uvicorn api:app --host 0.0.0.0 --port 8000

3. SPRING BOOT :
cd backend
./mvnw spring-boot:run

4. Test The whole ecosystem works properly (Gitbash):
curl -X POST "http://localhost:8080/api/send-transaction"      -H "Content-Type: application/json"      -d '{"features": [1500.00, -2.3, 1.5, 0.2, 0.9, -1.2, 2.8, 0.3, 1.1, -0.8, 1.9, -0.7, 0.6, -1.5, 2.4, -0.2, 0.5, -2.1, 1.3, 0.8, -0.5, 1.7, 0.4, -1.0, 2.6, -0.3, 0.7, 1.4, -2.0]}'
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