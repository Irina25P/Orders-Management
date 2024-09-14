package presentation;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Vector;

public class TableModelUtils {

    public static void populateTableWithReflection(JTable table, List<?> items) {
        Vector<String> columnNames = new Vector<>();
        Vector<Vector<Object>> data = new Vector<>();

        if (!items.isEmpty()) {
            Object firstItem = items.get(0);
            for (Field field : firstItem.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                columnNames.add(field.getName());
            }

            for (Object item : items) {
                Vector<Object> vector = new Vector<>();
                for (Field field : item.getClass().getDeclaredFields()) {
                    field.setAccessible(true);
                    try {
                        vector.add(field.get(item));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                data.add(vector);
            }
        }

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        table.setModel(model);
    }
}
