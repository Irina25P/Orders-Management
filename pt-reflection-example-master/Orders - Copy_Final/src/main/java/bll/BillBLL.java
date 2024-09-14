package bll;

import bll.validators.BillValidator;
import dao.BillDAO;
import model.Bill;

import java.util.List;
/**
 * Handles business logic for bill operations including insertion and retrieval from a database.
 * This class ensures that all bill transactions meet business rules through validation before
 * proceeding with database operations.
 */
public class BillBLL {
    private final BillValidator billValidator;
    /**
     * Constructs a {@code BillBLL} object, initializing the necessary validators.
     */
    public BillBLL() {
        this.billValidator = new BillValidator();
    }
    /**
     * Inserts a bill into the database after performing necessary validations.
     * @param bill The bill to be inserted.
     * @throws IllegalArgumentException If the bill amount is not positive.
     */
    public void insertBill(Bill bill) {
        billValidator.validate(bill);
        BillDAO.insert(bill);
    }
    /**
     * Retrieves all bills from the database.
     * @return A list of all bills.
     */
    public List<Bill> getAllBills() {
        return BillDAO.findAll();
    }
}

