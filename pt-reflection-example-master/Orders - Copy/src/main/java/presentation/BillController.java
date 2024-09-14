package presentation;

import javax.swing.table.DefaultTableModel;
import bll.BillBLL;
import model.Bill;
import java.util.List;

public class BillController {
    private BillView view;

    public BillController(BillView view) {
        this.view = view;
    }

    public void fillBillData() {
        List<Bill> bills = new BillBLL().getAllBills();
        DefaultTableModel model = (DefaultTableModel) view.getTableModel();
        for (Bill bill : bills) {
            model.addRow(new Object[]{bill.orderId(), bill.clientId(), bill.amount(), bill.dateTime()});
        }
    }
}
