package org.appo.impl;

import org.appo.Data;
import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Dialog;
import org.rspeer.runetek.api.component.Production;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.SceneObjects;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

public class WalkingBack extends Task {

    private final Position case1[] = {new Position(1938, 4966, 0), new Position(1937, 4966, 0), new Position(1937, 4965),new Position(1938, 4965),new Position(1939, 4965, 0)};
    private final Position case2[] = {new Position(1939, 4964, 0),new Position(1938, 4964, 0),new Position(1938, 4963, 0),new Position(1938, 4962, 0)};
    private final Position dispenser1 = new Position(1940,4964,0);
    private final Position dispenser2 = new Position(1939, 4963, 0);
    private final Position dispenser3 = new Position(1940,4962, 0);
    private final String DISPENSER_ACTION = "take";

    int randomNumber = Random.nextInt(0,11);
    int caseNumber = 0;

    @Override
    public boolean validate() {
        if(Data.readyToWalkToBarDispenser)
            return true;

        return false;
    }

    @Override
    public int execute() {

        Log.fine("Walking to dispenser");

        walkBack();

        return Random.nextInt(800, 1600);
    }

    private Position pickFromArray(Position position[]){
        int randomNumber = Random.nextInt(0,position.length);
        Position returnValue = new Position(1);
        for(int i=0;i<position.length;i++){
            if(i==randomNumber){
                returnValue = position[i];
            }
        }
        return returnValue;
    }

    //Uses dispenser then walks to bank
    private void useDispenser(){
        SceneObject barDispenser = SceneObjects.getNearest("bar dispenser");

        while (Players.getLocal().distance(dispenser1)!=0 && Players.getLocal().distance(dispenser2)!=0 && Players.getLocal().distance(dispenser3)!=0){
            Log.fine("sleeping distance to dispenser not 0");
            Time.sleep(200,400);
        }

        barDispenser.interact(DISPENSER_ACTION);

        Log.fine("Taking bars and walking to bank");

        while (!Production.isOpen()){
            Time.sleep(200,400);
        }

        Time.sleep(Random.nextInt(600,1000));

        Production.initiate();

        Time.sleep(Random.nextInt(700,1200));

        walkToBank();

        Time.sleep(Random.nextInt(700,1700));

    }

    //Walks from dispenser to bank
    private void walkToBank(){

        int caseNumb = 0;
        int randomNumb = Random.nextInt(0,10);

        if (randomNumb<3)
            caseNumb=0;
        else
            caseNumb=1;

        switch (caseNumb){
            case 0:
                    caseNumb=1;
            case 1:
                Movement.walkTo(Data.BANK_TILE);
                SceneObject bank = SceneObjects.getNearest("bank chest");
                bank.interact("use");
                Data.readyToBank = true;
                Data.readyToWalkToBarDispenser=false;
        }

    }




    //Walks to dispenser, uses it then walks to bank
    private void walkBack(){

        if(randomNumber>=0&&randomNumber<=1)
            caseNumber=1;
        else if(randomNumber==2)
            caseNumber=2;
        else if(randomNumber>2&&randomNumber<6)
            caseNumber=3;
        else if(randomNumber>=6&&randomNumber<=8)
            caseNumber=4;
        else
            caseNumber=5;

        int whichTile = Random.nextInt(0,2);

        switch (caseNumber){
            case 1:
                Position move1 = pickFromArray(case1);
                Movement.walkTo(move1);
                Time.sleep(Random.nextInt(600,900));


                if (whichTile==0)
                    caseNumber=3;
                else if(whichTile==1)
                    caseNumber=4;
                else
                    caseNumber=5;


            case 2:
                Position move2 = pickFromArray(case2);
                Movement.walkTo(move2);
                Time.sleep(Random.nextInt(600,900));

                if (whichTile==0)
                    caseNumber=3;
                else if(whichTile==1)
                    caseNumber=4;
                else
                    caseNumber=5;

            case 3:
                Movement.walkTo(dispenser1);
                Time.sleep(Random.nextInt(600,900));
                useDispenser();
                break;

            case 4:
                Movement.walkTo(dispenser2);
                Time.sleep(Random.nextInt(600,900));
                useDispenser();
                break;
            default:
                Movement.walkTo(dispenser3);
                Time.sleep(Random.nextInt(600,900));
                useDispenser();
                break;
        }
    }

}
