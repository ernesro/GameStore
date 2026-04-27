package com.gameStore.ernestasUrbonas.kafka.consumer;

import com.gameStore.ernestasUrbonas.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaConsumerService {
    private final EmailService emailService;

    @Autowired
    public KafkaConsumerService(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(topics = "${app.kafka.topics.order-created}", groupId = "gamestore-group", autoStartup = "false")
    public void consumeOrderCreated(String message) {
        log.info("Order created event received: {}", message);
        emailService.sendOrderConfirmation(message);
    }

    @KafkaListener(topics = "${app.kafka.topics.order-status-changed}", groupId = "gamestore-group", autoStartup = "false")
    public void consumeOrderStatusChanged(String message) {
        log.info("Order status changed event received: {}", message);
        emailService.sendOrderStatusChanged(message);
    }

    @KafkaListener(topics = "${app.kafka.topics.low-stock}", groupId = "gamestore-group", autoStartup = "false")
    public void consumeLowStock(String message) {
        log.info("Low stock event received: {}", message);
        emailService.sendLowStockAlert(message);
    }
}
