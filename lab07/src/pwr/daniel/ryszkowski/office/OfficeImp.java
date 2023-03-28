package pwr.daniel.ryszkowski.office;

import interfaces.IClub;
import interfaces.IOffice;
import model.Category;
import model.Report;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OfficeImp extends UnicastRemoteObject implements IOffice {

    private HashMap<String,IClub> listOdClubs;
    private HashMap<String,String> sectorsRequest;
    private HashMap<String,ArrayList<Report>> reportsFromClubs;
    private final OfficeApp officeApp;
    private List<List<String>> temp;
    private HashMap<String,Integer> searchingStatus;



    public OfficeImp(OfficeApp officeApp) throws RemoteException, AlreadyBoundException {
        super();
        this.officeApp = officeApp;
        init();
        creatSectors();
    }

    private void init() throws AlreadyBoundException, RemoteException {

        Registry registry = LocateRegistry.createRegistry(1500);
        registry.bind("office",this);

        listOdClubs = new HashMap<>();
        sectorsRequest = new HashMap<>();
        reportsFromClubs = new HashMap<>();
        searchingStatus = new HashMap<>();

    }

    @Override
    public boolean register(IClub ic) throws RemoteException {
        if(!listOdClubs.containsKey(ic.getName())){
            listOdClubs.put(ic.getName(), ic);

            updateDataForListClub();

            return true;

        }

        return false;
    }

    public void updateDataForListClub(){

        temp = new ArrayList<>();
        int clubID = 0;
        for(String s : listOdClubs.keySet()){
            clubID++;

            List<String> temp2 = new ArrayList<>();

            temp2.add(s);
            temp2.add(String.valueOf(clubID));

            temp.add(temp2);
        }

        officeApp.getListOfClubs().clearPage();
        officeApp.getListOfClubs().addList(temp);
    }

    @Override
    public boolean unregister(String clubName) throws RemoteException {
        if(listOdClubs.containsKey(clubName)){
            listOdClubs.remove(clubName);

            updateDataForListClub();

            return true;
        }

        return false;
    }

    public void updateDataForListPermissions(){

        temp = new ArrayList<>();

        for(String sector : sectorsRequest.keySet()){

            if(sectorsRequest.get(sector) != null){

                List<String> temp2 = new ArrayList<>();

                temp2.add(sectorsRequest.get(sector));
                temp2.add(sector);

                temp.add(temp2);

            }

        }

        officeApp.getPermissionsStats().clearPage();
        officeApp.getPermissionsStats().addList(temp);

    }

    @Override
    public boolean permissionRequest(String clubName, String sector) throws RemoteException {

        if(sectorsRequest.containsKey(sector) && sectorsRequest.get(sector) == null){
            sectorsRequest.put(sector,clubName);

            updateDataForListPermissions();

            return true;
        }
        return false;
    }

    @Override
    public boolean permissionEnd(String clubName, String sector) throws RemoteException {

        if (sectorsRequest.get(sector) != null && sectorsRequest.get(sector).equals(clubName)){
            sectorsRequest.put(sector,null);

            updateDataForListPermissions();

            return true;

        }

        return false;
    }

    public void updateDataForListOfRaports(){

        temp = new ArrayList<>();

        for(String name : reportsFromClubs.keySet()){

            ArrayList<Report> tempRaports = reportsFromClubs.get(name);

            for (Report report : tempRaports){

                List<String> temp2 = new ArrayList<>();

                if(report.getArtifact() != null && report.getArtifact().getCategory() != Category.EMPTY){

                    temp2.add(name);
                    temp2.add(report.getSector());
                    temp2.add(report.getField());
                    temp2.add(String.valueOf(report.getArtifact().getCategory()));

                    temp.add(temp2);
                }

            }

        }
        System.out.println(temp);
        officeApp.getRaportsTabel().clearPage();
        officeApp.getRaportsTabel().addList(temp);

    }

    public void updateDataForListOfSearchingStatus(){
        temp = new ArrayList<>();

        for(String sector : searchingStatus.keySet())
            searchingStatus.put(sector, 0);

        for(String name : reportsFromClubs.keySet()){

            ArrayList<Report> tempRaports = reportsFromClubs.get(name);

            for(Report report : tempRaports)
                if(report.getArtifact() != null)
                    searchingStatus.put(report.getSector(), searchingStatus.get(report.getSector()) + 1 );

        }

        for(String sector : searchingStatus.keySet()){

            List<String> tempStats = new ArrayList<>();

            tempStats.add(sector);

            if(searchingStatus.get(sector) > 0) tempStats.add(searchingStatus.get(sector) + " %");
            else tempStats.add("N/D");

            temp.add(tempStats);

        }

        officeApp.getSectorTable().clearPage();
        officeApp.getSectorTable().addList(temp);

    }

    @Override
    public boolean report(List<Report> reports, String clubName) throws RemoteException {

        reportsFromClubs.put(clubName, (ArrayList<Report>) reports);
        updateDataForListOfRaports();
        updateDataForListOfSearchingStatus();

        return false;
    }

    @Override
    public synchronized List<IClub> getClubs() throws RemoteException {
        return new ArrayList<>(listOdClubs.values());
    }

    public void creatSectors(){

        char letter = 'A';

        for(int i = 0; i < 8 ; i++){
            for(int j = 1 ; j <= 8; j++){
                sectorsRequest.put(letter + ""+ j, null);
                searchingStatus.put(letter + ""+ j, 0);
            }
            letter ++;
        }

    }
}
