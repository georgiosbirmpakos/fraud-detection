package com.example.fraud_detection;

import com.example.fraud_detection.service.KafkaConsumerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FraudDetectionApplication {

	public static void main(String[] args) {
		SpringApplication.run(FraudDetectionApplication.class, args);
	}

	@Bean
	CommandLineRunner startConsumer(KafkaConsumerService kafkaConsumerService) {
		return args -> kafkaConsumerService.consumeTransactions();
	}
}
