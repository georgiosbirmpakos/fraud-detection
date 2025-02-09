package com.example.fraud_detection.controller;

import com.example.fraud_detection.service.KafkaProducerService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class TransactionController {
    private final KafkaProducerService kafkaProducerService;

    public TransactionController(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    @PostMapping("/send-transaction")
    public String sendTransaction(@RequestBody String transactionData) {
        kafkaProducerService.sendTransaction(transactionData);
        return "Transaction sent to Kafka!";
    }

    @PostMapping("/start-producing")
    public String startProducing() {
        kafkaProducerService.startProducing();
        return "Started automatic transaction generation!";
    }

    @PostMapping("/stop-producing")
    public String stopProducing() {
        kafkaProducerService.stopProducing();
        return "Stopped automatic transaction generation!";
    }
}

