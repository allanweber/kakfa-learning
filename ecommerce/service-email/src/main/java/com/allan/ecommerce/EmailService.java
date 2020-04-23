package com.allan.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.Map;

public class EmailService {
    public static void main(String[] args) {
        var emailService = new EmailService();
        try(var kafkaService = new KafkaService(
                EmailService.class.getSimpleName(),
                "ECCOMERCE_SEND_EMAIL",
                emailService::parse,
                String.class,
                Map.of())) {
            kafkaService.run();
        }
    }

    private void parse(ConsumerRecord<String, String> record){
        System.out.println(record.key());
        System.out.println(record.value());
        System.out.println(record.partition());
        System.out.println("Email sent");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("------------------------------------------------------------------------------------------");
    }
}
