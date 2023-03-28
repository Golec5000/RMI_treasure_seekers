package pwr.daniel.ryszkowski.world.worldGUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Sector extends JPanel {
    private final String name;
    private ArrayList<Field> fields;

    public Sector(int x, int y, String name){
        this.setLocation(x,y);
        this.name = name;
        this.setSize(130,130);
        this.setBackground(new Color(70, 122, 45));
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setLayout(null);


        createFieldsForSector();
        addFieldsForSector();
    }

    public void createFieldsForSector(){
        fields = new ArrayList<>();
        char letter = 'J';
        int number = 1;
        for(int i = 0; i < 130; i+= 13){

            for(int j = 0; j < 130; j+= 13){
                fields.add(new Field(j,i,letter + "" + number));
                number++;
            }
            letter--;
            number = 1;
        }


    }

    public void addFieldsForSector(){

        for(Field field : fields) this.add(field);

    }

    public ArrayList<Field> getFields() {
        return fields;
    }

    @Override
    public String getName() {
        return name;
    }
}
