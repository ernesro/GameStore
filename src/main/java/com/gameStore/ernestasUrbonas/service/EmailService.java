package com.gameStore.ernestasUrbonas.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {

    public void sendOrderConfirmation(String message) {
        log.info("📧 Sending order confirmation email: {}", message);
    }

    public void sendOrderStatusChanged(String message) {
        log.info("📧 Sending order status update email: {}", message);
    }

    public void sendLowStockAlert(String message) {
        log.info("📧 Sending low stock alert to admin: {}", message);
    }
}