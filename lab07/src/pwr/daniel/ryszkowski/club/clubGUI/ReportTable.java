package pwr.daniel.ryszkowski.club.clubGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ReportTable extends JPanel {

    private DefaultTableModel dataTable;
    private JTable data;
    public ReportTable(){

        dataTable = new DefaultTableModel();
        dataTable.addColumn("Name");
        dataTable.addColumn("Arti type");
        dataTable.addColumn("Sector");
        dataTable.addColumn("Field");


        data = new JTable(dataTable);
        data.setPreferredScrollableViewportSize(new Dimension(290, 300));
        data.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(data);
        this.setSize(310, 300);

        this.add(scrollPane);


    }

    public void addList(List<java.util.List<String>> list){

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
