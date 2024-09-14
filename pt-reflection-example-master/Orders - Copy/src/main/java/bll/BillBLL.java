package bll;

import bll.validators.BillValidator;
import dao.BillDAO;
import model.Bill;

import java.util.List;

public class BillBLL {
    private final BillValidator billValidator;

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

