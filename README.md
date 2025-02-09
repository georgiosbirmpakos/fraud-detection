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

## 🔧 Running Locally (for Development & Testing)

### 0️⃣ Update `.env` Files
Modify `.env` files in both **backend** and **global** to:
- Comment out **Docker Production** settings.
- Uncomment **Localhost Settings**.

### 1️⃣ Start Kafka & Zookeeper
Run the following commands:
```sh
# Start Zookeeper
docker run -d --name zookeeper -p 2181:2181 wurstmeister/zookeeper

# Start Kafka
docker run -d --name kafka --link zookeeper:zookeeper -p 9092:9092 \
    -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092 \
    -e KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:9092 \
    -e KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181 \
    -e KAFKA_BROKER_ID=1 \
    -e KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR=1 \
    wurstmeister/kafka
```
Check running containers:
```sh
docker ps
```
Create Kafka topic:
```sh
docker exec -it kafka kafka-topics.sh --create --topic transactions \
    --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1
```
Check available topics:
```sh
docker exec -it kafka kafka-topics.sh --list --bootstrap-server localhost:9092
```
To stop Kafka and Zookeeper:
```sh
docker stop kafka zookeeper
```

### 2️⃣ Start FastAPI (Machine Learning Model)
```sh
cd ml_model
uvicorn api:app --host 0.0.0.0 --port 8000
```

### 3️⃣ Start Spring Boot Backend
```sh
cd backend
./mvnw spring-boot:run
mvn spring-boot:run
```

### 4️⃣ Test Transactions
#### 🔹 Send a Single Transaction
```sh
curl -X POST "http://localhost:8080/api/send-transaction" -H "Content-Type: application/json" -d '{"features": [1500.00, -2.3, 1.5, 0.2, 0.9, -1.2, 2.8, 0.3, 1.1, -0.8, 1.9, -0.7, 0.6, -1.5, 2.4, -0.2, 0.5, -2.1, 1.3, 0.8, -0.5, 1.7, 0.4, -1.0, 2.6, -0.3, 0.7, 1.4, -2.0]}'
```

#### 🔹 Start Automatic Transaction Generation
```sh
curl -X POST http://localhost:8080/api/start-producing
```
#### 🔹 Stop Automatic Transaction Generation
```sh
curl -X POST http://localhost:8080/api/stop-producing
```

## 🚀 Running with Docker
```sh
docker-compose up --build -d
```
This will start **Kafka**, **Zookeeper**, **MySQL**, **Spring Boot**, and **FastAPI** services.

Check running containers:
```sh
docker ps
```

## 📝 API Endpoints
| Method | Endpoint                 | Description                          |
|--------|--------------------------|--------------------------------------|
| `POST` | `/api/send-transaction`  | Submit a transaction for validation |
| `POST` | `/api/start-producing`   | Start automatic transaction sending |
| `POST` | `/api/stop-producing`    | Stop automatic transaction sending  |
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

