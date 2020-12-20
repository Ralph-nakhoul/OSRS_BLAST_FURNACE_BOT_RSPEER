package org.appo.impl;

import com.sun.source.tree.PrimitiveTypeTree;
import com.sun.source.tree.WhileLoopTree;
import org.appo.Data;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.commons.BankLocation;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.SceneObjects;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

public class Banking extends Task {
    @Override
    public boolean validate() {

        if(Data.readyToBank==true&&Players.getLocal().distance(Data.BANK_TILE)==0)
            return true;

        return false;
    }

    @Override
    public int execute() {

        Log.fine("Banking");

        while (!Bank.isOpen()){
            Time.sleep(300,600);
        }

        Time.sleep(600,1200);

        Bank.depositAll("steel bar");

        Time.sleep(600,1200);

        if(Inventory.getItems().length>5)
            Time.sleep(200,400);


        while(!Bank.isOpen())
            Time.sleep(200,400);

        if(Bank.isOpen()&&!Inventory.isFull())
            withdrawCoal();

        while(Inventory.getItems().length<5){
            Time.sleep(100,300);
        }

        Time.sleep(469,855);

        Bank.close();

        Time.sleep(314,661);

        Data.coalBag[0].interact("fill");
        Time.sleep(1187,1651);

        while (Inventory.getItems().length>5){
            Time.sleep(100,200);
        }

        SceneObject bank = SceneObjects.getNearest("bank chest");
        bank.interact("use");

        Time.sleep(728,1244);

        while(!Bank.isOpen())
            Time.sleep(200,400);

        if(Bank.isOpen()&&!Inventory.isFull())
            withdrawIron();

        while(Inventory.getItems().length<5){
            Time.sleep(100,300);
        }

        Time.sleep(451,749);

        Bank.close();

        Data.readyToBank = false;
        Data.coalBagFull = true;
        Data.readyToWalkToBelt=true;

        return Random.nextInt(800, 1600);
    }

    private void withdrawCoal(){
        Bank.withdrawAll("coal");
        Time.sleep(712,1114);
    }

    private void withdrawIron(){
        Bank.withdrawAll("iron ore");
        Time.sleep(712,1114);
    }

}
