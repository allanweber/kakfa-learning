package com.allan.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.io.Closeable;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Pattern;

public class KafkaService<T> implements Closeable {

    private final KafkaConsumer<String, T> consumer;
    private final ConsumerFunction parse;

    public KafkaService(String groupId, String topic, ConsumerFunction parse, Class<T> clazz, Map<String, String> properties) {
        this(groupId, parse, clazz, properties);
        consumer.subscribe(Collections.singleton(topic));
    }

    public KafkaService(String groupId, Pattern topic, ConsumerFunction parse, Class<T> clazz, Map<String, String> properties) {
        this(groupId, parse, clazz, properties);
        consumer.subscribe(topic);
    }

    private KafkaService(String groupId, ConsumerFunction parse, Class<T> clazz, Map<String, String> properties) {
        consumer = new KafkaConsumer<>(getProperties(groupId, clazz, properties));
        this.parse = parse;
    }

    private Properties getProperties(String groupId, Class<T> clazz, Map<String, String> overrideProperties) {
        var properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, GsonDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1");
        properties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.setProperty(GsonDeserializer.TYPE_CONFIG, clazz.getName());

        properties.putAll(overrideProperties);
        return properties;
    }

    public void run() {
        while (true) {
            var records = consumer.poll(Duration.ofMillis(100));
            if (!records.isEmpty()) {
                for (var record : records) {
                    parse.consume(record);
                }
            }
        }
    }

    @Override
    public void close() {
        consumer.close();
    }
}
