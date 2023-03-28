package pwr.daniel.ryszkowski.seeker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class SeekerApp extends JFrame implements ActionListener {

    private final SeekerImp seekerImp;
    private JButton refresh, unSub;
    private JComboBox<String> listOfClubs;
    private JProgressBar progressBar;
    public SeekerApp() throws NotBoundException, RemoteException {
        seekerImp = new SeekerImp(this);
        createFrame();
    }


    public void createFrame(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300,300);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("Lab07 - seeker");
        this.setLayout(null);
        this.setVisible(true);
        this.setBackground(Color.GRAY);

        progressBar = new JProgressBar();
        progressBar.setSize(300,50);
        progressBar.setLocation(0,100);
        progressBar.setValue(0);
        progressBar.setVisible(false);
        progressBar.setStringPainted(true);
        progressBar.setString("Digging...");


        listOfClubs = new JComboBox<>();
        listOfClubs.addItem("Choose club");
        listOfClubs.setSize(250,40);
        listOfClubs.setLocation(25,220);
        listOfClubs.addActionListener(this);

        refresh = new JButton("Refresh");
        refresh.setSize(100,30);
        refresh.setLocation(50,180);
        refresh.addActionListener(this);

        unSub = new JButton("Unregister");
        unSub.setLocation(150,180);
        unSub.setSize(100,30);
        unSub.addActionListener(this);
        unSub.setEnabled(false);

        this.add(refresh);
        this.add(listOfClubs);
        this.add(unSub);
        this.add(progressBar);

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                try {
                    if(seekerImp.getName() != null){
                        seekerImp.unSub();
                    }
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
                System.exit(0);
            }
        });

    }
    public static void main(String[] args) throws InterruptedException, InvocationTargetException {
        SwingUtilities.invokeAndWait(() ->{

            try {
                SeekerApp seekerApp = new SeekerApp();
            } catch (RemoteException | NotBoundException e) {
                System.out.println("Seeker error " + e.getMessage());
            }


        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == refresh){
            seekerImp.refreshClubs();
        }else if(e.getSource() == listOfClubs){

            seekerImp.registerToClub((String) listOfClubs.getSelectedItem());
        }else if(e.getSource() == unSub){

            seekerImp.unSub();
            listOfClubs.setEnabled(true);
            refresh.setEnabled(true);
            unSub.setEnabled(false);

        }

    }

    public JButton getRefresh() {
        return refresh;
    }

    public JButton getUnSub() {
        return unSub;
    }

    public JComboBox<String> getListOfClubs() {
        return listOfClubs;
    }

    public JProgressBar getProgressBar() {
        return progressBar;
    }

}
