package pwr.daniel.ryszkowski.seeker;

import interfaces.IClub;
import interfaces.IOffice;
import interfaces.ISeeker;
import interfaces.IWorld;
import model.Artifact;
import model.Report;

import javax.swing.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class SeekerImp extends UnicastRemoteObject implements ISeeker {

    private IOffice iOffice;
    private IWorld iWorld;
    protected IClub iClub;
    private String seekerName;
    private ArrayList<IClub> clubs;
    private final SeekerApp seekerApp;

    public SeekerImp(SeekerApp seekerApp) throws RemoteException, NotBoundException {
        super();
        init();
        this.seekerApp = seekerApp;
    }

    private void init() throws RemoteException, NotBoundException {

        Registry registry;

        registry = LocateRegistry.getRegistry("192.168.82.73",1500);

        iOffice = (IOffice) registry.lookup("office");

        registry = LocateRegistry.getRegistry("192.168.82.73",2000);

        iWorld = (IWorld) registry.lookup("world");

    }

    public void refreshClubs(){
        clubs = new ArrayList<>();
        try {
            clubs = (ArrayList<IClub>) iOffice.getClubs();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        seekerApp.getListOfClubs().removeAllItems();
        seekerApp.getListOfClubs().addItem("Choose club");

        for(IClub c : clubs) {
            try {
                seekerApp.getListOfClubs().addItem(c.getName());
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }


    }

    public void registerToClub(String nameClub){

        for(IClub c : clubs){

            try {
                if(c.getName().equals(nameClub)){
                    iClub = c;
                    do {
                        seekerName = JOptionPane.showInputDialog(null, "Give nick", "register", JOptionPane.INFORMATION_MESSAGE);
                    }while (seekerName == null || seekerName.equals(""));

                    if(iClub.register(this)) {
                        seekerApp.getListOfClubs().setEnabled(false);
                        seekerApp.getRefresh().setEnabled(false);
                        seekerApp.getUnSub().setEnabled(true);
                    }
                    else
                        JOptionPane.showMessageDialog(null,"This name is all ready existed, try again","Warning",JOptionPane.WARNING_MESSAGE);
                    break;
                }
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }

        }


    }

    public void unSub(){

        try {
            iClub.unregister(seekerName);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public boolean exploreTask(String sector, String field) throws RemoteException {

        Artifact artifact = iWorld.explore(seekerName,sector,field);

        progres(artifact);

        Report report = new Report(sector,field,artifact);
        iClub.report(report,seekerName);

        return artifact != null;
    }

    private void progres(Artifact artifact){


        if(artifact != null){
            int sleepTime = artifact.getExcavationTime()/100;
            seekerApp.getProgressBar().setVisible(true);
            seekerApp.getUnSub().setEnabled(false);
            for (int i = 0; i < 100; i++){


                try {
                    sleep(sleepTime);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                seekerApp.getProgressBar().setValue(i);
            }

            seekerApp.getProgressBar().setVisible(false);
            seekerApp.getUnSub().setEnabled(true);

            JOptionPane.showMessageDialog(null,artifact.getCategory() + " is find", "Successes",JOptionPane.INFORMATION_MESSAGE);
        }else {
            JOptionPane.showMessageDialog(null,"This field all ready wisted or this field dont exist","Warning",JOptionPane.WARNING_MESSAGE);
        }

    }

    @Override
    public String getName() throws RemoteException {
        return seekerName;
    }
}
