package bll.validators;

import model.Bill;

public class BillValidator implements Validator<Bill> {

    @Override
    public void validate(Bill bill) {
        if (bill == null) {
            throw new IllegalArgumentException("Bill cannot be null.");
        }
        if (bill.amount() <= 0) {
            throw new IllegalArgumentException("Bill amount must be greater than zero.");
        }
        if (bill.orderId() <= 0) {
            throw new IllegalArgumentException("Order ID must be positive.");
        }
        if (bill.clientId() <= 0) {
            throw new IllegalArgumentException("Client ID must be positive.");
        }
    }
}

