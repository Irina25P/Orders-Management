package model;

import java.time.LocalDateTime;

public record Bill(int orderId, int clientId, double amount, LocalDateTime dateTime) {
}