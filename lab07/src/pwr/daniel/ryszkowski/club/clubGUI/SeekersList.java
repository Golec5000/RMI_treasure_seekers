package pwr.daniel.ryszkowski.club.clubGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SeekersList extends JPanel {

    private DefaultTableModel dataTable;
    private JTable data;

    public SeekersList(){
        dataTable = new DefaultTableModel();
        dataTable.addColumn("ID");
        dataTable.addColumn("Seeker name");


        data = new JTable(dataTable);
        data.setPreferredScrollableViewportSize(new Dimension(290, 300));
        data.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(data);
        this.setSize(310, 300);

        this.add(scrollPane);

    }

    public void addList(List<List<String>> list){

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
