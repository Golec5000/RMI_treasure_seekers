package pwr.daniel.ryszkowski.office.officeGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class RaportsTabel extends JPanel {


    private DefaultTableModel dataTable;
    private JTable data;

    public RaportsTabel(){

        dataTable = new DefaultTableModel();
        dataTable.addColumn("Club name");
        dataTable.addColumn("Sector");
        dataTable.addColumn("Field");
        dataTable.addColumn("Arti type");

        data = new JTable(dataTable);
        data.setPreferredScrollableViewportSize(new Dimension(390, 400));
        data.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(data);
        this.setSize(400, 400);

        this.add(scrollPane);

    }
    public void addList(List<List<String>> list){

        for(List<String> temp1 : list)
            dataTable.addRow(new Object[]{temp1.get(0),temp1.get(1),temp1.get(2),temp1.get(3)});

    }

    public void clearPage() {
        int rowCount = dataTable.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            dataTable.removeRow(i);
        }

    }

}
