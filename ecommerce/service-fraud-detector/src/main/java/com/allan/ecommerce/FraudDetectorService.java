package com.allan.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.Map;

public class FraudDetectorService {
    public static void main(String[] args) {
        var fraudDetectorService = new FraudDetectorService();
        try(var kafkaService = new KafkaService(
                FraudDetectorService.class.getSimpleName(),
                "ECCOMERCE_NEW_ORDER",
                fraudDetectorService::parse,
                Order.class,
                Map.of())) {
            kafkaService.run();
        }
    }

    private void parse(ConsumerRecord<String, Order> record) {
        System.out.println(record.key());
        System.out.println(record.value());
        System.out.println("Partition:: " + record.partition());
        System.out.println("Order Processed");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("------------------------------------------------------------------------------------------");
    }
}
