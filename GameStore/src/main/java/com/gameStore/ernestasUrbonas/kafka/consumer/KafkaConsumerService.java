package com.gameStore.ernestasUrbonas.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaConsumerService {
    @KafkaListener(topics = "${app.kafka.topics.order-created}", groupId = "gamestore-group")
    public void consumeOrderCreated(String message) {
        log.info("Order created event received: {}", message);
    }

    @KafkaListener(topics = "${app.kafka.topics.order-status-changed}", groupId = "gamestore-group")
    public void consumeOrderStatusChanged(String message) {
        log.info("Order status changed event received: {}", message);
    }

    @KafkaListener(topics = "${app.kafka.topics.low-stock}", groupId = "gamestore-group")
    public void consumeLowStock(String message) {
        log.info("Low stock event received: {}", message);
    }
}
