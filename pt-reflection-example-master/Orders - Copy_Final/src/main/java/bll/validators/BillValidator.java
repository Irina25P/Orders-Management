package bll.validators;

import model.Bill;
/**
 * The {@code BillValidator} class is responsible for validating {@link Bill} instances
 * before they are processed in the business logic layer. This validation ensures that
 * all required attributes of a bill meet specific requirements to maintain data integrity
 * and business rules.
 */
public class BillValidator implements Validator<Bill> {
    /**
     * Validates the specified {@code Bill} object to ensure its compliance with
     * the system's requirements.
     *
     * @param bill the {@code Bill} object to validate.
     * @throws IllegalArgumentException if any attribute of {@code Bill} does not meet the
     *         required validation rules:
     *         <ul>
     *             <li>The {@code Bill} object itself must not be null.</li>
     *             <li>The amount of the bill must be greater than zero to ensure
     *             economic validity.</li>
     *             <li>The order ID and client ID must be positive to ensure they
     *             refer to valid records in the database.</li>
     *         </ul>
     */
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

