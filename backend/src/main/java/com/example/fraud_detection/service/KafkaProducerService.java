package com.example.fraud_detection.service;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

@Service
public class KafkaProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "transactions";
     private final Gson gson = new Gson();
    private final Random random = new Random();
    private final AtomicBoolean producing = new AtomicBoolean(false);

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendTransaction(String transactionData) {
        kafkaTemplate.send(TOPIC, transactionData);
    }

    // Generate and send a transaction every second, only if producing is enabled
    @Scheduled(fixedRate = 1000)
    public void sendRandomTransaction() {
        if (!producing.get()) return; // Skip execution if producing is disabled
        double[] features = new double[29];
        for (int i = 0; i < features.length; i++) {
            features[i] = -2.5 + (5.0 * random.nextDouble()); // Random value between -2.5 and 2.5
        }

        String transactionJson = gson.toJson(new Transaction(features));
        kafkaTemplate.send(TOPIC, transactionJson);
        System.out.println("Produced transaction: " + transactionJson);
    }

    // Methods to start/stop the transaction producer
    public void startProducing() {
        producing.set(true);
    }

    public void stopProducing() {
        producing.set(false);
    }

    // Inner class to represent transaction
    static class Transaction {
        private final double[] features;

        public Transaction(double[] features) {
            this.features = features;
        }

        public double[] getFeatures() {
            return features;
        }
    }
}
