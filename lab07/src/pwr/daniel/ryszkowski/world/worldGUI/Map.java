package pwr.daniel.ryszkowski.world.worldGUI;

import pwr.daniel.ryszkowski.world.WorldApp;

import java.util.ArrayList;

public class Map {

    private ArrayList<Sector> sectors;

    private final WorldApp worldApp;

    public Map(WorldApp worldApp){
        creatSectors();
        this.worldApp = worldApp;
        addSectorsToWorld();
    }

    public void addSectorsToWorld(){
        for(Sector sector : sectors)
            worldApp.add(sector);
    }

    private void creatSectors(){

        sectors = new ArrayList<>();

        char letter = 'H';
        int number = 1;
        for(int i = 0; i < 1040; i+= 130){

            for(int j = 0; j < 1040; j+= 130){
                sectors.add(new Sector(j,i,letter + "" + number));
                number++;
            }
            letter--;
            number = 1;
        }
    }

    public ArrayList<Sector> getSectors() {
        return sectors;
    }
}
