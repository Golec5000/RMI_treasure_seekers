package pwr.daniel.ryszkowski.office;

import pwr.daniel.ryszkowski.office.officeGUI.ClubsTable;
import pwr.daniel.ryszkowski.office.officeGUI.RaportsTabel;
import pwr.daniel.ryszkowski.office.officeGUI.SectorTable;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;


public class OfficeApp extends JFrame{

    private final OfficeImp officeImp;
    private ClubsTable listOfClubs, permissionsStats;
    private RaportsTabel raportsTabel;
    private SectorTable sectorTable;

    public OfficeApp() throws AlreadyBoundException, RemoteException {
        officeImp = new OfficeImp(this);
        creatFrame();
    }

    public void creatFrame(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500,500);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("Lab07 - office");
        this.setLayout(null);
        this.setVisible(true);

        listOfClubs = new ClubsTable("ID");
        permissionsStats = new ClubsTable("Sector");
        raportsTabel = new RaportsTabel();
        sectorTable = new SectorTable();

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setSize(400,400);
        tabbedPane.setLocation(25,25);

        tabbedPane.add("Clubs",listOfClubs);
        tabbedPane.add("Permissions",permissionsStats);
        tabbedPane.add("Raports",raportsTabel);
        tabbedPane.add("Sectors",sectorTable);

        this.add(tabbedPane);

    }

    public static void main(String[] args) throws InterruptedException, InvocationTargetException {

        SwingUtilities.invokeAndWait(() -> {

            try {

                OfficeApp officeApp = new OfficeApp();
                System.out.println("Office started");
            } catch (RemoteException | AlreadyBoundException e) {
                System.out.println("Office error " + e.getMessage());
            }

        });

    }

    public ClubsTable getListOfClubs() {
        return listOfClubs;
    }

    public RaportsTabel getRaportsTabel() {
        return raportsTabel;
    }

    public SectorTable getSectorTable() {
        return sectorTable;
    }

    public ClubsTable getPermissionsStats() {
        return permissionsStats;
    }
}
