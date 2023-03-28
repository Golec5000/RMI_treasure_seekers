package pwr.daniel.ryszkowski.world;

import interfaces.IWorld;
import model.Artifact;
import pwr.daniel.ryszkowski.world.worldGUI.Field;
import pwr.daniel.ryszkowski.world.worldGUI.Sector;

import java.awt.*;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class WorldImp extends UnicastRemoteObject implements IWorld {

    private final WorldApp worldApp;

    public WorldImp(WorldApp worldApp) throws RemoteException, AlreadyBoundException {
        super();
        Registry registry = LocateRegistry.createRegistry(2000);
        registry.bind("world",this);
        this.worldApp = worldApp;
    }

    @Override
    public Artifact explore(String seekerName, String sector, String field) throws RemoteException {

        Artifact artifact;

        for(Sector s : worldApp.getMap().getSectors()){

            if(s.getName().equals(sector)) {

                for (Field f : s.getFields()) {

                    if (f.getName().equals(field) && !f.isExplored()){

                        f.setBackground(Color.RED);
                        artifact = f.getArtifact();
                        f.setExplored(true);
                        System.out.println(artifact.getCategory());
                        return artifact;
                    }

                }
            }

        }

        return null;
    }
}
