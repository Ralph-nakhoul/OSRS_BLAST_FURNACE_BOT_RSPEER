package org.appo.impl;

import org.appo.Data;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.SceneObjects;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;


public class UsingBelt extends Task {

    private static final String BELT_ACTION = "put-ore-on";

    @Override
    public boolean validate() {
        return ((Data.inventoryReady() && (Players.getLocal().distance(Data.BELT_TILE)==0)));
    }

    @Override
    public int execute() {


        SceneObject conveyorBelt = SceneObjects.getNearest("conveyor belt");

        Log.fine("Using conveyor belt");

        conveyorBelt.interact(BELT_ACTION);
        Time.sleep(Random.nextInt(600,1900));

        while(Inventory.contains("iron ore"))
            Time.sleep(100,300);


        if(Data.coalBag[0]!=null){
            Log.fine("Emptying coal bag");
            Data.coalBag[0].interact("Empty");
            Time.sleep(Random.nextInt(600,1900));
        }

        Log.fine("Using conveyor belt");
        conveyorBelt.interact(BELT_ACTION);

        Time.sleep(Random.nextInt(678,939));

        while (Inventory.contains("coal"))
            Time.sleep(100,300);

        Data.readyToWalkToBarDispenser=true;
        Data.coalBagFull=false;
        Data.readyToWalkToBelt=false;

        return Random.nextInt(800, 1600);
    }

}
