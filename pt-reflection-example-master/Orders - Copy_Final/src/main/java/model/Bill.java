package model;

import java.time.LocalDateTime;
/**
 * Represents a billing record, encapsulating details of a transaction.
 * Records in Java are a concise way to create immutable data-only classes.
 *
 * This record holds all necessary details about a bill, including identifiers for the order and client,
 * the transaction amount, and the precise time the transaction was recorded.
 *
 * @param orderId The unique identifier of the order associated with this bill.
 * @param clientId The unique identifier of the client associated with this bill.
 * @param amount The monetary amount charged in this bill.
 * @param dateTime The timestamp of when the bill was issued.
 */
public record Bill(int orderId, int clientId, double amount, LocalDateTime dateTime) {
}