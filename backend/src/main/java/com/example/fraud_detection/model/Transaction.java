package com.example.fraud_detection.model;

import lombok.Data;

@Data
public class Transaction {
    private double[] features;

    public double[] getFeatures() { 
        return features;
    }
}
