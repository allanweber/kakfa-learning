package com.allan.ecommerce;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderService {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        try (var orderDispatcher = new KafkaDispatcher<Order>()) {
            try (var emailDispatcher = new KafkaDispatcher<String>()) {
                for (int i = 0; i < 10; i++) {
                    var userId = UUID.randomUUID().toString();
                    var orderId = UUID.randomUUID().toString();
                    var amount = BigDecimal.valueOf(Math.random() * 5000 + 1);
                    var order = new Order(userId, orderId, amount);
                    orderDispatcher.send("ECCOMERCE_NEW_ORDER", userId, order);

                    var key = UUID.randomUUID().toString();
                    emailDispatcher.send("ECCOMERCE_SEND_EMAIL", key, "Thank you for order.");
                }
            }
        }
    }
}
