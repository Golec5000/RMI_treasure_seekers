package pwr.daniel.ryszkowski.world.worldGUI;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Field extends JPanel {

    private final String name;
    private Artifact artifact;

    private boolean isExplored = false;
    public Field(int x, int y, String name){

        this.setLocation(x,y);
        this.name = name;
        this.setSize(10,10);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        creatArtifact();

        switch (artifact.getCategory()){
            case GOLD -> this.setBackground(Color.YELLOW);
            case IRON -> this.setBackground(Color.darkGray);
            case SILVER -> this.setBackground(Color.GRAY);
            case EMPTY -> this.setBackground(Color.WHITE);
            case BRONZE -> this.setBackground(Color.ORANGE);
            case OTHER -> this.setBackground(new Color(53, 32, 79));
        }

    }

    public void creatArtifact(){

        switch (new Random().nextInt(3)){

            case 0 -> artifact = new Blank(9000);
            case 1 -> artifact = new Junk(10000);
            case 2 ->{

                switch (new Random().nextInt(6)){

                    case 0 -> artifact = new Treasure(25000, Category.GOLD);
                    case 1 -> artifact = new Treasure(20000,Category.SILVER);
                    case 2 -> artifact = new Treasure(18000,Category.BRONZE);
                    case 3 -> artifact = new Treasure(15000,Category.IRON);
                    case 4 -> artifact = new Treasure(10000,Category.OTHER);
                    case 5 -> artifact = new Treasure(9000,Category.EMPTY);
                }

            }
        }


    }

    @Override
    public String getName() {
        return name;
    }

    public boolean isExplored() {
        return isExplored;
    }

    public void setExplored(boolean explored) {
        isExplored = explored;
    }

    public Artifact getArtifact() {
        return artifact;
    }
}
