package com.example.fraud_detection.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class FraudDetectionService {
    //     private static final String PYTHON_API_URL = "http://localhost:8000/predict/";
    private static final String PYTHON_API_URL = "http://fastapi-ml:8000/predict/";

    public boolean isFraudulentTransaction(double[] features) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> request = new HashMap<>();
        request.put("features", features);

        ResponseEntity<Map> response = restTemplate.postForEntity(PYTHON_API_URL, request, Map.class);
        return (Integer) response.getBody().get("fraud") == 1;
    }
}
