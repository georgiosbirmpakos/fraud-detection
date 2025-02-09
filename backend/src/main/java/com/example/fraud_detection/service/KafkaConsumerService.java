package com.example.fraud_detection.service;

import com.example.fraud_detection.model.Transaction;
import com.google.gson.Gson;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.springframework.http.HttpHeaders;

@Service
public class KafkaConsumerService {
    private static final String TOPIC = "transactions";
    // private static final String FASTAPI_URL = "http://localhost:8000/predict/";
    private static final String FASTAPI_URL = "http://fastapi-ml:8000/predict/";
    private final Gson gson = new Gson();

    public void consumeTransactions() {
        Properties props = new Properties();
        // props.put("bootstrap.servers", "localhost:9092");
        props.put("bootstrap.servers", "kafka:9092");
        props.put("group.id", "fraud-detection-group");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(TOPIC));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                System.out.println("Received Transaction: " + record.value());

                // Convert JSON string to Transaction object
                Transaction transaction = gson.fromJson(record.value(), Transaction.class);

                // Send transaction to FastAPI ML model
                Boolean isFraud = callFastAPIFraudDetection(transaction);

                // Log fraud status
                System.out.println("Fraud Status: " + (isFraud ? "FRAUD" : "SAFE"));
            }
        }
    }

    private Boolean callFastAPIFraudDetection(Transaction transaction) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            // Convert Transaction object to a Map for FastAPI compatibility
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("features", transaction.getFeatures());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

            ResponseEntity<Map> response = restTemplate.exchange(FASTAPI_URL, HttpMethod.POST, request, Map.class);
            return (Integer) response.getBody().get("fraud") == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Default to SAFE if API call fails
        }
    }
}

