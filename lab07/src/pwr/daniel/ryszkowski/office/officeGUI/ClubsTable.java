package pwr.daniel.ryszkowski.office.officeGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ClubsTable extends JPanel {

    private DefaultTableModel dataTable;
    private JTable data;

    public ClubsTable(String dataType){

        dataTable = new DefaultTableModel();
        dataTable.addColumn("Club name");
        dataTable.addColumn(dataType);


        data = new JTable(dataTable);
        data.setPreferredScrollableViewportSize(new Dimension(390, 400));
        data.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(data);
        this.setSize(400, 400);

        this.add(scrollPane);

    }

    public void addList(List<java.util.List<String>> list){

        for(List<String> temp1 : list)
            dataTable.addRow(new Object[]{temp1.get(0),temp1.get(1)});

    }

    public void clearPage() {
        int rowCount = dataTable.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            dataTable.removeRow(i);
        }

    }

}
