package pwr.daniel.ryszkowski.world;

import pwr.daniel.ryszkowski.world.worldGUI.Map;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;

/**
 * @author Daniel Ryszkowski
 */
public class WorldApp extends JFrame {

    private WorldImp worldImp;

    private final Map map;
    public WorldApp() throws RemoteException, AlreadyBoundException {
        worldImp = new WorldImp(this);
        creatFrame();
        map = new Map(this);
    }

    public void creatFrame(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1060,1100);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("Lab07 - world");
        this.setLayout(null);
        this.setVisible(true);
        this.setBackground(Color.GRAY);

    }

    public static void main(String[] args) throws InterruptedException, InvocationTargetException {
        SwingUtilities.invokeAndWait(() ->{

            try {
                WorldApp worldApp = new WorldApp();
            } catch (RemoteException | AlreadyBoundException e) {
                throw new RuntimeException(e);
            }

        });
    }

    public Map getMap() {
        return map;
    }
}
