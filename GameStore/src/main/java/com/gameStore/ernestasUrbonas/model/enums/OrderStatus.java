package com.gameStore.ernestasUrbonas.model.enums;

public enum OrderStatus {
    PENDING,
    PROCESSING,
    SHIPPED,
    DELIVERED,
    CANCELLED;

    public boolean canTransitionTo(OrderStatus newStatus) {
        return switch (this) {
            case PENDING -> newStatus.equals(OrderStatus.PROCESSING) || newStatus.equals(OrderStatus.CANCELLED);
            case PROCESSING -> newStatus.equals(OrderStatus.SHIPPED) || newStatus.equals(OrderStatus.CANCELLED);
            case SHIPPED -> newStatus.equals(OrderStatus.DELIVERED);
            case DELIVERED, CANCELLED -> false;
        };
    }
}
