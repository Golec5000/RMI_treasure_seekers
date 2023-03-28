package pwr.daniel.ryszkowski.club;

import interfaces.IClub;
import interfaces.IOffice;
import interfaces.ISeeker;
import model.Report;

import javax.swing.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class ClubImp extends UnicastRemoteObject implements IClub {

    public ClubImp(ClubApp clubApp) throws RemoteException, NotBoundException {
        super();
        init();
        this.clubApp = clubApp;
    }

    private final ClubApp clubApp;
    private IOffice iOffice;
    private HashMap<String,ISeeker> listOfSeekers;
    private String clubName;
    private HashSet<String> sector;
    private ArrayList<Report> reports;
    private List<List<String>> tasks;
    private List<List<String>> raports;

    public void init() throws RemoteException, NotBoundException {

        Registry registry = LocateRegistry.getRegistry("192.168.82.73",1500);

        iOffice = (IOffice) registry.lookup("office");

        listOfSeekers = new HashMap<>();
        sector = new HashSet<>();
        reports = new ArrayList<>();
        tasks = new ArrayList<>();
        raports = new ArrayList<>();

    }

    public boolean registerClub(){

        try {
            return iOffice.register(this);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }

    public void unRegisterClub(){

        clubApp.getListOfRaports().clearPage();
        clubApp.getListOfTasks().clearPage();
        reports.clear();
        tasks.clear();
        try {
            iOffice.unregister(clubName);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }


    }

    public void getSector(String tempSector){
        try {
            if(sector.size() < 2 && tempSector != null &&iOffice.permissionRequest(clubName,tempSector)) {
                sector.add(tempSector);
            }
            else {
                JOptionPane.showMessageDialog(null,"Warning","Warning",JOptionPane.ERROR_MESSAGE);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void giveTask(String nameSeeker,String sector, String field){
        try {
            if(listOfSeekers.containsKey(nameSeeker)) {

                List<String> temp = new ArrayList<>();

                boolean test = listOfSeekers.get(nameSeeker).exploreTask(sector, field);

                temp.add(nameSeeker);
                temp.add(sector);
                temp.add(field);

                if(test)
                    temp.add("Successes");
                else
                    temp.add("Failed");

                tasks.add(temp);
                System.out.println(tasks);
                clubApp.getListOfTasks().clearPage();
                clubApp.getListOfTasks().addList(tasks);
            }
            namesOfSeekers();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }

    public void exitSector(String tempSector){

        try{
            if(iOffice.permissionEnd(clubName,tempSector)){

                sector.remove(tempSector);
                System.out.println(sector);

            }else {
                JOptionPane.showMessageDialog(null,"Warning","Warning",JOptionPane.ERROR_MESSAGE);
            }

        }catch (RemoteException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean register(ISeeker ic) throws RemoteException {
        if(!listOfSeekers.containsKey(ic.getName()) && ic.getName() != null & !Objects.equals(ic.getName(), "")){

            listOfSeekers.put(ic.getName(), ic);
            namesOfSeekers();
            updateListOFSeekers();
            System.out.println(listOfSeekers);
            clubApp.getExitButt().setEnabled(false);
            return true;
        }
        return false;
    }

    public void updateListOFSeekers(){
        List<List<String>> listSeekers = new ArrayList<>();

        int id = 0;

        for(String name : listOfSeekers.keySet()){
            List<String> temp = new ArrayList<>();

            id++;

            temp.add(String.valueOf(id));
            temp.add(name);
            listSeekers.add(temp);
        }

        clubApp.getSeekersList().clearPage();
        clubApp.getSeekersList().addList(listSeekers);

    }

    @Override
    public boolean unregister(String seekerName) throws RemoteException {
        if(listOfSeekers.containsKey(seekerName)){
            listOfSeekers.remove(seekerName);
            namesOfSeekers();
            updateListOFSeekers();
            System.out.println(listOfSeekers);

            if(listOfSeekers.size() == 0)
                clubApp.getExitButt().setEnabled(true);

            return true;
        }

        return false;
    }

    public void  namesOfSeekers(){
        clubApp.getSeekers().removeAllItems();
        clubApp.getSeekers().addItem("Choose seeker");
        for(String name : listOfSeekers.keySet())
            clubApp.getSeekers().addItem(name);

    }

    @Override
    public String getName() throws RemoteException {
        return clubName;
    }

    @Override
    public boolean report(Report report, String seekerName) throws RemoteException {

        List<String> temp = new ArrayList<>();
        if(report.getArtifact() != null) {
            temp.add(seekerName);
            temp.add(String.valueOf(report.getArtifact().getCategory()));
            temp.add(report.getSector());
            temp.add(report.getField());

            raports.add(temp);

            clubApp.getListOfRaports().clearPage();
            clubApp.getListOfRaports().addList(raports);

            reports.add(report);

            iOffice.report(reports, clubName);
        }

        return false;
    }
    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public HashSet<String> getSector() {
        return sector;
    }

    public HashMap<String, ISeeker> getListOfSeekers() {
        return listOfSeekers;
    }
}
