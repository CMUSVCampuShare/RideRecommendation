package com.campushare.RideRecommendation.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaUserEventProducer {
    private final KafkaProducer<String, String> producer;
    private final String bootstrapServers = "35.219.146.212:9092";

    public KafkaUserEventProducer() {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        producer = new KafkaProducer<>(properties);
    }

    public void sendCreateUserEvent(String message) {
        producer.send(new ProducerRecord<>("create_user_topic", message));
    }

    public void sendEditUserEvent(String message) {
        producer.send(new ProducerRecord<>("edit_user_topic", message));
    }

    public void close() {
        producer.close();
    }
}
