package presentation;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Vector;
/**
 * Utility class for populating a {@link JTable} using reflection to automatically
 * extract and display data from a list of objects.
 */
public class TableModelUtils {

    /**
     * Populates a specified JTable with data extracted from a list of objects.
     * The fields of the objects in the list are used to generate the column names and
     * the values of these fields are used as the data for the table rows.
     *
     * @param table The {@link JTable} to be populated.
     * @param items The list of objects to extract data from. Each object in the list
     *              should have the same type, as the first object's fields are used
     *              to define the column names.
     */
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
