package com.gameStore.ernestasUrbonas.kafka.producer;

import com.gameStore.ernestasUrbonas.kafka.event.LowStockEvent;
import com.gameStore.ernestasUrbonas.kafka.event.OrderCreatedEvent;
import com.gameStore.ernestasUrbonas.kafka.event.OrderStatusChangedEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${app.kafka.topics.order-created}")
    private String orderCreatedTopic;

    @Value("${app.kafka.topics.order-status-changed}")
    private String orderStatusChangedTopic;

    @Value("${app.kafka.topics.low-stock}")
    private String lowStockEventTopic;

    public KafkaProducerService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrderCreatedEvent(OrderCreatedEvent event) {
        kafkaTemplate.send(
                orderCreatedTopic,
                event.getOrderId().toString(),
                event
        );
    }

    public void sendOrderStatusChangedEvent(OrderStatusChangedEvent event) {
        kafkaTemplate.send(
                orderStatusChangedTopic,
                event.getOrderId().toString(),
                event
        );
    }

    public void sendLowStockEvent(LowStockEvent event) {
        kafkaTemplate.send(
                lowStockEventTopic,
                event.getProductId().toString(),
                event
        );
    }
}