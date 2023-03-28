package pwr.daniel.ryszkowski.club;

import pwr.daniel.ryszkowski.club.clubGUI.ReportTable;
import pwr.daniel.ryszkowski.club.clubGUI.SeekersList;
import pwr.daniel.ryszkowski.club.clubGUI.TasksTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ClubApp extends JFrame implements ActionListener{

    private JTextField registerBar;
    private JButton acceptRegister, exitButt,permissionGet,permissionEnding;
    private final ClubImp clubImp;
    private JLabel sectorText;
    private JComboBox<String> seekers;
    private TasksTable listOfTasks;
    private ReportTable listOfRaports;
    private SeekersList seekersList;


    public ClubApp () throws NotBoundException, RemoteException {
        clubImp = new ClubImp(this);
        creatFrame();
    }

    public void creatFrame(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500,500);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("Lab07 - club");
        this.setLayout(null);
        this.setVisible(true);
        this.setBackground(Color.GRAY);

        JLabel label = new JLabel("Club name:");
        label.setSize(150,20);
        label.setLocation(20,400);

        sectorText = new JLabel("Sectors: ");
        sectorText.setSize(120,20);
        sectorText.setLocation(100,430);

        registerBar = new JTextField();
        registerBar.setSize(200,20);
        registerBar.setLocation(100,400);

        acceptRegister = new JButton("Sub");
        acceptRegister.setSize(60,30);
        acceptRegister.setLocation(310,400);

        exitButt = new JButton("Unsub");
        exitButt.setSize(80,30);
        exitButt.setLocation(380,400);
        exitButt.setEnabled(false);

        permissionGet = new JButton("get sector");
        permissionGet.setSize(120,30);
        permissionGet.setLocation(310,310);
        permissionGet.setEnabled(false);

        permissionEnding = new JButton("end sector");
        permissionEnding.setSize(120,30);
        permissionEnding.setLocation(310,350);
        permissionEnding.setEnabled(false);

        acceptRegister.addActionListener(this);
        exitButt.addActionListener(this);
        permissionGet.addActionListener(this);
        permissionEnding.addActionListener(this);

        seekers = new JComboBox<>();
        seekers.setSize(120,30);
        seekers.setLocation(310,270);

        seekers.addActionListener(this);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setSize(300,300);
        tabbedPane.setLocation(0,0);

        listOfRaports = new ReportTable();
        listOfTasks = new TasksTable();
        seekersList = new SeekersList();

        tabbedPane.add("Seekers",seekersList);
        tabbedPane.add("Tasks list", listOfTasks);
        tabbedPane.add("Raports",listOfRaports);

        this.add(registerBar);
        this.add(acceptRegister);
        this.add(exitButt);
        this.add(label);
        this.add(sectorText);
        this.add(permissionGet);
        this.add(permissionEnding);
        this.add(seekers);
        this.add(tabbedPane);

    }

    public static void main(String[] args) throws InterruptedException, InvocationTargetException {
        SwingUtilities.invokeAndWait(() ->{

            try {

                ClubApp clubApp = new ClubApp();
                System.out.println("Club started");

            }catch (RemoteException | NotBoundException e){
                System.out.println("Club error " + e.getMessage());
            }

        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == acceptRegister){

            String name = registerBar.getText();

            if(name != null && !name.equals("")) {
                System.out.println("S");
                clubImp.setClubName(name);
                if(clubImp.registerClub()) {

                    registerBar.setEnabled(false);

                    acceptRegister.setEnabled(false);
                    exitButt.setEnabled(true);

                    permissionEnding.setEnabled(true);
                    permissionGet.setEnabled(true);
                }else {
                    System.out.println("F");
                    JOptionPane.showMessageDialog(null, "This name club is all ready exists", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }

        }
        else if(e.getSource() == exitButt){
            ArrayList<String> temp  = new ArrayList<>(clubImp.getSector());
            for(int i = clubImp.getSector().size() - 1; i >= 0 ; i --)
                clubImp.exitSector(temp.get(i));

            clubImp.unRegisterClub();

            sectorText.setText("Sectors: ");

            acceptRegister.setEnabled(true);
            exitButt.setEnabled(false);

            registerBar.setText("");
            registerBar.setEnabled(true);

            permissionEnding.setEnabled(false);
            permissionGet.setEnabled(false);


        }
        else if(e.getSource() == permissionGet){

            clubImp.getSector(JOptionPane.showInputDialog(null,"Give sector (A1 - H8):","Sector",JOptionPane.INFORMATION_MESSAGE));
            sectorText.setText("Sectors: " + clubImp.getSector());
        }
        else if(e.getSource() == permissionEnding){

            clubImp.exitSector(JOptionPane.showInputDialog(null,"Give sector to remove:","Sector",JOptionPane.INFORMATION_MESSAGE));
            sectorText.setText("Sectors: " + clubImp.getSector());
        }
        if(e.getSource() == seekers && clubImp.getSector().size() > 0 && clubImp.getListOfSeekers().containsKey(seekers.getSelectedItem())){

            String sector = JOptionPane.showInputDialog(null,"Select sector from : " + clubImp.getSector(),"Give sector",JOptionPane.INFORMATION_MESSAGE);
            if(clubImp.getSector().contains(sector)) {

                String field = JOptionPane.showInputDialog(null, "Select field (A1 - J10) : " + clubImp.getSector(), "Give sector", JOptionPane.INFORMATION_MESSAGE);
                clubImp.giveTask((String) seekers.getSelectedItem(), sector, field);

            }else {
                JOptionPane.showMessageDialog(null,"Warning","Wrong sector selcted",JOptionPane.ERROR_MESSAGE);
            }

        }

    }
    public JComboBox<String> getSeekers() {
        return seekers;
    }
    public TasksTable getListOfTasks() {
        return listOfTasks;
    }
    public ReportTable getListOfRaports() {
        return listOfRaports;
    }
    public SeekersList getSeekersList() {
        return seekersList;
    }
    public JButton getExitButt() {
        return exitButt;
    }
}
