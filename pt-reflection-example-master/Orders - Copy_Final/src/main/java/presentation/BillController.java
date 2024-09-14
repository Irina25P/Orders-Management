package presentation;

import javax.swing.table.DefaultTableModel;
import bll.BillBLL;
import model.Bill;
import java.util.List;
/**
 * Controller for managing the presentation layer related to bills.
 * This class handles the interaction between the view for bills ({@link BillView}) and the underlying business logic.
 * It retrieves bill data from the business layer and updates the GUI accordingly.
 */
public class BillController {
    private BillView view;
    /**
     * Constructs a BillController with a specified view.
     * @param view the {@link BillView} instance that this controller will manage.
     */
    public BillController(BillView view) {
        this.view = view;
    }
    /**
     * Fills the table model of the view with bill data.
     * This method retrieves all bills using {@link BillBLL} and adds them to the table model in the view.
     * Each row in the table model corresponds to one bill, showing the order ID, client ID, bill amount, and date/time.
     */
    public void fillBillData() {
        List<Bill> bills = new BillBLL().getAllBills();
        DefaultTableModel model = (DefaultTableModel) view.getTableModel();
        for (Bill bill : bills) {
            model.addRow(new Object[]{bill.orderId(), bill.clientId(), bill.amount(), bill.dateTime()});
        }
    }
}
